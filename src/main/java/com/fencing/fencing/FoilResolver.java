package com.fencing.fencing;

import com.fencing.cards.Rank;

public final class FoilResolver {
    public static Outcome resolve(Rank attack, Rank parry) {
        boolean aF = Special.isFleche(attack);
        boolean pF = Special.isFleche(parry);
        boolean aB = Special.isBind(attack);
        boolean pB = Special.isBind(parry);
        boolean aSpec = aF || aB;
        boolean pSpec = pF || pB;

        // NEW: Specials get countered too if same number (10/10, J/J, Q/Q, K/K), regardless of side
        if (aSpec && pSpec && Special.numberLabel(attack) == Special.numberLabel(parry)) {
            return Outcome.COUNTER;
        }

        // Flèche logic: only blocked by Bind (unless already handled as COUNTER above)
        if (aF) {
            if (pB) return Outcome.DEFEND;   // same-number case already handled
            return Outcome.HIT;
        }

        // Parry with Bind blocks any non-Flèche attack (and same-number COUNTER handled above)
        if (pB) {
            return Outcome.DEFEND;
        }

        // Regular grid (A..9) resolution
        boolean aGrid = Grid.isGridCard(attack);
        boolean pGrid = Grid.isGridCard(parry);
        if (aGrid && pGrid) {
            if (attack == parry) return Outcome.RIPOSTE;
            return Grid.sameRowOrCol(attack, parry) ? Outcome.DEFEND : Outcome.HIT;
        }

        // Fallback: anything else defends (safe default)
        return Outcome.DEFEND;
    }
}
