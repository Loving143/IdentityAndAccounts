package com.accounts.service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.accounts.entity.LoginAttempt;
import com.accounts.entity.TokenBucketConfig;
import com.accounts.enumm.AttemptType;
import com.accounts.helper.RateLimitInfo;
import com.accounts.helper.RateLimitResult;
import com.accounts.repository.LoginAttemptRepository;

@Service
@Transactional
public class TokenBucketRateLimitService {
    
    @Autowired
    private LoginAttemptRepository loginAttemptRepository;
    
    // Default configurations for different attempt types
    private final Map<AttemptType, TokenBucketConfig> defaultConfigs = createDefaultConfigs();
    
    private Map<AttemptType, TokenBucketConfig> createDefaultConfigs() {
        Map<AttemptType, TokenBucketConfig> configs = new HashMap<>();
        
        // 5 attempts per 15 minutes (5 tokens, refill 0.333 tokens/min)
        configs.put(AttemptType.PASSWORD_CHANGE, new TokenBucketConfig(5, 0.333, 1,AttemptType.PASSWORD_CHANGE));
        
        // 10 login attempts per 30 minutes (10 tokens, refill 0.333 tokens/min)  
        configs.put(AttemptType.LOGIN, new TokenBucketConfig(10, 0.333, 1,AttemptType.LOGIN));
        
        // 3 password reset attempts per hour (3 tokens, refill 0.05 tokens/min)
        configs.put(AttemptType.RESET_PASSWORD, new TokenBucketConfig(3, 0.05, 1,AttemptType.RESET_PASSWORD));
        
        return configs;
    }
    
    public RateLimitResult checkRateLimit(String username, AttemptType attemptType) {
        TokenBucketConfig config = defaultConfigs.get(attemptType);
        
        if (config == null) {
            // If no config found for this attempt type, allow the request
            return RateLimitResult.allowed(1, 0);
        }
        
        // Calculate current tokens based on attempt history
        double currentTokens = calculateCurrentTokens(username, attemptType, config);
        
        if (currentTokens >= config.getTokensPerRequest()) {
            return RateLimitResult.allowed(config.getTokensPerRequest(), 0);
        } else {
            long waitTimeMs = calculateWaitTime(currentTokens, config);
            return RateLimitResult.rejected(config.getTokensPerRequest(), waitTimeMs);
        }
    }
    
    private double calculateCurrentTokens(String username, AttemptType attemptType, 
                                        TokenBucketConfig config) {
        // Calculate the time window based on capacity and refill rate
        long windowMinutes = (long) Math.ceil(config.getCapacity() / config.getRefillRate());
        LocalDateTime windowStart = LocalDateTime.now().minusMinutes(windowMinutes);
        
        // Get all attempts in the calculated window
        List<LoginAttempt> attempts = loginAttemptRepository.findAttemptsForTokenBucket(
            username, attemptType, windowStart);
        
        // Start with full bucket capacity
        double tokens = config.getCapacity();
        LocalDateTime lastRefillTime = windowStart;
        
        // Process each attempt to calculate token consumption and refill
        for (LoginAttempt attempt : attempts) {
            // Refill tokens based on time elapsed since last event
        	//// STEP 1: Pichle attempt se is attempt tak kitne tokens refill hue?
            tokens = refillTokens(tokens, lastRefillTime, attempt.getAttemptTime(), config);
            
            // Consume tokens for this attempt (usually 1 token per attempt)
            // STEP 2: Is attempt ke liye tokens deduct karo
            tokens -= config.getTokensPerRequest();
            
            // Update last refill time to this attempt's time
            lastRefillTime = attempt.getAttemptTime();
        }
        
        // Final refill from last attempt to current time
        tokens = refillTokens(tokens, lastRefillTime, LocalDateTime.now(), config);
        
        // Ensure we don't go below zero
        return Math.max(0, tokens);
    }
    
    private double refillTokens(double currentTokens, LocalDateTime from, 
                               LocalDateTime to, TokenBucketConfig config) {
        long minutesPassed = Duration.between(from, to).toMinutes();
        if (minutesPassed <= 0) {
            return currentTokens;
        }
        
        double tokensToAdd = minutesPassed * config.getRefillRate();
        return Math.min(config.getCapacity(), currentTokens + tokensToAdd);
    }
    
    private long calculateWaitTime(double currentTokens, TokenBucketConfig config) {
        double tokensNeeded = config.getTokensPerRequest() - currentTokens;
        double minutesToWait = tokensNeeded / config.getRefillRate();
        return (long) (minutesToWait * 60 * 1000); // Convert to milliseconds
    }
    
    public RateLimitInfo getRateLimitInfo(String username, AttemptType attemptType) {
        TokenBucketConfig config = defaultConfigs.get(attemptType);
        if (config == null) {
            return null;
        }
        
        double currentTokens = calculateCurrentTokens(username, attemptType, config);
        long windowMinutes = (long) Math.ceil(config.getCapacity() / config.getRefillRate());
        LocalDateTime windowStart = LocalDateTime.now().minusMinutes(windowMinutes);
        
        long recentAttempts = loginAttemptRepository.countRecentAttemptsByUsername(
            username, attemptType, windowStart);
        
        return new RateLimitInfo(currentTokens, config, recentAttempts, windowMinutes);
    }
}



