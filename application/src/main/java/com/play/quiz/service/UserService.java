package com.play.quiz.service;

import java.util.List;

import com.play.quiz.domain.Account;
import com.play.quiz.dto.AccountDto;
import com.play.quiz.record.PasswordInput;
import org.springframework.web.multipart.MultipartFile;

public interface UserService {

    Account save(AccountDto accountDto);

    Account save(AccountDto accountDto, MultipartFile avatar);

    Account findByEmail(String email);

    List<AccountDto> getAccountList();

    boolean userExists(AccountDto accountDto);

    void sendAccountVerificationEmail(Account accountDto);

    void activateAccount(String verificationToken);

    void changePassword(PasswordInput password);

    boolean verifyOldPassword(PasswordInput password);
}
