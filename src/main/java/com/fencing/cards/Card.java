package com.fencing.cards;

import java.util.Objects;

/** Immutable playing card. */
public final class Card implements Comparable<Card> {
    private final Suit suit;
    private final Rank rank;

    public Card(Suit suit, Rank rank) {
        this.suit = Objects.requireNonNull(suit);
        this.rank = Objects.requireNonNull(rank);
    }

    public Suit suit() { return suit; }
    public Rank rank() { return rank; }

    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Card c)) return false;
        return suit == c.suit && rank == c.rank;
    }

    @Override public int hashCode() { return suit.hashCode() * 31 + rank.hashCode(); }

    @Override public String toString() { return rank + String.valueOf(suit.symbol()); }

    /** Natural order: by suit then rank; customize as needed per game. */
    @Override public int compareTo(Card other) {
        int s = suit.compareTo(other.suit);
        return (s != 0) ? s : Integer.compare(rank.value(), other.rank.value());
    }
}

