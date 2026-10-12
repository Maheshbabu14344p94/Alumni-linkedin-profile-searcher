package com.assignment.alumni.integration;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import java.util.*;

@Component
public class PhantomBusterClient {
    private final RestTemplate restTemplate;
    private final ObjectMapper mapper;
    private final String apiKey;
    private final String agentId;
    private final String launchUrl;

    public PhantomBusterClient(ObjectMapper mapper,
        @Value("${phantombuster.api-key:}") String apiKey,
        @Value("${phantombuster.agent-id:}") String agentId,
        @Value("${phantombuster.launch-url:https://api.phantombuster.com/api/v2/agents/launch}") String launchUrl) {
        this.restTemplate = new RestTemplate();
        this.mapper = mapper;
        this.apiKey = apiKey;
        this.agentId = agentId;
        this.launchUrl = launchUrl;
    }

    public boolean isConfigured() { return !apiKey.isBlank() && !agentId.isBlank(); }

    /**
     * Launches the configured PhantomBuster agent. Agent-specific input schemas vary, so
     * the JSON input is configurable and deliberately does not pretend a launch is completed scraping.
     */
    public String launch(String university, String designation, Integer passoutYear) {
        if (!isConfigured()) {
            throw new IllegalStateException("PhantomBuster is not configured. Set PHANTOMBUSTER_API_KEY and PHANTOMBUSTER_AGENT_ID.");
        }
        try {
            Map<String, Object> search = new LinkedHashMap<>();
            search.put("university", university);
            search.put("designation", designation);
            if (passoutYear != null) search.put("passoutYear", passoutYear);
            Map<String, Object> payload = new LinkedHashMap<>();
            payload.put("id", agentId);
            payload.put("argument", Map.of("search", search));
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("X-Phantombuster-Key", apiKey);
            ResponseEntity<String> response = restTemplate.exchange(launchUrl, HttpMethod.POST,
                new HttpEntity<>(payload, headers), String.class);
            if (!response.getStatusCode().is2xxSuccessful()) {
                throw new IllegalStateException("PhantomBuster launch failed with HTTP " + response.getStatusCode().value());
            }
            JsonNode json = mapper.readTree(response.getBody() == null ? "{}" : response.getBody());
            return json.path("containerId").asText(json.path("id").asText(""));
        } catch (RestClientException e) {
            throw new IllegalStateException("Unable to contact PhantomBuster. Verify API credentials, agent configuration, and network access.", e);
        } catch (java.io.IOException e) {
            throw new IllegalStateException("PhantomBuster returned an unreadable response.", e);
        }
    }
}
