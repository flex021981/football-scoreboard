package ru.soft.service;

import ru.soft.model.Match;
import ru.soft.repository.MatchRepository;

import java.util.Comparator;
import java.util.List;

public class ScoreboardService {
    private final MatchRepository repository;

    public ScoreboardService(MatchRepository repository) {
        this.repository = repository;
    }

    public void startGame(String homeTeam, String awayTeam) {
        repository.addMatch(new Match(homeTeam, awayTeam));
    }

    public void finishGame(String homeTeam, String awayTeam) {
        repository.removeMatch(homeTeam, awayTeam);
    }

    public void updateScore(String homeTeam, String awayTeam, int homeScore, int awayScore) {
        repository.findAll().stream()
                .filter(m -> m.homeTeam().equals(homeTeam) && m.awayTeam().equals(awayTeam))
                .findFirst()
                .ifPresentOrElse(
                        match -> repository.updateMatch(match.withScores(homeScore, awayScore)),
                        () -> { throw new IllegalArgumentException("Match not found"); }
                );
    }

    public List<Match> getSummary() {
        return repository.findAll().stream()
                .sorted(Comparator
                        .comparingInt(Match::totalScore).reversed()
                        .thenComparing(Comparator.comparing(Match::startTime).reversed()))
                .toList();
    }
}