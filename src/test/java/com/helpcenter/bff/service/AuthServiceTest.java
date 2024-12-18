package com.helpcenter.bff.service;

import com.okta.sdk.resource.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuthServiceTest {

    @Mock
    private OktaWrapper oktaWrapper;

    @Mock
    private CacheService cacheService; // Add this line to mock CacheService

    @InjectMocks
    private AuthService authService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetUserByIdSuccess() {
        // Define test data
        String userId = "test-user-id";
        String cacheKey = userId;

        // Mock cacheService behavior: Cache miss (null)
        when(cacheService.getValue(cacheKey)).thenReturn(null);

        // Mock OktaWrapper behavior
        User mockUser = mock(User.class);
        when(mockUser.getId()).thenReturn(userId);
        when(oktaWrapper.getUserById(userId)).thenReturn(mockUser);

        // Call the service method
        User result = authService.getUserById(userId);

        // Assertions
        assertNotNull(result, "User should not be null");
        assertEquals(userId, result.getId(), "User ID should match");

        // Verify cache interactions
        verify(cacheService, times(1)).getValue(cacheKey); // Ensure cache was checked
        verify(cacheService, times(1)).setValue(eq(cacheKey), eq(mockUser), eq(10L), eq(TimeUnit.MINUTES)); // Cache set
        verify(oktaWrapper, times(1)).getUserById(userId); // Ensure OktaWrapper was called
    }
}
