package com.accounts.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.accounts.client.EmailClient;
import com.accounts.request.EmailRequest;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;

@Service
public class EmailService {

    private static final String OTP_CB = "otpCB";
    private static final String OTP_RETRY = "otpRetry";
    
    @Autowired
    private EmailClient emailClient;

    @Retry(name = OTP_RETRY, fallbackMethod = "retryFallback")
    @CircuitBreaker(name = OTP_CB, fallbackMethod = "otpFallback")
    public String sendOtpWithCircuitBreaker(EmailRequest req) {
        return emailClient.sendOtp(req);
    }
    
    public String otpFallback(EmailRequest req, Throwable ex) {
        return "Email service temporarily unavailable. OTP could not be sent.";
    }
    
    public String retryFallback(EmailRequest req, Throwable ex) {
        return "Service unavailable after multiple retries"; // Called after all retry attempts exhausted
    }
    
}