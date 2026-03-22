package com.example.backend.mapper;

import com.example.backend.dataHandler.dto.*;
import com.example.backend.dataHandler.entity.Student;
import com.example.backend.dataHandler.entity.Stats;
import com.example.backend.dataHandler.mapper.StudentMapper;
import com.example.backend.dataHandler.mapper.StatsMapper;
import com.example.backend.utils.HumorType;
import com.example.backend.utils.StudentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.lang.reflect.Constructor;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class StudentMapperTest {

    private StudentMapper mapper;
    private StatsMapper statsMapper;

    @BeforeEach
    void setUp() throws Exception {
        // StatsMapper is simple and usually has a no-arg impl; obtain it first
        statsMapper = Mappers.getMapper(StatsMapper.class);

        // Try the simple MapStruct factory first; if the generated impl uses Spring (constructor injection),
        // Mappers.getMapper may fail because the impl has a constructor requiring dependencies.
        try {
            mapper = Mappers.getMapper(StudentMapper.class);
        } catch (RuntimeException ex) {
            // Fallback: instantiate the generated implementation reflectively using the StatsMapper dependency.
            // The generated class name follows the pattern: <interfaceName>Impl in the same package.
            String implClassName = "com.example.backend.dataHandler.mapper.StudentMapperImpl";
            Class<?> implClass = Class.forName(implClassName);
            // Find a constructor that accepts StatsMapper (MapStruct generated impl will have one when componentModel=spring)
            Constructor<?> ctor = null;
            for (Constructor<?> c : implClass.getDeclaredConstructors()) {
                Class<?>[] params = c.getParameterTypes();
                if (params.length == 1 && params[0].isAssignableFrom(statsMapper.getClass().getInterfaces()[0])) {
                    ctor = c;
                    break;
                }
            }
            // If we didn't find the constructor by interface match above, try the simpler approach:
            if (ctor == null) {
                // try to find a constructor that accepts StatsMapper.class directly
                try {
                    ctor = implClass.getDeclaredConstructor(StatsMapper.class);
                } catch (NoSuchMethodException ignored) {
                    // last resort: pick the first constructor and try to pass statsMapper (works if single-param constructor exists)
                    Constructor<?>[] ctors = implClass.getDeclaredConstructors();
                    if (ctors.length > 0) {
                        ctor = ctors[0];
                    }
                }
            }
            if (ctor == null) {
                throw new IllegalStateException("Could not find a suitable constructor for StudentMapperImpl", ex);
            }
            ctor.setAccessible(true);
            // instantiate with the statsMapper (if ctor expects different param types this will throw and surface the issue)
            mapper = (StudentMapper) ctor.newInstance(statsMapper);
        }
    }

    // ---------------------------------------------------------
    // toEntity (StudentCreateDto → Student)
    // ---------------------------------------------------------
    @Test
    void toEntity_mapsAllFields() {
        StudentCreateDto dto = new StudentCreateDto(
                "John",
                "Doe",
                "Johnny",
                "Engineering",
                StudentType.FISA,
                HumorType.FANTOME,
                new StatsDto(10, 5, 3),
                "Flying",
                "Up, up and away!",
                "http://image.com/john.png"
        );

        Student entity = mapper.toEntity(dto);

        assertEquals("John", entity.getFirstName());
        assertEquals("Doe", entity.getLastName());
        assertEquals("Johnny", entity.getNickname());
        assertEquals("Engineering", entity.getSpeciality());
        assertEquals(StudentType.FISA, entity.getType());
        assertEquals(HumorType.FANTOME, entity.getHumorType());
        assertNotNull(entity.getStats());
        assertEquals(10, entity.getStats().getHp());
        assertEquals("Flying", entity.getSuperPower());
        assertEquals("Up, up and away!", entity.getCatchPhrase());
        assertEquals("http://image.com/john.png", entity.getImageUrl());
    }

    // ---------------------------------------------------------
    // toResponseDto (Student → StudentResponseDto)
    // ---------------------------------------------------------
    @Test
    void toResponseDto_mapsAllFields() {
        Student student = new Student();
        student.setId(1L);
        student.setFirstName("Alice");
        student.setLastName("Smith");
        student.setNickname("Ally");
        student.setSpeciality("Magic");
        student.setType(StudentType.FISA);
        student.setHumorType(HumorType.FANTOME);

        Stats stats = new Stats();
        stats.setHp(20);
        stats.setAttack(7);
        stats.setDefense(4);
        student.setStats(stats);

        student.setSuperPower("Invisibility");
        student.setCatchPhrase("Now you see me!");
        student.setImageUrl("http://image.com/alice.png");
        student.setCreatedAt(LocalDateTime.now());

        StudentResponseDto dto = mapper.toResponseDto(student);

        assertEquals(1L, dto.id());
        assertEquals("Alice", dto.firstName());
        assertEquals("Smith", dto.lastName());
        assertEquals("Ally", dto.nickname());
        assertEquals("Magic", dto.speciality());
        assertEquals(StudentType.FISA, dto.type());
        assertEquals(HumorType.FANTOME, dto.humorType());
        assertEquals(20, dto.stats().hp());
        assertEquals("Invisibility", dto.superPower());
        assertEquals("Now you see me!", dto.catchPhrase());
        assertEquals("http://image.com/alice.png", dto.imageUrl());
        assertNotNull(dto.createdAt());
    }

    // ---------------------------------------------------------
    // updateFromDto (partial update)
    // ---------------------------------------------------------
    @Test
    void updateFromDto_ignoresNullFields() {
        StudentUpdateDto dto = new StudentUpdateDto(
                99L,       // id (ignored by mapping)
                null,      // firstName null → ignored
                "Updated", // lastName updated
                null,      // nickname ignored
                null,      // speciality ignored
                StudentType.FISA,
                null,
                null,
                null,
                null,
                null
        );

        Student entity = new Student();
        entity.setFirstName("Original");
        entity.setLastName("Original");
        entity.setNickname("Nick");
        entity.setSpeciality("Spec");
        entity.setType(StudentType.FISA);
        entity.setHumorType(HumorType.FANTOME);
        entity.setCreatedAt(LocalDateTime.of(2020, 1, 1, 0, 0));

        mapper.updateFromDto(dto, entity);

        assertEquals("Original", entity.getFirstName()); // unchanged
        assertEquals("Updated", entity.getLastName());   // updated
        assertEquals("Nick", entity.getNickname());      // unchanged
        assertEquals("Spec", entity.getSpeciality());    // unchanged
        assertEquals(StudentType.FISA, entity.getType()); // updated
        assertEquals(HumorType.FANTOME, entity.getHumorType()); // unchanged
        assertEquals(LocalDateTime.of(2020, 1, 1, 0, 0), entity.getCreatedAt()); // ignored
    }
}
