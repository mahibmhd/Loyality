package com.loyalty.dxvalley.controllers;

import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;

@RestController
@RequestMapping("/api/images")
public class ImageController {

    private static final String IMAGE_DIRECTORY = "/data/Loyalty/images/";

    @GetMapping("/{imageName:.+}")
    public ResponseEntity<Resource> getImage(@PathVariable String imageName) {
        try {
            File imageFile = new File(IMAGE_DIRECTORY + imageName);
            // Check if the image file exists
            if (imageFile.exists()) {
                // Return the image as a ResponseEntity with proper content type
                return ResponseEntity.ok()
                        .contentType(MediaType.IMAGE_JPEG) // Adjust content type based on your image format
                        .body(new FileSystemResource(imageFile));
            } else {
                // Return 404 Not Found if the image does not exist
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            // Handle exceptions (e.g., IOException) here
            return ResponseEntity.status(500).build();
        }
    }
}

