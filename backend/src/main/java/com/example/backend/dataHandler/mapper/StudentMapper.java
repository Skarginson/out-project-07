package com.example.backend.dataHandler.mapper;

import com.example.backend.dataHandler.dto.StudentCreateDto;
import com.example.backend.dataHandler.dto.StudentResponseDto;
import com.example.backend.dataHandler.dto.StudentUpdateDto;
import com.example.backend.dataHandler.entity.Student;
import org.mapstruct.*;

@Mapper(uses = StatsMapper.class)
public interface StudentMapper {

    Student toEntity(StudentCreateDto dto);

    StudentResponseDto toResponseDto(Student entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "createdAt", ignore = true)
    void updateFromDto(StudentUpdateDto dto, @MappingTarget Student entity);
}
