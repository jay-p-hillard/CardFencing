package com.fencing.cards;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public final class Deck {
    private final List<Card> cards;        // remaining cards (top at end)
    private final boolean includeJokers;

    /** Build a standard 52-card deck (optionally 54 with jokers), shuffled or not. */
    public static Deck standard(boolean shuffle, boolean includeJokers) {
        List<Card> all = new ArrayList<>(includeJokers ? 54 : 52);
        for (Suit s : Suit.values()) {
            for (Rank r : Rank.values()) {
                all.add(new Card(s, r));
            }
        }
        if (includeJokers) {
            // Represent jokers however you like; here’s one simple approach:
            // all.add(new JokerCard(Color.RED)); all.add(new JokerCard(Color.BLACK));
            // Or skip jokers if not needed.
        }
        if (shuffle) Collections.shuffle(all, ThreadLocalRandom.current());
        return new Deck(all, includeJokers);
    }

    private Deck(List<Card> initial, boolean includeJokers) {
        this.cards = new ArrayList<>(Objects.requireNonNull(initial));
        this.includeJokers = includeJokers;
    }

    /** Number of cards remaining in the deck. */
    public int size() { return cards.size(); }

    /** Is the deck empty? */
    public boolean isEmpty() { return cards.isEmpty(); }

    /** Peek at top (no remove). */
    public Card top() { return cards.get(cards.size() - 1); }

    /** Draw one from the top; throws if empty. */
    public Card draw() { return cards.remove(cards.size() - 1); }

    /** Draw up to n cards (throws if insufficient). */
    public List<Card> draw(int n) {
        if (n < 0 || n > cards.size()) throw new IllegalArgumentException("n=" + n);
        List<Card> hand = cards.subList(cards.size() - n, cards.size());
        List<Card> out = new ArrayList<>(hand);
        hand.clear(); // removes from deck
        return out;
    }

    /** Deal hands to players: count hands of size handSize. */
    public List<List<Card>> deal(int players, int handSize) {
        if (players <= 0 || handSize < 0) throw new IllegalArgumentException();
        int total = players * handSize;
        if (total > cards.size()) throw new IllegalStateException("Not enough cards");
        List<List<Card>> hands = new ArrayList<>(players);
        for (int p = 0; p < players; p++) {
            hands.add(new ArrayList<>(handSize));
        }
        // round-robin dealing
        for (int i = 0; i < handSize; i++) {
            for (int p = 0; p < players; p++) {
                hands.get(p).add(draw());
            }
        }
        return hands;
    }

    /** Shuffle remaining cards. */
    public void shuffle() { Collections.shuffle(cards, ThreadLocalRandom.current()); }

    /** View of remaining cards (read-only). */
    public List<Card> view() { return Collections.unmodifiableList(cards); }

    /** Reset to a fresh deck (same jokers setting), optionally shuffled. */
    public void reset(boolean shuffle) {
        Deck fresh = Deck.standard(shuffle, includeJokers);
        cards.clear();
        cards.addAll(fresh.cards);
    }
}

