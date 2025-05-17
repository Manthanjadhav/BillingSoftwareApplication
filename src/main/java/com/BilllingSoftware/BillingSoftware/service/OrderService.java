package com.BilllingSoftware.BillingSoftware.service;

import com.BilllingSoftware.BillingSoftware.io.OrderRequest;
import com.BilllingSoftware.BillingSoftware.io.OrderResponse;
import com.BilllingSoftware.BillingSoftware.io.PaymentVerificationRequest;

import java.util.List;

public interface OrderService {
    OrderResponse createOrder(OrderRequest request);

    void deleteOrder(String orderId);

    List<OrderResponse> getLatestOrders();

    OrderResponse verifyPayment(PaymentVerificationRequest request);
}
