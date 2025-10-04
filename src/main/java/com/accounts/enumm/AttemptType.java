package com.accounts.enumm;

public enum AttemptType {
    LOGIN("User login attempt"),
    PASSWORD_CHANGE("Password change attempt"),
    RESET_PASSWORD("Password reset attempt"),
    TWO_FACTOR_VERIFICATION("Two-factor authentication"),
    ACCOUNT_RECOVERY("Account recovery attempt"),
    EMAIL_VERIFICATION("Email verification attempt"),
    PHONE_VERIFICATION("Phone verification attempt"),
    SECURITY_QUESTION_ANSWER("Security question attempt");
    
    private final String description;
    
    AttemptType(String description) {
        this.description = description;
    }
    
    public String getDescription() {
        return description;
    }
}