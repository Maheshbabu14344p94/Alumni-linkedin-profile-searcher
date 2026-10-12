package com.assignment.alumni.model;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "alumni_profiles", indexes = {
    @Index(name = "idx_alumni_university", columnList = "university"),
    @Index(name = "idx_alumni_current_role", columnList = "current_designation"),
    @Index(name = "idx_alumni_passout_year", columnList = "passout_year")
})
public class AlumniProfile {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 250) private String name;
    @Column(name = "current_designation", nullable = false, length = 250) private String currentRole;
    @Column(nullable = false, length = 300) private String university;
    @Column(length = 250) private String location;
    @Column(name = "linkedin_headline", length = 1000) private String linkedinHeadline;
    @Column(name = "passout_year") private Integer passoutYear;
    @Column(name = "linkedin_url", length = 1000) private String linkedinUrl;
    @Column(name = "created_at", nullable = false, updatable = false) private Instant createdAt;

    protected AlumniProfile() {}
    public AlumniProfile(String name, String currentRole, String university, String location,
                         String linkedinHeadline, Integer passoutYear, String linkedinUrl) {
        this.name = name; this.currentRole = currentRole; this.university = university;
        this.location = location; this.linkedinHeadline = linkedinHeadline;
        this.passoutYear = passoutYear; this.linkedinUrl = linkedinUrl;
    }
    @PrePersist void onCreate() { createdAt = Instant.now(); }
    public Long getId() { return id; }
    public String getName() { return name; }
    public String getCurrentRole() { return currentRole; }
    public String getUniversity() { return university; }
    public String getLocation() { return location; }
    public String getLinkedinHeadline() { return linkedinHeadline; }
    public Integer getPassoutYear() { return passoutYear; }
    public String getLinkedinUrl() { return linkedinUrl; }
    public Instant getCreatedAt() { return createdAt; }
}
