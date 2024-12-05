package com.helpcenter.bff.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.okta.sdk.resource.client.ApiClient;
import com.okta.sdk.resource.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class OktaWrapper {

    private static final Logger logger = LoggerFactory.getLogger(OktaWrapper.class);
    private final ApiClient oktaApiClient;

    public OktaWrapper(ApiClient oktaApiClient) {
        this.oktaApiClient = oktaApiClient;
    }

    public User getUserById(String userId) {
        try {
            logger.info("Fetching user from Okta with ID: {}", userId);
            String path = "/api/v1/users/" + userId;

            return oktaApiClient.invokeAPI(
                    path,
                    "GET",
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    "application/json",
                    null,
                    new String[]{"Authorization"},
                    new TypeReference<User>() {}
            );
        } catch (NullPointerException e) {
            logger.error("NullPointerException while fetching user with ID: {}", userId, e);
            throw new IllegalStateException("Okta API client is not properly configured", e);
        } catch (IllegalArgumentException e) {
            logger.error("Invalid user ID: {}", userId, e);
            throw new IllegalArgumentException("Invalid user ID provided: " + userId, e);
        } catch (Exception e) {
            logger.error("Error fetching user with ID: {}", userId, e);
            throw new RuntimeException("Error fetching user from Okta with ID: " + userId, e);
        }
    }

    public void deactivateUser(String userId) {
        try {
            logger.info("Deactivating user in Okta with ID: {}", userId);
            String path = "/api/v1/users/" + userId + "/lifecycle/deactivate";

            oktaApiClient.invokeAPI(
                    path,
                    "POST",
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    "application/json",
                    null,
                    new String[]{"Authorization"},
                    null
            );

            logger.info("User with ID {} successfully deactivated", userId);
        } catch (Exception e) {
            logger.error("Error deactivating user with ID: {}", userId, e);
            throw new RuntimeException("Error deactivating user in Okta with ID: " + userId, e);
        }
    }
}
