package engine.Pieces;

import chess.PieceType;
import engine.Player;

import static java.lang.Math.abs;

public class King extends Piece {

    public King(int x, int y, Player player) {
        super(PieceType.KING, x, y, player);
    }

    @Override
    public boolean internalRule(int x, int y) {
        return (abs(this.getX() - x) <= 1 && abs(this.getY() - y) <= 1);
    }
}