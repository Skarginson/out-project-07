package com.example.backend.dataHandler.entity;

import com.example.backend.utils.HumorType;
import com.example.backend.utils.StudentType;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "students")
@Data
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String lastName;
    private String nickname;

    private String speciality;

    @Enumerated(EnumType.STRING)
    private StudentType type;

    @Enumerated(EnumType.STRING)
    private HumorType humorType;

    @Embedded
    private Stats stats;

    private String superPower;
    private String catchPhrase;
    private String imageUrl;

    private LocalDateTime createdAt;
}
