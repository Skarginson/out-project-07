package com.example.backend.service;

import com.example.backend.dataHandler.dto.StudentCreateDto;
import com.example.backend.dataHandler.dto.StudentUpdateDto;
import com.example.backend.dataHandler.entity.Student;
import com.example.backend.dataHandler.mapper.StudentMapper;
import com.example.backend.exception.ResourceNotFoundException;
import com.example.backend.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentService {

    private final StudentRepository studentRepository;
    private final StudentMapper studentMapper;

    public Student findById(Long id) {
        return  studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found"));
    }

    public List<Student> findAll() {
        return studentRepository.findAll();
    }

    @Transactional
    public Student save(StudentCreateDto student) {
        return studentRepository.save(studentMapper.toEntity(student));
    }

    @Transactional
    public Student update(Long id, StudentUpdateDto dto) {
        Student existing = findById(id);
        studentMapper.updateFromDto(dto, existing);
        return studentRepository.save(existing);
    }

    @Transactional
    public void deleteById(Long id) {
        if (!studentRepository.existsById(id)) {
            throw new ResourceNotFoundException("Student not found: " + id);
        }
        studentRepository.deleteById(id);
    }
}
