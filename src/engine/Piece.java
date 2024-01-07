package engine;

import chess.ChessView;
import chess.PieceType;
import chess.PlayerColor;

abstract public class Piece {
    private Position position;

    private final PlayerColor color;



    public Piece( int x, int y, PlayerColor color) {
        this.position = new Position(x,y);
        this.color = color;

    }

    abstract public PieceType getType();

    public int getX() {
        return this.position.getX();
    }

    public int getY() {
        return this.position.getY();
    }

    public PlayerColor getColor() {
        return color;
    }


    public void setCoordinate(int x, int y) {
        this.position.update(x,y);
    }


    abstract public String canMove(int x, int y, Engine engine) ;
       // return ruleset.availableMove(this, x, y, engine);


    abstract public void updateMatrix(int toX, int toY, Engine engine) ;
       // ruleset.updateMatrix(this, toX, toY, engine);


    public void move(int toX, int toY, Engine engine) {

    }
}

