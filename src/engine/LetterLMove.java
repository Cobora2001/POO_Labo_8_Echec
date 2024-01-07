package engine;

public class LetterLMove extends Ruleset {
    public boolean movePossible(Piece piece, int toX, int toY) {
        int diffX = Math.abs(toX - piece.getX());
        int diffY = Math.abs(toY - piece.getY());

        if(diffX == 2 && diffY == 1 || diffX == 1 && diffY == 2) {
            return true;
        }

        return false;
    }
}
