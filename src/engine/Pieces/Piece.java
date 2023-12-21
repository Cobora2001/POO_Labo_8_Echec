package engine.Pieces;

import chess.ChessView;
import chess.PieceType;
import chess.PlayerColor;
import engine.Engine;

public abstract class Piece {
    private PieceType type;
    private int x;
    private int y;

    public Piece(PieceType type, int x, int y) {
        this.type = type;
        this.x = x;
        this.y = y;
    }

    public boolean move(int x, int y, Engine engine) {
        if(!engine.checkObstruction(getX(), getY(), x, y)) {
            engine.displayMessage("There were pieces in the way, so the movement was invalid.");
            return false;
        }
        if(!internalRule(x, y, engine)) {
            engine.displayMessage("This piece can't move like that");
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

    public abstract boolean internalRule(int x, int y, Engine engine);

    public void changeType(PieceType type) {
        this.type = type;
    }

    public void draw(ChessView view, PlayerColor color) {
        view.putPiece(this.getType(), color, this.getX(), this.getY());
    }
}
