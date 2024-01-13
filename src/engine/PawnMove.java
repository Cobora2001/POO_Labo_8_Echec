/**
 * PawnMove.java: Class to handle the movement of a pawn
 */

package engine;

import chess.PlayerColor;

public class PawnMove extends CaresAboutObstacles {
    // Constants
    static final int MaxStep = 2;
    static final int Step = 1;

    /**
     * Check if the pawn is obstructed by another piece on its way to the given coordinates
     * @param piece The pawn to move
     * @param toX The x coordinate to move to
     * @param toY The y coordinate to move to
     * @param matrix The matrix of the game
     * @return True if the pawn can move to the given coordinates, false otherwise
     */
    @Override
    public boolean isObstructed(Piece piece, int toX, int toY, Piece[][] matrix) {
        if(piece.getX() == toX) {
            return super.isObstructed(piece, toX, toY, matrix) || matrix[toX][toY] != null;
        } else {
            return matrix[toX][toY] == null && !targetsEnPassant(toX, toY);
        }

    }

    /**
     * Check if the pawn can move to the given coordinates without taking into account the state of the game
     * @param piece The pawn to move
     * @param toX The x coordinate to move to
     * @param toY The y coordinate to move to
     * @return True if the pawn can move to the given coordinates, false otherwise
     */
    @Override
    public boolean movePossible(Piece piece, int toX, int toY) {
        int distance = toY - piece.getY();

        if((piece.getColor() == PlayerColor.WHITE) !=  distance > 0) {
            return false;
        }

        int diffX = Math.abs(toX - piece.getX());
        int diffY = Math.abs(toY - piece.getY());

        if(diffX == 0) {
            if(diffY > MaxStep) {
                return false;
            } else if (diffY == MaxStep) {
                return !((Pawn)piece).hasMoved();
            } else {
                return true;
            }
        } else if(diffX == Step) {
            return diffY == Step;
        } else
            return false;
    }

    /**
     * Check if the pawn is aiming for the en passant coordinates
     * @param toX The x coordinate to move to
     * @param toY The y coordinate to move to
     * @return True if the pawn is targeting the en passant coordinates, false otherwise
     */
    static private boolean targetsEnPassant(int toX, int toY) {
        return Pawn.getEnPassant() != null &&
                (toX == Pawn.getEnPassant().getX() &&
                (Pawn.getEnPassant().getColor() == PlayerColor.WHITE ?
                        toY == Pawn.getEnPassant().getY() - Step : toY == Pawn.getEnPassant().getY() + Step));
    }

    /**
     * Update the matrix of the game according to how the pawn moves
     * @param piece The pawn to move
     * @param toX The x coordinate to move to
     * @param toY The y coordinate to move to
     * @param engine The engine of the game
     */
    public void updateMatrix(Piece piece, int toX, int toY, Engine engine) {
        super.updateMatrix(piece, toX, toY, engine);

        if(Math.abs(toY - piece.getY()) == MaxStep)
            // Here, we know that it's a pawn, because it is only called from a pawn
            Pawn.setEnPassant((Pawn)piece);

        if(targetsEnPassant(toX, toY)) {
            engine.getMatrix()[Pawn.getEnPassant().getX()][Pawn.getEnPassant().getY()] = null;
        }

        if (toY == engine.getDimension() - Step || toY == 0){
            engine.promotion(toX, toY);
        }
    }

}
