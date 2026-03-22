package com.example.backend.mapper;

import com.example.backend.dataHandler.dto.StatsDto;
import com.example.backend.dataHandler.entity.Stats;
import com.example.backend.dataHandler.mapper.StatsMapper;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.*;

class StatsMapperTest {

    private final StatsMapper mapper = Mappers.getMapper(StatsMapper.class);

    @Test
    void toEntity_mapsAllFields() {
        StatsDto dto = new StatsDto(10, 5, 3);

        Stats entity = mapper.toEntity(dto);

        assertEquals(10, entity.getHp());
        assertEquals(5, entity.getAttack());
        assertEquals(3, entity.getDefense());
    }

    @Test
    void toDto_mapsAllFields() {
        Stats entity = new Stats();
        entity.setHp(20);
        entity.setAttack(7);
        entity.setDefense(4);

        StatsDto dto = mapper.toDto(entity);

        assertEquals(20, dto.hp());
        assertEquals(7, dto.attack());
        assertEquals(4, dto.defense());
    }
}
