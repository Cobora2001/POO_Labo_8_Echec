

package chess.engine;

import chess.ChessController;
import chess.ChessView;
import chess.PieceType;
import chess.PlayerColor;
import chess.engine.Pieces.*;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;

public class Board implements ChessController {
    private ChessView view;
    private Tile[][] tiles = new Tile[8][8];
    private ArrayList<Piece> piecesOnBoard;
    private Move lastMove;
    private PlayerColor currentPlayer;
    private King whiteKing;
    private King blackKing;
    private boolean isCheck = false;

    /**
     * Lancement de la vue
     *
     * @param view la vue à utiliser
     */
    @Override
    public void start(ChessView view) {
        if (view == null)
            throw new RuntimeException("La vue est requise");

        this.view = view;
        view.startView();
    }

    /**
     * Lancement d'une nouvelle partie
     */
    @Override
    public void newGame() {
        if(piecesOnBoard != null)
            for(Piece piece : piecesOnBoard)
                piece.wipeOff(view);

        tiles = new Tile[8][8];
        for (int i = 0; i < 8; i++)
            for (int j = 0; j < 8; j++)
                tiles[i][j] = new Tile(j, i);

        piecesOnBoard = new ArrayList<>(32);
        currentPlayer = PlayerColor.WHITE;
        addPieceDefaultPosition();
        setState();
    }

    /**
     * Ajouter les pièces à leurs positions par défaut
     */
    private void addPieceDefaultPosition() {
        addPieces(PlayerColor.BLACK, 7);
        addPawns(PlayerColor.BLACK, 6);
        addPieces(PlayerColor.WHITE, 0);
        addPawns(PlayerColor.WHITE, 1);

        // Affichage des pièces sur la vue
        for (Piece piece : piecesOnBoard)
            piece.display(view);
    }

    /**
     * Ajouter les pièces (sauf pions) à leur emplacement par défaut selon la couleur et la colonne
     *
     * @param pc     couleur de la pièce
     * @param column colonne sur laquelle ajouter les pièces
     */
    private void addPieces(PlayerColor pc, int column) {
        piecesOnBoard.add(tiles[column][0].setPiece(new Rook(pc)));
        piecesOnBoard.add(tiles[column][1].setPiece(new Knight(pc)));
        piecesOnBoard.add(tiles[column][2].setPiece(new Bishop(pc)));
        piecesOnBoard.add(tiles[column][3].setPiece(new Queen(pc)));
        piecesOnBoard.add(tiles[column][5].setPiece(new Bishop(pc)));
        piecesOnBoard.add(tiles[column][6].setPiece(new Knight(pc)));
        piecesOnBoard.add(tiles[column][7].setPiece(new Rook(pc)));

        // On garde la référence sur les rois pour détecter les échecs
        if (pc == PlayerColor.WHITE) {
            whiteKing = (King) tiles[column][4].setPiece(new King(pc));
            piecesOnBoard.add(whiteKing);
        } else {
            blackKing = (King) tiles[column][4].setPiece(new King(pc));
            piecesOnBoard.add(blackKing);
        }
    }

    /**
     * Ajouter les pions à leur emplacement par défaut selon leur couleur et la colonne
     *
     * @param pc     couleur des pions à ajouter
     * @param column colonne sur laquelle les ajouter
     */
    private void addPawns(PlayerColor pc, int column) {
        for (int i = 0; i < 8; i++) {
            piecesOnBoard.add(tiles[column][i].setPiece(new Pawn(pc)));
            view.putPiece(PieceType.PAWN, pc, i, column);
        }
    }

    /**
     * Effectuer un mouvement demandé par l'utilisateur.
     * Seul un mouvement valide n'est effectué
     *
     * @param fromX position de départ selon l'axe X
     * @param fromY position de départ selon l'axe Y
     * @param toX   position d'arrivée selon l'axe X
     * @param toY   position d'arrivée selon l'axe Y
     * @return si le déplacement a pu se réaliser ou non
     */
    @Override
    public boolean move(int fromX, int fromY, int toX, int toY) {
        if (fromX < 0 || fromX > 7 || fromY < 0 || fromY > 7 || toX < 0 || toX > 7 || toY < 0 || toY > 7)
            throw new RuntimeException("Positions données invalides");

        Piece piece = getTile(fromX, fromY).getPiece();
        if (piece != null) {
            Move move = new Move(getTile(fromX, fromY), getTile(toX, toY), piece);

            List<Move> availableMoves = piece.getMoves(this);

            // Ajout du mouvement en passant si disponible
            if (piece.getPieceType() == PieceType.PAWN) {
                Move enPassantMove = ((Pawn) piece).enPassant(lastMove, this);
                if (enPassantMove != null) {
                    availableMoves.add(enPassantMove);
                    if (move.equals(enPassantMove))
                        move = enPassantMove;
                }
            }

            // Vérification que le bon joueur a joué et que le mouvement est correct
            if (piece.getPlayerColor() == currentPlayer && availableMoves.contains(move)) {

                // Remplacement du mouvement par celui de roque si ce mouvement est effectué
                Move castling = availableMoves.get(availableMoves.indexOf(move));
                if (castling.isSmallCastling() || castling.isBigCastling())
                    move = castling;

                // Si échec, on doit s'assurer que le mouvement joué enlève l'échec
                if (isCheck) {
                    // Mouvement sur la board
                    Piece removedPiece = makeMovement(move);

                    // S'il y a toujours échec : mouvement non valide
                    if (isCheck(piece.getPlayerColor())) {
                        cancelMovement(move, removedPiece);
                        return false;
                    } else {
                        isCheck = false;
                        afterMoveActions(piece, toX, toY);
                    }

                } else {
                    // Mouvement sur la board
                    Piece removedPiece = makeMovement(move);

                    // Validation que le mouvement ne met pas le joueur en cours en échec
                    if (isCheck(piece.getPlayerColor())) {
                        cancelMovement(move, removedPiece);
                        return false;
                    } else {
                        afterMoveActions(piece, toX, toY);
                    }
                }
            }
        } else {
            setState();
            return false;
        }

        setState();
        return true;
    }

