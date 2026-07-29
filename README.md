# Card Fencing

A turn-based card game engine that simulates fencing exchanges — foil, épée, and saber — each with its own rule set for attacks, parries, counters, and (in épée) double touches. Built in Java with a JavaFX viewer.

## Origin

This started as a design for a real-time, Atari-style fencing game: two players choosing from 9 attacks/defenses under a time limit, with hits, parries, and counters resolved based on timing and positioning. While working out the rule set, I started prototyping with a physical deck of cards instead of code — using rank and suit to represent the attack/defense grid — as a fast way to test whether the mechanics actually held up before writing any software.

That paper prototype turned out to be a fully playable game on its own. **Grid Fencing** became the real-time two-player version of the original concept, and **Card Fencing** — this repo — is the turn-based card game that shares its core resolution logic. The rule set here (five game variants: Foil Rush, 5-Card Foil, 5-Card Épée, 3-Card "one touch" Épée, and 5-Card Saber) was fully validated by hand before being implemented in code.

## What this demonstrates

- Translating a hand-designed rule set (grid-based positional resolution, weapon-specific mechanics) into a working object model
- Separating core game logic (deck/hand management, exchange resolution per weapon type) from presentation (JavaFX viewer)
- Modeling multiple related but distinct rule sets (Foil, Épée, Saber) that share structure but diverge in resolution priority and scoring

## Tech stack

- **Java** (Gradle build)
- **JavaFX** for the viewer/UI
- Gradle Wrapper included — no local Gradle install required

## Running it

Clone the repo, then from the project root:

```bash
./gradlew run
```

(Windows: `gradlew.bat run`)

## Game modes

| Mode | Weapon | Summary |
|---|---|---|
| Foil Rush | Foil | Simultaneous draw, first to 5 points — "War," with exchanges |
| 5-Card Foil | Foil | Hand of 5, face-down exchange, right-of-way resolution |
| 5-Card Épée | Épée | Suit strength matters; double touches possible; first to 5 alone wins |
| 3-Card Épée | Épée | "One touch" format — single score wins outright |
| 5-Card Saber | Saber | Adds blocking in addition to parrying; suits define slash direction |

Full rules, including the attack/defense grid and per-weapon resolution tables, are in [`RULES.md`](RULES.md). 
## Development notes

This project grew out of a college-level Java course and was extended with AI-assisted pair programming — design and rule logic are original work; AI tools were used alongside manual coding for implementation and debugging.

## License

MIT License — see [`LICENSE`](LICENSE) for details.
