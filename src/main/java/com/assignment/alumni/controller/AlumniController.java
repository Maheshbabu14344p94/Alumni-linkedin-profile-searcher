package com.assignment.alumni.controller;

import com.assignment.alumni.dto.*;
import com.assignment.alumni.service.AlumniService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/alumni")
@Tag(name = "Alumni Profiles", description = "Search and retrieve saved alumni profiles")
public class AlumniController {
    private final AlumniService service;
    public AlumniController(AlumniService service) { this.service = service; }

    @PostMapping("/search")
    @Operation(summary = "Search saved alumni profiles",
      description = "Accepts university, current designation, and optional pass-out year. When PhantomBuster credentials are configured, launches the configured agent; returns matching profiles already persisted in PostgreSQL.")
    public ResponseEntity<ApiResponse<List<AlumniProfileResponse>>> search(@Valid @RequestBody AlumniSearchRequest request) {
        return ResponseEntity.ok(ApiResponse.success(service.search(request)));
    }

    @GetMapping("/all")
    @Operation(summary = "Get all saved alumni profiles")
    public ResponseEntity<ApiResponse<List<AlumniProfileResponse>>> all() {
        return ResponseEntity.ok(ApiResponse.success(service.findAll()));
    }
}
