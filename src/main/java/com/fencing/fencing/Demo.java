package com.fencing.fencing;
import com.fencing.cards.*;

public class Demo {
    public static void main(String[] args) {
        Deck deck = Deck.standard(true, false); // shuffled 52
        var hand = deck.draw(5);
        System.out.println("Hand: " + hand);
        System.out.println("Remaining: " + deck.size());
        var dealt = deck.deal(4, 5);
        System.out.println("P1: " + dealt.get(0));
    }
}
