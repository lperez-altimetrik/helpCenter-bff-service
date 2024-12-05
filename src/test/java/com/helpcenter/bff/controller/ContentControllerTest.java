package com.helpcenter.bff.controller;

import com.helpcenter.bff.model.ContentResponse;
import com.helpcenter.bff.service.ContentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class ContentControllerTest {

    @Mock
    private ContentService contentService;

    @InjectMocks
    private ContentController contentController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetContent() {
        String category = "test-category";
        when(contentService.getContentByCategory(category)).thenReturn(
                new ContentResponse(category, "Sample content for category: test-category")
        );

        ResponseEntity<?> response = contentController.getContent(category);

        assertEquals(200, response.getStatusCode().value());
    }
}
