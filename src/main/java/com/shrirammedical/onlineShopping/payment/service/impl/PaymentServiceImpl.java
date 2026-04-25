package com.shrirammedical.onlineShopping.payment.service.impl;

import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import com.shrirammedical.onlineShopping.order.service.OrderService;
import com.shrirammedical.onlineShopping.payment.dto.CreatePaymentRequest;
import com.shrirammedical.onlineShopping.payment.dto.PaymentResponse;
import com.shrirammedical.onlineShopping.payment.dto.VerifyPaymentRequest;
import com.shrirammedical.onlineShopping.payment.entity.Payment;
import com.shrirammedical.onlineShopping.payment.repository.PaymentRepo;
import com.shrirammedical.onlineShopping.payment.service.PaymentService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepo paymentRepo;
    private final OrderService orderService;

    @Value("${razorpay.key}")
    private String key;

    @Value("${razorpay.secret}")
    private String secret;

    /**
     * @param request
     * @return
     */
    @Override
    public PaymentResponse createPaymentOrder(CreatePaymentRequest request) throws RazorpayException {

        log.info("Create payment order, request: {}", request);

        // Normally call Razorpay API here
        String fakeGatewayOrderId = createOrderId(request.getAmount());

        Payment payment = Payment.builder()
                .orderId(request.getOrderId())
                .amount(request.getAmount())
                .gatewayOrderId(fakeGatewayOrderId)
                .paymentStatus("CREATED")
                .paymentMethod("ONLINE")
                .build();

        paymentRepo.save(payment);

        return PaymentResponse.builder()
                .gatewayOrderId(fakeGatewayOrderId)
                .amount(request.getAmount())
                .key(key)
                .build();
    }

    /**
     * @param request
     */
    @Override
    public void verifyPayment(VerifyPaymentRequest request) {

        Payment payment = paymentRepo
                .findByGatewayOrderId(request.getGatewayOrderId())
                .orElseThrow(() -> new RuntimeException("Payment not found"));

        // TODO: verify signature (important for production)

        payment.setGatewayPaymentId(request.getGatewayPaymentId());
        payment.setPaymentStatus("SUCCESS");

        paymentRepo.save(payment);

        // 🔥 IMPORTANT: update ORDER status here
         orderService.markAsConfirmed(payment.getOrderId());
    }

    // Private Helper Methods

    private String createOrderId(Double amount) throws RazorpayException {

        RazorpayClient client = new RazorpayClient(key, secret);

        JSONObject options = new JSONObject();
        options.put("amount", (int) (amount * 100)); // paise
        options.put("currency", "INR");
        options.put("receipt", "order_rcptid_" + System.currentTimeMillis());

        Order order = client.orders.create(options);

        log.info("Options: " +options);
        log.info("Order: " + order.toString());
        return order.get("id");

    }
}
