/**
 * Engine class
 * This class is the main class of the engine package.
 * It contains the matrix of the game, the pieces of the game, and the methods to move the pieces, among others.
 */
package engine;

// import from custom classes
import chess.ChessView;
import chess.PlayerColor;

// import from java standard library
import java.util.Arrays;
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
    private static final int startTurn = 1;
    private String endMessage = "";

    /**
     * Constructor of the Engine class
     * @param view the view of the game
     */
    public Engine(ChessView view) {
        this.view = view;
        matrix = new Piece[dimension][dimension];
        playerPieces = new Pair[nbPlayers];
        turn = startTurn;
        initiateGame();
    }

    /**
     * Initiates the game
     */
    private void initiateGame() {
        // We initiate the players, one per color. At this point, they are our source of truth for the pieces
        for(PlayerColor color : PlayerColor.values()) {
            initiatePlayer(color);
        }
        // We set the matrix from the players, given that they are the source of truth
        setMatrix();
    }

    /**
     * Initiates a player
     * @param color the color of the player
     */
    private void initiatePlayer(PlayerColor color) {
        // This is the starting line of the pieces, and only works with two players
        int yStart = (dimension - color.ordinal()) % dimension;

        // We create the set of pieces, with only the rooks for the time being
        LinkedList<Piece> setOfPieces = new LinkedList<>(
                Arrays.asList(new Rook(0, yStart, color),
                              new Rook(dimension - 1, yStart, color)));

        // We create the king, such that it knows its set of rooks available for castling
        King king = new King(columnKing, yStart, color, setOfPieces);

        // We add the other pieces from the same line to the set of pieces
        setOfPieces.add(new Queen(3, yStart, color));
        setOfPieces.add(new Knight(1, yStart, color));
        setOfPieces.add(new Knight(6, yStart, color));
        setOfPieces.add(new Bishop(2, yStart, color));
        setOfPieces.add(new Bishop(5, yStart, color));

        // We add the pawns to the set of pieces
        yStart = Math.abs(yStart -1);
        for(int i = 0; i < dimension; ++i) {
            setOfPieces.add(new Pawn( i, yStart, color));
        }

        // We add the king and the set of pieces as the player
        playerPieces[color.ordinal()] = new Pair<>(king, setOfPieces);
    }

    /**
     * Adds a piece to the matrix
     * @param piece the piece to add
     */
    private void addPieceMatrix(Piece piece) {
        matrix[piece.getX()][piece.getY()] = piece;
    }

    /**
     * Sets the matrix from the players
     */
    private void setMatrix() {
        for(Pair<King, LinkedList<Piece>> pieces : playerPieces) {
            // We add the king first, given that it's the only piece that isn't in the list of pieces
            addPieceMatrix(pieces.getFirst());
            // We add the other pieces
            for(Piece piece : pieces.getSecond()) {
                addPieceMatrix(piece);
            }
        }
    }

    /**
     * Initiates the view
     * This method is called at the beginning of the game, and after each turn, when the view is empty.
     */
    public void initiateView() {
        for(Pair<King, LinkedList<Piece>> pieces : playerPieces) {
            Piece king = pieces.getFirst();
            view.putPiece(king.getPieceType(), king.getColor(), king.getX(), king.getY());
            for(Piece piece : pieces.getSecond()) {
                view.putPiece(piece.getPieceType(), piece.getColor(), piece.getX(), piece.getY());
            }
        }
    }

    /**
     * Displays a message in the view
     * @param message the message to display
     */
    private void displayMessage(String message) {
        view.displayMessage((endMessage.isEmpty() ? "" : endMessage + " | ") + message);
    }

    /**
     * Finds a piece in the matrix
     * @param x the x coordinate of the piece
     * @param y the y coordinate of the piece
     * @return the piece at the given coordinates (can be null)
     */
    private Piece findPiece(int x, int y) {
        return matrix[x][y];
    }

    /**
     * Finds the color of the player whose turn it is
     * @return the color of the player playing
     */
    private PlayerColor colorPlaying() {
        return turn % 2 == 1 ? PlayerColor.WHITE : PlayerColor.BLACK;
    }

    /**
     * Checks if the destination is valid
     * @param toX the x coordinate of the destination
     * @param toY the y coordinate of the destination
     * @return a string describing the error if the destination is occupied by a piece of the same color, null otherwise
     */
    private String checkDestination(int toX, int toY) {
        Piece destination = findPiece(toX, toY);
        if(destination != null && destination.getColor() == colorPlaying()) {
            return "There was a " + destination.getColor() + " piece at the place we wanted to put our " + colorPlaying() + " piece at.";
        }
        return null;
    }

    /**
     * Asks the engine to move a piece from a position to another and go to the next turn, unless the move is invalid
     * @param fromX the x coordinate of the starting position
     * @param fromY the y coordinate of the starting position
     * @param toX the x coordinate of the destination
     * @param toY the y coordinate of the destination
     * @return true if the move was successful, false otherwise
     */
    public boolean move(int fromX, int fromY, int toX, int toY) {

        if(fromX == toX && fromY == toY) {
            displayMessage("Movement cancelled");
            return false;
        }

        if(toX < 0 || toX >= dimension || toY < 0 || toY >= dimension) {
            displayMessage("Invalid destination position");
            return false;
        }

        // We search for a piece at the starting position
        Piece piece = findPiece(fromX, fromY);

        if(piece == null) {
            displayMessage("No piece found at starting position");
            return false;
        }

        if(piece.getColor() != colorPlaying()) {
            displayMessage("The piece selected isn't " + colorPlaying() + ", who's turn it is.");
            return false;
        }

        String errorMessage = checkDestination(toX, toY);

        if(errorMessage != null) {
            displayMessage(errorMessage);
            return false;
        }

        // We check if the piece can move to the destination
        errorMessage = piece.canMove(toX, toY, this);

        if(errorMessage != null) {
            displayMessage(errorMessage);
            return false;
        }

        // We save the en passant pawn, in case we need to revert the matrix after we tried a move with a pawn
        Pawn pawnEnPassant = Pawn.getEnPassant();

        // We update the matrix with the move
        piece.updateMatrix(toX, toY, this);

        // We search for the position of the king.
        // If the king is the piece that moved, we search for the new position of the king
        Pair<Integer, Integer> king = findKing(piece.getColor(), fromX, fromY, toX, toY);

        // We check if the move puts the king at risk, given that the move would be illegal
        if(isThreatened(piece.getColor(), king.getFirst(), king.getSecond())) {
            displayMessage("Doing this move would put the " + piece.getColor() + " king at risk");
            revertMatrix();
            // We reset the en passant pawn, given that we didn't move, but had updated the matrix,
            // where we update en passant for Pawn
            Pawn.setEnPassant(pawnEnPassant);
            return false;
        }

        // We then update the player pieces, given that the move is legal
        // and that the matrix is our source of truth now
        movePieces(toX, toY);

        // We check what to write, depending on the state of the game
        displayGameState();

        return true;
    }

    /**
     * Displays the state of the game
     */
    private void displayGameState() {
        String response;
        PlayerColor colorPlaying = colorPlaying();
        // We find the position of the king according to the player pieces
        int x = playerPieces[colorPlaying.ordinal()].getFirst().getX();
        int y = playerPieces[colorPlaying.ordinal()].getFirst().getY();
        // If the king is threatened, we check if it's checkmate or not
        if(isThreatened(colorPlaying, x, y)) {
            // If the king is threatened but that a piece can break the check, it's not checkmate
            if(canAnyPieceMove(colorPlaying, true))
                response = "Check !";
            else {
                response = "";
                endMessage = "Checkmate !";
            }
        } else {
            // If the king isn't threatened but that no piece can move, it's stalemate
            if(canAnyPieceMove(colorPlaying, false))
                response = "Move made";
            else {
                response = "";
                endMessage = "Stalemate !";
            }
        }

        displayMessage(response);
    }

    /**
     * Reverts the matrix to the previous state
     */
    private void revertMatrix() {
        // We empty the matrix
        matrix = new Piece[dimension][dimension];
        // We add the pieces to the matrix from the source of truth, the player pieces
        setMatrix();
    }

    /**
     * Empties the view
     */
    private void emptyView() {
        for(int i = 0; i < dimension; ++i) {
            for(int j = 0; j < dimension; ++j) {
                removeFromView(i, j);
            }
        }
    }

    /**
     * Removes a piece from the view
     * @param x the x coordinate of the piece to remove
     * @param y the y coordinate of the piece to remove
     */
    private void removeFromView(int x, int y) {
        view.removePiece(x, y);
    }

    /**
     * Sets the pieces from the matrix
     * @param toX the new x coordinate of the piece that moved
     * @param toY the new y coordinate of the piece that moved
     */
    private void setPiecesFromMatrix(int toX, int toY) {
        for(Pair<King, LinkedList<Piece>> pieces : playerPieces) {
            // We empty the list of pieces, given that the matrix is our source of truth
            pieces.setSecond(new LinkedList<>());
            // If the king moved, we must manually update its position
            Piece king = pieces.getFirst();
            // We check if the king is in the matrix at the same position as the king in the player pieces
            if(king != matrix[king.getX()][king.getY()]) {
                // Here, we know that the king moved, and that it must be at the new position.
                if(king != matrix[toX][toY]) {
                    // If not, we throw an exception
                    throw new RuntimeException("If a king moved, he must be at the destination selected, and he isn't");
                }
                // We update the position of the king
                king.setCoordinate(toX,toY);
            }
        }
        // We add the pieces to the list of pieces, given that the matrix is our source of truth
        for(int i = 0; i < matrix.length; ++i) {
            for(int j = 0; j < matrix[0].length; ++j) {
                if(matrix[i][j] != null) {
                    // Kings don't go in this list
                    if(matrix[i][j] != playerPieces[matrix[i][j].getColor().ordinal()].getFirst()) {
                        // We add the piece to the list of pieces after updating its position
                        matrix[i][j].setCoordinate(i, j);
                        playerPieces[matrix[i][j].getColor().ordinal()].getSecond().add(matrix[i][j]);
                    }
                }
            }
        }
    }

    /**
     * Resets the en passant pawn
     */
    private void pawnReset() {
        Pawn enPassant = Pawn.getEnPassant();
        // If the en passant pawn is from the player whose turn it is, we reset it,
        // because it means that it's not from last turn
        if(enPassant != null && enPassant.getColor() == colorPlaying()) {
            Pawn.resetEnPassant();
        }
    }

    /**
     * Moves the pieces from the matrix, empties the view, initiates the view,
     * resets the en passant pawn, and goes to the next turn
     * @param toX the new x coordinate of the piece that moved
     * @param toY the new y coordinate of the piece that moved
     */
    private void movePieces(int toX, int toY) {
        setPiecesFromMatrix(toX, toY);
        emptyView();
        initiateView();
        ++turn;
        pawnReset();
    }

    /**
     * Finds the king of a color after a move from the matrix
     * @param color the color of the player
     * @param fromX the x coordinate of the starting position
     * @param fromY the y coordinate of the starting position
     * @param toX the x coordinate of the destination
     * @param toY the y coordinate of the destination
     * @return the position of the king of the color given
     */
    private Pair<Integer, Integer> findKing(PlayerColor color, int fromX, int fromY, int toX, int toY) {
        Piece king = playerPieces[color.ordinal()].getFirst();
        if (king.getX() == fromX && king.getY() == fromY) {
            return new Pair<>(toX, toY);
        } else {
            return new Pair<>(king.getX(), king.getY());
        }
    }

    /**
     * Checks if a position is threatened by a piece of another color
     * @param color the color that we want to check if it's threatened
     * @param threatenX the x coordinate of the position to check
     * @param threatenY the y coordinate of the position to check
     * @return true if the position is threatened, false otherwise
     */
    public boolean isThreatened(PlayerColor color, int threatenX, int threatenY) {
        // We iterate through the other players
        for (Pair<King, LinkedList<Piece>> playerPiece : playerPieces) {
            if (playerPiece.getFirst().getColor() != color) {
                // We check if the player's pieces can move to the position
                LinkedList<Piece> pieces = playerPiece.getSecond();
                for (Piece piece : pieces) {
                    // We check if the piece is still alive (meaning, still in the matrix)
                    if(piece == matrix[piece.getX()][piece.getY()]) {
                        if(piece.canMove(threatenX, threatenY, this) == null)
                            return true;
                    }
                }
                if(playerPiece.getFirst().canMove(threatenX, threatenY, this) == null) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Gets the matrix
     * @return the matrix
     */
    public Piece[][] getMatrix() {
        return matrix;
    }

    /**
     * Gets the dimension of the matrix
     * @return the dimension of the matrix
     */
    public int getDimension(){return dimension;}

    /**
     * Manages the promotion of a piece
     * @param piece the piece to promote
     */
    private record Promotion(Piece piece) implements ChessView.UserChoice {
        @Override
        public String textValue() {
            return piece.getClass().getSimpleName();
        }
    }

    /**
     * Promotes a pawn
     * @param toX the x coordinate of the pawn to promote
     * @param toY the y coordinate of the pawn to promote
     */
    public void promotion(int toX, int toY){
        matrix[toX][toY] = view.askUser("Promotion", "What do you prefer?",
                new Promotion(new Bishop(toX, toY, colorPlaying())),
                new Promotion(new Queen(toX, toY, colorPlaying())),
                new Promotion(new Rook(toX, toY, colorPlaying())),
                new Promotion(new Knight(toX, toY, colorPlaying()))).piece();
    }

    /**
     * Checks if any piece can move
     * @param color the color of the player
     * @param isThreatened true if we want to check if the king is threatened, false otherwise
     * @return true if any piece can move, false otherwise
     */
    private boolean canAnyPieceMove(PlayerColor color, boolean isThreatened) {
        Pair<King, LinkedList<Piece>> playerPiece = playerPieces[color.ordinal()];
        King king = playerPiece.getFirst();
        LinkedList<Piece> pieces = playerPiece.getSecond();
        // We first check if a piece from the list can move
        for (Piece piece : pieces) {
            if(canPieceMove(piece, isThreatened ? king : null)) {
                return true;
            }
        }
        // If not, we check if the king can move
        return canPieceMove(king, isThreatened ? king : null);
    }

    /**
     * Checks if a piece can move
     * @param piece the piece to check
     * @param kingThreatened the king if it is threatened, null otherwise
     * @return true if the piece can move, false otherwise
     */
    private boolean canPieceMove(Piece piece, King kingThreatened) {
        // If the king is threatened, we must update the matrix to check that he isn't anymore after the potential move
        boolean mustUpdate = kingThreatened != null;

        // We iterate through the matrix to find the positions that the piece can move to
        for(int x = 0; x < matrix.length; ++x) {
            for(int y = 0; y < matrix[0].length; ++y) {
                if(piece.canMove(x, y, this) == null) {
                    if(checkDestination(x, y) == null) {
                        // If the king is threatened, we must update the matrix to check that he isn't anymore after the potential move
                        if(mustUpdate) {
                            piece.updateMatrix(x, y, this);
                            int xKing = kingThreatened.getX();
                            int yKing = kingThreatened.getY();
                            if(piece == kingThreatened) {
                                xKing = x;
                                yKing = y;
                            }
                            boolean result = !isThreatened(kingThreatened.getColor(), xKing, yKing);
                            revertMatrix();
                            if(result) {
                                return true;
                            }
                        } else {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
}
