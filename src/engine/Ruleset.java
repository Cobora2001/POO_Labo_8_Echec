package engine;

import java.util.LinkedList;

public class Ruleset {
    static public String availableMove(Piece piece, int toX, int toY, Engine engine) {
        Piece[][] matrix = engine.getMatrix();
        if(!movePossible(piece, toX, toY)) {
            return "This piece can't move in such a way.";
        }
        if(isObstructed(piece, toX, toY, matrix)) {
            return "The way is obstructed.";
        }
        return null;
    }

    static private boolean movePossible(Piece piece, int toX, int toY) {
        // FIXME en fonction de la pièce
        return true;
    }

    static private boolean isObstructed(Piece piece, int toX, int toY, Piece[][] matrix) {
        LinkedList<Pair<Integer, Integer>> spacesPassedThrough = getPassedPlaces(piece.getX(), piece.getY(), toX, toY);
        if(spacesPassedThrough == null) {
            return false;
        }
        for(Pair<Integer, Integer> position : spacesPassedThrough) {
            if(matrix[position.getFirst()][position.getSecond()] != null) {
                return false;
            }
        }
        return true;
    }

    static private LinkedList<Pair<Integer, Integer>> getPassedPlaces(int fromX, int fromY, int toX, int toY) {
        int diffX = toX - fromX;
        int diffY = toY - fromY;

        if(diffX == diffY) {
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

        for(; fromX != toX && fromY != toY;) {
            response.add(new Pair<>(fromX, fromY));
            fromX += changeX;
            fromY += changeY;
        }
        return response;
    }
}
