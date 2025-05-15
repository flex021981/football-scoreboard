package ru.soft;

import ru.soft.model.Match;
import ru.soft.repository.InMemoryMatchRepository;
import ru.soft.repository.MatchRepository;
import ru.soft.service.ScoreboardService;

import java.util.List;

public class FootballScoreboardApp {
    public static void main(String[] args) throws InterruptedException {
        // Initialize dependencies
        MatchRepository repository = new InMemoryMatchRepository();
        ScoreboardService scoreboard = new ScoreboardService(repository);

        // Add matches with slight delays to create different timestamps
        scoreboard.startGame("Mexico", "Canada");
        Thread.sleep(10);
        scoreboard.startGame("Spain", "Brazil");
        Thread.sleep(10);
        scoreboard.startGame("Germany", "France");
        Thread.sleep(10);
        scoreboard.startGame("Uruguay", "Italy");
        Thread.sleep(10);
        scoreboard.startGame("Argentina", "Australia");

        // Update scores
        scoreboard.updateScore("Mexico", "Canada", 0, 5);
        scoreboard.updateScore("Spain", "Brazil", 10, 2);
        scoreboard.updateScore("Germany", "France", 2, 2);
        scoreboard.updateScore("Uruguay", "Italy", 6, 6);
        scoreboard.updateScore("Argentina", "Australia", 3, 1);

        // Get and display summary
        System.out.println("Summary of matches:");
        List<Match> summary = scoreboard.getSummary();
        for (int i = 0; i < summary.size(); i++) {
            System.out.println((i + 1) + ". " + summary.get(i));
        }
    }
}