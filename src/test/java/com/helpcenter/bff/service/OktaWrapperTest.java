package com.helpcenter.bff.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.okta.sdk.resource.client.ApiClient;
import com.okta.sdk.resource.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

class OktaWrapperTest {

    @Mock
    private ApiClient oktaApiClient;

    private OktaWrapper oktaWrapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        oktaWrapper = new OktaWrapper(oktaApiClient);
    }

    @Test
    void testGetUserById() throws Exception {
        String userId = "test-user-id";

        // Mock Okta API response
        User mockUser = mock(User.class);
        when(oktaApiClient.invokeAPI(
                eq("/api/v1/users/" + userId),
                eq("GET"),
                any(),
                any(),
                any(),
                any(),
                any(),
                any(),
                any(),
                eq("application/json"),
                any(),
                any(),
                any()
        )).thenReturn(mockUser);

        // Call the method
        User user = oktaWrapper.getUserById(userId);

        // Assert and verify
        assertNotNull(user, "The returned user should not be null.");
        verify(oktaApiClient, times(1)).invokeAPI(anyString(), anyString(), any(), any(), any(), any(), any(), any(), any(), anyString(), any(), any(), any());
    }

    @Test
    void testDeactivateUser() throws Exception {
        String userId = "test-user-id";

        // Call the method
        oktaWrapper.deactivateUser(userId);

        // Verify the correct API call
        verify(oktaApiClient, times(1)).invokeAPI(
                eq("/api/v1/users/" + userId + "/lifecycle/deactivate"),
                eq("POST"),
                any(),
                any(),
                any(),
                any(),
                any(),
                any(),
                any(),
                eq("application/json"),
                any(),
                any(),
                isNull()
        );
    }
}
