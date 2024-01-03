package engine;

public class Vertical extends Ruleset{
    public boolean movePossible(Piece piece, int toX, int toY) { // can't be static if overridden
        return super.movePossible( piece, toX, toY)
        && piece.getX() == toX;

    }
}
