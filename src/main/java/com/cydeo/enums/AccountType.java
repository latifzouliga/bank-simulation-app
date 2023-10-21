package com.cydeo.enums;

public enum AccountType {
    CHECKING("Checking"),
    SAVING("Saving");

    private final String value;

    AccountType(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}
