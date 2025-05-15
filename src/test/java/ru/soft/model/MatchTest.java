package ru.soft.model;

import org.junit.jupiter.api.Test;
import java.time.Instant;
import static org.assertj.core.api.Assertions.*;

class MatchTest {

    @Test
    void shouldCreateNewMatchWithZeroScores() {
        Match match = new Match("Home", "Away");

        assertThat(match.homeTeam()).isEqualTo("Home");
        assertThat(match.awayTeam()).isEqualTo("Away");
        assertThat(match.homeScore()).isZero();
        assertThat(match.awayScore()).isZero();
        assertThat(match.startTime()).isBeforeOrEqualTo(Instant.now());
    }

    @Test
    void shouldCalculateTotalScoreCorrectly() {
        Match match = new Match("Home", "Away", 3, 2, Instant.now());
        assertThat(match.totalScore()).isEqualTo(5);
    }

    @Test
    void shouldUpdateScoresImmutable() {
        Match original = new Match("Home", "Away", 0, 0, Instant.now());
        Match updated = original.withScores(2, 1);

        assertThat(original.homeScore()).isZero();
        assertThat(original.awayScore()).isZero();
        assertThat(updated.homeScore()).isEqualTo(2);
        assertThat(updated.awayScore()).isEqualTo(1);
    }

    @Test
    void shouldRejectNegativeScores() {
        assertThatThrownBy(() -> new Match("Home", "Away", -1, 0, Instant.now()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Scores cannot be negative");
    }

    @Test
    void shouldRejectEmptyTeamNames() {
        assertThatThrownBy(() -> new Match("", "Away"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Team names cannot be null or empty");
    }
}