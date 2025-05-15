# Football World Cup Scoreboard - README

## Overview

This project implements a live Football World Cup Scoreboard that tracks matches and scores with the following operations:

- Start a new game (initial score 0-0)
- Finish an existing game
- Update match scores
- Get a summary of games ordered by total score (descending) and start time (most recent first for ties)

## Features

- Immutable domain model using Java Records
- Clean architecture with separation of concerns
- Thread-safe in-memory repository
- Comprehensive unit tests with Mockito and AssertJ
- Proper sorting according to business requirements

## Requirements

- Java 17+
- Gradle 7.6+

## Installation

1. Clone the repository:
```bash
git clone https://github.com/flex021981/football-scoreboard.git
```

2. Build the project:
```bash
cd football-scoreboard
gradle build
```

## Usage

### Running the Application

```bash
gradle run
```

### Example Code

```java
// Initialize scoreboard
MatchRepository repository = new InMemoryMatchRepository();
ScoreboardService scoreboard = new ScoreboardService(repository);

// Start games
scoreboard.startGame("Mexico", "Canada");
scoreboard.startGame("Spain", "Brazil");

// Update scores
scoreboard.updateScore("Mexico", "Canada", 0, 5);
scoreboard.updateScore("Spain", "Brazil", 10, 2);

// Get summary
List<Match> summary = scoreboard.getSummary();
summary.forEach(System.out::println);

// Finish game
scoreboard.finishGame("Mexico", "Canada");
```

### Expected Output

```
Spain 10 - Brazil 2
Mexico 0 - Canada 5
```

## Project Structure

```
src/
├── main/
│   └── java/
│       ├── domain/        # Domain models
│       ├── repository/    # Data persistence
│       ├── service/       # Business logic
│       └── FootballScoreboardApp.java  # Main application
└── test/
    └── java/             # Unit and integration tests
```

## Testing

Run all tests:
```bash
gradle test
```

Test reports will be generated in `build/reports/tests/`

## Sorting Algorithm

The summary is sorted by:
1. Total score (home + away) in descending order
2. Start time in descending order (most recent first) for matches with equal total scores

Example:
```
Uruguay 6 - Italy 6     (highest total score)
Spain 10 - Brazil 2     (next highest)
Mexico 0 - Canada 5     (lower score)
Germany 2 - France 2    (tie, ordered by time)
```

## Contributing

1. Fork the project
2. Create your feature branch (`git checkout -b feature/your-feature`)
3. Commit your changes (`git commit -am 'Add some feature'`)
4. Push to the branch (`git push origin feature/your-feature`)
5. Open a pull request

## License

This project is licensed under the MIT License.