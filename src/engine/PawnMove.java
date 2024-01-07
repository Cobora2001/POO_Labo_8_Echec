package engine;

import chess.PlayerColor;

public class PawnMove extends VerticalMove {
    static final int MaxStep = 2;


    @Override
    public boolean isObstructed(Piece piece, int toX, int toY, Piece[][] matrix) {
        return ((piece.getX() == toX) == ( matrix[toX][toY] != null))
                || super.isObstructed(piece, toX, toY, matrix);
    }

    @Override
    public boolean movePossible(Piece piece, int toX, int toY) {
        int distance = toY - piece.getY();
        return  (piece.getColor() == PlayerColor.WHITE) ==  distance > 0; //
             //   &&
               // && super.movePossible(piece, toX, toY)
                //;

    }


}
