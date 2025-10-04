package com.accounts.entity;

import java.time.Duration;
import java.time.LocalDateTime;

import com.accounts.enumm.AttemptType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;

@Entity
@Table(name = "token_bucket_configs")
public class TokenBucketConfig {
    @Id
    @Enumerated(EnumType.STRING)
    @Column(length = 50)
    private AttemptType attemptType;
    
    @Column(nullable = false)
    private int capacity; // Maximum tokens in bucket
    
    @Column(nullable = false)
    private double refillRate; // Tokens per minute
    
    @Column(nullable = false)
    private int tokensPerRequest; // Tokens consumed per attempt
    
    @Column(nullable = false, length = 255)
    private String description;
    
    @Column(nullable = false)
    private boolean enabled = true;
    
    @Column(nullable = false)
    private int burstAllowance; // Additional burst capacity (percentage)
    
    @Column(nullable = false)
    private boolean notifyOnLimitExceeded = true;
    
    @Column(nullable = false)
    private LocalDateTime createdAt;
    
    @Column(nullable = false)
    private LocalDateTime updatedAt;
    
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }
    
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
    
    // Constructors
    public TokenBucketConfig() {}
    
    public TokenBucketConfig(AttemptType attemptType, int capacity, double refillRate, 
                           int tokensPerRequest, String description) {
        this.attemptType = attemptType;
        this.capacity = capacity;
        this.refillRate = refillRate;
        this.tokensPerRequest = tokensPerRequest;
        this.description = description;
        this.burstAllowance = 20; // Default 20% burst allowance
    }
    
    public TokenBucketConfig(int capacity, double refillRate, int tokenPerRequest ,AttemptType attemptType) {
    	this.capacity = capacity;
    	this.refillRate = refillRate;
    	this.tokensPerRequest = tokenPerRequest;
    	this.attemptType = attemptType;
	}

	// Getters and Setters
    public AttemptType getAttemptType() { return attemptType; }
    public void setAttemptType(AttemptType attemptType) { this.attemptType = attemptType; }
    
    public int getCapacity() { return capacity; }
    public void setCapacity(int capacity) { this.capacity = capacity; }
    
    public double getRefillRate() { return refillRate; }
    public void setRefillRate(double refillRate) { this.refillRate = refillRate; }
    
    public int getTokensPerRequest() { return tokensPerRequest; }
    public void setTokensPerRequest(int tokensPerRequest) { this.tokensPerRequest = tokensPerRequest; }
    
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    
    public boolean isEnabled() { return enabled; }
    public void setEnabled(boolean enabled) { this.enabled = enabled; }
    
    public int getBurstAllowance() { return burstAllowance; }
    public void setBurstAllowance(int burstAllowance) { this.burstAllowance = burstAllowance; }
    
    public boolean isNotifyOnLimitExceeded() { return notifyOnLimitExceeded; }
    public void setNotifyOnLimitExceeded(boolean notifyOnLimitExceeded) { this.notifyOnLimitExceeded = notifyOnLimitExceeded; }
    
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
    
    // Utility methods
    public double getEffectiveCapacity() {
        return capacity * (1 + burstAllowance / 100.0);
    }
    
    public long getTimeWindowMinutes() {
        return (long) Math.ceil(capacity / refillRate);
    }
    
    public Duration getTimeWindow() {
        return Duration.ofMinutes(getTimeWindowMinutes());
    }
}