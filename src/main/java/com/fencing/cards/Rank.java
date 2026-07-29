package com.fencing.cards;

public enum Rank {
    ACE(1), TWO(2), THREE(3), FOUR(4), FIVE(5), SIX(6),
    SEVEN(7), EIGHT(8), NINE(9), TEN(10), JACK(11), QUEEN(12), KING(13);

    private final int value;
    Rank(int value) { this.value = value; }
    public int value() { return value; }

    @Override public String toString() {
        return switch (this) {
            case ACE -> "A"; case JACK -> "J"; case QUEEN -> "Q"; case KING -> "K";
            default -> String.valueOf(value);
        };
    }
}

