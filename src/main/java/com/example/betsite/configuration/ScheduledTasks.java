package com.example.betsite.configuration;

import com.example.betsite.model.Game;
import com.example.betsite.service.GameService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Random;

@Component
@RequiredArgsConstructor
public class ScheduledTasks {
    private final GameService gameService;

    @Scheduled(fixedRate = 10000)
    public void cleanUpGames() {
        for (Game game : gameService.getGamesBefore()) {
            if (game.isDone()) {
                continue;
            }
            LocalDateTime time = LocalDateTime.now();
            if (game.getDate().isBefore(time)) {
                game.setScoreTeamB(new Random().nextInt((10) + 1));
                game.setScoreTeamA(new Random().nextInt((10) + 1));
                game.setDone(true);
                gameService.saveGame(game);
            }
        }
    }
}
