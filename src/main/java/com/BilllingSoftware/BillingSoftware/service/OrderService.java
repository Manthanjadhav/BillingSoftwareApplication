package com.BilllingSoftware.BillingSoftware.service;

import com.BilllingSoftware.BillingSoftware.io.OrderRequest;
import com.BilllingSoftware.BillingSoftware.io.OrderResponse;

import java.util.List;

public interface OrderService {
    OrderResponse createOrder(OrderRequest request);

    void deleteOrder(String orderId);

    List<OrderResponse> getLatestOrders();
}
