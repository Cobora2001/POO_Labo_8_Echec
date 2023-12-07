package engine.piece;

import chess.PieceType;
import chess.PlayerColor;
import engine.Board;
import engine.move.Diagonal;
import engine.move.Vertical;

/**
 * Classe pion héritant de la sous-classe PieceMove
 *
 * @author : Valentin Bugna, Theodros Mulugeta et Stéphane Nascimento Santos
 * @since  : JDK 17.0.4
 */
public class Pawn extends PieceMove {

    public Pawn(PlayerColor pieceColor) {
        super(pieceColor);
    }

    @Override
    public PieceType getPieceType() {
        return PieceType.PAWN;
    }

    @Override
    public void move(Board board, int fromX, int fromY, int toX, int toY) {
        if (isMovedByTwo(board, fromX, fromY, toX, toY)) {
            board.setEnPassantTurn();
            board.setEnPassantPawn(this);
        }

        if (isEnPassant(board, fromY, toX)) {
            board.setPiece(toX, fromY, null);
        }

        board.movePiece(fromX, fromY, toX, toY, this);
        setHasMoved();

        if (toY == board.getDimension() - 1 || toY == 0){
            board.promotion(toX, toY);
        }
    }

    @Override
    public boolean isMoveValid(Board board, boolean checkLegality, int fromX, int fromY, int toX, int toY) {
        Piece toPiece = board.getPiece(toX, toY);
        boolean vertical = new Vertical().isValid(fromX, fromY, toX, toY);

        if ((getPieceColor() == PlayerColor.WHITE && fromY > toY) || (getPieceColor() == PlayerColor.BLACK && fromY < toY)) {
            return false;

        } else if (Math.abs(toY - fromY) > 2) {
            return false;

        } else if (isMovedByTwo(board, fromX, fromY, toX, toY)) {
            return board.isMoveFree(fromX, fromY, toX, toY)
                    && (!checkLegality || board.isMoveLegal(fromX, fromY, toX, toY));

        } else if (Math.abs(toY - fromY) == 1) {
            if (new Diagonal().isValid(fromX, fromY, toX, toY) && (toPiece != null || isEnPassant(board, fromY, toX))) {
                    return board.isMoveAuthorized(fromX, fromY, toX, toY)
                            && (!checkLegality || board.isMoveLegal(fromX, fromY, toX, toY));

            } else {
                return vertical && board.getPiece(toX, toY) == null
                        && (!checkLegality || board.isMoveLegal(fromX, fromY, toX, toY));
            }
        } else {
            return false;
        }
    }

    /**
     * Fonction qui détermine s'il est possible d'avancer de deux cases
     * @param board l'échiquier
     * @param fromX la coordonnée de départ sur l'axe des abscisses
     * @param fromY la coordonnée de départ sur l'axe des ordonnées
     * @param toX   la coordonnée d'arrivée sur l'axe des abscisses
     * @param toY   la coordonnée d'arrivée sur l'axe des ordonnées
     * @return vrai si c'est possible d'avancer de deux cases
     */
    private boolean isMovedByTwo(Board board, int fromX, int fromY, int toX, int toY) {
        return  !getHasMoved()
                && Math.abs(toY - fromY) == 2
                && new Vertical().isValid(fromX, fromY, toX, toY)
                && board.getPiece(toX, toY) == null;
    }

    /**
     * Fonction qui détermine s'il est possible de manger en passant
     * @param board l'échiquier
     * @param fromY la coordonnée de départ sur l'axe des ordonnées
     * @param toX   la coordonnée d'arrivée sur l'axe des abscisses
     * @return vrai si c'est possible d'avancer de manger en passant
     */
    private boolean isEnPassant(Board board, int fromY, int toX) {
        return  board.getPiece(toX, fromY) == board.getEnPassantPawn()
                && (board.getEnPassantTurn() + 1 == board.getTurn());
    }
}
