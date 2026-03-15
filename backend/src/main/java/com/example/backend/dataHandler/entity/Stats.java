package com.example.backend.dataHandler.entity;

import jakarta.persistence.Embeddable;

@Embeddable
public class Stats {
    private int hp;
    private int attack;
    private int defense;
}
