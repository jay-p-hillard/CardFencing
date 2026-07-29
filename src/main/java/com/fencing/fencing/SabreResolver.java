package com.fencing.fencing;

import com.fencing.cards.Rank;

public final class SabreResolver {
    public static final class Result {
        public final Outcome outcome;
        public final boolean switchRoles;
        public Result(Outcome o, boolean s) { outcome = o; switchRoles = s; }
    }

    public static Result resolve(Rank[] atk, Rank[] pry) {
        // Expect 3 cards each
        for (int i = 0; i < 3; i++) {
            Outcome o = FoilResolver.resolve(atk[i], pry[i]);
            switch (o) {
                case HIT -> { return new Result(Outcome.HIT, true); }
                case RIPOSTE -> { return new Result(Outcome.RIPOSTE, true); }
                case DEFEND -> {
                    // If grid cards and adjacent H/V → attack ends immediately (no point), roles switch
                    if (Grid.isGridCard(atk[i]) && Grid.isGridCard(pry[i]) && Grid.adjacentHV(atk[i], pry[i])) {
                        return new Result(Outcome.DEFEND, true);
                    }
                    // else continue unless this was the last pair
                    if (i == 2) return new Result(Outcome.DEFEND, true); // sequence over → switch roles
                    // continue loop
                }
                case COUNTER -> { return new Result(Outcome.COUNTER, true); }
            }
        }
        // Shouldn’t reach here
        return new Result(Outcome.DEFEND, true);
    }
}
