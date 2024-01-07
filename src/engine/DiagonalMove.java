package engine;

public class DiagonalMove extends CaresAboutObstacles {
    public boolean movePossible(Piece piece, int toX, int toY) {
        int diffX = toX - piece.getX();
        int diffY = toY - piece.getY();

        if(Math.abs(diffX) != Math.abs(diffY)) {
            return false;
        }

        return true;
    }

}
