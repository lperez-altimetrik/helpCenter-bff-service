package com.helpcenter.bff.service;

import com.helpcenter.bff.model.ContentResponse;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ContentServiceTest {

    private final ContentService contentService = new ContentService();

    @Test
    void testGetContentByCategory() {
        String category = "test-category";
        ContentResponse response = contentService.getContentByCategory(category);

        assertEquals("test-category", response.getCategory());
        assertEquals("Sample content for category: test-category", response.getContent());
    }
}
