package engine.piece;

import chess.PieceType;
import chess.PlayerColor;
import engine.Board;

/**
 * Classe roi héritant de la sous-classe PieceMove
 *
 * @author : Valentin Bugna, Theodros Mulugeta et Stéphane Nascimento Santos
 * @since  : JDK 17.0.4
 */
public class King extends PieceMove {
    private final PieceMove rookL;
    private final PieceMove rookR;

    /**
     * Constructeur qui enregistre les deux pièces tours
     * @param playerColor la couleur du joueur
     * @param rookL la tour de gauche
     * @param rookR la tour de droite
     */
    public King(PlayerColor playerColor, PieceMove rookL, PieceMove rookR) {
        super(playerColor);
        this.rookL = rookL;
        this.rookR = rookR;
    }

    @Override
    public PieceType getPieceType() {
        return PieceType.KING;
    }

    @Override
    public void move(Board board, int fromX, int fromY, int toX, int toY) {
        if (isRoqueLeft(board, toX, toY)) {
            board.movePiece(fromX, fromY, fromX - 2, fromY, this);
            board.movePiece(toX, toY, fromX - 1, fromY, rookL);
            board.setKingPosition(new Board.KingPosition(fromX - 2, fromY));
            rookL.setHasMoved();

        } else if (isRoqueRight(board, toX, toY)) {
            board.movePiece(fromX, fromY, fromX + 2, fromY, this);
            board.movePiece(toX, toY, fromX + 1, fromY, rookR);
            board.setKingPosition(new Board.KingPosition(fromX + 2, fromY));
            rookR.setHasMoved();

        } else if (isBasic(fromX, fromY, toX, toY)) {
            board.movePiece(fromX, fromY, toX, toY, this);
            board.setKingPosition(new Board.KingPosition(toX, toY));
        }
        setHasMoved();
    }

    @Override
    public boolean isMoveValid(Board board, boolean checkLegality, int fromX, int fromY, int toX, int toY) {
        if (isRoqueLeft(board, toX, toY) || isRoqueRight(board, toX, toY)) {
            return board.isMoveFree(fromX, fromY, toX, toY)
                    && (!checkLegality || isRoqueMoveLegal(board, fromX, fromY, toX));
        } else {
            return isBasic(fromX, fromY, toX, toY)
                    && board.isMoveFree(fromX, fromY, toX, toY)
                    && board.isMoveAuthorized(fromX, fromY, toX, toY)
                    && (!checkLegality || board.isMoveLegal(fromX, fromY, toX, toY, new Board.KingPosition(toX, toY)));
        }
    }

    /**
     * Fonction déterminant s'il est possible d'effectuer un roque vers la gauche
     * @param board l'échiquier
     * @param toX   est la coordonnée des abscisses
     * @param toY   est la coordonnée des ordonnées
     * @return vrai si c'est possible d'effectuer le roque
     */
    private boolean isRoqueLeft(Board board, int toX, int toY) {
        return board.getPiece(toX, toY) == rookL && !rookL.getHasMoved() && !getHasMoved();
    }

    /**
     * Fonction déterminant s'il est possible d'effectuer un roque vers la droite
     * @param board l'échiquier
     * @param toX   est la coordonnée des abscisses
     * @param toY   est la coordonnée des ordonnées
     * @return vrai si c'est possible d'effectuer le roque
     */
    private boolean isRoqueRight(Board board, int toX, int toY) {
        return board.getPiece(toX, toY) == rookR && !rookR.getHasMoved() && !getHasMoved();
    }

    /**
     * Fonction établissant ce qu'est un mouvement basic pour le roi
     * @param fromX la coordonnée de départ sur l'axe des abscisses
     * @param fromY la coordonnée de départ sur l'axe des ordonnées
     * @param toX   la coordonnée d'arrivée sur l'axe des abscisses
     * @param toY   la coordonnée d'arrivée sur l'axe des ordonnées
     * @return vrai si c'est possible de réaliser le mouvement
     */
    private boolean isBasic(int fromX, int fromY, int toX, int toY) {
        return (Math.abs(toX - fromX) <= 1) && (Math.abs(toY - fromY) <= 1);
    }

    /**
     * Fonction qui détermine si un mouvement du roque est légal
     * @param board représente l'échiquier
     * @param fromX la coordonnée de départ sur l'axe des abscisses
     * @param fromY la coordonnée de départ sur l'axe des ordonnées
     * @param toX   la coordonnée d'arrivée sur l'axe des abscisses
     * @return vrai si le mouvement est légal
     */
    private boolean isRoqueMoveLegal(Board board, int fromX, int fromY, int toX){
        int increment = fromX > toX ? -1 : 1;
        toX = (increment == -1) ? fromX - 2 : fromX + 2;

        do {
            if(board.isInCheck(getPieceColor(), new Board.KingPosition(fromX, fromY))){
                return false;
            }
            fromX += increment;
        } while (fromX <= toX);

        return true;
    }
}