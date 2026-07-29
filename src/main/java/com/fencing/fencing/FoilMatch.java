package com.fencing.fencing;

import com.fencing.cards.*;

import java.util.Deque;

/**
 * Runs a Foil match to a target score using separate color piles.
 * Player A draws from BLACK suits; Player B draws from RED suits.
 * Attacker alternates after each exchange (customize if your rules differ).
 */
public final class FoilMatch {

    public enum Player { A, B }

    private final int target;
    private final Deque<Card> aPile; // black
    private final Deque<Card> bPile; // red

    private Player attacker;         // who has priority (Joker)
    private int scoreA = 0;
    private int scoreB = 0;
    private int exchanges = 0;

    public FoilMatch(int target, Player startingAttacker) {
        this.target = target;
        // Build a fresh shuffled deck, split by color
        Deck d = Deck.standard(true, false);
        ColorPile piles = new ColorPile(d);
        this.aPile = piles.black; // Player A uses BLACK pile
        this.bPile = piles.red;   // Player B uses RED pile
        this.attacker = startingAttacker;
    }

    /** Play until someone hits target, or we run out of cards. */
    public Player run() {
        while (scoreA < target && scoreB < target && canContinue()) {
            step();
        }
        return (scoreA >= target) ? Player.A :
                (scoreB >= target) ? Player.B : null; // null means deck exhaustion
    }
    public Exchange step() {
        // stop if match finished
        if (scoreA >= target || scoreB >= target || aPile.isEmpty() || bPile.isEmpty()) {
            return null;
        }

        exchanges++;
        Player attackerBefore = attacker;

        Card atkCard;
        Card defCard;

        if (attacker == Player.A) {
            atkCard = aPile.removeLast();
            defCard = bPile.removeLast();
        } else {
            atkCard = bPile.removeLast();
            defCard = aPile.removeLast();
        }

        Outcome o = FoilResolver.resolve(atkCard.rank(), defCard.rank());

        // scoring
        switch (o) {
            case HIT -> {
                if (attacker == Player.A) scoreA++; else scoreB++;
            }
            case RIPOSTE, COUNTER -> {
                if (attacker == Player.A) scoreB++; else scoreA++;
            }
            case DEFEND -> {}
        }

        // your rule: don't flip on riposte
        if (o != Outcome.RIPOSTE) {
            attacker = (attacker == Player.A) ? Player.B : Player.A;
        }

        return new Exchange(
                exchanges,
                attackerBefore,
                atkCard,
                defCard,
                o,
                scoreA,
                scoreB,
                attacker
        );
    }
    public record Exchange(
            int number,
            FoilMatch.Player attackerBefore,
            Card attackCard,
            Card defendCard,
            Outcome outcome,
            int scoreA,
            int scoreB,
            FoilMatch.Player attackerAfter
    ) {}
    /** One foil exchange: each side draws one card, resolve, score, switch attacker. */
    private void playOneExchange() {
        exchanges++;

        // Draw attacker/defender cards from their piles
        Card atkCard, defCard;
        if (attacker == Player.A) {
            atkCard = draw(aPile);
            defCard = draw(bPile);
        } else {
            atkCard = draw(bPile);
            defCard = draw(aPile);
        }

        Outcome o = FoilResolver.resolve(atkCard.rank(), defCard.rank());

        // Score based on outcome
        switch (o) {
            case HIT -> {
                if (attacker == Player.A) scoreA++; else scoreB++;
            }
            case RIPOSTE, COUNTER -> {
                // defender scores
                if (attacker == Player.A) scoreB++; else scoreA++;
            }
            case DEFEND -> { /* no point */ }
        }

        // (Optional) print a minimal log; remove if your grader forbids prints
        System.out.printf(
                "Exch %d | Attacker=%s | %s vs %s => %s | Score A-B: %d-%d%n",
                exchanges, attacker, atkCard, defCard, o, scoreA, scoreB
        );

        // Flip attacker after each exchange (adjust if your table rules differ)
        attacker = (o != Outcome.RIPOSTE)
                ? (attacker == Player.A ? Player.B : Player.A)
                : attacker;
    }

    private static Card draw(Deque<Card> pile) {
        // Take from top; ColorPile pushes “top” at the end, so removeLast()
        return pile.removeLast();
    }

    private boolean canContinue() {
        // Need at least one card for each side to run the next exchange
        return !aPile.isEmpty() && !bPile.isEmpty();
    }

    // --- Getters for UI/tests ---
    public int scoreA() { return scoreA; }
    public int scoreB() { return scoreB; }
    public Player attacker() { return attacker; }
    public int exchanges() { return exchanges; }

    /** Quick demo runner */
    public static void main(String[] args) {
        FoilMatch match = new FoilMatch(5, Player.A); // first to 5, A starts
        Player winner = match.run();
        System.out.println("Winner: " + winner + " | Final score A-B: " +
                match.scoreA() + "-" + match.scoreB());
    }
}
