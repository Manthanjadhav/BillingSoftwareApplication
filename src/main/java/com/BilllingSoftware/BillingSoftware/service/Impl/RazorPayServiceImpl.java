package com.BilllingSoftware.BillingSoftware.service.Impl;
import com.BilllingSoftware.BillingSoftware.io.RazorPayOrderResponse;
import com.BilllingSoftware.BillingSoftware.service.RazorPayService;
import com.razorpay.*;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class RazorPayServiceImpl implements RazorPayService {
    @Value("${razorpay.key.id}")
    private String razorpayKeyId;

    @Value("${razorpay.key.secret}")
    private String razorpayKeySecret;

    @Override
    public RazorPayOrderResponse createOrder(Double amount, String currency) throws RazorpayException, JSONException {
        RazorpayClient razorpayClient = new RazorpayClient(razorpayKeyId,razorpayKeySecret);
        JSONObject  orderRequest = new JSONObject();
        orderRequest.put("amount",amount*100);
        orderRequest.put("currency", currency);
        orderRequest.put("receipt","order_rcpid_"+System.currentTimeMillis());
        orderRequest.put("payment_capture",1);
        Order order = razorpayClient.orders.create(orderRequest);
        return convertToResponse(order);
    }

    private RazorPayOrderResponse convertToResponse(com.razorpay.Order order) {
        return RazorPayOrderResponse.builder()
                .id(order.get("id"))
                .entity(order.get("entity"))
                .amount(order.get("amount"))
                .currency(order.get("currency"))
                .status(order.get("status"))
                .createdAt(order.get("created_at"))
                .receipt(order.get("receipt"))
                .build();
    }
}
