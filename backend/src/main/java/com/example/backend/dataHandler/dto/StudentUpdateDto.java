package com.example.backend.dataHandler.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class StudentUpdateDto {
    private Long id;
    @NotBlank private String firstName;
    @NotBlank private String lastName;
    private String nickname;
    private String speciality;
    @NotNull private com.example.backend.utils.StudentType type;
    private com.example.backend.utils.HumorType humorType;
    private StatsDto stats;
    private String superPower;
    private String catchPhrase;
    private String imageUrl;
}
