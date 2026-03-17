package com.example.backend;

import com.example.backend.dataHandler.dto.StudentCreateDto;
import com.example.backend.dataHandler.dto.StudentUpdateDto;
import com.example.backend.dataHandler.entity.Student;
import com.example.backend.dataHandler.mapper.StudentMapper;
import com.example.backend.exception.ResourceNotFoundException;
import com.example.backend.repository.StudentRepository;
import com.example.backend.service.StudentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StudentServiceTests {

    @Mock
    private StudentRepository studentRepository;

    @Mock
    private StudentMapper studentMapper;

    @InjectMocks
    private StudentService studentService;

    @Captor
    private ArgumentCaptor<Student> studentCaptor;

    @BeforeEach
    void setUp() {
    }

    @Test
    void findById_whenStudentExists_returnsStudent() {
        Student expected = mock(Student.class);
        when(studentRepository.findById(1L)).thenReturn(Optional.of(expected));

        Student actual = studentService.findById(1L);

        assertSame(expected, actual);
        verify(studentRepository).findById(1L);
    }

    @Test
    void findById_whenStudentDoesNotExist_throwsResourceNotFoundException() {
        when(studentRepository.findById(42L)).thenReturn(Optional.empty());

        ResourceNotFoundException ex = assertThrows(ResourceNotFoundException.class, () -> studentService.findById(42L));
        assertTrue(ex.getMessage().toLowerCase().contains("student not found"));
        verify(studentRepository).findById(42L);
    }

    @Test
    void findAll_returnsListFromRepository() {
        Student s1 = mock(Student.class);
        Student s2 = mock(Student.class);
        List<Student> expected = List.of(s1, s2);
        when(studentRepository.findAll()).thenReturn(expected);

        List<Student> actual = studentService.findAll();

        assertEquals(expected, actual);
        verify(studentRepository).findAll();
    }

    @Test
    void save_mapsDtoAndSavesEntity() {
        // pass null DTO; stub mapper to accept null and return an entity
        StudentCreateDto dto = null;
        Student entity = mock(Student.class);
        Student saved = mock(Student.class);

        when(studentMapper.toEntity(dto)).thenReturn(entity);
        when(studentRepository.save(entity)).thenReturn(saved);

        Student result = studentService.save(dto);

        assertSame(saved, result);
        verify(studentMapper).toEntity(dto);
        verify(studentRepository).save(entity);
    }

    @Test
    void update_whenStudentExists_updatesAndSaves() {
        Long id = 7L;
        StudentUpdateDto dto = null;
        Student existing = mock(Student.class);
        Student saved = mock(Student.class);

        when(studentRepository.findById(id)).thenReturn(Optional.of(existing));
        // studentService.update calls findById -> which uses repository; we stub repository directly
        // stub mapper.updateFromDto to do nothing (void)
        doNothing().when(studentMapper).updateFromDto(dto, existing);
        when(studentRepository.save(existing)).thenReturn(saved);

        Student result = studentService.update(id, dto);

        assertSame(saved, result);
        verify(studentRepository).findById(id);
        verify(studentMapper).updateFromDto(dto, existing);
        verify(studentRepository).save(existing);
    }

    @Test
    void update_whenStudentDoesNotExist_throwsResourceNotFoundException() {
        Long id = 99L;
        StudentUpdateDto dto = null;

        when(studentRepository.findById(id)).thenReturn(Optional.empty());

        ResourceNotFoundException ex = assertThrows(ResourceNotFoundException.class, () -> studentService.update(id, dto));
        assertTrue(ex.getMessage().toLowerCase().contains("student not found"));
        verify(studentRepository).findById(id);
        verifyNoMoreInteractions(studentMapper, studentRepository);
    }

    @Test
    void deleteById_whenExists_deletes() {
        Long id = 5L;
        when(studentRepository.existsById(id)).thenReturn(true);
        doNothing().when(studentRepository).deleteById(id);

        studentService.deleteById(id);

        verify(studentRepository).existsById(id);
        verify(studentRepository).deleteById(id);
    }

    @Test
    void deleteById_whenNotExists_throwsResourceNotFoundException() {
        Long id = 123L;
        when(studentRepository.existsById(id)).thenReturn(false);

        ResourceNotFoundException ex = assertThrows(ResourceNotFoundException.class, () -> studentService.deleteById(id));
        assertTrue(ex.getMessage().contains(String.valueOf(id)));
        verify(studentRepository).existsById(id);
        verify(studentRepository, never()).deleteById(anyLong());
    }
}
