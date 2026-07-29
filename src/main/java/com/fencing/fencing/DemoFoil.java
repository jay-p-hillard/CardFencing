package com.fencing.fencing;

import com.fencing.cards.*;
import com.fencing.fencing.*;

public class DemoFoil {
    public static void main(String[] args) {
        // Build a standard deck; split to color piles
        Deck d = Deck.standard(true, false);
        ColorPile piles = new ColorPile(d);

        // Attacker draws top of black, defender top of red (or vice versa—your table rule)
        Card atk = piles.black.removeLast();
        Card pry = piles.red.removeLast();

        Rank ar = atk.rank(), pr = pry.rank();
        Outcome o = FoilResolver.resolve(ar, pr);

        System.out.println("Attack: " + atk + "  Parry: " + pry + "  → " + o);
    }
}
