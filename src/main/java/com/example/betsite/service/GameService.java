package com.example.betsite.service;

import com.example.betsite.dto.GameDTO;
import com.example.betsite.model.Game;
import com.example.betsite.repository.GameRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GameService {
    private final GameRepository gameRepository;

    public List<Game> getGames() {
        return gameRepository.getGamesByDateAfter(LocalDateTime.now());
    }

    public List<Game> getGamesBefore() {
        return gameRepository.getGamesByDateBeforeAndDoneEquals(LocalDateTime.now(), false);
    }

    public Game getGameById(String id) {
        return gameRepository.getGameById(id);
    }

    public void saveGame(GameDTO gameDTO) {
        gameRepository.saveAndFlush(new Game(gameDTO.teamA, gameDTO.teamB, gameDTO.date, gameDTO.sport));
    }

    public void saveGame(Game game) {
        gameRepository.saveAndFlush(game);
    }
}
