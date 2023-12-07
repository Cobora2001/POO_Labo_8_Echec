package engine.piece;

import chess.PlayerColor;

/**
 * Sous classe de la classe Piece qui regroupe certains types de pièces ayant des mouvements spéciaux
 *
 * @author : Valentin Bugna, Theodros Mulugeta et Stéphane Nascimento Santos
 * @since  : JDK 17.0.4
 */
abstract public class PieceMove extends Piece{
    private boolean hasMoved;

    /**
     * Constructeur
     * @param pieceColor couleur de la pièce    (blanc ou noir)
     */
    public PieceMove(PlayerColor pieceColor) {
        super(pieceColor);
        this.hasMoved = false;
    }

    /**
     * Getter de la variable hasMoved
     * @return true si la pièce a déjà bougé, false sinon
     */
    public boolean getHasMoved() {
        return hasMoved;
    }

    /**
     * Fonction qui met à true la variable hasMoved lorsque la pièce a bougé
     */
    public void setHasMoved(){
        if(!hasMoved){
            hasMoved = true;
        }
    }
}
