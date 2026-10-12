
package com.assignment.alumni.controller;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping("/")
    public Map<String, Object> home() {
        Map<String, Object> response = new LinkedHashMap<>();

        response.put("application", "Alumni LinkedIn Profile Searcher");
        response.put("status", "success");
        response.put("message", "Application is running successfully");
        response.put(
                "documentation",
                "/swagger-ui/index.html");
        response.put("searchEndpoint", "POST /api/alumni/search");
        response.put("allProfilesEndpoint", "GET /api/alumni/all");

        return response;
    }
}
