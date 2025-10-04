package com.accounts.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.accounts.entity.LoginAttempt;
import com.accounts.enumm.AttemptType;

public interface LoginAttemptRepository extends JpaRepository<LoginAttempt,Long>{
	
    @Query(""
    		+ "SELECT la FROM LoginAttempt la "
    		+ "WHERE la.account.email = :username " 
            + "AND la.attemptType = :attemptType " 
            + "AND la.attemptTime >= :since " 
            + "ORDER BY la.attemptTime ASC")
    List<LoginAttempt> findAttemptsForTokenBucket(String username,
                                               AttemptType attemptType,
                                                LocalDateTime since);
    
    @Query("SELECT la FROM LoginAttempt la WHERE la.account.id = :accountId " +
           "AND la.attemptType = :attemptType " +
           "AND la.attemptTime >= :since " +
           "ORDER BY la.attemptTime ASC")
    List<LoginAttempt> findAttemptsByAccountIdForTokenBucket(Integer accountId,AttemptType attemptType,
                                                           LocalDateTime since);
    
    // Account-specific queries
    List<LoginAttempt> findByAccountEmailOrderByAttemptTimeDesc(String username);
    
    @Query("SELECT COUNT(la) FROM LoginAttempt la WHERE la.account.email = :username " +
           "AND la.attemptType = :attemptType " +
           "AND la.attemptTime >= :since")
    long countRecentAttemptsByUsername(String username,
                                    AttemptType attemptType,
                                    LocalDateTime since);
    
    // Find latest attempt for an account
    Optional<LoginAttempt> findFirstByAccountEmailAndAttemptTypeOrderByAttemptTimeDesc(
        String username, AttemptType attemptType);
    
    @Modifying
    @Query("DELETE FROM LoginAttempt la WHERE la.account.id = :accountId AND la.attemptTime < :expiryTime")
    void deleteOldAttemptsForAccount( Integer accountId, 
                                    LocalDateTime expiryTime);
    
    @Modifying
    @Query("DELETE FROM LoginAttempt la WHERE la.attemptTime < :expiryTime")
    void deleteOldAttempts(@Param("expiryTime") LocalDateTime expiryTime);
}
