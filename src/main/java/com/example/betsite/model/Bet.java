package com.example.betsite.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Getter
@NoArgsConstructor
public class Bet {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name="UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String id;
    private int teamA;
    private int teamB;
    @ManyToOne
    @JoinColumn
    @JsonIgnore
    private User user;
    @ManyToOne
    @JoinColumn
    private Game game;

    public Bet(int teamA, int teamB, User user, Game game) {
        this.teamA = teamA;
        this.teamB = teamB;
        this.user = user;
        this.game = game;
    }
}
