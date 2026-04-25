package com.shrirammedical.onlineShopping.payment.controller;

import com.razorpay.RazorpayException;
import com.shrirammedical.onlineShopping.payment.dto.CreatePaymentRequest;
import com.shrirammedical.onlineShopping.payment.dto.PaymentResponse;
import com.shrirammedical.onlineShopping.payment.dto.VerifyPaymentRequest;
import com.shrirammedical.onlineShopping.payment.service.PaymentService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/payment")
@AllArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PreAuthorize("hasRole('CUSTOMER')")
    @PostMapping("/create")
    public ResponseEntity<PaymentResponse> createPayment(
            @RequestBody CreatePaymentRequest request) {

        PaymentResponse response = null;
        try{
            response = paymentService.createPaymentOrder(request);
        } catch (RazorpayException e){
            log.error("Error while creating payment order, ", e);
        } catch (Exception e){
            log.error("Unexpected error while creating payment order, ", e);
        }
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasRole('CUSTOMER')")
    @PostMapping("/verify")
    public ResponseEntity<?> verifyPayment(
            @RequestBody VerifyPaymentRequest request) {

        paymentService.verifyPayment(request);
        return ResponseEntity.ok("Payment verified");
    }
}
