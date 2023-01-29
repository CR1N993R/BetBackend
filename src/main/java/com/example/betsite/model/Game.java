package com.example.betsite.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
public class Game {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name="UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String id;
    private String teamA;
    private String teamB;
    private LocalDateTime date;
    private boolean done;
    private int scoreTeamA;
    private int scoreTeamB;
    private String sport;

    public Game(String teamA, String teamB, LocalDateTime date, String sport) {
        this.teamA = teamA;
        this.teamB = teamB;
        this.date = date;
        this.sport = sport;
    }
}
