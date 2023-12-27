package engine;

import chess.ChessView;
import chess.PieceType;
import chess.PlayerColor;

import java.util.LinkedList;

public class Engine {
    public static final int dimension = 8;
    private static final int nbPlayers = PlayerColor.values().length;
    private ChessView view;

    private Piece[][] matrix;
    private LinkedList<Piece>[] playerPieces;

    private int turn;

    public Engine(ChessView view) {
        this.view = view;
        matrix = new Piece[dimension][dimension];
        playerPieces = new LinkedList[nbPlayers];
        int size = playerPieces.length;
        for(int i = 0; i < size; ++i) {
            playerPieces[i] = new LinkedList<>();
            playerPieces[i].add(new Piece(PieceType.KING, 4, 0, PlayerColor.values()[i], new Ruleset()));
        }
        turn = 1;
        initiateGame();
    }

    private void initiateGame() {
        initiatePlayer(0, playerPieces[0]);
        initiatePlayer(7, playerPieces[1]);
    }

    private void initiatePlayer(int yStart, LinkedList<Piece> pieces) {
        Piece first = pieces.getFirst();
        first.setY(Math.abs(yStart));
        PlayerColor color = first.getColor();

        pieces.add(new Piece(PieceType.QUEEN,  3, Math.abs(yStart), color, new Ruleset()));
        pieces.add(new Piece(PieceType.ROOK,   0, Math.abs(yStart), color, new Ruleset()));
        pieces.add(new Piece(PieceType.ROOK,   7, Math.abs(yStart), color, new Ruleset()));
        pieces.add(new Piece(PieceType.KNIGHT, 1, Math.abs(yStart), color, new Ruleset()));
        pieces.add(new Piece(PieceType.KNIGHT, 6, Math.abs(yStart), color, new Ruleset()));
        pieces.add(new Piece(PieceType.BISHOP, 2, Math.abs(yStart), color, new Ruleset()));
        pieces.add(new Piece(PieceType.BISHOP, 5, Math.abs(yStart), color, new Ruleset()));

        for(int i = 0; i < dimension; ++i) {
            pieces.add(new Piece(PieceType.PAWN,  i, Math.abs(yStart-1), color, new Ruleset()));
        }

    }

    private void addPieceMatrix(Piece piece) {
        matrix[piece.getX()][piece.getY()] = piece;
    }

    private void initiateMatrix() {
        for(LinkedList<Piece> pieces : playerPieces) {
            for(Piece piece : pieces) {
                addPieceMatrix(piece);
            }
        }
    }

    public void initiateView() {
        for(LinkedList<Piece> pieces : playerPieces) {
            for(Piece piece : pieces) {
                view.putPiece(piece.getType(), piece.getColor(), piece.getX(), piece.getY());
            }
        }
    }

    private void displayMessage(String message) {
        view.displayMessage(message);
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

        return false;
    }
}
