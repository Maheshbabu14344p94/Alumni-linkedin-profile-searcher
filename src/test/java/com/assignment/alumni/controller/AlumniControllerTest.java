
package com.assignment.alumni.controller;

import com.assignment.alumni.dto.AlumniProfileResponse;
import com.assignment.alumni.dto.AlumniSearchRequest;
import com.assignment.alumni.service.AlumniService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AlumniController.class)
@Import(com.assignment.alumni.exception.GlobalExceptionHandler.class)
class AlumniControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private AlumniService service;

    @Test
    void searchReturnsProfiles() throws Exception {
        when(service.search(any(AlumniSearchRequest.class)))
                .thenReturn(List.of(
                        new AlumniProfileResponse(
                                1L,
                                "Alex Kim",
                                "Software Engineer",
                                "Example University",
                                "Remote",
                                "Engineer building APIs",
                                2021,
                                "https://www.linkedin.com/in/example"
                        )
                ));

        String requestJson = """
                {
                    "university": "Example University",
                    "designation": "Software Engineer",
                    "passoutYear": 2021
                }
                """;

        mvc.perform(post("/api/alumni/search")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("success"))
                .andExpect(jsonPath("$.data[0].name").value("Alex Kim"))
                .andExpect(jsonPath("$.data[0].currentRole")
                        .value("Software Engineer"));
    }

    @Test
    void searchRejectsMissingUniversity() throws Exception {
        String requestJson = """
                {
                    "university": " ",
                    "designation": "Engineer"
                }
                """;

        mvc.perform(post("/api/alumni/search")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value("error"));
    }
}
