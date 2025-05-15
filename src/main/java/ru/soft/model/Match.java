package ru.soft.model;

import java.time.Instant;

public record Match(
        String homeTeam,
        String awayTeam,
        int homeScore,
        int awayScore,
        Instant startTime
) {
    public Match {
        if (homeTeam == null || awayTeam == null || homeTeam.isBlank() || awayTeam.isBlank()) {
            throw new IllegalArgumentException("Team names cannot be null or empty");
        }
        if (homeScore < 0 || awayScore < 0) {
            throw new IllegalArgumentException("Scores cannot be negative");
        }
    }

    public Match(String homeTeam, String awayTeam) {
        this(homeTeam, awayTeam, 0, 0, Instant.now());
    }

    public Match withScores(int newHomeScore, int newAwayScore) {
        return new Match(homeTeam, awayTeam, newHomeScore, newAwayScore, startTime);
    }

    public int totalScore() {
        return homeScore + awayScore;
    }

    @Override
    public String toString() {
        return "%s %d - %s %d".formatted(homeTeam, homeScore, awayTeam, awayScore);
    }
}