package engine;

public abstract class Ruleset {

     public String availableMove(Piece piece, int toX, int toY, Engine engine) { // can't be static if overridden
        if(!movePossible(piece, toX, toY)) {
            return "This piece can't move in such a way.";
        }
        return null;
    }

    abstract public boolean movePossible(Piece piece, int toX, int toY);


    public void updateMatrix(Piece piece, int toX, int toY, Engine engine) {
        // FIXME dans le king
        engine.getMatrix()[piece.getX()][piece.getY()] = null;
        engine.getMatrix()[toX][toY] = piece;
    }
}
