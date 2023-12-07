

package chess.engine.Pieces;

import chess.ChessView;
import chess.PieceType;
import chess.PlayerColor;
import chess.engine.Board;
import chess.engine.Move;
import chess.engine.Tile;

import java.util.List;

public abstract class Piece {
    private PieceType pieceType;
    protected PlayerColor playerColor;
    protected Tile tile;

    /**
     * Méthode à implémenter par les sous-classes pour obtenir les mouvements
     *
     * @param board de l'échiquier
     * @return une liste de mouvement disponibles
     */
    public abstract List<Move> getMoves(Board board);

    /**
     * Instanciation d'une pièce du jeu
     *
     * @param pt type de la pièce
     * @param pc couleur de la pièce
     */
    public Piece(PieceType pt, PlayerColor pc) {
        if (pt == null || pc == null)
            throw new IllegalArgumentException("Paramètre nul");

        this.pieceType = pt;
        this.playerColor = pc;
    }

    /**
     * Définir la case sur laquelle se trouve une pièce
     *
     * @param tile case sur laquelle se trouve la pièce
     */
    public void setTile(Tile tile) {
        if (this.tile != null)
            this.tile.removePiece();
        this.tile = tile;
    }

    /**
     * Retirer la pièce de sa case
     */
    public void removeTile() {
        this.tile = null;
    }

    /**
     * @return le type de la pièce
     */
    public PieceType getPieceType() {
        return pieceType;
    }

    /**
     * Afficher la pièce sur la vue
     *
     * @param view sur laquelle afficher la pièce
     */
    public void display(ChessView view) {
        view.putPiece(pieceType, playerColor, tile.getPosX(), tile.getPosY());
    }

    /**
     * Masquer la pièce de la vue
     *
     * @param view sur laquelle masquer la pièce
     */
    public void wipeOff(ChessView view) {
        view.removePiece(tile.getPosX(), tile.getPosY());
    }

    /**
     * Savoir si la position de la pièce est valide ou non
     *
     * @param posX position en X de la pièce
     * @param posY position en Y de la pièce
     * @return si la position de la pièce est valide ou non
     */
    static public boolean valid(int posX, int posY) {
        return posX >= 0 && posX <= 7 && posY >= 0 && posY <= 7;
    }

    /**
     * @return la case de la pièce
     */
    public Tile getTile() {
        return tile;
    }

    /**
     * @return la couleur de la pièce
     */
    public PlayerColor getPlayerColor() {
        return playerColor;
    }

    /**
     * Obtenir les mouvement disponibles d'une pièce selon des paramètres de variations donnés
     * Plusieurs itérations
     *
     * @param valX décalage en X
     * @param valY décalage en Y
     * @param board échiquier sur lequel on veut obtenir le mouvements disponibles
     * @param moves liste de movemements à dont on souhaite ajouter les mouvement disponibles
     */
    protected void addMovesWithChoosedVariations(int valX, int valY, Board board, List<Move> moves) {
        Tile currentTile = tile;
        int x = tile.getPosX();
        int y = tile.getPosY();

        if (!valid(x + valX, y + valY))
            return;

        Tile newTile;
        boolean stop = false;
        for (int i = x + valX, j = y + valY; i >= 0 && j >= 0 && i <= 7 && j <= 7 && !stop; i += valX, j += valY) {
            newTile = board.getTile(i, j);

            if (!newTile.isOccupied())
                moves.add(new Move(currentTile, newTile, this));
            else if (newTile.getPiece().playerColor != playerColor) { // Case occupée par une pièce adverse
                moves.add(new Move(currentTile, newTile, this));
                stop = true;
            } else // Case occupée par une pièce du joueur
                stop = true;
        }
    }

    /**
     * Obtenir les mouvement disponibles d'une pièce selon des paramètres de variations donnés
     * Une seule itération
     *
     * @param valX décalage en X
     * @param valY décalage en Y
     * @param board échiquier sur lequel on veut obtenir le mouvements disponibles
     * @param moves liste de movemements à dont on souhaite ajouter les mouvement disponibles
     */
    protected void addSimpleMoveWithChoosedVariation(int valX, int valY, Board board, List<Move> moves) {
        Tile currentTile = tile;
        int x = tile.getPosX();
        int y = tile.getPosY();

        if (!valid(x + valX, y + valY))
            return;

        Tile newTile = board.getTile(x + valX, y + valY);
        if (!newTile.isOccupied() || newTile.getPiece().playerColor != playerColor)
            moves.add(new Move(currentTile, newTile, this));
    }
}
