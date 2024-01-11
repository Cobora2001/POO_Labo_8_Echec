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
    private final Pair<King, LinkedList<Piece>>[] playerPieces;

    private int turn;

    // Constants to prevent magic numbers
    private static final int columnKing = 4;
    private static final int invalidPosition = -1;

    public Engine(ChessView view) {
        this.view = view;
        matrix = new Piece[dimension][dimension];
        playerPieces = new Pair[nbPlayers];
        int size = playerPieces.length;
        for(int i = 0; i < size; ++i) {
            playerPieces[i] = new Pair<>(new King( columnKing, invalidPosition, PlayerColor.values()[i]), new LinkedList<>());
        }
        turn = 1;
        initiateGame();
    }

    private void initiateGame() {
        // For two players
        for(int i = 0; i < nbPlayers; ++i) {
            initiatePlayer((dimension - i) % dimension, playerPieces[i]);
        }
        setMatrix();
    }

    private void initiatePlayer(int yStart, Pair<King, LinkedList<Piece>> pieces) {
        King king = pieces.getFirst();
        king.initiatePosition(king.getX(), Math.abs(yStart));
        PlayerColor color = king.getColor();

        LinkedList<Piece> setOfPieces = pieces.getSecond();

        setOfPieces.add(new Queen(3, Math.abs(yStart), color));
        Rook rook = new Rook(  0, Math.abs(yStart), color);
        king.addCastle(rook);
        setOfPieces.add(rook);
        rook = new Rook(7, Math.abs(yStart), color);
        king.addCastle(rook);
        setOfPieces.add(rook);
        setOfPieces.add(new Knight( 1, Math.abs(yStart), color));
        setOfPieces.add(new Knight( 6, Math.abs(yStart), color));
        setOfPieces.add(new Bishop( 2, Math.abs(yStart), color));
        setOfPieces.add(new Bishop( 5, Math.abs(yStart), color));

        for(int i = 0; i < dimension; ++i) {
            setOfPieces.add(new Pawn(  i, Math.abs(yStart-1), color));
        }

    }

    private void addPieceMatrix(Piece piece) {
        matrix[piece.getX()][piece.getY()] = piece;
    }

    private void setMatrix() {
        for(Pair<King, LinkedList<Piece>> pieces : playerPieces) {
            addPieceMatrix(pieces.getFirst());
            for(Piece piece : pieces.getSecond()) {
                addPieceMatrix(piece);
            }
        }
    }

    public void initiateView() {
        for(Pair<King, LinkedList<Piece>> pieces : playerPieces) {
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
        Pawn pawnEnPassant = Pawn.getEnPassant();
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
        if(isThreatened(piece.getColor(), king.getFirst(), king.getSecond())) {
            displayMessage("Doing this move would put the " + piece.getColor() + " king at risk");
            revertMatrix();
            Pawn.setEnPassant(pawnEnPassant);
            return false;
        }

        movePieces(toX, toY);

        displayGameState();

        return true;
    }

    private void displayGameState() {
        String response;
        PlayerColor colorPlaying = colorPlaying();
        int x = -1 , y = -1;
        for (Pair<King, LinkedList<Piece>> playerPiece : playerPieces) {
            if (playerPiece.getFirst().getColor() == colorPlaying) {
                x = playerPiece.getFirst().getX();
                y = playerPiece.getFirst().getY();
            }
        }
        if(isThreatened(colorPlaying, x, y)) {
            if(canAnyPieceMove(colorPlaying, true))
                response = "Check !";
            else
                response = "Checkmate !";
        } else {
            if(canAnyPieceMove(colorPlaying, false))
                response = "Move made";
            else
                response = "Stalemate !";
        }

        displayMessage(response);
    }

    private void revertMatrix() {
        matrix = new Piece[dimension][dimension];
        setMatrix();
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
        for(Pair<King, LinkedList<Piece>> pieces : playerPieces) {
            pieces.setSecond(new LinkedList<>());
            Piece king = pieces.getFirst();
            if(king != matrix[king.getX()][king.getY()]) {
                if(king != matrix[toX][toY]) {
                    throw new RuntimeException("Only one king should move during a turn, at maximum.");
                }
                king.setCoordinate(toX,toY);
            }
        }
        for(int i = 0; i < matrix.length; ++i) {
            for(int j = 0; j < matrix[0].length; ++j) {
                if(matrix[i][j] != null) {
                    if(matrix[i][j].getType() != PieceType.KING) {
                        for(Pair<King, LinkedList<Piece>> pieces: playerPieces) {
                            matrix[i][j].setCoordinate(i,j);
                            if(pieces.getFirst().getColor() == matrix[i][j].getColor()) {
                                pieces.getSecond().add(matrix[i][j]);
                            }
                        }
                    }
                }
            }
        }
    }

    private void pawnReset() {
        Pawn enPassant = Pawn.getEnPassant();
        if(enPassant != null && enPassant.getColor() == colorPlaying()) {
            Pawn.resetEnPassant();
        }
    }

    private void turnReset() {
        // We created this function in case we had to do multiple such operations in the future
        pawnReset();
    }

    private void movePieces(int toX, int toY) {
        setPiecesFromMatrix(toX, toY);
        nextTurn();
        emptyView();
        initiateView();
        turnReset();
    }

    private Pair<Integer, Integer> findKing(PlayerColor color, int fromX, int fromY, int toX, int toY) {
        for (Pair<King, LinkedList<Piece>> playerPiece : playerPieces) {
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

    public boolean isThreatened(PlayerColor color, int threatenX, int threatenY) {
        for (Pair<King, LinkedList<Piece>> playerPiece : playerPieces) {
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
    public int getDimension(){return dimension;}

    private record Promotion(Piece piece) implements ChessView.UserChoice {
        @Override
        public String textValue() {
            return piece.getClass().getSimpleName();
        }
    }

    public void promotion(int toX, int toY){
        matrix[toX][toY] = view.askUser("Promotion", "What do you prefer?",
                new Promotion(new Bishop(toX, toY, colorPlaying())),
                new Promotion(new Queen(toX, toY, colorPlaying())),
                new Promotion(new Rook(toX, toY, colorPlaying())),
                new Promotion(new Knight(toX, toY, colorPlaying()))).piece();
    }

    private boolean canAnyPieceMove(PlayerColor color, boolean isThreatened) {
        for (Pair<King, LinkedList<Piece>> playerPiece : playerPieces) {
            King king = playerPiece.getFirst();
            if (king.getColor() == color) {
                LinkedList<Piece> pieces = playerPiece.getSecond();
                for (Piece piece : pieces) {
                    if(manageImaginaryMove(piece, isThreatened ? king : null)) {
                        return true;
                    }
                }
                if(manageImaginaryMove(king, isThreatened ? king : null)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean canPieceMove(Piece piece, boolean mustUpdate, Pair<Integer, Integer> destToFill) {
        for(int x = 0; x < matrix.length; ++x) {
            for(int y = 0; y < matrix[0].length; ++y) {
                if(piece.canMove(x, y, this) == null) {
                    if(mustUpdate) {
                        piece.updateMatrix(x, y, this);
                    }
                    destToFill.setFirst(x);
                    destToFill.setSecond(y);
                    return true;
                }
            }
        }
        return false;
    }

    private boolean manageImaginaryMove(Piece piece, King kingThreatened) {

        boolean mustUpdate = kingThreatened != null;
        Pair<Integer, Integer> potentialDest = new Pair<>(null, null);
        boolean result = canPieceMove(piece, mustUpdate, potentialDest);

        if(mustUpdate) {
            if(result) {
                int x;
                int y;
                if(piece != kingThreatened) {
                    x = kingThreatened.getX();
                    y = kingThreatened.getY();
                } else {
                    x = potentialDest.getFirst();
                    y = potentialDest.getSecond();
                }
                result = !isThreatened(kingThreatened.getColor(), x, y);
            }
            revertMatrix();
        }
        return result;
    }

}
