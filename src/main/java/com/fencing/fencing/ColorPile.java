package com.fencing.fencing;

import com.fencing.cards.*;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public final class ColorPile {
    public final Deque<Card> red = new ArrayDeque<>();
    public final Deque<Card> black = new ArrayDeque<>();

    public ColorPile(Deck full) {
        // separate by suit color
        List<Card> reds = new ArrayList<>(), blacks = new ArrayList<>();
        for (Card c : full.view()) {
            boolean isRed = (c.suit() == Suit.HEARTS || c.suit() == Suit.DIAMONDS);
            (isRed ? reds : blacks).add(c);
        }
        Collections.shuffle(reds, ThreadLocalRandom.current());
        Collections.shuffle(blacks, ThreadLocalRandom.current());
        reds.forEach(red::addLast);
        blacks.forEach(black::addLast);
    }
}
