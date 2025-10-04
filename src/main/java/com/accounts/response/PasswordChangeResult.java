package com.accounts.response;

public class PasswordChangeResult {
    private final boolean success;
    private final String message;
    private final String errorCode;
    private final Long waitTimeMs;
    
    // Private constructor
    private PasswordChangeResult(boolean success, String message, String errorCode, Long waitTimeMs) {
        this.success = success;
        this.message = message;
        this.errorCode = errorCode;
        this.waitTimeMs = waitTimeMs;
    }
    
    // Success case - static factory method
    public static PasswordChangeResult success() {
        return new PasswordChangeResult(true, "Password changed successfully", null, null);
    }
    
    // Failure cases - static factory methods
    public static PasswordChangeResult rateLimited(Long waitTimeMs) {
        return new PasswordChangeResult(false, "Too many attempts. Please try again later.", 
                                      "RATE_LIMITED", waitTimeMs);
    }
    
    public static PasswordChangeResult invalidCurrentPassword() {
        return new PasswordChangeResult(false, "Current password is incorrect", 
                                      "INVALID_CURRENT_PASSWORD", null);
    }
    
    public static PasswordChangeResult userNotFound() {
        return new PasswordChangeResult(false, "User account not found", 
                                      "USER_NOT_FOUND", null);
    }
    
    public static PasswordChangeResult weakPassword() {
        return new PasswordChangeResult(false, "New password does not meet strength requirements", 
                                      "WEAK_PASSWORD", null);
    }
    
    // Getters
    public boolean isSuccess() { return success; }
    public String getMessage() { return message; }
    public String getErrorCode() { return errorCode; }
    public Long getWaitTimeMs() { return waitTimeMs; }
}