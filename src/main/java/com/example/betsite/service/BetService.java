package com.example.betsite.service;

import com.example.betsite.model.Bet;
import com.example.betsite.model.Game;
import com.example.betsite.repository.BetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BetService {
    private final BetRepository betRepository;

    public Bet getBetById(String id) {
        return betRepository.findBetById(id);
    }

    public List<Bet> getBetsByGame(Game game) {
        return betRepository.findAllByGame(game);
    }

    public void createBet(Bet bet) {
        betRepository.saveAndFlush(bet);
    }

    public Bet getByUserIdAndGameId(String userId, String gameId) {
        return betRepository.findFirstByGameIdAndUserId(gameId, userId);
    }

    public List<Bet> getBetsByUser(String userId) {
        return betRepository.findAllByUserId(userId);
    }
}
