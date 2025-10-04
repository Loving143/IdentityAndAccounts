package com.accounts.entity;

import java.time.LocalDateTime;

import com.accounts.enumm.AttemptType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "login_attempts", indexes = {
    @Index(name = "idx_username_attempt_type_time", columnList = "user_name,attemptType,attemptTime"),
    @Index(name = "idx_attempt_time", columnList = "attemptTime")
})
public class LoginAttempt {
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, length = 100)
    private String userName;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    private AttemptType attemptType;
    
    @Column(nullable = false, length = 45)
    private String ipAddress;
    
    @Column(length = 500)
    private String userAgent;
    
    @Column(nullable = false)
    private boolean successful;
    
    @Column(nullable = false, length = 100)
    private String failureReason;
    
    @Column(nullable = false)
    private LocalDateTime attemptTime;
   
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;
    
    public LoginAttempt() {
        this.attemptTime = LocalDateTime.now();
    }
    
    public LoginAttempt(String username, AttemptType attemptType, String ipAddress, 
                       boolean successful, String failureReason) {
        this();
        this.userName = username;
        this.attemptType = attemptType;
        this.ipAddress = ipAddress;
        this.successful = successful;
        this.failureReason = failureReason;
    }
    
    // Simple static factory methods
    public static LoginAttempt success(String username, AttemptType attemptType, String ipAddress) {
        return new LoginAttempt(username, attemptType, ipAddress, true, "SUCCESS");
    }
    
    public static LoginAttempt failure(String username, AttemptType attemptType, 
                                     String ipAddress, String failureReason) {
        return new LoginAttempt(username, attemptType, ipAddress, false, failureReason);
    }
    
    // Getters and Setters (only for essential fields)
    public Long getId() { return id; }
    
    public AttemptType getAttemptType() { return attemptType; }
    public void setAttemptType(AttemptType attemptType) { this.attemptType = attemptType; }
    
    public String getIpAddress() { return ipAddress; }
    public void setIpAddress(String ipAddress) { this.ipAddress = ipAddress; }
    
    public String getUserAgent() { return userAgent; }
    public void setUserAgent(String userAgent) { this.userAgent = userAgent; }
    
    public boolean isSuccessful() { return successful; }
    public void setSuccessful(boolean successful) { this.successful = successful; }
    
    public String getFailureReason() { return failureReason; }
    public void setFailureReason(String failureReason) { this.failureReason = failureReason; }
    
    public LocalDateTime getAttemptTime() { return attemptTime; }
    public void setAttemptTime(LocalDateTime attemptTime) { this.attemptTime = attemptTime; }

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {this.account = account;}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
}