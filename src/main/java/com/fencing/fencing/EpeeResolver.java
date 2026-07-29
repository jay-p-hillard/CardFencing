package com.fencing.fencing;

import com.fencing.cards.Rank;
import java.util.Arrays;

public final class EpeeResolver {
    public static Outcome resolve(Rank attack, Rank[] parries) {
        // Only positions (A..9) matter for epee per the doc
        long matches = Arrays.stream(parries).filter(p -> p == attack).count();
        if (matches == 0) return Outcome.HIT;
        if (matches == 1) return Outcome.DEFEND;
        return Outcome.COUNTER; // 2+ matches
    }
}
