// Authors: Thomas Vuilleumier, Aurélien Richard, and Stéphane Nascimento Santos

package engine;

import java.util.Iterator;

/**
 * KingMove class: This class is responsible for the movement of the king piece.
 */
public class KingMove extends CaresAboutObstacles {

    /**
     * This method checks if the king can move to the given coordinates.
     * @param piece The king piece.
     * @param toX The x coordinate of the destination.
     * @param toY The y coordinate of the destination.
     * @param engine The engine.
     * @return A string containing the error message if the move is not possible, null otherwise.
     */
    public String availableMove(Piece piece, int toX, int toY, Engine engine) { // can't be static if overridden
        String response = super.availableMove(piece, toX, toY, engine);

        if(response == null) {
            return null;
        }

        if(!canCastle(piece, toX, toY, engine)) {
            return response;
        }
        return null;
    }

    /**
     * This method checks if the king can move to the given coordinates without taking the board into account.
     * @param piece the piece to try to move
     * @param toX the x coordinate of the destination
     * @param toY the y coordinate of the destination
     * @return true if the move is possible, false otherwise (doesn't check for the castling)
     */
    public boolean movePossible(Piece piece, int toX, int toY) {
        int diffX = toX - piece.getX();
        int diffY = toY - piece.getY();

        return Math.abs(diffX) <= 1 && Math.abs(diffY) <= 1;
    }

    /**
     * This method checks if the king can castle to the given coordinates.
     * @param piece The king piece.
     * @param toX The x coordinate of the destination.
     * @param toY The y coordinate of the destination.
     * @param engine The engine.
     * @return true if the king can castle, false otherwise.
     */
    private boolean canCastle(Piece piece, int toX, int toY, Engine engine){

        // can't castle if the king is trying to move vertically
        if(toY != piece.getY())
            return false;

        int diffX = toX - piece.getX();

        // can't castle if vertical movement
        if (toY != piece.getY()){
            return false;
        }

        // check if king has moved
        if(((King)piece).hasMoved()){
            return false;
        }

        Iterator<Pair<Rook, Integer>> castles = ((King) piece).getCastlesIterator();

        // finds the rook that the king is aiming to castle with, if any
        Rook rook = null;
        while(castles.hasNext() && rook == null) {
            Pair<Rook, Integer> castle = castles.next();
            if(castle.getSecond() == toX) {
                rook = castle.getFirst();
            }
        }

        // can't castle if there is no rook
        if(rook == null || engine.getMatrix()[rook.getX()][rook.getY()] != rook) {
            return false;
        }

        // can't castle if the rook has moved
        if(rook.hasMoved()) {
            return false;
        }

        // we prepare the variables for the loop
        int stepX = -1 * diffX / Math.abs(diffX);
        int indicatorX = rook.getX();
        indicatorX += stepX;

        // check if the path is obstructed up to the future position of the king from the rook
        // (the obstruction for the king is checked in moveAvailable)
        while(indicatorX != toX) {
            if(engine.getMatrix()[indicatorX][piece.getY()] != null)
                return false;
            indicatorX += stepX;
        }

        boolean flag = true;

        // check if the path is threatened from the king's destination to its current position
        while(flag) {
            if(engine.isThreatened(piece.getColor(), indicatorX, piece.getY()))
                return false;
            if(indicatorX != piece.getX())
                indicatorX += stepX;
            else flag = false;
        }

        return true;
    }

    /**
     * This method updates the matrix after the king has moved.
     * @param piece The king piece.
     * @param toX The x coordinate of the destination.
     * @param toY The y coordinate of the destination.
     * @param engine The engine.
     */
    @Override
    public void updateMatrix(Piece piece, int toX, int toY, Engine engine) {
        super.updateMatrix(piece, toX, toY, engine);

        // if the king hadn't yet moved, we check if it has castled
        if(!((King)piece).hasMoved()) {
            Iterator<Pair<Rook, Integer>> castles = ((King) piece).getCastlesIterator();

            boolean flag = true;

            // we find the rook that the king has castled with, if any
            while (castles.hasNext() && flag) {
                Pair<Rook, Integer> castle = castles.next();
                if (castle.getSecond() == toX) {
                    // we update the matrix with the rook's new position
                    flag = false;
                    Rook rook = castle.getFirst();
                    int diffX = toX - rook.getX();
                    engine.getMatrix()[toX + diffX / Math.abs(diffX)][toY] = rook;
                    engine.getMatrix()[rook.getX()][rook.getY()] = null;
                }
            }
        }
    }
}
