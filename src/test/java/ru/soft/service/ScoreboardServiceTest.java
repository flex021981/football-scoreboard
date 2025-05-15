package ru.soft.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.soft.model.Match;
import ru.soft.repository.MatchRepository;

import java.time.Instant;
import java.util.List;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ScoreboardServiceTest {
    @Mock
    private MatchRepository repository;

    @InjectMocks
    private ScoreboardService scoreboardService;

    private Match match1;
    private Match match2;
    private Match match3;

    @BeforeEach
    void setUp() {
        match1 = new Match("Home1", "Away1", 1, 0, Instant.now().minusSeconds(60));
        match2 = new Match("Home2", "Away2", 2, 2, Instant.now().minusSeconds(30));
        match3 = new Match("Home3", "Away3", 3, 1, Instant.now());
    }

    @Test
    void shouldStartGame() {
        scoreboardService.startGame("Home", "Away");

        verify(repository).addMatch(argThat(match ->
                match.homeTeam().equals("Home") &&
                        match.awayTeam().equals("Away") &&
                        match.homeScore() == 0 &&
                        match.awayScore() == 0
        ));
    }

    @Test
    void shouldFinishGame() {
        scoreboardService.finishGame("Home1", "Away1");

        verify(repository).removeMatch("Home1", "Away1");
    }

    @Test
    void shouldUpdateScore() {
        when(repository.findAll()).thenReturn(List.of(match1));

        scoreboardService.updateScore("Home1", "Away1", 2, 1);

        verify(repository).updateMatch(argThat(match ->
                match.homeTeam().equals("Home1") &&
                        match.awayTeam().equals("Away1") &&
                        match.homeScore() == 2 &&
                        match.awayScore() == 1
        ));
    }

    @Test
    void shouldThrowWhenUpdatingNonExistentMatch() {
        when(repository.findAll()).thenReturn(List.of());

        assertThatThrownBy(() ->
                scoreboardService.updateScore("Home1", "Away1", 2, 1))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Match not found");

        verify(repository, never()).updateMatch(any());
    }

    @Test
    void shouldGetSummaryOrderedByTotalScoreAndStartTime() {
        when(repository.findAll()).thenReturn(List.of(match1, match2, match3));

        List<Match> summary = scoreboardService.getSummary();

        assertThat(summary)
                .extracting(Match::homeTeam)
                .containsExactly("Home3", "Home2", "Home1");
    }

    @Test
    void shouldOrderByStartTimeWhenTotalScoreEqual() {
        Match highScoreOld = new Match("Old", "Team", 3, 2, Instant.now().minusSeconds(60));
        Match highScoreNew = new Match("New", "Team", 3, 2, Instant.now());

        when(repository.findAll()).thenReturn(List.of(highScoreOld, highScoreNew));

        List<Match> summary = scoreboardService.getSummary();

        assertThat(summary)
                .extracting(Match::homeTeam)
                .containsExactly("New", "Old");
    }
}