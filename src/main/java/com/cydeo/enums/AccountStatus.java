package com.cydeo.enums;

public enum AccountStatus {
    ACTIVE("Active"),
    DELETED("Deleted");

    private final String value;

    AccountStatus(String value) {
        this.value = value;
    }

    public String getValue(){
        return this.value;
    }
}
