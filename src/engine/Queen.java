package engine;

import chess.PieceType;
import chess.PlayerColor;

public class Queen extends Piece {

    private static Ruleset ruleset = new Ruleset();

    public PieceType getType() {
        return PieceType.QUEEN;
    }

    public Queen(int x, int y, PlayerColor color) {
        super(x, y, color);
    }


}
