package com.accounts.embeddable;

import java.io.Serializable;

import jakarta.persistence.Embeddable;

@Embeddable
public class UserRoleId implements Serializable {
    private Long accountId;
    private Long roleId;
	public Long getAccountId() {
		return accountId;
	}
	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}
	public Long getRoleId() {
		return roleId;
	}
	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}
    
    
}