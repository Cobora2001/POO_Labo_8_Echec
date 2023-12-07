package engine.piece;

import chess.PieceType;
import chess.PlayerColor;
import engine.Board;

/**
 * Classe principale du package engine.piece
 *
 * @author : Valentin Bugna, Theodros Mulugeta et Stéphane Nascimento Santos
 * @since  : JDK 17.0.4
 */
public abstract class Piece {
    private final PlayerColor color;

    /**
     * Constructeur
     * @param pieceColor couleur de la pièce    (blanc ou noir)
     */
    public Piece(PlayerColor pieceColor) {
        this.color = pieceColor;
    }

    /**
     * Getter de la couleur de la pièce
     * @return la couleur de la pièce sous la forme d'un enum
     */
    public PlayerColor getPieceColor() {
        return this.color;
    }

    /**
     * Getter du type de la pièce
     * @return le type de la pièce sous la forme d'un enum
     */
    abstract public PieceType getPieceType();

    /**
     * Fonction qui effectue le mouvement des pièces
     * @param board représente l'échiquier
     * @param fromX la coordonnée de départ sur l'axe des abscisses
     * @param fromY la coordonnée de départ sur l'axe des ordonnées
     * @param toX   la coordonnée d'arrivée sur l'axe des abscisses
     * @param toY   la coordonnée d'arrivée sur l'axe des ordonnées
     */
    public abstract void move(Board board, int fromX, int fromY, int toX, int toY);

    /**
     * Fonction qui détermine si le le mouvement est valide en fonction du type de la pièce
     * @param board représente l'échiquier
     * @param checkLegality booléen qui détermine si on vérifie la légalité du mouvement
     * @param fromX la coordonnée de départ sur l'axe des abscisses
     * @param fromY la coordonnée de départ sur l'axe des ordonnées
     * @param toX   la coordonnée d'arrivée sur l'axe des abscisses
     * @param toY   la coordonnée d'arrivée sur l'axe des ordonnées
     * @return vrai si le mouvement est valide
     */
    public abstract boolean isMoveValid(Board board, boolean checkLegality, int fromX, int fromY, int toX, int toY);

}
