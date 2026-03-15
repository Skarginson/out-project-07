package com.example.backend.controller;

import com.example.backend.dataHandler.dto.StudentCreateDto;
import com.example.backend.dataHandler.dto.StudentResponseDto;
import com.example.backend.dataHandler.dto.StudentUpdateDto;
import com.example.backend.dataHandler.entity.Student;
import com.example.backend.service.StudentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/students")
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;
    private final Logger log = LoggerFactory.getLogger(StudentController.class);

    @PostMapping
    public ResponseEntity<StudentResponseDto> createStudent(
            @Valid @RequestBody StudentCreateDto dto,
            UriComponentsBuilder uriBuilder) {

        log.info("Creating student {} {}", dto.firstName(), dto.lastName());
        Student created = studentService.save(dto);
        StudentResponseDto response = StudentControllerMapper.toResponseDto(created);

        URI location = uriBuilder.path("/api/students/{id}")
                .buildAndExpand(created.getId())
                .toUri();

        return ResponseEntity.created(location).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<StudentResponseDto> getById(@PathVariable Long id) {
        Student student = studentService.findById(id);
        return ResponseEntity.ok(StudentControllerMapper.toResponseDto(student));
    }

    @GetMapping
    public ResponseEntity<List<StudentResponseDto>> getAll() {
        List<StudentResponseDto> list = studentService.findAll()
                .stream()
                .map(StudentControllerMapper::toResponseDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(list);
    }

    @PutMapping("/{id}")
    public ResponseEntity<StudentResponseDto> update(
            @PathVariable Long id,
            @Valid @RequestBody StudentUpdateDto dto) {

        Student updated = studentService.update(id, dto);
        return ResponseEntity.ok(StudentControllerMapper.toResponseDto(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        studentService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
