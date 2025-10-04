package com.accounts.entity;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import com.accounts.dto.AccountDto;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "accounts")
public class Account {

    @Id
    @GeneratedValue
    private Integer id;   // Primary Key

    @Column(unique = true, length = 255)
    private String email;

    @Column(unique = true, length = 20)
    private String phone;

    @Column(name = "password_hash", nullable = false, length = 255)
    private String passwordHash;

    @Column(name = "verified_email", nullable = false)
    private boolean verifiedEmail = false;

    @Column(name = "verified_phone", nullable = false)
    private boolean verifiedPhone = false;
    
    @OneToMany(mappedBy="account",cascade=CascadeType.ALL,orphanRemoval=true)
    private List<Device>devices;
    
    @OneToMany(mappedBy="account",cascade=CascadeType.ALL,orphanRemoval=true)
    private List<Session>sessions;
    
    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Auditlog> auditLogs = new ArrayList<>();
    
    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<VerificationToken> verificationToken = new ArrayList<>();
    
    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PasswordResetToken> passwordResetToken = new ArrayList<>();
    
    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RefreshToken> refreshToken = new ArrayList<>();
    
    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<LoginAttempt> loginAttempts = new ArrayList<>();
    
    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserRole> userRole = new ArrayList<>();
    @Column(length = 20, nullable = false)
    
    private String status = "ACTIVE"; // ACTIVE, SUSPENDED, DELETED

    @Column(name = "lockout_until")
    private Instant lockoutUntil;

    @Column(name = "created_at", updatable = false)
    private Instant createdAt = Instant.now();

    @Column(name = "updated_at")
    private Instant updatedAt = Instant.now();

    @Column(name = "last_login_at")
    private Instant lastLoginAt;

    @Column(name = "global_logout_at")
    private Instant globalLogoutAt;

    @Column(name = "delete_at")
    private Instant deleteAt;

    // --- Getters & Setters ---

    public Account(AccountDto account) {
    	this.email = account.getUserName();
    }
	public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPasswordHash() {
        return passwordHash;
    }
    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public boolean isVerifiedEmail() {
        return verifiedEmail;
    }
    public void setVerifiedEmail(boolean verifiedEmail) {
        this.verifiedEmail = verifiedEmail;
    }

    public boolean isVerifiedPhone() {
        return verifiedPhone;
    }
    public void setVerifiedPhone(boolean verifiedPhone) {
        this.verifiedPhone = verifiedPhone;
    }

    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }

    public Instant getLockoutUntil() {
        return lockoutUntil;
    }
    public void setLockoutUntil(Instant lockoutUntil) {
        this.lockoutUntil = lockoutUntil;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }
    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Instant getLastLoginAt() {
        return lastLoginAt;
    }
    public void setLastLoginAt(Instant lastLoginAt) {
        this.lastLoginAt = lastLoginAt;
    }

    public Instant getGlobalLogoutAt() {
        return globalLogoutAt;
    }
    public void setGlobalLogoutAt(Instant globalLogoutAt) {
        this.globalLogoutAt = globalLogoutAt;
    }

    public List<Device> getDevices() {
		return devices;
	}
	public void setDevices(List<Device> devices) {
		this.devices = devices;
	}
	public List<Session> getSessions() {
		return sessions;
	}
	public void setSessions(List<Session> sessions) {
		this.sessions = sessions;
	}
	public Instant getDeleteAt() {
        return deleteAt;
    }
    public void setDeleteAt(Instant deleteAt) {
        this.deleteAt = deleteAt;
    }
	public List<Auditlog> getAuditLogs() {
		return auditLogs;
	}
	
	public void setAuditLogs(List<Auditlog> auditLogs) {
		this.auditLogs = auditLogs;
	}
	
	public List<VerificationToken> getVerificationToken() {
		return verificationToken;
	}
	
	public void setVerificationToken(List<VerificationToken> verificationToken) {
		this.verificationToken = verificationToken;
	}
	
	public List<PasswordResetToken> getPasswordResetToken() {
		return passwordResetToken;
	}
	
	public void setPasswordResetToken(List<PasswordResetToken> passwordResetToken) {
		this.passwordResetToken = passwordResetToken;
	}
	
	public List<RefreshToken> getRefreshToken() {
		return refreshToken;
	}
	
	public void setRefreshToken(List<RefreshToken> refreshToken) {
		this.refreshToken = refreshToken;
	}
	
	public List<UserRole> getUserRole() {
		return userRole;
	}
	public void setUserRole(List<UserRole> userRole) {
		this.userRole = userRole;
	}
	
	public Account() {
		super();
	}
	
}
