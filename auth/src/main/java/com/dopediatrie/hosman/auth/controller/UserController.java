package com.dopediatrie.hosman.auth.controller;

import com.dopediatrie.hosman.auth.entity.User;
import com.dopediatrie.hosman.auth.payload.request.UserRequest;
import com.dopediatrie.hosman.auth.payload.response.UserResponse;
import com.dopediatrie.hosman.auth.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Log4j2
public class UserController {
    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        log.info("UserController | getAllUsers is called");
        return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Long> addUser(@RequestBody UserRequest userRequest) {

        log.info("UserController | addUser is called");
        log.info("UserController | addUser | userRequest : " + userRequest.toString());

        long userId = userService.addUser(userRequest);
        return new ResponseEntity<>(userId, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable("id") long userId) {

        log.info("UserController | getUserById is called");
        log.info("UserController | getUserById | userId : " + userId);

        UserResponse userResponse
                = userService.getUserById(userId);
        return new ResponseEntity<>(userResponse, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> editUser(@RequestBody UserRequest userRequest,
                                         @PathVariable("id") long userId) {

        log.info("UserController | editUser is called");
        log.info("UserController | editUser | userId : " + userId);

        userService.editUser(userRequest, userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public void deleteUserById(@PathVariable("id") long userId) {
        userService.deleteUserById(userId);
    }
}
