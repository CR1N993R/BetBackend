package com.example.betsite.repository;

import com.example.betsite.model.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface GameRepository extends JpaRepository<Game, String> {
    Game getGameById(String id);
    List<Game> getGamesByDateAfter(LocalDateTime date);
}
