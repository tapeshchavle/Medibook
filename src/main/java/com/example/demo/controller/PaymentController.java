package com.example.demo.controller;

import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/payment")
public class PaymentController {

    @Value("${razorpay.key_id}")
    private String keyId;

    @Value("${razorpay.key_secret}")
    private String keySecret;

    @PostMapping("/create-order")
    public ResponseEntity<?> createOrder(@RequestBody Map<String, Object> data) {
        try {
            RazorpayClient razorpay = new RazorpayClient(keyId, keySecret);

            Double amount = Double.parseDouble(data.get("amount").toString());

            JSONObject orderRequest = new JSONObject();
            orderRequest.put("amount", amount * 100); // Amount in paise
            orderRequest.put("currency", "INR");
            orderRequest.put("receipt", "txn_" + System.currentTimeMillis());

            Order order = razorpay.orders.create(orderRequest);

            return ResponseEntity.ok(order.toString());

        } catch (RazorpayException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @PostMapping("/verify")
    public ResponseEntity<?> verifyPayment(@RequestBody Map<String, Object> data) {
        // In a real app, verify signature here using Razorpay SDK
        // Utils.verifyPaymentSignature(attributes, api_secret);
        return ResponseEntity.ok(Map.of("status", "success"));
    }
}
