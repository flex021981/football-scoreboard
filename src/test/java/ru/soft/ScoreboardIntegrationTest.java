package ru.soft;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.soft.model.Match;
import ru.soft.service.ScoreboardService;
import ru.soft.repository.*;
import java.util.List;
import static org.assertj.core.api.Assertions.*;

class ScoreboardIntegrationTest {
    private ScoreboardService scoreboardService;
    private MatchRepository repository;

    @BeforeEach
    void setUp() {
        repository = new InMemoryMatchRepository();
        scoreboardService = new ScoreboardService(repository);
    }

    @Test
    void shouldMaintainCorrectOrderAfterMultipleOperations() {
        // Start games
        scoreboardService.startGame("Mexico", "Canada");
        scoreboardService.startGame("Spain", "Brazil");
        scoreboardService.startGame("Germany", "France");

        // Update scores
        scoreboardService.updateScore("Mexico", "Canada", 0, 5);
        scoreboardService.updateScore("Spain", "Brazil", 10, 2);
        scoreboardService.updateScore("Germany", "France", 2, 2);

        // Finish one game
        scoreboardService.finishGame("Germany", "France");

        // Start another game
        scoreboardService.startGame("Uruguay", "Italy");
        scoreboardService.updateScore("Uruguay", "Italy", 6, 6);

        // Get summary
        List<Match> summary = scoreboardService.getSummary();

        assertThat(summary)
                .hasSize(3)
                .extracting(Match::toString)
                .containsExactly(
                        "Uruguay 6 - Italy 6",
                        "Spain 10 - Brazil 2",
                        "Mexico 0 - Canada 5"
                );
    }
}
