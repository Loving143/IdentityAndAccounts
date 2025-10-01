package com.accounts.entity;

import java.time.Instant;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "sessions") 
public class Session { 

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    private Account account;
    private String sessionToken;
    private String deviceId;
    private String ipAddress;
    private String userAgent;
    private Instant issuedAt;
    private Instant expiresAt;
    private Instant revokedAt;

    private String replacedBy;
    private Boolean isActive = true;
    
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Account getAccount() {
		return account;
	}
	public void setAccount(Account account) {
		this.account = account;
	}
	public String getSessionToken() {
		return sessionToken;
	}
	public void setSessionToken(String sessionToken) {
		this.sessionToken = sessionToken;
	}
	public String getDeviceId() {
		return deviceId;
	}
	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}
	public String getIpAddress() {
		return ipAddress;
	}
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}
	public String getUserAgent() {
		return userAgent;
	}
	public void setUserAgent(String userAgent) {
		this.userAgent = userAgent;
	}
	public Instant getIssuedAt() {
		return issuedAt;
	}
	public void setIssuedAt(Instant issuedAt) {
		this.issuedAt = issuedAt;
	}
	public Instant getExpiresAt() {
		return expiresAt;
	}
	public void setExpiresAt(Instant expiresAt) {
		this.expiresAt = expiresAt;
	}
	public Instant getRevokedAt() {
		return revokedAt;
	}
	public void setRevokedAt(Instant revokedAt) {
		this.revokedAt = revokedAt;
	}
	public String getReplacedBy() {
		return replacedBy;
	}
	public void setReplacedBy(String replacedBy) {
		this.replacedBy = replacedBy;
	}
	public Boolean getIsActive() {
		return isActive;
	}
	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}
    
}
