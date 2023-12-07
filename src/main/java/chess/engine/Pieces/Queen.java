
package chess.engine.Pieces;

import chess.PieceType;
import chess.PlayerColor;
import chess.engine.Board;
import chess.engine.Move;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;

public class Queen extends Piece {

    /**
     * Instanciation d'une reine
     *
     * @param pc couleur du joueur
     */
    public Queen(PlayerColor pc) {
        super(PieceType.QUEEN, pc);
    }

    /**
     * Obtenir la liste des mouvements disponibles de la reine
     *
     * @param board de l'échiquier
     * @return la liste des mouvements disponibles pour la reine
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
        addMovesWithChoosedVariations(1, 0, board, moves);
        addMovesWithChoosedVariations(-1, 0, board, moves);
        addMovesWithChoosedVariations(0, -1, board, moves);
        addMovesWithChoosedVariations(0, 1, board, moves);

        return moves;
    }
}
