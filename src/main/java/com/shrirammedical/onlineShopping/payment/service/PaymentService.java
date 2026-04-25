package com.shrirammedical.onlineShopping.payment.service;

import com.razorpay.RazorpayException;
import com.shrirammedical.onlineShopping.payment.dto.CreatePaymentRequest;
import com.shrirammedical.onlineShopping.payment.dto.PaymentResponse;
import com.shrirammedical.onlineShopping.payment.dto.VerifyPaymentRequest;

public interface PaymentService {

    PaymentResponse createPaymentOrder(CreatePaymentRequest request) throws RazorpayException;

    void verifyPayment(VerifyPaymentRequest request);
}
