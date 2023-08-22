package com.play.quiz.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Constant {
    public static final int SAVE_OPERATION_SUCCESSFULLY = 1;

    public static final String EXPRESS = "EXPRESS";
    public static final String ENCRYPTION_SECRET_KEY = "ENCRYPTION_SECRET_KEY";
    public static final String ENCRYPTION_ALGORITHM = "ENCRYPTION_ALGORITHM";
    public static final String DEFAULT_EXPRESS_QUESTIONS_COUNT = "DEFAULT_EXPRESS_QUESTIONS_COUNT";
    public static final String EXPRESS_QUIZ_DEFAULT_ANSWER_COUNT = "EXPRESS_QUIZ_DEFAULT_ANSWER_COUNT";
    public static final String RIGHT_ANSWER_PREFIX = "A_";
    public static final String WRONG_ANSWER_PREFIX = "Z_";
}
