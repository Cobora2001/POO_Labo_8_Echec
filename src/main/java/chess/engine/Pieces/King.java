

package chess.engine.Pieces;

import chess.PieceType;
import chess.PlayerColor;
import chess.engine.Board;
import chess.engine.Move;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;

public class King extends Piece {
    private boolean hasMoved = false;
    private static boolean isCheckingCastling = false;

    /**
     * Constructeur pour un roi
     *
     * @param couleur
     */
    public King(PlayerColor pc) {
        super(PieceType.KING, pc);
    }

    /**
     * revoie liste des mouvement possible pour un roi
     *
     * @param état de l'échiquier
     * @return
     */
    @Override
    public List<Move> getMoves(Board board) {
        if (board == null)
            throw new InvalidParameterException("board nulle");

        List<Move> moves = new ArrayList<>();
        addSimpleMoveWithChoosedVariation(0, 1, board, moves);
        addSimpleMoveWithChoosedVariation(0, -1, board, moves);
        addSimpleMoveWithChoosedVariation(1, 0, board, moves);
        addSimpleMoveWithChoosedVariation(-1, 0, board, moves);
        addSimpleMoveWithChoosedVariation(1, 1, board, moves);
        addSimpleMoveWithChoosedVariation(1, -1, board, moves);
        addSimpleMoveWithChoosedVariation(-1, 1, board, moves);
        addSimpleMoveWithChoosedVariation(-1, -1, board, moves);

        // Ajout des mouvements de roque si disponibles
        addCastlingMove(true, board, moves);
        addCastlingMove(false, board, moves);

        return moves;
    }

    /**
     * Comme c'est un roi om ajoute liste des mouvement possible pour un roque
     *
     * @param isSmallCastling true = petit roque, false = grand roque
     * @param état d'échequier
     * @param moves la liste dans laquelle ajouter les mouvements de roque si disponibles
     */
    private void addCastlingMove(boolean isSmallCastling, Board board, List<Move> moves) {
        isCheckingCastling = true; // Utilisée pour éviter un appel infini de la validation du roque si les deux joueurs peuvent roquer en même temps

        // Instanciation des positions d'arrivée du roi
        int posX = 2;
        if (isSmallCastling)
            posX = 6;

        int posY = 0;
        if (playerColor == PlayerColor.BLACK)
            posY = 7;

        // Choix de la tour en fonction de la destination du roi choisie
        Piece rook = isSmallCastling ? board.getTile(7, posY).getPiece() : board.getTile(0, posY).getPiece();

        // Vérification si les bons pions sont aux bonnes cases et qu'ils n'ont pas bougé
        if (hasMoved || rook == null || rook.getPieceType() != PieceType.ROOK || ((Rook) rook).hasMoved() || board.isCheck())
            return;

        // Vérification que les cases concernées par le roque ne sont pas occupées
        int[] posToValidate = isSmallCastling ? new int[]{5, 6} : new int[]{1, 2, 3};
        for (int pos : posToValidate)
            if (board.getTile(pos, posY).isOccupied())
                return;

        // Vérification que les cases par lesquelles le roi passe par le roque ne peuvent pas être prises
        // deuxième boucle pour réduire la complexité car on vérifie que elles sont vides avant de générer tous les coups possibles de l'adversaire
        posToValidate = isSmallCastling ? new int[]{5, 6} : new int[]{2, 3};
        if (!isCheckingCastling)
            for (int pos : posToValidate)
                if (board.canBeKilled(playerColor, board.getTile(pos, posY)))
                    return;

        moves.add(new Move(tile, board.getTile(posX, posY), this, isSmallCastling, !isSmallCastling));
        isCheckingCastling = false;
    }

    /**
     * Permet de définir si la pièce à bouger ou non
     *
     * @param hasMoved booléen si elle a bougé ou non
     */
    public void setHasMoved(boolean hasMoved) {
        this.hasMoved = hasMoved;
    }
}
