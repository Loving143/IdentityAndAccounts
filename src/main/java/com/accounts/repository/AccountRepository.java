package com.accounts.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.accounts.entity.Account;

public interface AccountRepository extends JpaRepository<Account,Integer> {

	Optional<Account> findAccountByEmail(String userName);

}
