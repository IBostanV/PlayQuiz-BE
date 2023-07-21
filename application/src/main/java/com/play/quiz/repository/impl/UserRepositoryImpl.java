package com.play.quiz.repository.impl;

import com.play.quiz.enums.UserRole;
import com.play.quiz.model.Account;
import com.play.quiz.model.Role;
import com.play.quiz.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.math.BigDecimal.ONE;
import static java.util.stream.Collectors.groupingBy;

@Slf4j
@Repository
@AllArgsConstructor
public class UserRepositoryImpl implements UserRepository {
    private final String saveUserSql;
    private final String saveUserRoles;
    private final String findAllUsersSql;
    private final String findUserRolesSql;
    private final String findUserByEmailSql;
    private final JdbcTemplate jdbcTemplate;
    private final String activateAccountSql;
    private final String userSequenceNextVal;
    private final NamedParameterJdbcTemplate namedJdbcTemplate;

    @Override
    public Account save(final Account account) {
        return Objects.nonNull(account.getAccountId()) ? handleUpdate(account) : handleInsert(account);
    }

    private Account handleInsert(final Account account) {
        final Long nextUserId = jdbcTemplate.queryForObject(userSequenceNextVal, Long.class);
        log.info("Execute INSERT USER with id: " + nextUserId);
        return executeSave(nextUserId, account);
    }

    private Account handleUpdate(final Account account) {
        log.info("Execute UPDATE USER with id: " + account.getAccountId());
        return executeSave(account.getAccountId(), account);
    }

    private Account executeSave(final Long accountId, final Account account) {
        namedJdbcTemplate.update(saveUserSql, getProperties(accountId, account));
        account.getRoles().forEach(role -> namedJdbcTemplate.update(saveUserRoles,
                Map.of("accountId", accountId, "roleId", role.getRoleId())));
        log.info("User with id: " + accountId + " successfully saved");
        return new Account(account, accountId);
    }

    private Map<String, Object> getProperties(final Long accountId, final Account account) {
        Map<String, Object> propertyMap = new HashMap<>();
        propertyMap.put("accountId", accountId);
        propertyMap.put("email", account.getEmail());
        propertyMap.put("isEnabled", account.isEnabled());
        propertyMap.put("password", String.valueOf(mapNull().apply(account.getPassword())));
        propertyMap.put("name", mapNull().apply(account.getName()));
        propertyMap.put("birthday", mapNull().apply(account.getBirthday()));
        return propertyMap;
    }

    private Function<Object, Object> mapNull() {
        return input -> Objects.nonNull(input) ? input : "";
    }

    @Override
    public Optional<Account> findUserByEmail(final String email) {
        return executeQueryForObject(email).map(this::setAccountRoles);
    }

    private Account setAccountRoles(final Account account) {
        List<Role> roles = handleRoles(account);
        log.info("Found roles: " + roles + " for user: " + account.getEmail());
        account.setRoles(roles);
        return account;
    }

    private Optional<Account> executeQueryForObject(final String email) {
        try {
            BeanPropertyRowMapper<Account> rowMapper = new BeanPropertyRowMapper<>(Account.class);
            Account account = namedJdbcTemplate.queryForObject(findUserByEmailSql, Map.of("email", email), rowMapper);
            log.info("Found user with email: " + email);

            return Optional.ofNullable(account);
        } catch (EmptyResultDataAccessException exception) {
            log.info("No user found with email: " + email);
            return Optional.empty();
        }
    }

    private List<Role> handleRoles(final Account account) {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource("accountId", account.getAccountId());
        List<Map<String, Object>> rolesMap = namedJdbcTemplate.queryForList(findUserRolesSql, parameterSource);
        return getRoleList(rolesMap);
    }

    private static List<Role> getRoleList(final List<Map<String, Object>> rolesMap) {
        return rolesMap.stream()
                .map(UserRepositoryImpl::mapRole)
                .collect(Collectors.toList());
    }

    private static Role mapRole(final Map<String, Object> item) {
        return Role.builder()
                .roleId(((BigDecimal) item.get("ROLE_ID")).longValue())
                .name(UserRole.valueOf((String) item.get("NAME")))
                .build();
    }

    @Override
    public List<Account> findAll() {
        List<Map<String, Object>> accountMap = jdbcTemplate.queryForList(findAllUsersSql);
        return mapResultSetRows(accountMap);
    }

    @Override
    public void enableAccount(final Long accountId) {
        namedJdbcTemplate.update(activateAccountSql, Map.of("accountId", accountId));
    }

    private List<Account> mapResultSetRows(final List<Map<String, Object>> accountMap) {
        return accountMap.stream()
                .map(UserRepositoryImpl::mapAccount)
                .collect(groupingBy(Account::getAccountId))
                .values().stream()
                .map(UserRepositoryImpl::getAccount)
                .collect(Collectors.toList());
    }

    private static Account mapAccount(final Map<String, Object> accountRow) {
        return Account.builder()
                .birthday(getBirthday(accountRow))
                .name((String) accountRow.get("NAME"))
                .email((String) accountRow.get("EMAIL"))
                .isEnabled(ONE.equals((accountRow.get("IS_ENABLED"))))
                .accountId(((BigDecimal) accountRow.get("ACCOUNT_ID")).longValue())
                .roles(Collections.singletonList(getUserRole(accountRow)))
                .build();
    }

    private static Account getAccount(final List<Account> accountGroup) {
        return accountGroup.stream()
                .map(account -> {
                    account.setRoles(getRoles(accountGroup));
                    return account;
                })
                .findFirst()
                .orElse(new Account());
    }

    private static List<Role> getRoles(final List<Account> accountGroup) {
        return accountGroup.stream()
                .flatMap(account -> account.getRoles().stream())
                .collect(Collectors.toList());
    }

    private static LocalDate getBirthday(final Map<String, Object> accountRow) {
        return Optional.ofNullable(((Timestamp) accountRow.get("BIRTHDAY")))
                .map(Timestamp::toLocalDateTime)
                .map(LocalDateTime::toLocalDate)
                .orElse(null);
    }

    private static Role getUserRole(final Map<String, Object> accountRow) {
        return Role.builder()
                .roleId(((BigDecimal) accountRow.get("ROLE_ID")).longValue())
                .name(UserRole.valueOf(accountRow.get("ROLE_NAME").toString()))
                .build();
    }
}
