package engine;

import chess.PieceType;
import chess.PlayerColor;

public class Queen extends Piece {

    private static Ruleset ruleset = new StarMove();

    public PieceType getType() {
        return PieceType.QUEEN;
    }

    @Override
    public String canMove(int x, int y, Engine engine) {
        return ruleset.availableMove(this, x, y, engine);
    }

    @Override
    public void updateMatrix(int toX, int toY, Engine engine) {
        ruleset.updateMatrix(this, toX, toY, engine);

    }

    public Queen(int x, int y, PlayerColor color) {
        super(x, y, color);
    }


}
