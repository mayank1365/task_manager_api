package com.example.taskmanager.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * Health check controller.
 */
@RestController
public class HealthController {

    /**
     * Health check endpoint.
     *
     * @return health status response
     */
    @GetMapping("/health")
    public ResponseEntity<Map<String, String>> health() {
        Map<String, String> response = new HashMap<>();
        response.put("status", "UP");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
