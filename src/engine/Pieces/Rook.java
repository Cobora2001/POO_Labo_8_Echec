package engine.Pieces;

import chess.PieceType;
import engine.Player;

import static java.lang.Math.abs;

public class Rook extends Piece {

    public Rook(int x, int y, Player player) {
        super(PieceType.ROOK, x, y, player);
    }

    @Override
    public boolean internalRule(int x, int y) {
        return (this.getX() - x == 0 || this.getY() - y == 0);
    }

}
