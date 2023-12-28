package engine;

import chess.ChessView;
import chess.PieceType;
import chess.PlayerColor;

abstract public class Piece {
    private int x;
    private int y;

    private final PlayerColor color;



    public Piece( int x, int y, PlayerColor color) {
        this.x = x;
        this.y = y;
        this.color = color;

    }

    abstract public PieceType getType();

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public PlayerColor getColor() {
        return color;
    }


    public void setCoordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }


    public String canMove(int x, int y, Engine engine) {
        return Ruleset.availableMove(this, x, y, engine);
    }

    public void updateMatrix(int toX, int toY, Engine engine) {
        Ruleset.updateMatrix(this, toX, toY, engine);
    }

    public void move(int toX, int toY, Engine engine) {

    }
}

