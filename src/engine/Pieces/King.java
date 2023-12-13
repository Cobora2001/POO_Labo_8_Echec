package engine.Pieces;

import chess.PieceType;
import chess.PlayerColor;

import static java.lang.Math.abs;

public class King extends Piece {

    public King(PlayerColor color, int x, int y) {
        super(PieceType.KING, color, x, y);
    }

    @Override
    public boolean internalRule(int x, int y) {
        return (abs(this.getX() - x) <= 1 && abs(this.getY() - y) <= 1);
    }
}
