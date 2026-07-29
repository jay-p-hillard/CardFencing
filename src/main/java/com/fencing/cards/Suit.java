package com.fencing.cards;
public enum Suit {
    CLUBS, DIAMONDS, HEARTS, SPADES;

    public char symbol() {
        return switch (this) {
            case CLUBS -> '♣';
            case DIAMONDS -> '♦';
            case HEARTS -> '♥';
            case SPADES -> '♠';
        };
    }

    public boolean isRed() { return this == DIAMONDS || this == HEARTS; }
}

