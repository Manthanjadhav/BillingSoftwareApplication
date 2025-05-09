package com.BilllingSoftware.BillingSoftware.service;

import com.BilllingSoftware.BillingSoftware.io.UserRequest;
import com.BilllingSoftware.BillingSoftware.io.UserResponse;

import java.util.List;

public interface UserService {
    UserResponse createUser(UserRequest request);

    String getUserRole(String email);


    List<UserResponse> readUser();

    void deleteUser(String id);
}
