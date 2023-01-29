package com.example.betsite.controller;

import com.example.betsite.dto.GameDTO;
import com.example.betsite.model.Game;
import com.example.betsite.model.User;
import com.example.betsite.service.CamundaService;
import com.example.betsite.service.GameService;
import com.example.betsite.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/game")
@RequiredArgsConstructor
public class GameController {
    private final CamundaService camundaService;
    private final GameService gameService;
    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<Game>> getGames(@RequestHeader String key) {
        String state = camundaService.getCurrentStateOfProcess(key).getName();
        if (state.equals("Game List")) {
            return ResponseEntity.status(HttpStatus.OK).body(gameService.getGames());
        }
        if (state.equals("Game View")) {
            camundaService.completeTask(key, "back", "true");
            return ResponseEntity.status(HttpStatus.OK).body(gameService.getGames());
        }
        if (state.equals("Your Bets") || state.equals("Create Game")) {
            camundaService.completeTask(key);
            return ResponseEntity.status(HttpStatus.OK).body(gameService.getGames());
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
    }

    @GetMapping("{gameId}")
    public ResponseEntity<Game> getGameById(@RequestHeader String key, @PathVariable String gameId) {
        String state = camundaService.getCurrentStateOfProcess(key).getName();
        if (state.equals("Game List")) {
            camundaService.completeTask(key, "case", "else");
            return ResponseEntity.status(HttpStatus.OK).body(gameService.getGameById(gameId));
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
    }

    @PostMapping
    public void createGame(@RequestHeader String key, @RequestBody GameDTO gameDTO) {
        String userId = camundaService.getVariableFromProcess("userId", key);
        String state = camundaService.getCurrentStateOfProcess(key).getName();
        User user = userService.getUserById(userId);
        if (state.equals("Game List") && user.isAdmin()) {
            // TODO
        }
    }
}
