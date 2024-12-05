package com.helpcenter.bff.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class CacheService {

    private static final Logger logger = LoggerFactory.getLogger(CacheService.class);
    private final RedisTemplate<String, Object> redisTemplate;

    public CacheService(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void setValue(String key, Object value, long timeout, TimeUnit unit) {
        try {
            logger.info("Setting value in Redis for key: {}, with timeout: {} {}", key, timeout, unit);
            redisTemplate.opsForValue().set(key, value, timeout, unit);
            logger.info("Value successfully set for key: {}", key);
        } catch (NullPointerException e) {
            logger.error("RedisTemplate or ValueOperations is null while setting value for key: {}", key, e);
            throw new IllegalStateException("RedisTemplate is not properly configured", e);
        } catch (Exception e) {
            logger.error("Error occurred while setting value for key: {}", key, e);
            throw new RuntimeException("Error setting value in Redis for key: " + key, e);
        }
    }

    public Object getValue(String key) {
        try {
            logger.info("Fetching value from Redis for key: {}", key);
            Object value = redisTemplate.opsForValue().get(key);
            if (value == null) {
                logger.warn("No value found in Redis for key: {}", key);
            } else {
                logger.info("Value successfully fetched for key: {}", key);
            }
            return value;
        } catch (NullPointerException e) {
            logger.error("RedisTemplate or ValueOperations is null while fetching value for key: {}", key, e);
            throw new IllegalStateException("RedisTemplate is not properly configured", e);
        } catch (Exception e) {
            logger.error("Error occurred while fetching value for key: {}", key, e);
            throw new RuntimeException("Error fetching value from Redis for key: " + key, e);
        }
    }

    public void deleteValue(String key) {
        try {
            logger.info("Deleting value from Redis for key: {}", key);
            boolean isDeleted = Boolean.TRUE.equals(redisTemplate.delete(key));
            if (isDeleted) {
                logger.info("Value successfully deleted for key: {}", key);
            } else {
                logger.warn("No value found to delete for key: {}", key);
            }
        } catch (NullPointerException e) {
            logger.error("RedisTemplate is null while deleting value for key: {}", key, e);
            throw new IllegalStateException("RedisTemplate is not properly configured", e);
        } catch (Exception e) {
            logger.error("Error occurred while deleting value for key: {}", key, e);
            throw new RuntimeException("Error deleting value from Redis for key: " + key, e);
        }
    }
}
