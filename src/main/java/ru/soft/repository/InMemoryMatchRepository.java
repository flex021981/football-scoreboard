package ru.soft.repository;

import ru.soft.model.Match;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class InMemoryMatchRepository implements MatchRepository {
    private final List<Match> matches = new CopyOnWriteArrayList<>();

    @Override
    public void addMatch(Match match) {
        matches.add(match);
    }

    @Override
    public void removeMatch(String homeTeam, String awayTeam) {
        matches.removeIf(m -> m.homeTeam().equals(homeTeam) && m.awayTeam().equals(awayTeam));
    }

    @Override
    public void updateMatch(Match match) {
        for (int i = 0; i < matches.size(); i++) {
            Match existing = matches.get(i);
            if (existing.homeTeam().equals(match.homeTeam()) &&
                    existing.awayTeam().equals(match.awayTeam())) {
                matches.set(i, match);
                return;
            }
        }
        throw new IllegalArgumentException("Match not found");
    }

    @Override
    public List<Match> findAll() {
        return new ArrayList<>(matches);
    }
}
