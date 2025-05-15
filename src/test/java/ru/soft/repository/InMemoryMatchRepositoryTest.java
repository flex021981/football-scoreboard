package ru.soft.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.soft.model.Match;

import java.time.Instant;
import java.util.List;
import static org.assertj.core.api.Assertions.*;

class InMemoryMatchRepositoryTest {
    private InMemoryMatchRepository repository;
    private Match match1;
    private Match match2;

    @BeforeEach
    void setUp() {
        repository = new InMemoryMatchRepository();
        match1 = new Match("Home1", "Away1", 1, 0, Instant.now());
        match2 = new Match("Home2", "Away2", 2, 2, Instant.now());
    }

    @Test
    void shouldAddAndFindAllMatches() {
        repository.addMatch(match1);
        repository.addMatch(match2);

        List<Match> matches = repository.findAll();
        assertThat(matches)
                .hasSize(2)
                .containsExactlyInAnyOrder(match1, match2);
    }

    @Test
    void shouldRemoveMatch() {
        repository.addMatch(match1);
        repository.addMatch(match2);

        repository.removeMatch("Home1", "Away1");

        assertThat(repository.findAll())
                .hasSize(1)
                .containsExactly(match2);
    }

    @Test
    void shouldUpdateMatch() {
        repository.addMatch(match1);
        Match updated = match1.withScores(3, 1);

        repository.updateMatch(updated);

        assertThat(repository.findAll())
                .hasSize(1)
                .first()
                .extracting(Match::homeScore, Match::awayScore)
                .containsExactly(3, 1);
    }

    @Test
    void shouldThrowWhenUpdatingNonExistentMatch() {
        assertThatThrownBy(() -> repository.updateMatch(match1))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Match not found");
    }
}