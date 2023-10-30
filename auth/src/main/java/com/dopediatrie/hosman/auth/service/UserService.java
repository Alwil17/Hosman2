package com.dopediatrie.hosman.auth.service;

import com.dopediatrie.hosman.auth.entity.User;
import com.dopediatrie.hosman.auth.payload.request.UserRequest;
import com.dopediatrie.hosman.auth.payload.response.UserResponse;

import java.util.List;

public interface UserService {
    List<User> getAllUsers();

    long addUser(UserRequest userRequest);

    void addUser(List<UserRequest> userRequests);

    UserResponse getUserById(long userId);

    void editUser(UserRequest userRequest, long userId);

    public void deleteUserById(long userId);
}
