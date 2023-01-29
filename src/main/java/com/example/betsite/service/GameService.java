package com.example.betsite.service;

import com.example.betsite.model.Game;
import com.example.betsite.repository.GameRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GameService {
    private final GameRepository gameRepository;

    public List<Game> getGames() {
        return gameRepository.findAll();
    }

    public Game getGameById(String id) {
        return gameRepository.getGameById(id);
    }


}
