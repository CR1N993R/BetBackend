package com.example.betsite.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class GameDTO {
    public String teamA;
    public String teamB;
    public String sport;
    public LocalDateTime date;
}
