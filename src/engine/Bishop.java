package engine;

import chess.PieceType;
import chess.PlayerColor;

public class Bishop extends Piece {

    private static Ruleset ruleset = new DiagonalMove();

    public PieceType getPieceType() {
        return PieceType.BISHOP;
    }

    @Override
    public String canMove(int toX, int toY, Engine engine) {
        return ruleset.availableMove(this, toX, toY, engine);
    }

    @Override
    public void updateMatrix(int toX, int toY, Engine engine) {
        ruleset.updateMatrix(this, toX, toY, engine);
    }

    public Bishop(int x, int y, PlayerColor color) {
        super(x, y, color);
    }


}