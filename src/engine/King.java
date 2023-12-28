package engine;

import chess.PieceType;
import chess.PlayerColor;

public class King extends PiecesWithInitialMove {

    private static Ruleset ruleset = new Ruleset();

    public PieceType getType() {
        return PieceType.KING;
    }

    public King(int x, int y, PlayerColor color) {
        super(x, y, color);
    }


}
