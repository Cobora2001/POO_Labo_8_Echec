

package chess.engine.Pieces;

import chess.PieceType;
import chess.PlayerColor;
import chess.engine.Board;
import chess.engine.Move;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;


public class Bishop extends Piece {

    /**
     * Constructeur pour un fou
     *
     * @param pc couleur
     */
    public Bishop(PlayerColor pc) {
        super(PieceType.BISHOP, pc);
    }

    /**
     * revoi liste des mouvement possible d'un fou
     *
     * @param état de l'échecier
     * @return mouvement possible pour un fou
     */
    @Override
    public List<Move> getMoves(Board board) {
        if (board == null)
            throw new InvalidParameterException("board nulle");

        List<Move> moves = new ArrayList<>();

        addMovesWithChoosedVariations(1, 1, board, moves);
        addMovesWithChoosedVariations(1, -1, board, moves);
        addMovesWithChoosedVariations(-1, 1, board, moves);
        addMovesWithChoosedVariations(-1, -1, board, moves);

        return moves;
    }
}
