package com.helpcenter.bff.service;

import com.okta.sdk.resource.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class AuthService {

    private static final Logger logger = LoggerFactory.getLogger(AuthService.class);
    private final OktaWrapper oktaWrapper;
    private final CacheService cacheService;

    @Autowired
    public AuthService(OktaWrapper oktaWrapper, CacheService cacheService) {
        this.oktaWrapper = oktaWrapper;
        this.cacheService = cacheService;
    }

    public User getUserById(String userId) {
        try {
            // Check if user is cached
            User cachedUser = (User) cacheService.getValue(userId);
            if (cachedUser != null) {
                return cachedUser;
            }

            // Fetch user from Okta
            User user = oktaWrapper.getUserById(userId);

            // Cache the user for 10 minutes
            cacheService.setValue(userId, user, 10, TimeUnit.MINUTES);

            return user;
        } catch (IllegalArgumentException e) {
            logger.warn("Invalid user ID: {}", userId, e);
            throw e;
        } catch (Exception e) {
            logger.error("Error in AuthService while fetching user with ID: {}", userId, e);
            throw new RuntimeException("Error while fetching user details for ID: " + userId, e);
        }
    }


    public void deactivateUser(String userId) {
        // Deactivate user in Okta via OktaWrapper
        oktaWrapper.deactivateUser(userId);

        // Remove the user from the cache
        cacheService.deleteValue(userId);
    }
}
