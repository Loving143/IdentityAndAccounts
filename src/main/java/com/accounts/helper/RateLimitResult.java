package com.accounts.helper;

public class RateLimitResult {
    private final boolean allowed;
    private final int tokensConsumed;
    private final long waitTimeMs;
    
    private RateLimitResult(boolean allowed, int tokensConsumed, long waitTimeMs) {
        this.allowed = allowed;
        this.tokensConsumed = tokensConsumed;
        this.waitTimeMs = waitTimeMs;
    }
    
    public static RateLimitResult allowed(int tokensConsumed, long waitTimeMs) {
        return new RateLimitResult(true, tokensConsumed, waitTimeMs);
    }
    
    public static RateLimitResult rejected(int tokensRequested, long waitTimeMs) {
        return new RateLimitResult(false, 0, waitTimeMs);
    }
    
    // Getters
    public boolean isAllowed() { return allowed; }
    public int getTokensConsumed() { return tokensConsumed; }
    public long getWaitTimeMs() { return waitTimeMs; }
}