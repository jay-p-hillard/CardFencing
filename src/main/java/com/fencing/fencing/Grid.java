package com.fencing.fencing;

import com.fencing.cards.*;

public final class Grid {
    // Map A(1)–9(9) onto a 3×3 grid in reading order:
    // 1 2 3
    // 4 5 6
    // 7 8 9
    public static record Pos(int row, int col) {}
    public static boolean isGridCard(Rank r) {
        int v = r.value();
        return v >= 1 && v <= 9; // A..9
    }
    public static Pos posOf(Rank r) {
        int v = r.value(); // 1..9
        int idx = v - 1;
        return new Pos(idx / 3, idx % 3);
    }
    public static boolean sameRowOrCol(Rank a, Rank b) {
        Pos pa = posOf(a), pb = posOf(b);
        return pa.row() == pb.row() || pa.col() == pb.col();
    }
    public static boolean adjacentHV(Rank a, Rank b) {
        Pos pa = posOf(a), pb = posOf(b);
        int dr = Math.abs(pa.row()-pb.row());
        int dc = Math.abs(pa.col()-pb.col());
        return (dr + dc == 1); // horiz or vert, not diagonal
    }
}
