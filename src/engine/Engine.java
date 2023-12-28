package engine;

import chess.ChessView;
import chess.PieceType;
import chess.PlayerColor;

import java.util.LinkedList;

public class Engine {
    public static final int dimension = 8;
    private static final int nbPlayers = PlayerColor.values().length;
    private final ChessView view;

    private Piece[][] matrix;
    private final Pair<Piece, LinkedList<Piece>>[] playerPieces;

    private Pair<Piece, Pair<Integer, Integer>> lastMove;

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

    private PlayerColor colorPlaying() {
        return turn % 2 == 1 ? PlayerColor.WHITE : PlayerColor.BLACK;
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

        if(piece.getColor() != colorPlaying()) {
            displayMessage("The piece selected isn't " + colorPlaying() + ", who's turn it is.");
            return false;
        }

        Piece destination = findPiece(toX, toY);

        if(destination != null && destination.getColor() == piece.getColor()) {
            displayMessage("There was a " + destination.getColor() + " piece at the place we wanted to put our " + piece.getColor() + " piece at.");
            return false;
        }

        String errorMessage = piece.canMove(toX, toY, this);
        if(errorMessage != null) {
            displayMessage(errorMessage);
            return false;
        }

        piece.updateMatrix(toX, toY, this);

        Pair<Integer, Integer> king = findKing(piece.getColor(), fromX, fromY, toX, toY);
        assert king != null;
        if(moveWouldThreaten(piece.getColor(), king.getFirst(), king.getSecond())) {
            displayMessage("Doing this move would put the " + piece.getColor() + " king at risk");
            revertMatrix();
            return false;
        }

        movePiece(piece, toX, toY);

        return true;

    }

    private void revertMatrix() {
        matrix = new Piece[dimension][dimension];
        initiateMatrix();
    }

    private void addPieceToView(Piece piece) {
        view.putPiece(piece.getType(), piece.getColor(), piece.getX(), piece.getY());
    }

    private void emptyView() {
        for(int i = 0; i < dimension; ++i) {
            for(int j = 0; j < dimension; ++j) {
                removeFromView(i, j);
            }
        }
    }

    private void removeFromView(int x, int y) {
        view.removePiece(x, y);
    }

    private void nextTurn() {
        ++turn;
    }

    private void setPiecesFromMatrix(int toX, int toY) {
        for(Pair<Piece, LinkedList<Piece>> pieces : playerPieces) {
            pieces.setSecond(new LinkedList<>());
            Piece king = pieces.getFirst();
            if(king != matrix[king.getX()][king.getY()]) {
                if(king != matrix[toX][toY]) {
                    throw new RuntimeException("Only one king should move during a turn, at maximum.");
                }
                king.setX(toX);
                king.setY(toY);
            }
        }
        for(int i = 0; i < matrix.length; ++i) {
            for(int j = 0; j < matrix[0].length; ++j) {
                if(matrix[i][j] != null) {
                    if(matrix[i][j].getType() != PieceType.KING) {
                        for(Pair<Piece, LinkedList<Piece>> pieces: playerPieces) {
                            matrix[i][j].setX(i);
                            matrix[i][j].setY(j);
                            if(pieces.getFirst().getColor() == matrix[i][j].getColor()) {
                                pieces.getSecond().add(matrix[i][j]);
                            }
                        }
                    }
                }
            }
        }
    }

    private void movePiece(Piece piece, int toX, int toY) {
        setPiecesFromMatrix(toX, toY);
        nextTurn();
        lastMove = new Pair<>(piece, new Pair<>(toX, toY));
        emptyView();
        initiateView();
        displayMessage("Move made");
    }

    private Pair<Integer, Integer> findKing(PlayerColor color, int fromX, int fromY, int toX, int toY) {
        for (Pair<Piece, LinkedList<Piece>> playerPiece : playerPieces) {
            Piece king = playerPiece.getFirst();
            if (king.getColor() == color) {
                if (king.getX() == fromX && king.getY() == fromY) {
                    return new Pair<>(toX, toY);
                } else {
                    return new Pair<>(king.getX(), king.getY());
                }
            }
        }
        return null;
    }

    private boolean moveWouldThreaten(PlayerColor color, int threatenX, int threatenY) {
        for (Pair<Piece, LinkedList<Piece>> playerPiece : playerPieces) {
            if (playerPiece.getFirst().getColor() != color) {
                LinkedList<Piece> pieces = playerPiece.getSecond();
                for (Piece piece : pieces) {
                    if(piece == matrix[piece.getX()][piece.getY()]) {
                        String response = piece.canMove(threatenX, threatenY, this);
                        if(response == null)
                            return true;
                    }
                }
                String response = playerPiece.getFirst().canMove(threatenX, threatenY, this);
                if(response == null) {
                    return true;
                }
            }
        }
        return false;
    }

    public Piece[][] getMatrix() {
        return matrix;
    }

    public Pair<Piece, Pair<Integer, Integer>> getLastMove() {
        return lastMove;
    }
}
