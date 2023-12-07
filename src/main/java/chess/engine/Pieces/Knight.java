

package chess.engine.Pieces;

import chess.PieceType;
import chess.PlayerColor;
import chess.engine.Board;
import chess.engine.Move;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;

public class Knight extends Piece {

    /**
     * Instanciation d'un chevalier
     *
     * @param pc couleur du joueur
     */
    public Knight(PlayerColor pc) {
        super(PieceType.KNIGHT, pc);
    }

    /**
     * Obtenir la liste des mouvements disponibles du chevalier
     *
     * @param board de l'échiquier
     * @return la liste des mouvements disponibles pour le chevalier
     */
    @Override
    public List<Move> getMoves(Board board) {
        if (board == null)
            throw new InvalidParameterException("board nulle");

        List<Move> moves = new ArrayList<>();

        addSimpleMoveWithChoosedVariation(2, 1, board, moves);
        addSimpleMoveWithChoosedVariation(2, -1, board, moves);
        addSimpleMoveWithChoosedVariation(-2, 1, board, moves);
        addSimpleMoveWithChoosedVariation(-2, -1, board, moves);
        addSimpleMoveWithChoosedVariation(1, 2, board, moves);
        addSimpleMoveWithChoosedVariation(-1, 2, board, moves);
        addSimpleMoveWithChoosedVariation(1, -2, board, moves);
        addSimpleMoveWithChoosedVariation(-1, -2, board, moves);

        return moves;
    }
}
