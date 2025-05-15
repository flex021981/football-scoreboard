package ru.soft.repository;

import ru.soft.model.Match;

import java.util.List;

public interface MatchRepository {
    void addMatch(Match match);
    void removeMatch(String homeTeam, String awayTeam);
    void updateMatch(Match match);
    List<Match> findAll();
}
