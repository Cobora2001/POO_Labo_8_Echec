package engine;

import chess.PieceType;
import chess.PlayerColor;

public class Pawn extends PiecesWithInitialMove {
    static private Pawn enPassant;

    private static Ruleset ruleset = new PawnMove();

    public PieceType getPieceType() {
        return PieceType.PAWN;
    }

    @Override
    public String canMove(int toX, int toY, Engine engine) {
        return ruleset.availableMove(this, toX, toY, engine);

    }

    @Override
    public void updateMatrix(int toX, int toY, Engine engine) {
        ruleset.updateMatrix(this, toX, toY, engine);

    }

    public Pawn(int x, int y, PlayerColor color) {
        super(x, y, color);
    }

    static public void resetEnPassant() {
        enPassant = null;
    }

    static public Pawn getEnPassant() {
        return enPassant;
    }

    static public void setEnPassant(Pawn pawn) {
        enPassant = pawn;
    }
}
