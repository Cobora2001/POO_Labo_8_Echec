package engine;

import chess.PlayerColor;

public class PawnMove extends CaresAboutObstacles {
    static final int MaxStep = 2;


    @Override
    public boolean isObstructed(Piece piece, int toX, int toY, Piece[][] matrix) {

        if(piece.getX() == toX) {
            return super.isObstructed(piece, toX, toY, matrix) && matrix[toX][toY] == null;
        } else {
            return matrix[toX][toY] != null || targetsEnPassant(toX, toY);
        }

    }

    @Override
    public boolean movePossible(Piece piece, int toX, int toY) {
        int distance = toY - piece.getY();

        if((piece.getColor() == PlayerColor.WHITE) !=  distance > 0) {
            return false;
        }

        int diffX = Math.abs(toX - piece.getX());
        int diffY = Math.abs(toY - piece.getY());

        if(diffX == 0) {
            if(diffY > 2) {
                return false;
            } else if (diffY == 2) {
                return !((Pawn)piece).hasMoved();
            } else {
                return true;
            }
        } else if(diffX == 1) {
            return diffY == 1;
        } else
            return false;

    }

    static private boolean targetsEnPassant(int toX, int toY) {

        return Pawn.getEnPassant() != null &&
                toX == Pawn.getEnPassant().getX() &&
                (Pawn.getEnPassant().getColor() == PlayerColor.WHITE ?
                        toY == Pawn.getEnPassant().getY() - 1 : toY == Pawn.getEnPassant().getY() + 1);
    }

    static public void updateMatrix(Piece piece, int toX, int toY, Engine engine) {
        CaresAboutObstacles.updateMatrix(piece, toX, toY, engine);

        if(targetsEnPassant(toX, toY)) {
            engine.getMatrix()[Pawn.getEnPassant().getX()][Pawn.getEnPassant().getY()] = null;
        }
    }

}
