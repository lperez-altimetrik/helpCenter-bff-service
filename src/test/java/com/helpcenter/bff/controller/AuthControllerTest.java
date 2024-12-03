package com.helpcenter.bff.controller;

import com.helpcenter.bff.service.AuthService;
import com.okta.sdk.resource.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuthControllerTest {

    @Mock
    private AuthService authService; // Mock AuthService

    @InjectMocks
    private AuthController authController; // Inject the mock AuthService into AuthController

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // Initialize mocks
    }

    @Test
    void testGetUserSuccess() {
        String userId = "test-user-id";

        // Mock the User object
        User mockUser = mock(User.class);
        when(mockUser.getId()).thenReturn(userId);

        // Mock the service behavior
        when(authService.getUserById(userId)).thenReturn(mockUser);

        // Call the controller
        User result = authController.getUser(userId);

        // Assert the result
        assertNotNull(result, "User should not be null");
        assertEquals(userId, result.getId(), "User ID should match");

        // Verify the service was called
        verify(authService, times(1)).getUserById(userId);
    }

    @Test
    void testGetUserFailure() {
        String userId = "test-user-id";

        // Mock the service to throw an exception
        when(authService.getUserById(userId)).thenThrow(new RuntimeException("User not found"));

        // Call the controller and assert exception
        RuntimeException exception = assertThrows(RuntimeException.class, () -> authController.getUser(userId));

        // Assert exception message
        assertEquals("User not found", exception.getMessage(), "Exception message should match");

        // Verify the service was called
        verify(authService, times(1)).getUserById(userId);
    }
}
