package engine;

import java.util.LinkedList;

public class Ruleset {
     public String availableMove(Piece piece, int toX, int toY, Engine engine) { // can't be static if overridden
        Piece[][] matrix = engine.getMatrix();
        if(!movePossible(piece, toX, toY)) {
            return "This piece can't move in such a way.";
        }
        if(isObstructed(piece, toX, toY, matrix)) {
            return "The way is obstructed.";
        }
        return null;
    }

    public boolean movePossible(Piece piece, int toX, int toY) { // can't be static if overridden
        // FIXME en fonction de la pièce
        // Make it public then
        return true;
    }

    private boolean isObstructed(Piece piece, int toX, int toY, Piece[][] matrix) {
        LinkedList<Pair<Integer, Integer>> spacesPassedThrough = getPassedPlaces(piece.getX(), piece.getY(), toX, toY);
        if(spacesPassedThrough == null) {
            return true;
        }
        for(Pair<Integer, Integer> position : spacesPassedThrough) {
            if(matrix[position.getFirst()][position.getSecond()] != null) {
                return true;
            }
        }
        return false;
    }

    private LinkedList<Pair<Integer, Integer>> getPassedPlaces(int fromX, int fromY, int toX, int toY) {
        int diffX = toX - fromX;
        int diffY = toY - fromY;

        if(Math.abs(diffX) == Math.abs(diffY)) {
            if(diffX == 0) {
                return new LinkedList<>();
            }
        } else {
            if(diffX != 0 && diffY != 0) {
                // Pas en ligne droite
                return null;
            }
        }

        int changeX = Integer.compare(diffX, 0);
        int changeY = Integer.compare(diffY, 0);

        LinkedList<Pair<Integer, Integer>> response = new LinkedList<>();

        fromX += changeX;
        fromY += changeY;

        while(!(fromX == toX && fromY == toY)) {
            response.add(new Pair<>(fromX, fromY));
            fromX += changeX;
            fromY += changeY;
        }

        return response;
    }

    static public void updateMatrix(Piece piece, int toX, int toY, Engine engine) {
        // FIXME en fonction de ce dont on a besoin
        engine.getMatrix()[piece.getX()][piece.getY()] = null;
        engine.getMatrix()[toX][toY] = piece;
    }
}
