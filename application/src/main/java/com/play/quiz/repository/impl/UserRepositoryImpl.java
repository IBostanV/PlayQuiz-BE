package com.play.quiz.repository.impl;

import static java.math.BigDecimal.ONE;
import static java.util.stream.Collectors.groupingBy;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
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

import com.play.quiz.domain.Account;
import com.play.quiz.domain.Language;
import com.play.quiz.domain.Role;
import com.play.quiz.enums.UserRole;
import com.play.quiz.exception.UserNotFoundException;
import com.play.quiz.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
@AllArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    private final String findAllSql;
    private final String saveSql;
    private final String saveUserRoles;
    private final String findByEmailSql;
    private final String saveNoAvatarSql;
    private final String findUserRolesSql;
    private final JdbcTemplate jdbcTemplate;
    private final String activateAccountSql;
    private final String userSequenceNextVal;
    private final String updateUserPasswordSql;
    private final NamedParameterJdbcTemplate namedJdbcTemplate;

    public static final String EMAIL = "email";
    public static final String ACCOUNT_ID = "accountId";

    @Override
    public Account save(final Account account) {
        return Objects.nonNull(account.getAccountId())
                ? handleUpdate(account)
                : handleInsert(account);
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
        namedJdbcTemplate.update(
                Objects.nonNull(account.getAvatar()) ? saveSql : saveNoAvatarSql,
                getProperties(accountId, account));

        if (Objects.isNull(account.getAccountId())) {
            account.getRoles().forEach(role -> namedJdbcTemplate.update(saveUserRoles,
                    Map.of(ACCOUNT_ID, accountId, "roleId", role.getRoleId())));
        }

        log.info("User with id: " + accountId + " successfully saved");
        return account.toBuilder()
                .accountId(accountId)
                .build();
    }

    private Map<String, Object> getProperties(final Long accountId, final Account account) {
        Map<String, Object> propertyMap = new HashMap<>();
        propertyMap.put(ACCOUNT_ID, accountId);
        propertyMap.put(EMAIL, account.getEmail());
        propertyMap.put("avatar", account.getAvatar());
        propertyMap.put("isEnabled", account.isEnabled());
        propertyMap.put("name", mapNull().apply(account.getName()));
        propertyMap.put("theme", mapNull().apply(account.getTheme()));
        propertyMap.put("surname", mapNull().apply(account.getSurname()));
        propertyMap.put("birthday", mapNull().apply(account.getBirthday()));
        propertyMap.put("username", mapNull().apply(account.getUsername()));

        propertyMap.put("language", Optional.ofNullable(account.getLanguage())
                .map(Language::getLangId)
                .orElse(null));

        propertyMap.put("password", Objects.nonNull(account.getPassword())
                ? String.copyValueOf(account.getPassword()) : null);
        return propertyMap;
    }

    private Function<Object, Object> mapNull() {
        return input -> Objects.nonNull(input) ? input : "";
    }

    @Override
    public Optional<Account> findUserByEmail(String email) {
        return executeQueryForObject(email)
                .map(this::setAccountRoles);
    }

    private Account setAccountRoles(final Account account) {
        List<Role> roles = handleRoles(account);
        log.info("Found roles: " + roles + " for user: " + account.getEmail());
        account.setRoles(roles);
        return account;
    }

    private Optional<Account> executeQueryForObject(String email) {
        try {
            AccountRowMapper accountRowMapper = new AccountRowMapper();
            Account account = namedJdbcTemplate.queryForObject(findByEmailSql, Map.of(EMAIL, email), accountRowMapper);
            log.info("Found user with email: " + email);

            return Optional.ofNullable(account);
        } catch (EmptyResultDataAccessException exception) {
            log.info("No user found with email: " + email);
            return Optional.empty();
        }
    }

    private List<Role> handleRoles(final Account account) {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource(ACCOUNT_ID, account.getAccountId());
        List<Map<String, Object>> rolesMap = namedJdbcTemplate.queryForList(findUserRolesSql, parameterSource);
        return getRoleList(rolesMap);
    }

    private static List<Role> getRoleList(final List<Map<String, Object>> rolesMap) {
        return rolesMap.stream()
                .map(UserRepositoryImpl::mapRole)
                .toList();
    }

    private static Role mapRole(final Map<String, Object> item) {
        return Role.builder()
                .roleId(((BigDecimal) item.get("ROLE_ID")).longValue())
                .name(UserRole.valueOf((String) item.get("NAME")))
                .build();
    }

    @Override
    public List<Account> findAll() {
        List<Map<String, Object>> accountMap = jdbcTemplate.queryForList(findAllSql);
        return mapResultSetRows(accountMap);
    }

    @Override
    public void enableAccount(final Long accountId) {
        namedJdbcTemplate.update(activateAccountSql, Map.of(ACCOUNT_ID, accountId));
    }

    @Override
    public int updateUserPassword(String userEmail, char[] password) {
        return namedJdbcTemplate.update(updateUserPasswordSql,
                Map.of(EMAIL, userEmail,
                        "password", String.copyValueOf((char[]) mapNull().apply(password))));
    }

    private List<Account> mapResultSetRows(final List<Map<String, Object>> accountMap) {
        return accountMap.stream()
                .map(UserRepositoryImpl::mapAccount)
                .collect(groupingBy(Account::getAccountId))
                .values().stream()
                .map(UserRepositoryImpl::getAccount)
                .toList();
    }

    private static Account mapAccount(final Map<String, Object> accountRow) {
        return Account.builder()
                .birthday(getBirthday(accountRow))
                .name((String) accountRow.get("NAME"))
                .email((String) accountRow.get(EMAIL.toUpperCase()))
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
                .toList();
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

    private static class AccountRowMapper implements RowMapper<Account> {

        @Override
        public Account mapRow(ResultSet resultSet, int rowNum) throws SQLException {
            BeanPropertyRowMapper<Account> rowMapper = new BeanPropertyRowMapper<>(Account.class);
            Language language = new Language(resultSet.getLong("lang_id"),
                    resultSet.getString("lang_code"),
                    resultSet.getString("lang_name"));

            return Optional.ofNullable(rowMapper.mapRow(resultSet, rowNum))
                    .orElseThrow(() -> new UserNotFoundException("No user found for the given email"))
                    .toBuilder().language(language).build();
        }
    }
}
