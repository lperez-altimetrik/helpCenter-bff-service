package com.helpcenter.bff.controller;

import com.helpcenter.bff.model.ContentResponse;
import com.helpcenter.bff.service.ContentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ContentController {

    private static final Logger logger = LoggerFactory.getLogger(ContentController.class);

    private final ContentService contentService;

    @Autowired
    public ContentController(ContentService contentService) {
        this.contentService = contentService;
    }

    @GetMapping("/content")
    public ResponseEntity<ContentResponse> getContent(@RequestParam String category) {
        logger.info("Received request to fetch content for category: {}", category);

        try {
            ContentResponse response = contentService.getContentByCategory(category);
            logger.info("Successfully fetched content for category: {}", category);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            logger.error("Invalid category provided: {}", category, e);
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            logger.error("Unexpected error occurred while fetching content for category: {}", category, e);
            return ResponseEntity.internalServerError().build();
        }
    }
}
