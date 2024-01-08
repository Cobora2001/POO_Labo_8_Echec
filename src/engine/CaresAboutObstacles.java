package engine;

public abstract class CaresAboutObstacles extends Ruleset{
    public String availableMove(Piece piece, int toX, int toY, Engine engine) { // can't be static if overridden
        String response = super.availableMove(piece, toX, toY, engine);

        if(response != null) {
            return response;
        }

        if(isObstructed(piece, toX, toY, engine.getMatrix())) {
            return "The way is obstructed.";
        }
        return null;
    }


    public boolean isObstructed(Piece piece, int toX, int toY, Piece[][] matrix) {
        int fromX = piece.getX();
        int fromY = piece.getY();

        int diffX = toX - fromX;
        int diffY = toY - fromY;

        if(Math.abs(diffX) == Math.abs(diffY)) {
            if(diffX == 0) {
                return false;
            }
        } else {
            if(diffX != 0 && diffY != 0) {
                // Pas en ligne droite
                return true;
            }
        }

        int changeX = Integer.compare(diffX, 0);
        int changeY = Integer.compare(diffY, 0);

        fromX += changeX;
        fromY += changeY;

        while(!(fromX == toX && fromY == toY)) {
            if(matrix[fromX][fromY] != null) {
                return true;
            }

            fromX += changeX;
            fromY += changeY;
        }

        return false;
    }


}
