package engine;

import chess.ChessView;
import chess.PlayerColor;
import engine.piece.*;

/**
 * Classe représentant l'échiquier grâce à une matrice de pièce.
 *
 * @author : Valentin Bugna, Theodros Mulugeta et Stéphane Nascimento Santos
 * @since  : JDK 17.0.4
 */
public class Board {
    public static class KingPosition {
        private int x;
        private int y;
        public KingPosition(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    private record Promotion(Piece piece) implements ChessView.UserChoice {
    @Override
        public String textValue() {
            return piece.getClass().getSimpleName();
        }
    }

    private int turn;
    private int enPassantTurn;
    private PieceMove enPassantPawn;
    private final ChessView view;
    private final Piece[][] board;
    private final KingPosition whiteKingPosition;
    private final KingPosition blackKingPosition;
    private static final int DIMENSION = 8;

    /**
     * Constructeur
     * @param view la vue à utiliser
     */
    public Board(ChessView view) {
        this.turn = 0;
        this.enPassantTurn = 0;
        this.enPassantPawn = null;
        this.view = view;
        this.board = new Piece[DIMENSION][DIMENSION];
        this.whiteKingPosition = new KingPosition(4, 0);
        this.blackKingPosition = new KingPosition(4, 7);
        this.resetBoard();
    }

    /**
     * Getter qui retourne le nombre de tours joués
     * @return le nombre de tours joués
     */
    public int getTurn() {
        return turn;
    }

    /**
     * Setter du nombre de tours joués
     */
    public void setTurn() {
        turn++;
    }

    /**
     * Getter de la variable de type int qui indique à quel tour le dernier pion à avancé de 2
     * @return la variable int EnPassantTurn
     */
    public int getEnPassantTurn() {
        return enPassantTurn;
    }

    /**
     * Setter de la variable qui indique à quel tour le dernier pion à avancé de 2
     */
    public void setEnPassantTurn() {
        enPassantTurn = turn;
    }

    /**
     * Getter de la variable de type PieceMove (Pawn) qui indique quel est le dernier pion à avoir avancer de 2 cases
     * @return un objet de type PieceMove
     */
    public PieceMove getEnPassantPawn() {
        return enPassantPawn;
    }

    /**
     * Setter de la variable de type PieceMove (Pawn) qui indique quel est le dernier pion à avoir avancer de 2 cases
     * @param piece qui remplace la valeur de EnPassantPawn
     */
    public void setEnPassantPawn(PieceMove piece) {
        enPassantPawn = piece;
    }

    /**
     * Fonction qui renvoie la pièce aux cordonnées x et y sur l'échiquier
     * @param x est la coordonnée des abscisses
     * @param y est la coordonnée des ordonnées
     * @return la pièce sur l'échiquier
     */
    public Piece getPiece(int x, int y) {
        if (x < 0 || x > 7 || y < 0 || y > 7) {
            throw new RuntimeException();
        }

        return board[x][y];
    }

    /**
     * Fonction qui remplace la pièce aux cordonnées x et y sur l'échiquier
     * @param x est la coordonnée des abscisses
     * @param y est la coordonnée des ordonnées
     * @param piece a installé sur l'échiquier
     */
    public void setPiece(int x, int y, Piece piece) {
        if (x < 0 || x > 7 || y < 0 || y > 7) {
            throw new RuntimeException();
        }

        board[x][y] = piece;

        if (piece == null) {
            view.removePiece(x, y);
        } else {
            view.putPiece(piece.getPieceType(), piece.getPieceColor(), x, y);
        }
    }

    /**
     * Fonction qui déplace une pièce
     *
     * @param fromX la coordonnée de départ sur l'axe des abscisses
     * @param fromY la coordonnée de départ sur l'axe des ordonnées
     * @param toX   la coordonnée d'arrivée sur l'axe des abscisses
     * @param toY   la coordonnée d'arrivée sur l'axe des ordonnées
     * @param piece la pièce à déplacer
     */
    public void movePiece(int fromX, int fromY, int toX, int toY, Piece piece) {
        setPiece(fromX, fromY, null);
        setPiece(toX, toY, piece);
    }

    /**
     * Fonction qui renvoie la localisation du roi sur l'échiquier
     * @param color est la couleur du roi dont on veut connaître les coordonnées
     * @return les coordonnées du roi
     */
    private KingPosition getKingPosition(PlayerColor color) {
        return (color == PlayerColor.WHITE) ? whiteKingPosition : blackKingPosition;
    }

    /**
     * Fonction qui déplace le roi sur l'échiquier
     * @param kingPosition Les nouvelles coordonnées du roi
     */
    public void setKingPosition(KingPosition kingPosition) {
        if(isWhiteTurn()){
            whiteKingPosition.x = kingPosition.x;
            whiteKingPosition.y = kingPosition.y;
        } else {
            blackKingPosition.x = kingPosition.x;
            blackKingPosition.y = kingPosition.y;
        }
    }

    /**
     * Getter de la variable DIMENSION
     * @return la variable DIMENSION
     */
    public int getDimension() {
        return DIMENSION;
    }

    /**
     * Fonction qui retourne un boolen qui indique si le joueur actuel est blanc
     * @return vrai si le joueur actuel est blanc
     */
    public boolean isWhiteTurn(){
        return (getTurn() % 2 == 0);
    }

    /**
     * Fonction qui retourne la couleur du joueur actuel
     * @return la couleur du joueur actuel
     */
    public PlayerColor getCurrentPlayerColor() {
        return isWhiteTurn() ? PlayerColor.WHITE : PlayerColor.BLACK;
    }

    /**
     * Fonction qui retourne la couleur du joueur adverse
     * @return la couleur du joueur adverse
     */
    public PlayerColor getOpponentPlayerColor() {
        return isWhiteTurn() ? PlayerColor.BLACK : PlayerColor.WHITE;
    }

    /**
     * Fonction qui détermine si le joueur adverse est en échec
     * @return vrai si le joueur adverse est en échec
     */
    public boolean isOpponentPlayerInCheck() {
        return isInCheck(getOpponentPlayerColor(), getKingPosition(getOpponentPlayerColor()));
    }

    /**
     * Fonction qui promeut un pion sur l'échiquier lorsqu'il a atteint la limite de son déplacement
     * @param toX est la coordonnée des abscisses
     * @param toY est la coordonnée des ordonnées
     */
    public void promotion(int toX, int toY){
        setPiece(toX, toY, view.askUser("Promotion", "What is your wish?",
                new Promotion(new Bishop(getCurrentPlayerColor())),
                new Promotion(new Queen(getCurrentPlayerColor())),
                new Promotion(new Rook(getCurrentPlayerColor())),
                new Promotion(new Knight(getCurrentPlayerColor()))).piece());
    }

    /**
     * Permet de vérifier qu'une pièce ne vient pas capturer une pièce de la même couleur. Le roque est le seul mouvement
     * à ne pas utiliser cette fonction.
     *
     * @param fromX la coordonnée de départ sur l'axe des abscisses
     * @param fromY la coordonnée de départ sur l'axe des ordonnées
     * @param toX   la coordonnée d'arrivée sur l'axe des abscisses
     * @param toY   la coordonnée d'arrivée sur l'axe des ordonnées
     * @return vrai si le mouvement est valide
     */
    public boolean isMoveAuthorized(int fromX, int fromY, int toX, int toY) {
        return (board[toX][toY] == null || board[fromX][fromY].getPieceColor() != board[toX][toY].getPieceColor());
    }

    /**
     * Permet de vérifier qu'un mouvement ne rencontre aucune pièce jusqu'à la position souhaitée. Ne pas utiliser cette
     * fonction pour les distances de 1 et le cavalier.
     *
     * @param fromX la coordonnée de départ sur l'axe des abscisses
     * @param fromY la coordonnée de départ sur l'axe des ordonnées
     * @param toX   la coordonnée d'arrivée sur l'axe des abscisses
     * @param toY   la coordonnée d'arrivée sur l'axe des ordonnées
     * @return vrai si le mouvement est libre
     */
    public boolean isMoveFree(int fromX, int fromY, int toX, int toY) {
        int tempX = fromX;
        int tempY = fromY;

        int directionX = Integer.compare(toX, fromX);
        int directionY = Integer.compare(toY, fromY);

        do {
            tempX += directionX;
            tempY += directionY;

            if (board[tempX][tempY] != null) {
                return (tempX == toX) && (tempY == toY);
            }

        } while (!((tempX == toX) && (tempY == toY)));

        return true;
    }

    /**
     * Fonction surcharge qui détermine si un mouvement est légal
     * @param fromX la coordonnée de départ sur l'axe des abscisses
     * @param fromY la coordonnée de départ sur l'axe des ordonnées
     * @param toX   la coordonnée d'arrivée sur l'axe des abscisses
     * @param toY   la coordonnée d'arrivée sur l'axe des ordonnées
     * @return vrai si le mouvement est légal
     */
    public boolean isMoveLegal(int fromX, int fromY, int toX, int toY) {
        return isMoveLegal(fromX, fromY, toX, toY, getKingPosition(getCurrentPlayerColor()));
    }


    /**
     * Fonction qui détermine si un mouvement est légal avec une position de roi donnée
     * @param fromX la coordonnée de départ sur l'axe des abscisses
     * @param fromY la coordonnée de départ sur l'axe des ordonnées
     * @param toX   la coordonnée d'arrivée sur l'axe des abscisses
     * @param toY   la coordonnée d'arrivée sur l'axe des ordonnées
     * @param kingPosition Position du roi du joueur actuel à tester
     * @return vrai si le mouvement est légal
     */
    public boolean isMoveLegal(int fromX, int fromY, int toX, int toY, KingPosition kingPosition) {
        Piece fromPiece = board[fromX][fromY];
        Piece toPiece = (board[toX][toY] == null) ? null : board[toX][toY];

        board[toX][toY] = fromPiece;
        board[fromX][fromY] = null;

        boolean isInCheck = isInCheck(fromPiece.getPieceColor(), kingPosition);

        board[toX][toY] = toPiece;
        board[fromX][fromY] = fromPiece;

        return !isInCheck;
    }

    /**
     * Fonction qui détermine si le joueur attaqué est en échec
     * @param colorAttacked la couleur du joueur attaqué
     * @param kingPosition la position du roi
     * @return vrai si le joueur attaqué est en échec
     */
    public boolean isInCheck(PlayerColor colorAttacked, KingPosition kingPosition) {
        for (int i = 0; i < DIMENSION; ++i) {
            for (int j = 0; j < DIMENSION; ++j) {
                Piece p = getPiece(i, j);
                if (p != null && p.getPieceColor() != colorAttacked) {
                    if (p.isMoveValid(this, false, i, j, kingPosition.x, kingPosition.y)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * Fonction qui initialise le board et la vue
     */
    private void resetBoard() {
        addPieces(0, PlayerColor.WHITE);
        addPieces(DIMENSION - 1, PlayerColor.BLACK);

        for (int i = 0; i < DIMENSION; i++) {
            addPiece(i, 1, new Pawn(PlayerColor.WHITE));
            addPiece(i, 6, new Pawn(PlayerColor.BLACK));
        }
    }

    /**
     * Fonction qui installe correctement les pièces, exceptées les pions, lors de l'initialisation d'un jeu
     * @param y est la coordonnée des ordonnées
     * @param color est la couleur de la pièce
     */
    private void addPieces(int y, PlayerColor color) {
        addPiece(0, y, new Rook(color));
        addPiece(1, y, new Knight(color));
        addPiece(2, y, new Bishop(color));
        addPiece(3, y, new Queen(color));
        addPiece(5, y, new Bishop(color));
        addPiece(6, y, new Knight(color));
        addPiece(DIMENSION - 1, y, new Rook(color));
        addPiece(4, y, new King(color, (Rook) board[0][y], (Rook) board[DIMENSION - 1][y]));
    }

    /**
     * Fonction qui installe les pièces lors de l'initialisation d'un jeu
     * @param x est la coordonnée des abscisses
     * @param y est la coordonnée des ordonnées
     * @param piece la pièce à installer
     */
    private void addPiece(int x, int y, Piece piece) {
        board[x][y] = piece;
        view.putPiece(piece.getPieceType(), piece.getPieceColor(), x, y);
    }
}