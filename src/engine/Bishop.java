package engine;

import chess.PieceType;
import chess.PlayerColor;

public class Bishop extends Piece {

    private static Ruleset ruleset = new Ruleset();

    public PieceType getType() {
        return PieceType.BISHOP;
    }

    public Bishop(int x, int y, PlayerColor color) {
        super(x, y, color);
    }


}