package com.helpcenter.bff.service;

import com.helpcenter.bff.model.ContentResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class ContentService {

    private static final Logger logger = LoggerFactory.getLogger(ContentService.class);

    public ContentResponse getContentByCategory(String category) {
        logger.info("Fetching content for category: {}", category);

        try {
            if (category == null || category.trim().isEmpty()) {
                throw new IllegalArgumentException("Category cannot be null or empty");
            }

            ContentResponse response = new ContentResponse(category, "Sample content for category: " + category);
            logger.info("Successfully fetched content for category: {}", category);

            return response;
        } catch (IllegalArgumentException e) {
            logger.error("Error fetching content for category: {}", category, e);
            throw e; // Re-throw to let the controller handle it
        } catch (Exception e) {
            logger.error("Unexpected error occurred while fetching content for category: {}", category, e);
            throw new RuntimeException("Failed to fetch content for category: " + category, e);
        }
    }
}
