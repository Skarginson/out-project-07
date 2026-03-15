package com.example.backend.dataHandler.mapper;

import com.example.backend.dataHandler.dto.StatsDto;
import com.example.backend.dataHandler.entity.Stats;
import org.mapstruct.Mapper;

@Mapper
public interface StatsMapper {
    Stats toEntity(StatsDto dto);
    StatsDto toDto(Stats entity);
}