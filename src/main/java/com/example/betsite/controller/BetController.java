package com.example.betsite.controller;

import com.example.betsite.dto.BetDTO;
import com.example.betsite.model.Bet;
import com.example.betsite.model.Game;
import com.example.betsite.model.User;
import com.example.betsite.service.BetService;
import com.example.betsite.service.CamundaService;
import com.example.betsite.service.GameService;
import com.example.betsite.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bet")
@RequiredArgsConstructor
public class BetController {
    private final UserService userService;
    private final CamundaService camundaService;
    private final BetService betService;
    private final GameService gameService;

    @PostMapping
    public void placeBet(@RequestBody BetDTO betDTO, @RequestHeader String key) {
        String state = camundaService.getCurrentStateOfProcess(key).getName();
        if (state.equals("Game View")) {
            User user = userService.getUserById(camundaService.getVariableFromProcess("userId", key));
            Game game = gameService.getGameById(betDTO.getGameId());
            Bet bet = new Bet(betDTO.getTeamA(), betDTO.getTeamB(), user, game);
            betService.createBet(bet);
            camundaService.completeTask(key, "back", "true");
        }
    }

    @GetMapping("{gameId}")
    public ResponseEntity<Bet> getBetByUserAndGame(@PathVariable String gameId, @RequestHeader String key) {
        String state = camundaService.getCurrentStateOfProcess(key).getName();
        if (state.equals("Game List")) {
            User user = userService.getUserById(camundaService.getVariableFromProcess("userId", key));
            Bet bet = betService.getByUserIdAndGameId(user.getId(), gameId);
            camundaService.completeTask(key, "case", "none");
            return ResponseEntity.status(HttpStatus.OK).body(bet);
        }
        if (state.equals("Game View")) {
            User user = userService.getUserById(camundaService.getVariableFromProcess("userId", key));
            Bet bet = betService.getByUserIdAndGameId(user.getId(), gameId);
            return ResponseEntity.status(HttpStatus.OK).body(bet);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
    }

    @GetMapping
    public ResponseEntity<List<Bet>> getBetsByUser(@RequestHeader String key) {
        String state = camundaService.getCurrentStateOfProcess(key).getName();
        if (state.equals("Game List") || state.equals("Your Bets")) {
            camundaService.completeTask(key, "case", "bets");
            String userId = camundaService.getVariableFromProcess("userId", key);
            List<Bet> bet = betService.getBetsByUser(userId);
            return ResponseEntity.status(HttpStatus.OK).body(bet);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
    }
}
