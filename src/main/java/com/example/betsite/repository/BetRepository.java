package com.example.betsite.repository;

import com.example.betsite.model.Bet;
import com.example.betsite.model.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BetRepository extends JpaRepository<Bet, String> {
    Bet findBetById(String id);
    List<Bet> findAllByGame(Game game);
    Bet findFirstByGameIdAndUserId(String game_id, String user_id);
    List<Bet> findAllByUserId(String user_id);
}
