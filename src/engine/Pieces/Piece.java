package engine.Pieces;

import chess.ChessView;
import chess.PieceType;
import chess.PlayerColor;

public abstract class Piece {
    private PieceType type;
    private int x;
    private int y;

    public Piece(PieceType type, int x, int y) {
        this.type = type;
        this.x = x;
        this.y = y;
    }

    public boolean move(int x, int y) {
        if(!internalRule(x, y)) {
            return false;
        }
        this.x = x;
        this.y = y;
        return true;
    }

    public PieceType getType() {
        return type;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean isThere(int x, int y) {
        return this.getX() == x && this.getY() == y;
    }

    public abstract boolean internalRule(int x, int y);

    public void changeType(PieceType type) {
        this.type = type;
    }

    public void draw(ChessView view, PlayerColor color) {
        view.putPiece(this.getType(), color, this.getX(), this.getY());
    }
}
