package com.fencing.fencing;

import com.fencing.cards.Rank;

public final class Special {
    public static boolean isFleche(Rank r) { return r == Rank.TEN; }
    public static boolean isBind(Rank r)    { return r == Rank.JACK || r == Rank.QUEEN || r == Rank.KING; }

    // Number label (for “same number” flèche/bind counter): use 10 for fleche; 11/12/13 for J/Q/K
    public static int numberLabel(Rank r) {
        if (isFleche(r)) return 10;
        if (r == Rank.JACK) return 11;
        if (r == Rank.QUEEN) return 12;
        if (r == Rank.KING) return 13;
        return r.value(); // A..9 = 1..9
    }
}
