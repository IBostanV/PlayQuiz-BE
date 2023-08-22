package com.play.quiz.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import com.play.quiz.aop.annotation.Conditional;
import com.play.quiz.dto.AccountDto;
import com.play.quiz.email.EmailService;
import com.play.quiz.email.helper.EmailMessage;
import com.play.quiz.email.helper.EmailMessageFactory;
import com.play.quiz.exception.EmailSendFailedException;
import com.play.quiz.exception.RecordNotFoundException;
import com.play.quiz.exception.UserNotFoundException;
import com.play.quiz.mapper.AccountMapper;
import com.play.quiz.model.Account;
import com.play.quiz.model.VerificationToken;
import com.play.quiz.repository.UserRepository;
import com.play.quiz.service.UserService;
import com.play.quiz.service.VerificationTokenService;
import jakarta.mail.MessagingException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final EmailService emailService;
    private final UserRepository userRepository;
    private final AccountMapper accountMapper;
    private final EmailMessageFactory emailMessageFactory;
    private final VerificationTokenService verificationTokenService;

    @Override
    @Transactional
    public Account save(final AccountDto accountDto) {
        final Account account = accountMapper.toEntity(accountDto);
        return userRepository.save(account);
    }

    @Override
    @Transactional
    public Account findByEmail(final @NonNull String userEmail) {
        log.debug("Find user by email: " + userEmail);
        return userRepository.findUserByEmail(userEmail)
                .orElseThrow(() -> new UserNotFoundException("No user found with email: " + userEmail));
    }

    @Override
    public List<AccountDto> getAccountList() {
        List<Account> accounts = userRepository.findAll();
        return accountMapper.toDtoList(accounts);
    }

    @Override
    @Transactional
    public boolean userExists(final AccountDto accountDto) {
        return userRepository.findUserByEmail(accountDto.getEmail()).isPresent();
    }

    @Override
    @Transactional
    public void activateAccount(final @NonNull String token) {
        final VerificationToken verificationToken = verificationTokenService.findByToken(token)
                .orElseThrow(() -> new RecordNotFoundException("No records found for token: " + token));
        handleAccountActivation(verificationToken);
    }

    private void handleAccountActivation(final VerificationToken verificationToken) {
        enableAccount(verificationToken.getAccount());
        updateVerificationToken(verificationToken);
    }

    private void enableAccount(final Account account) {
        account.enable();
        userRepository.enableAccount(account.getAccountId());
    }

    private void updateVerificationToken(final VerificationToken verificationToken) {
        verificationToken.setActivationDate(LocalDateTime.now());
        verificationTokenService.save(verificationToken);
    }

    @Async
    @Override
    @Conditional(property = "application.email.sending.enabled", value = "true", matchIfMissing = true)
    public void sendAccountVerificationEmail(final Account account) {
        VerificationToken verificationToken = verificationTokenService.createVerificationToken(account);
        EmailMessage emailMessage = emailMessageFactory.createAccountVerificationEmailMessage(account, verificationToken);
        handleEmailSending(emailMessage);
    }

    private void handleEmailSending(final EmailMessage emailMessage) {
        try {
            log.debug("Sending email to: "+ emailMessage.getTo());
            emailService.sendEmail(emailMessage);
        }
        catch (MessagingException exception) {
            log.warn(exception.getMessage());
            throw new EmailSendFailedException(exception.getMessage());
        }
    }
}