    /**
     * Actions à effectuer après un mouvement valide d'une pièce
     *
     * @param piece dernière pièce déplacée
     * @param toX   position en X d'arrivée du déplacement
     * @param toY   position en Y d'arrivée du déplacement
     */
    private void afterMoveActions(Piece piece, int toX, int toY) {
        // Savoir si le roi ou la tour on bougé pour le petit/grand roque
        if (piece.getPieceType() == PieceType.ROOK)
            ((Rook) piece).setHasMoved(true);
        else if (piece.getPieceType() == PieceType.KING)
            ((King) piece).setHasMoved(true);

        // Promotion de pion
        if (piece.getClass() == Pawn.class && ((Pawn) piece).canBePromoted())
            piece = pawnPromotion((Pawn) piece, getTile(toX, toY));

        // Détection de l'échec adverse
        checkIfOnCheck(otherColor(piece.getPlayerColor()));

        // Changement de joueur
        currentPlayer = otherColor(currentPlayer);
    }

    /**
     * Annuler un mouvement car il met en échec le roi
     *
     * @param move         le mouvement à annuler
     * @param removedPiece la pièce à remettre sur l'échiquier
     */
    private void cancelMovement(Move move, Piece removedPiece) {
        makeMovement(new Move(getTile(move.getTo().getPosX(), move.getTo().getPosY()),
                getTile(move.getFrom().getPosX(), move.getFrom().getPosY()), move.getPiece()), removedPiece);
        setState();
    }

    /**
     * Réalisation d'un mouvement
     *
     * @param move mouvement à effectuer
     * @return la pièce retirée de l'échiquier (car si le mouvement est annulé, il faut la replacer)
     */
    private Piece makeMovement(Move move) {
        return makeMovement(move, null);
    }

    /**
     * Réalisation d'un mouvement d'annulation
     *
     * @param move       mouvement à effectuer
     * @param addedPiece la pièce à rajouter car le mouvement a été annulé
     * @return la pièce retirée de l'échiquier (car si le mouvement est annulé, il faut la replacer)
     */
    private Piece makeMovement(Move move, Piece addedPiece) {
        Piece piece = move.getPiece();
        int fromX = move.getFrom().getPosX();
        int fromY = move.getFrom().getPosY();
        int toX = move.getTo().getPosX();
        int toY = move.getTo().getPosY();

        // Suppression de la pièce de la case de départ
        piece.wipeOff(view);
        getTile(fromX, fromY).removePiece();

        // Si la case contient déjà un pièce, on l'enlève du jeu
        Piece removedPiece = getTile(toX, toY).removePiece();
        if (removedPiece != null)
            piecesOnBoard.remove(removedPiece);

        // Enlever la pièce si c'est la prise en passant
        Piece enPassantPiece = move.getEnPassantPiece();
        if (enPassantPiece != null) {
            enPassantPiece.wipeOff(view);
            piecesOnBoard.remove(enPassantPiece);
            removedPiece = enPassantPiece;
        }

        // Si petit roque, il faut déplacer la tour en premier
        if (move.isSmallCastling())
            makeMovement(new Move(getTile(7, fromY), getTile(5, fromY), getTile(7, fromY).getPiece()));

        // Si grand roque, il faut déplacer la tour en premier
        if (move.isBigCastling())
            makeMovement(new Move(getTile(0, fromY), getTile(3, fromY), getTile(0, fromY).getPiece()));

        // Ajout de la pièce sur la case d'arrivée
        getTile(toX, toY).setPiece(piece);
        piece.display(view);

        // Si ce n'est pas une annulation de mouvement
        if (addedPiece == null)
            lastMove = move;
        else { // Sinon, on remet la pièce
            getTile(fromX, fromY).setPiece(addedPiece);
            addedPiece.display(view);
            piecesOnBoard.add(addedPiece);
        }

        return removedPiece;
    }

