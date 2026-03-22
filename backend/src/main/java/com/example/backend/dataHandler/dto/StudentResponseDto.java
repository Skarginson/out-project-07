package com.example.backend.dataHandler.dto;

import com.example.backend.utils.HumorType;
import com.example.backend.utils.StudentType;

public record StudentResponseDto(
        Long id,
        String firstName,
        String lastName,
        String nickname,
        String speciality,
        StudentType type,
        HumorType humorType,
        StatsDto stats,
        String superPower,
        String catchPhrase,
        String imageUrl,
        java.time.LocalDateTime createdAt
) {}