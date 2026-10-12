package com.assignment.alumni.dto;

import com.assignment.alumni.model.AlumniProfile;

public record AlumniProfileResponse(Long id, String name, String currentRole, String university,
                                    String location, String linkedinHeadline, Integer passoutYear,
                                    String linkedinUrl) {
    public static AlumniProfileResponse from(AlumniProfile p) {
        return new AlumniProfileResponse(p.getId(), p.getName(), p.getCurrentRole(), p.getUniversity(),
            p.getLocation(), p.getLinkedinHeadline(), p.getPassoutYear(), p.getLinkedinUrl());
    }
}
