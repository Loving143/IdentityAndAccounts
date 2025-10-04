package com.accounts.client;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import com.accounts.request.EmailRequest;

@FeignClient(name = "email-service", url = "http://localhost:8041/email")
public interface EmailClient {

    @PostMapping("/send-otp")
    String sendOtp(@RequestBody EmailRequest emailReq);
}
