package com.helpcenter.bff.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class CacheServiceTest {

    @Mock
    private RedisTemplate<String, Object> redisTemplate;

    @Mock
    private ValueOperations<String, Object> valueOperations;

    private CacheService cacheService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Mock opsForValue to return the mocked ValueOperations
        when(redisTemplate.opsForValue()).thenReturn(valueOperations);

        cacheService = new CacheService(redisTemplate);
    }

    @Test
    void testSetValue() {
        // Call the method
        cacheService.setValue("key", "value", 10, TimeUnit.MINUTES);

        // Verify that set was called on ValueOperations
        verify(valueOperations, times(1)).set("key", "value", 10, TimeUnit.MINUTES);
    }

    @Test
    void testGetValue() {
        // Mock the behavior of ValueOperations#get
        when(valueOperations.get("key")).thenReturn("value");

        // Call the method
        Object value = cacheService.getValue("key");

        // Assert the result
        assertEquals("value", value);

        // Verify that get was called on ValueOperations
        verify(valueOperations, times(1)).get("key");
    }

    @Test
    void testDeleteValue() {
        // Call the method
        cacheService.deleteValue("key");

        // Verify that delete was called on RedisTemplate
        verify(redisTemplate, times(1)).delete("key");
    }
}
