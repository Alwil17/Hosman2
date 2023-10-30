package com.dopediatrie.hosman.auth.service.impl;

import com.dopediatrie.hosman.auth.entity.User;
import com.dopediatrie.hosman.auth.exception.AuthCustomException;
import com.dopediatrie.hosman.auth.payload.request.UserRequest;
import com.dopediatrie.hosman.auth.payload.response.UserResponse;
import com.dopediatrie.hosman.auth.repository.EmployeRepository;
import com.dopediatrie.hosman.auth.repository.UserRepository;
import com.dopediatrie.hosman.auth.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.springframework.beans.BeanUtils.copyProperties;

@Service
@RequiredArgsConstructor
@Log4j2
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final EmployeRepository employeRepository;
    private final String NOT_FOUND = "USER_NOT_FOUND";

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public long addUser(UserRequest userRequest) {
        log.info("UserServiceImpl | addUser is called");
        User user
                = User.builder()
                .username(userRequest.getUsername())
                .password(userRequest.getPassword())
                .avatar(userRequest.getAvatar())
                .remember_token(userRequest.getRemember_token())
                .is_active(userRequest.is_active())
                .last_access_time(userRequest.getLast_access_time())
                .email_verified_at(userRequest.getEmail_verified_at())
                .created_at(userRequest.getCreated_at())
                .employe(employeRepository.findById(userRequest.getEmploye_id()).get())
                .build();

        user = userRepository.save(user);

        log.info("UserServiceImpl | addUser | User Created");
        log.info("UserServiceImpl | addUser | User Id : " + user.getId());
        return user.getId();
    }

    @Override
    public void addUser(List<UserRequest> userRequests) {
        log.info("UserServiceImpl | addUser is called");

        for (UserRequest userRequest: userRequests) {
            User user
                    = User.builder()
                    .username(userRequest.getUsername())
                    .password(userRequest.getPassword())
                    .avatar(userRequest.getAvatar())
                    .remember_token(userRequest.getRemember_token())
                    .is_active(userRequest.is_active())
                    .last_access_time(userRequest.getLast_access_time())
                    .email_verified_at(userRequest.getEmail_verified_at())
                    .created_at(userRequest.getCreated_at())
                    .employe(employeRepository.findById(userRequest.getEmploye_id()).get())
                    .build();
            userRepository.save(user);
        }

        log.info("UserServiceImpl | addUser | Users Created");
    }

    @Override
    public UserResponse getUserById(long userId) {
        log.info("UserServiceImpl | getUserById is called");
        log.info("UserServiceImpl | getUserById | Get the user for userId: {}", userId);

        User user
                = userRepository.findById(userId)
                .orElseThrow(
                        () -> new AuthCustomException("User with given Id not found", NOT_FOUND));

        UserResponse userResponse = new UserResponse();

        copyProperties(user, userResponse);

        log.info("UserServiceImpl | getUserById | userResponse :" + userResponse.toString());

        return userResponse;
    }

    @Override
    public void editUser(UserRequest userRequest, long userId) {
        log.info("UserServiceImpl | editUser is called");

        User user
                = userRepository.findById(userId)
                .orElseThrow(() -> new AuthCustomException(
                        "User with given Id not found",
                        NOT_FOUND
                ));
        user.setUsername(userRequest.getUsername());
        user.setPassword(userRequest.getPassword());
        user.setAvatar(userRequest.getAvatar());
        user.setRemember_token(userRequest.getRemember_token());
        user.set_active(userRequest.is_active());
        user.setLast_access_time(userRequest.getLast_access_time());
        user.setEmail_verified_at(userRequest.getEmail_verified_at());
        user.setUpdated_at(userRequest.getUpdated_at());
        userRepository.save(user);

        log.info("UserServiceImpl | editUser | User Updated");
        log.info("UserServiceImpl | editUser | User Id : " + user.getId());
    }

    @Override
    public void deleteUserById(long userId) {
        log.info("User id: {}", userId);

        if (!userRepository.existsById(userId)) {
            log.info("Im in this loop {}", !userRepository.existsById(userId));
            throw new AuthCustomException(
                    "User with given with Id: " + userId + " not found:",
                    NOT_FOUND);
        }
        log.info("Deleting User with id: {}", userId);
        userRepository.deleteById(userId);
    }
}
