package com.example.backend.controller;

import com.example.backend.dataHandler.dto.*;
import com.example.backend.dataHandler.entity.Student;
import com.example.backend.dataHandler.mapper.StudentMapper;
import com.example.backend.service.StudentService;
import com.example.backend.utils.HumorType;
import com.example.backend.utils.StudentType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class StudentControllerTest {

    private MockMvc mockMvc;

    @Mock
    private StudentService studentService;

    @Mock
    private StudentMapper studentMapper;

    @InjectMocks
    private StudentController studentController;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(studentController).build();
        objectMapper = new ObjectMapper();
    }

    // ---------------------------------------------------------
    // Helper builders
    // ---------------------------------------------------------

    private StatsDto stats() {
        return new StatsDto(10, 5, 3);
    }

    private StudentResponseDto responseDto(Long id, String first, String last) {
        return new StudentResponseDto(
                id,
                first,
                last,
                "Nick",
                "Engineering",
                StudentType.FISA,
                HumorType.FANTOME,
                stats(),
                "Flying",
                "Up, up and away!",
                "http://image.com/img.png",
                LocalDateTime.now()
        );
    }

    private StudentCreateDto createDto() {
        return new StudentCreateDto(
                "John",
                "Doe",
                "Johnny",
                "Engineering",
                StudentType.FISA,
                HumorType.FANTOME,
                stats(),
                "Flying",
                "Up, up and away!",
                "http://image.com/john.png"
        );
    }

    private StudentUpdateDto updateDto(Long id) {
        return new StudentUpdateDto(
                id,
                "New",
                "Name",
                "Nick",
                "Engineering",
                StudentType.FISA,
                HumorType.FANTOME,
                stats(),
                "Flying",
                "Up, up and away!",
                "http://image.com/img.png"
        );
    }

    // ---------------------------------------------------------
    // Tests
    // ---------------------------------------------------------

    @Test
    void createStudent_returnsCreatedResponse() throws Exception {
        StudentCreateDto dto = createDto();
        Student entity = new Student();
        StudentResponseDto response = responseDto(1L, "John", "Doe");

        when(studentService.save(any())).thenReturn(entity);
        when(studentMapper.toResponseDto(entity)).thenReturn(response);

        mockMvc.perform(post("/api/students")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "http://localhost/api/students/1"))
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.firstName").value("John"))
                .andExpect(jsonPath("$.lastName").value("Doe"));

        verify(studentService).save(dto);
        verify(studentMapper).toResponseDto(entity);
    }

    @Test
    void getById_returnsStudent() throws Exception {
        Student entity = new Student();
        StudentResponseDto response = responseDto(5L, "Alice", "Smith");

        when(studentService.findById(5L)).thenReturn(entity);
        when(studentMapper.toResponseDto(entity)).thenReturn(response);

        mockMvc.perform(get("/api/students/5"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(5L))
                .andExpect(jsonPath("$.firstName").value("Alice"))
                .andExpect(jsonPath("$.lastName").value("Smith"));

        verify(studentService).findById(5L);
        verify(studentMapper).toResponseDto(entity);
    }

    @Test
    void getAll_returnsList() throws Exception {
        // FIX: give each Student a unique ID so Mockito can distinguish them
        Student s1 = new Student();
        s1.setId(1L);

        Student s2 = new Student();
        s2.setId(2L);

        StudentResponseDto dto1 = responseDto(1L, "A", "B");
        StudentResponseDto dto2 = responseDto(2L, "C", "D");

        when(studentService.findAll()).thenReturn(List.of(s1, s2));
        when(studentMapper.toResponseDto(s1)).thenReturn(dto1);
        when(studentMapper.toResponseDto(s2)).thenReturn(dto2);

        mockMvc.perform(get("/api/students"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[1].id").value(2L));

        verify(studentService).findAll();
        verify(studentMapper).toResponseDto(s1);
        verify(studentMapper).toResponseDto(s2);
    }

    @Test
    void update_returnsUpdatedStudent() throws Exception {
        StudentUpdateDto dto = updateDto(10L);
        Student entity = new Student();
        StudentResponseDto response = responseDto(10L, "New", "Name");

        when(studentService.update(eq(10L), any())).thenReturn(entity);
        when(studentMapper.toResponseDto(entity)).thenReturn(response);

        mockMvc.perform(put("/api/students/10")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(10L))
                .andExpect(jsonPath("$.firstName").value("New"))
                .andExpect(jsonPath("$.lastName").value("Name"));

        verify(studentService).update(10L, dto);
        verify(studentMapper).toResponseDto(entity);
    }

    @Test
    void delete_removesStudent() throws Exception {
        doNothing().when(studentService).deleteById(3L);

        mockMvc.perform(delete("/api/students/3"))
                .andExpect(status().isNoContent());

        verify(studentService).deleteById(3L);
    }
}
