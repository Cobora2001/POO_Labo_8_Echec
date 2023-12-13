package engine;

import engine.Pieces.Piece;

public class Board {
    private Piece piece;

    public void setPiece(Piece piece) {
        this.piece = piece;
    }

    public Piece getPiece() {
        return piece;
    }
}
