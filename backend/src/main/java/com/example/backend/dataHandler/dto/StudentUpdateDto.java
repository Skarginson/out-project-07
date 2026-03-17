package com.example.backend.dataHandler.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record StudentUpdateDto(
        Long id,
        @NotBlank String firstName,
        @NotBlank String lastName,
        String nickname,
        String speciality,
        @NotNull com.example.backend.utils.StudentType type,
        com.example.backend.utils.HumorType humorType,
        StatsDto stats,
        String superPower,
        String catchPhrase,
        String imageUrl
) {}
