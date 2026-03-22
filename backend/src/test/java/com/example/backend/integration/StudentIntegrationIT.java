package com.example.backend.integration;

import com.example.backend.dataHandler.dto.StudentCreateDto;
import com.example.backend.dataHandler.dto.StudentUpdateDto;
import com.example.backend.dataHandler.dto.StatsDto;
import com.example.backend.dataHandler.entity.Student;
import com.example.backend.dataHandler.mapper.StatsMapper;
import com.example.backend.repository.StudentRepository;
import com.example.backend.utils.HumorType;
import com.example.backend.utils.StudentType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Full integration tests for Student endpoints using WebApplicationContext to build MockMvc.
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class StudentIntegrationIT {

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private StatsMapper statsMapper;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void beforeEach() {
        // build MockMvc from the WebApplicationContext (no AutoConfigureMockMvc needed)
        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
        studentRepository.deleteAll();
    }

    @Test
    void createStudent_persistsAndReturnsCreated() throws Exception {
        StudentCreateDto dto = new StudentCreateDto(
                "Integration",
                "Tester",
                "IntTest",
                "Engineering",
                StudentType.FISA,
                HumorType.FANTOME,
                new StatsDto(11, 6, 4),
                "TestingPower",
                "Integration first!",
                "http://example.com/img.png"
        );

        mockMvc.perform(post("/api/students")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"))
                .andExpect(jsonPath("$.firstName").value("Integration"))
                .andExpect(jsonPath("$.lastName").value("Tester"));

        List<Student> all = studentRepository.findAll();
        assertThat(all).hasSize(1);
    }

    @Test
    void getById_returnsStudent() throws Exception {
        Student s = new Student();
        s.setFirstName("Get");
        s.setLastName("ById");
        s.setType(StudentType.FISA);
        s.setStats(statsMapper.toEntity(new StatsDto(5, 2, 1)));
        s.setCreatedAt(LocalDateTime.now());
        Student saved = studentRepository.save(s);

        mockMvc.perform(get("/api/students/{id}", saved.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(saved.getId()))
                .andExpect(jsonPath("$.firstName").value("Get"));
    }

    @Test
    void update_updatesExistingStudent() throws Exception {
        Student s = new Student();
        s.setFirstName("Before");
        s.setLastName("Change");
        s.setType(StudentType.FISA);
        s.setStats(statsMapper.toEntity(new StatsDto(3, 3, 3)));
        Student saved = studentRepository.save(s);

        StudentUpdateDto update = new StudentUpdateDto(
                saved.getId(),
                "After",
                "Changed",
                "Nick",
                "NewSpec",
                StudentType.FISA,
                HumorType.FANTOME,
                new StatsDto(9, 9, 9),
                "NewPower",
                "Changed now",
                "http://example.com/new.png"
        );

        mockMvc.perform(put("/api/students/{id}", saved.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(update)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("After"));

        Student reloaded = studentRepository.findById(saved.getId()).orElseThrow();
        assertThat(reloaded.getStats().getHp()).isEqualTo(9);
    }

    @Test
    void delete_removesStudent() throws Exception {
        Student s = new Student();
        s.setFirstName("ToDelete");
        s.setLastName("Now");
        Student saved = studentRepository.save(s);

        mockMvc.perform(delete("/api/students/{id}", saved.getId()))
                .andExpect(status().isNoContent());

        assertThat(studentRepository.existsById(saved.getId())).isFalse();
    }
}
