package com.helpcenter.bff.service;

import com.okta.sdk.resource.client.ApiClient;
import com.okta.sdk.resource.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuthServiceTest {

    @Mock
    private ApiClient apiClient;
    @InjectMocks
    private AuthService authService;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetUserByIdSuccess() {
        String userId = "test-user-id";

        // Mock the User object
        User mockUser = mock(User.class);
        when(mockUser.getId()).thenReturn(userId);

        // Debug invocation arguments
        doAnswer(invocation -> {
            Object[] args = invocation.getArguments();
            System.out.println("invokeAPI called with args: " + Arrays.toString(args));
            return mockUser;
        }).when(apiClient).invokeAPI(anyString(), anyString(), any(), any(), any(), any(), anyMap(), anyMap(), anyMap(), anyString(), any(), any(), any());

        // Call the service
        User result = authService.getUserById(userId);

        // Assert the result
        assertNotNull(result, "User should not be null");
        assertEquals(userId, result.getId(), "User ID should match");

        // Verify ApiClient interaction
        verify(apiClient, times(1)).invokeAPI(anyString(), anyString(), any(), any(), any(), any(), any(), any(), any(), anyString(), any(), any(), any());
    }
}
