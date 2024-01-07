package engine;

import chess.PieceType;
import chess.PlayerColor;

public class Rook extends PiecesWithInitialMove {

    private static Ruleset ruleset = new CrossMove();

    public PieceType getType() {
        return PieceType.ROOK;
    }

    @Override
    public String canMove(int toX, int toY, Engine engine) {
        return ruleset.availableMove(this, toX, toY, engine);
    }

    @Override
    public void updateMatrix(int toX, int toY, Engine engine) {
        ruleset.updateMatrix(this, toX, toY, engine);

    }

    public Rook(int x, int y, PlayerColor color) {
        super(x, y, color);
    }


}