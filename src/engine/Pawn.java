package engine;

import chess.PieceType;
import chess.PlayerColor;

public class Pawn extends PiecesWithInitialMove {

    private static Ruleset ruleset = new Ruleset();

    public PieceType getType() {
        return PieceType.PAWN;
    }

    public Pawn(int x, int y, PlayerColor color) {
        super(x, y, color);
    }


}
