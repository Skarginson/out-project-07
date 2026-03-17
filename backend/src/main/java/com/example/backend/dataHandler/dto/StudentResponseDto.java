package com.example.backend.dataHandler.dto;

public record StudentResponseDto(
        Long id,
        String firstName,
        String lastName,
        String nickname,
        String speciality,
        com.example.backend.utils.StudentType type,
        com.example.backend.utils.HumorType humorType,
        StatsDto stats,
        String superPower,
        String catchPhrase,
        String imageUrl,
        java.time.LocalDateTime createdAt
) {}