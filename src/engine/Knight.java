package engine;

import chess.PieceType;
import chess.PlayerColor;

public class Knight extends Piece {

    private static Ruleset ruleset = new Ruleset();

    public PieceType getType() {
        return PieceType.KNIGHT;
    }

    public Knight(int x, int y, PlayerColor color) {
        super(x, y, color);
    }


}