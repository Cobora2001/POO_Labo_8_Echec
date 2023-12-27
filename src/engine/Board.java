package engine;

import engine.Pieces.Piece;

public class Board {
    private static final int dimension = 8;
    private final Piece[][] board;

    public Board() {
        board = new Piece[8][8];
    }

    private boolean outsideIndex(int i, int j) {
        return (i < 0 || j < 0 || i >= board.length || j >= board[0].length);
    }

    public Piece getCase(int i, int j) {
        if(outsideIndex(i, j)) {
            throw new IndexOutOfBoundsException("Outside of the dimensions of the board.");
        }
        return board[i][j];
    }

    public boolean putPiece(int i, int j, Piece newPiece) {
        if(outsideIndex(i, j)) {
            return false;
        }
        board[i][j] = newPiece;
        return true;
    }



}

