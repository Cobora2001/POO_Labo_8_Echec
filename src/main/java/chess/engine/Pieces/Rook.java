

package chess.engine.Pieces;

import chess.PieceType;
import chess.PlayerColor;
import chess.engine.Board;
import chess.engine.Move;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;

public class Rook extends Piece {
    private boolean hasMoved = false;

    /**
     * Instanciation d'une tour
     *
     * @param pc couleur du joueur
     */
    public Rook(PlayerColor pc) {
        super(PieceType.ROOK, pc);
    }

    /**
     * Obtenir la liste des mouvements disponibles de la tour
     *
     * @param board de l'échiquier
     * @return la liste des mouvements disponibles pour la tour
     */
    @Override
    public List<Move> getMoves(Board board) {
        if (board == null)
            throw new InvalidParameterException("board nulle");

        List<Move> moves = new ArrayList<>();

        addMovesWithChoosedVariations(1, 0, board, moves);
        addMovesWithChoosedVariations(-1, 0, board, moves);
        addMovesWithChoosedVariations(0, -1, board, moves);
        addMovesWithChoosedVariations(0, 1, board, moves);

        return moves;
    }

    /**
     * Permet de définir si la pièce à bouger ou non
     *
     * @param hasMoved booléen si elle a bougé ou non
     */
    public void setHasMoved(boolean hasMoved) {
        this.hasMoved = hasMoved;
    }

    /**
     * @return si la tour a déjà bougée ou non
     */
    public boolean hasMoved() {
        return hasMoved;
    }
}