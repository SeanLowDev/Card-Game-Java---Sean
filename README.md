# Rummy Card Game

A Java implementation of Rummy (including a Gin Rummy variant), built with Gradle. Supports human vs. computer play, with multiple AI strategies of varying difficulty.

## Features

- Human vs. Computer gameplay
- Standard Rummy and Gin Rummy variants
- Multiple computer opponents (`Computer`, `SmartComputer`) with different play strategies
- Turn-based dealing, melding, and scoring logic
- Extensible component architecture using the Adaptor and Factory patterns (cards, decks, hands, buttons, actors, colors, fonts, etc.)
- Observer pattern for game/button events (`ButtonObserver`, `HandObserver`)

## Project Structure

```
app/src/main/java/rummy/
├── Driver.java              # Application entry point
├── Rummy.java                # Core game logic
├── Dealer.java                # Deals cards to players
├── MeldHandler.java          # Handles melds/sets/runs
├── player/                   # Human and Computer player implementations
├── card/, deck/, hand/       # Card, deck, and hand models
├── strategy/                 # AI decision-making strategies
├── score/                    # Scoring logic
└── ...                        # Supporting adaptors/factories (actor, button, color, font, etc.)
```

## Requirements

- JDK 21
- Gradle (wrapper included — no separate install needed)

## Getting Started

Clone the repository and run the app using the included Gradle wrapper:

```bash
git clone https://github.com/SeanLowDev/Rummy-Card-Game.git
cd Rummy-Card-Game

# Windows
gradlew.bat run

# macOS/Linux
./gradlew run
```

## Running Tests

```bash
# Windows
gradlew.bat test

# macOS/Linux
./gradlew test
```

## Documentation

Design and sequence diagrams, along with the project report, are available in the [documentation](documentation/) folder:

- `Full_Design_Diagram.pdf` — overall system design
- `DesignDiagram_Feature1.pdf`, `DesignDiagram_Feature2and3.pdf` — feature-specific design diagrams
- `SequenceDiagram.pdf` — sequence diagram of gameplay flow
- `report.pdf` — project report

## Tech Stack

- Java 21
- Gradle
- JUnit 4
- Guava
