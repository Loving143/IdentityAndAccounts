package com.accounts.helper;

import java.time.LocalDateTime;

import com.accounts.entity.TokenBucketConfig;

public class RateLimitInfo {
	
    private final double currentTokens;
    private final TokenBucketConfig config;
    private final long recentAttempts;
    private final long windowMinutes;
    private final LocalDateTime calculatedAt;
    
    public RateLimitInfo(double currentTokens, TokenBucketConfig config, 
                        long recentAttempts, long windowMinutes) {
        this.currentTokens = currentTokens;
        this.config = config;
        this.recentAttempts = recentAttempts;
        this.windowMinutes = windowMinutes;
        this.calculatedAt = LocalDateTime.now();
    }
    
    // Getters
    public double getCurrentTokens() { return Math.round(currentTokens * 100.0) / 100.0; }
    public TokenBucketConfig getConfig() { return config; }
    public long getRecentAttempts() { return recentAttempts; }
    public long getWindowMinutes() { return windowMinutes; }
    public LocalDateTime getCalculatedAt() { return calculatedAt; }
    
    public double getTokensUntilFull() {
        return Math.round((config.getCapacity() - currentTokens) * 100.0) / 100.0;
    }
}