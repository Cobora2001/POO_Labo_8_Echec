package engine;

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

    abstract public PieceType getPieceType();

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

    abstract public String canMove(int x, int y, Engine engine) ;
       // return ruleset.availableMove(this, x, y, engine);


    abstract public void updateMatrix(int toX, int toY, Engine engine) ;
       // ruleset.updateMatrix(this, toX, toY, engine);
}

