package com.play.quiz.aop;

import java.time.LocalDateTime;
import java.util.Objects;

import com.play.quiz.domain.Account;
import com.play.quiz.domain.helpers.BaseEntity;
import com.play.quiz.security.AuthenticationFacade;
import com.play.quiz.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

@Log4j2
@Aspect
@Component
@RequiredArgsConstructor
public class BaseEntityPreSaveAspect {

    private final AuthenticationFacade authenticationFacade;
    private final UserService userService;

    @Around("execution(* com.play.quiz.repository.*.save(..))")
    public Object beforeSaveEntity(final ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        Object entityArg = proceedingJoinPoint.getArgs()[0];

        if (entityArg instanceof BaseEntity entity) {
            LocalDateTime now = LocalDateTime.now();
            Account currentAccount = getCurrentAccount();

            if (Objects.isNull(entity.getId())) {
                entity.setCreatedDate(now);
                entity.setCreatedBy(currentAccount);
            } else {
                entity.setUpdatedDate(now);
                entity.setUpdatedBy(currentAccount);
            }
        }

        return proceedingJoinPoint.proceed();
    }

    private Account getCurrentAccount() {
        try {
            User principal = authenticationFacade.getPrincipal();
            return userService.findByEmail(principal.getUsername());
        } catch(Exception exception) {
            log.debug("User being registered, no authentication available, {}", exception.getMessage());
            return null;
        }
    }
}
