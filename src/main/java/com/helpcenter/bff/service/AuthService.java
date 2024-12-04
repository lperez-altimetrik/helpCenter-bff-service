package com.helpcenter.bff.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.okta.sdk.resource.client.ApiClient;
import com.okta.sdk.resource.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.concurrent.TimeUnit;

@Service
public class AuthService {

    private final ApiClient oktaApiClient;
    private final CacheService cacheService;

    @Autowired
    public AuthService(ApiClient oktaApiClient, CacheService cacheService) {
        this.oktaApiClient = oktaApiClient;
        this.cacheService = cacheService;
    }

    public User getUserById(String userId) {
        // Check if user is cached
        User cachedUser = (User) cacheService.getValue(userId);
        if (cachedUser != null) {
            return cachedUser;
        }

        try {
            String path = "/api/v1/users/" + userId; // API endpoint to fetch the user
            // Fetch the user from Okta API
            User user = oktaApiClient.invokeAPI(
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

            // Cache the user for 10 minutes
            cacheService.setValue(userId, user, 10, TimeUnit.MINUTES);

            // Return the fetched user
            return user;
        } catch (Exception e) {
            throw new RuntimeException("Error fetching user with ID: " + userId, e);
        }
    }

}
