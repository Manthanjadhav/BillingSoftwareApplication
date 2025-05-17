package com.BilllingSoftware.BillingSoftware.controller;

import com.BilllingSoftware.BillingSoftware.io.OrderResponse;
import com.BilllingSoftware.BillingSoftware.io.PaymentRequest;
import com.BilllingSoftware.BillingSoftware.io.PaymentVerificationRequest;
import com.BilllingSoftware.BillingSoftware.io.RazorPayOrderResponse;
import com.BilllingSoftware.BillingSoftware.service.OrderService;
import com.BilllingSoftware.BillingSoftware.service.RazorPayService;
import com.razorpay.RazorpayException;
import lombok.RequiredArgsConstructor;
import org.json.JSONException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payments")
@RequiredArgsConstructor
public class PaymentController {
    private final RazorPayService razorPayService;
    private final OrderService orderService;

    @PostMapping("/create_order")
    @ResponseStatus(HttpStatus.CREATED)
    public RazorPayOrderResponse createRazorpayOrder(@RequestBody PaymentRequest request) throws RazorpayException, JSONException {
        return razorPayService.createOrder(request.getAmount(), request.getCurrency());
    }

    @PostMapping("/verify")
    public OrderResponse verifyPayment(@RequestBody PaymentVerificationRequest request)
    {
        return orderService.verifyPayment(request);
    }
}
