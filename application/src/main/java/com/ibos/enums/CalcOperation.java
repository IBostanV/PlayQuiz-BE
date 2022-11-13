package com.ibos.enums;

import lombok.Getter;

public enum CalcOperation {
    ADDITION("+"),
    SUBTRACTION("-"),
    MULTIPLY("*"),
    DIVIDE("/");

    @Getter
    private String operation;

    CalcOperation(String operation) {
        this.operation = operation;
    }
}
