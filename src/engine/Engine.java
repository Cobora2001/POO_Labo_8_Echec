package engine;

import chess.ChessView;
import chess.PieceType;
import chess.PlayerColor;

import java.util.LinkedList;
import java.util.Map.Entry;

public class Engine {
    public static final int dimension = 8;
    private static final int nbPlayers = PlayerColor.values().length;
    private ChessView view;

    private Piece[][] matrix;
    private Pair<Piece, LinkedList<Piece>>[] playerPieces;
    // private LinkedList<Piece>[] playerPieces;

    private int turn;

    public Engine(ChessView view) {
        this.view = view;
        matrix = new Piece[dimension][dimension];
        playerPieces = new Pair[nbPlayers];
        int size = playerPieces.length;
        for(int i = 0; i < size; ++i) {
            playerPieces[i] = new Pair<>(new Piece(PieceType.KING, 4, 0, PlayerColor.values()[i], new Ruleset()), new LinkedList<>());
        }
        turn = 1;
        initiateGame();
    }

    private void initiateGame() {
        initiatePlayer(0, playerPieces[0]);
        initiatePlayer(7, playerPieces[1]);
        initiateMatrix();
    }

    private void initiatePlayer(int yStart, Pair<Piece, LinkedList<Piece>> pieces) {
        Piece king = pieces.getFirst();
        king.setY(Math.abs(yStart));
        PlayerColor color = king.getColor();

        LinkedList<Piece> setOfPieces = pieces.getSecond();

        setOfPieces.add(new Piece(PieceType.QUEEN,  3, Math.abs(yStart), color, new Ruleset()));
        setOfPieces.add(new Piece(PieceType.ROOK,   0, Math.abs(yStart), color, new Ruleset()));
        setOfPieces.add(new Piece(PieceType.ROOK,   7, Math.abs(yStart), color, new Ruleset()));
        setOfPieces.add(new Piece(PieceType.KNIGHT, 1, Math.abs(yStart), color, new Ruleset()));
        setOfPieces.add(new Piece(PieceType.KNIGHT, 6, Math.abs(yStart), color, new Ruleset()));
        setOfPieces.add(new Piece(PieceType.BISHOP, 2, Math.abs(yStart), color, new Ruleset()));
        setOfPieces.add(new Piece(PieceType.BISHOP, 5, Math.abs(yStart), color, new Ruleset()));

        for(int i = 0; i < dimension; ++i) {
            setOfPieces.add(new Piece(PieceType.PAWN,  i, Math.abs(yStart-1), color, new Ruleset()));
        }

    }

    private void addPieceMatrix(Piece piece) {
        matrix[piece.getX()][piece.getY()] = piece;
    }

    private void initiateMatrix() {
        for(Pair<Piece, LinkedList<Piece>> pieces : playerPieces) {
            addPieceMatrix(pieces.getFirst());
            for(Piece piece : pieces.getSecond()) {
                addPieceMatrix(piece);
            }
        }
    }

    public void initiateView() {
        for(Pair<Piece, LinkedList<Piece>> pieces : playerPieces) {
            Piece king = pieces.getFirst();
            view.putPiece(king.getType(), king.getColor(), king.getX(), king.getY());
            for(Piece piece : pieces.getSecond()) {
                view.putPiece(piece.getType(), piece.getColor(), piece.getX(), piece.getY());
            }
        }
    }

    private void displayMessage(String message) {
        view.displayMessage(message);
    }

    private Piece findPiece(int x, int y) {
        return matrix[x][y];
    }

    public boolean move(int fromX, int fromY, int toX, int toY) {
        if(fromX == toX && fromY == toY) {
            displayMessage("Movement cancelled");
            return false;
        }
        if(toX < 0 || toX > 7 || toY < 0 || toY > 7) {
            displayMessage("Invalid destination position (/!\\ hard coded)");
            return false;
        }
        Piece piece = findPiece(fromX, fromY);
        if(piece == null) {
            displayMessage("No piece found at starting position");
            return false;
        }

        Piece destination = findPiece(toX, toY);
        // On a à ce moment-là déjà appelé de quoi savoir que la position de départ et d'arrivée sont différentes, donc
        // les deux pièces peuvent pas être les mêmes, à moins qu'on ait plusieurs fois la même pièce sur le plateau...
        if(destination != null && destination.getColor() == piece.getColor()) {
            displayMessage("There was a " + destination.getColor() + " piece at the place we wanted to put our " + piece.getColor() + " piece at.");
            return false;
        }
        String errorMessage = piece.canMove(toX, toY, matrix);
        if(errorMessage == null) {
            removeFromView(fromX, fromY);
            addPieceToView(piece);
            nextTurn();
            displayMessage("Move made");
            return true;
        }
        displayMessage(errorMessage);
        return false;
    }

    private void addPieceToView(Piece piece) {
        view.putPiece(piece.getType(), piece.getColor(), piece.getX(), piece.getY());
    }

    private void removeFromView(int x, int y) {
        view.removePiece(x, y);
    }

    private void nextTurn() {
        ++turn;
    }
}
