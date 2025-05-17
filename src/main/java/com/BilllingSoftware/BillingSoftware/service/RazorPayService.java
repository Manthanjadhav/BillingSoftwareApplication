package com.BilllingSoftware.BillingSoftware.service;
import com.razorpay.*;
import com.BilllingSoftware.BillingSoftware.io.RazorPayOrderResponse;
import org.json.JSONException;

public interface RazorPayService {
    RazorPayOrderResponse createOrder(Double amount, String currency) throws RazorpayException, JSONException;
}
