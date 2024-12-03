package com.helpcenter.bff.controller;

import com.helpcenter.bff.service.AuthService;
import com.okta.sdk.resource.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @GetMapping("/user/{userId}")
    public User getUser(@PathVariable String userId) {
        return authService.getUserById(userId);
    }
}