package com.play.quiz.util;

import com.play.quiz.exception.AccountDisabledException;
import com.play.quiz.exception.DuplicateUserException;
import org.springframework.util.Assert;

public abstract class SystemAssert extends Assert {
    public static void isAccountEnabled(boolean isEnabled, final String userEmail) {
        if (!isEnabled) {
            throw new AccountDisabledException("Account " + userEmail + " disabled");
        }
    }

    public static void isAccountUnique(boolean userExists, final String userEmail) {
        if (userExists) {
            throw new DuplicateUserException("User " + userEmail + " already exists");
        }
    }
}
