package com.accounts.response;

public class AccountResponse {

    private String username;       // unique username or email
    private String fullName;       // optional: display name
    private String email;          // for notifications or 2FA
    private String phoneNumber;    // for OTP or 2FA if enabled
    private Boolean active;        // is account active
    private Boolean locked;        // is account locked
    private String role;           // ROLE_USER, ROLE_ADMIN, etc.
    // Optional Security Metadata
    private Boolean twoFactorEnabled; // if 2FA is enabled for this user
    private String preferredAuth;     // e.g., "PASSWORD", "OTP", "GOOGLE"
	private boolean validToken;
    public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public Boolean getActive() {
		return active;
	}
	public void setActive(Boolean active) {
		this.active = active;
	}
	public Boolean getLocked() {
		return locked;
	}
	public void setLocked(Boolean locked) {
		this.locked = locked;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public Boolean getTwoFactorEnabled() {
		return twoFactorEnabled;
	}
	public void setTwoFactorEnabled(Boolean twoFactorEnabled) {
		this.twoFactorEnabled = twoFactorEnabled;
	}
	public String getPreferredAuth() {
		return preferredAuth;
	}
	public void setPreferredAuth(String preferredAuth) {
		this.preferredAuth = preferredAuth;
	}
	public boolean isValidToken() {
		return validToken;
	}
	public void setValidToken(boolean validToken) {
		this.validToken = validToken;
	}

}
