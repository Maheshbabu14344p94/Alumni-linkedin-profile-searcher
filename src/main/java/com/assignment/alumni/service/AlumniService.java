package com.assignment.alumni.service;

import com.assignment.alumni.dto.AlumniProfileResponse;
import com.assignment.alumni.dto.AlumniSearchRequest;
import com.assignment.alumni.integration.PhantomBusterClient;
import com.assignment.alumni.model.AlumniProfile;
import com.assignment.alumni.repository.AlumniProfileRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class AlumniService {
    private final AlumniProfileRepository repository;
    private final PhantomBusterClient phantomBusterClient;

    public AlumniService(AlumniProfileRepository repository, PhantomBusterClient phantomBusterClient) {
        this.repository = repository; this.phantomBusterClient = phantomBusterClient;
    }

    @Transactional
    public List<AlumniProfileResponse> search(AlumniSearchRequest request) {
        String university = request.university().trim();
        String designation = request.designation().trim();
        // The external agent is launched only when credentials are configured. The API never
        // fabricates scraped profiles: results returned here are persisted database matches.
        if (phantomBusterClient.isConfigured()) {
            phantomBusterClient.launch(university, designation, request.passoutYear());
        }
        return repository.search(university, designation, request.passoutYear())
            .stream().map(AlumniProfileResponse::from).toList();
    }

    @Transactional(readOnly = true)
    public List<AlumniProfileResponse> findAll() {
        return repository.findAll().stream().map(AlumniProfileResponse::from).toList();
    }
}