    /**
     * Regarde si le joueur est en échec
     *
     * @param playerColor couleur du joueur
     */
    private void checkIfOnCheck(PlayerColor playerColor) {
        if (isCheck(playerColor))
            isCheck = true;
    }

    /**
     * Savoir si une couleur choisie est en échec
     *
     * @param playerColor la couleur à vérifier
     * @return booléen si la couleur choisie est en échec ou non
     */
    private boolean isCheck(PlayerColor playerColor) {
        King kingToCheck = playerColor == PlayerColor.WHITE ? whiteKing : blackKing;
        return canBeKilled(kingToCheck.getPlayerColor(), kingToCheck.getTile());
    }

    /**
     * Parcours les pions pour détecter si le roi a été mis en échec lors du dernier mouvement
     *
     * @param playerColor couleur du joueur à vérifier
     * @return booléen si il est en échec ou non
     */
    public boolean canBeKilled(PlayerColor playerColor, Tile tile) {
        if (playerColor == null || tile == null)
            throw new InvalidParameterException("Paramètre vide");

        // Ajout de tous les mouvements futurs possibles de l'adversaire
        ArrayList<Move> moves = new ArrayList<>();
        for (Piece piece : piecesOnBoard)
            if (piece.getPlayerColor() != playerColor)
                moves.addAll(piece.getMoves(this));

        // On regarde si un mouvement peut tuer la case donnée
        for (Move move : moves)
            if (move.getTo().equals(tile))
                return true;

        return false;
    }

    /**
     * Méthode de gestion de la promotion de pion
     *
     * @param pawn pion à promouvoir
     * @param tile case sur laquelle promouvoir le pion
     * @return la pièce choisie par l'utilisateur
     */
    private Piece pawnPromotion(Pawn pawn, Tile tile) {
        ChessView.UserChoice choice =
                view.askUser("Pawn promotion", "In which type of piece do you want your pawn to be promoted ? ",
                        new ChessView.UserChoice() {
                            @Override
                            public String textValue() {
                                return "Bishop";
                            }

                            @Override
                            public String toString() {
                                return "Bishop";
                            }
                        },
                        new ChessView.UserChoice() {
                            @Override
                            public String textValue() {
                                return "Knight";
                            }

                            @Override
                            public String toString() {
                                return "Knight";
                            }
                        },
                        new ChessView.UserChoice() {
                            @Override
                            public String textValue() {
                                return "Queen";
                            }

                            @Override
                            public String toString() {
                                return "Queen";
                            }
                        },
                        new ChessView.UserChoice() {
                            @Override
                            public String textValue() {
                                return "Rook";
                            }

                            @Override
                            public String toString() {
                                return "Rook";
                            }
                        });

        Piece newPiece = new Bishop(pawn.getPlayerColor());
        switch (choice.toString()) {
            case "Knight":
                newPiece = new Knight(pawn.getPlayerColor());
                break;
            case "Queen":
                newPiece = new Queen(pawn.getPlayerColor());
                break;
            case "Rook":
                newPiece = new Rook(pawn.getPlayerColor());
                break;
        }

        pawn.wipeOff(view);
        piecesOnBoard.remove(tile.removePiece());
        piecesOnBoard.add(newPiece);
        tile.setPiece(newPiece);
        newPiece.display(view);
        return newPiece;
    }

    /**
     * Affiche l'état du jeu dans la vue
     */
    private void setState() {
        StringBuilder sb = new StringBuilder();
        sb.append("The next player is : ");
        sb.append(currentPlayer);
        if (isCheck)
            sb.append(" / Check !");
        view.displayMessage(sb.toString());
    }

    /**
     * Retourne la couleur inverse de celle donnée en paramètre
     *
     * @param color couleur dont on veut obtenir l'inverse
     * @return la couleur / le joueur inverse
     */
    private PlayerColor otherColor(PlayerColor color) {
        return color == PlayerColor.WHITE ? PlayerColor.BLACK : PlayerColor.WHITE;
    }


    /**
     * Obtenir une case à partir de ses coordonnées
     *
     * @param posX sa coordonnée en X
     * @param posY sa coordonnée en Y
     * @return la case souhaitée
     * @throws IllegalArgumentException si c'est en dehors des bornes de l'échiquier
     */
    public Tile getTile(int posX, int posY) {
        if (posX < 0 || posX > 7 || posY < 0 || posY > 7)
            throw new IllegalArgumentException("Les valeurs doivent être comprises entre 0 et 7 compris");

        return tiles[posY][posX];
    }

    /**
     * @return booléen si il y a échec ou non
     */
    public boolean isCheck() {
        return isCheck;
    }
}
