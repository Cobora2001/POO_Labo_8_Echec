/**
 * King class: represents a king piece
 */

package engine;

import chess.PieceType;
import chess.PlayerColor;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class King extends PiecesWithInitialMove {
    // The list of castles the king can do (rook, x coordinate of the king after the castle)
    // We never remove a castle from this list, given that we don't want to take the time to analyse
    // if a piece is eaten and if it was a rook
    private final LinkedList<Pair<Rook, Integer>> castles = new LinkedList<>();

    // The ruleset of the king
    private static final Ruleset ruleset = new KingMove();

    /**
     * King: constructor of the king
     * @param x: the x coordinate of the king
     * @param y: the y coordinate of the king
     * @param color: the color of the king
     */
    public King(int x, int y, PlayerColor color, List<Piece> list) {
        super(x, y, color);
        for(Piece rook : list){
            addCastle(rook);
        }
    }



    /**
     * getPieceType: get the type of the piece
     * @return the type of the piece
     */
    @Override
    public PieceType getPieceType() {
        return PieceType.KING;
    }

    /**
     * canMove: check if the king can move to the given position
     * @param toX: the x coordinate of the position to move to
     * @param toY: the y coordinate of the position to move to
     * @param engine: the engine of the game
     * @return a string describing the error if the king can't move to the given position, null otherwise
     */
    @Override
    public String canMove(int toX, int toY, Engine engine) {
        return ruleset.availableMove(this, toX, toY, engine);
    }

    /**
     * updateMatrix: update the matrix of the game according to the move of the king
     * @param toX: the x coordinate of the position to move to
     * @param toY: the y coordinate of the position to move to
     * @param engine: the engine of the game
     */
    @Override
    public void updateMatrix(int toX, int toY, Engine engine) {
        ruleset.updateMatrix(this, toX, toY, engine);

    }

    /**
     * addCastle: add a castle to the list of castles
     * @param castle: the castle to add
     */
    private void addCastle(Piece castle){
        
        if(castle == null  ){
            System.err.println("Null Rook given, can't add to castle");
        } else if (castle.getClass() != Rook.class) {
            System.err.println("The castle piece is not a rook");
        } else if(castle.getColor() != this.getColor())
            System.err.println("Can't add to castle, Rook not of the same color");
        else if(castle.getY() == this.getY()) {
            int diffX = castle.getX() - this.getX();
            castles.add(new Pair<>((Rook)castle, this.getX() + 2 * diffX / Math.abs(diffX)));
        } else
            System.err.println("Can't add to castles, Rook not on the same line");
    }

 
    /**
     * getCastlesIterator: get the iterator of the list of castles
     * @return the iterator of the list of castles
     */
    public Iterator<Pair<Rook, Integer>> getCastlesIterator(){
        return castles.iterator();
    }

}
