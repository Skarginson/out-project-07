package com.example.backend.dataHandler.entity;

import jakarta.persistence.Embeddable;
import lombok.Data;

@Embeddable
@Data
public class Stats {
    private int hp;
    private int attack;
    private int defense;
}
