package com.helpcenter.bff.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.okta.sdk.resource.client.ApiClient;
import com.okta.sdk.resource.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class AuthService {

    private final ApiClient oktaApiClient;

    @Autowired
    public AuthService(ApiClient oktaApiClient) {
        this.oktaApiClient = oktaApiClient;
    }

    public User getUserById(String userId) {
        try {
            String path = "/api/v1/users/" + userId; // API endpoint to fetch the user
            return oktaApiClient.invokeAPI(
                    path,                          // Path to the API endpoint
                    "GET",                         // HTTP method
                    null,                          // Query parameters
                    null,                          // Collection query parameters
                    null,                          // URL query deep object
                    null,                          // Body (not required for GET)
                    Collections.emptyMap(),        // Header parameters
                    Collections.emptyMap(),        // Cookie parameters
                    Collections.emptyMap(),        // Form parameters
                    "application/json",            // Accept header
                    null,                          // Content-Type header (not required for GET)
                    new String[]{"Authorization"}, // Authentication names
                    new TypeReference<User>() {}   // Expected return type
            );
        } catch (Exception e) {
            throw new RuntimeException("Error fetching user with ID: " + userId, e);
        }
    }
}
