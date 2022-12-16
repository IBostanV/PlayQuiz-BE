package com.play.quiz.service.impl;

import com.play.quiz.dto.AccountDto;
import com.play.quiz.email.EmailService;
import com.play.quiz.email.helper.EmailMessage;
import com.play.quiz.email.helper.EmailMessageFactory;
import com.play.quiz.exception.RecordNotFoundException;
import com.play.quiz.exception.UserNotFoundException;
import com.play.quiz.mapper.impl.AccountMapperImpl;
import com.play.quiz.model.Account;
import com.play.quiz.model.VerificationToken;
import com.play.quiz.repository.UserRepository;
import com.play.quiz.service.UserService;
import com.play.quiz.service.VerificationTokenService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final EmailService emailService;
    private final UserRepository userRepository;
    private final AccountMapperImpl accountMapper;
    private final EmailMessageFactory emailMessageFactory;
    private final VerificationTokenService verificationTokenService;

    @Override
    public AccountDto save(final AccountDto accountDto) {
        final Account account = accountMapper.toEntity(accountDto);
        final Account savedAccount = userRepository.save(account);
        return accountMapper.toDto(savedAccount);
    }

    @Override
    public AccountDto findByEmail(final @NonNull String userEmail) {
        log.info("Find user by email: " + userEmail);
        final Account account = userRepository.findUserByEmail(userEmail)
                .orElseThrow(() -> new UserNotFoundException("No user found with email: " + userEmail));

        return accountMapper.toDto(account);
    }

    @Override
    public boolean userExists(final AccountDto accountDto) {
        return userRepository.findUserByEmail(accountDto.getEmail()).isPresent();
    }

    @Override
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
        userRepository.save(account);
    }

    private void updateVerificationToken(VerificationToken verificationToken) {
        verificationToken.setActivationDate(LocalDateTime.now());
        verificationTokenService.save(verificationToken);
    }

    @Async
    @Override
    public void sendAccountVerificationEmail(final AccountDto accountDto) {
        final Account account = accountMapper.toEntity(accountDto);
        final VerificationToken verificationToken = verificationTokenService.createVerificationToken(account);
        final EmailMessage emailMessage = emailMessageFactory.createAccountVerificationEmailMessage(accountDto, verificationToken);

        handleEmailSending(emailMessage);
    }

    private void handleEmailSending(final EmailMessage emailMessage) {
        try { emailService.sendEmail(emailMessage); }
        catch (MessagingException exception) {
            log.warn(exception.getMessage());
        }
    }
}
