package engine;

import chess.PieceType;
import chess.PlayerColor;

public class Rook extends PiecesWithInitialMove {

    private static Ruleset ruleset = new Ruleset();

    public PieceType getType() {
        return PieceType.ROOK;
    }

    public Rook(int x, int y, PlayerColor color) {
        super(x, y, color);
    }


}