import chess.ChessController;
import chess.ChessView;
import chess.views.gui.GUIView;

/**
 * Classe de test
 *
 * @author : Valentin Bugna, Theodros Mulugeta et Stéphane Nascimento Santos
 * @since  : JDK 17.0.4
 */

public class MainTest {
    public static void main(String[] args) {
        try {
            // 1. Création du contrôleur pour gérer le jeu d’échec
            ChessController controller = new engine.Controller();

            // 2. Création de la vue
            ChessView gui = new GUIView(controller);         // mode GUI

            // 3. Lancement du programme
            controller.start(gui);
            controller.newGame();

            // Déplacement d'un pion:
            controller.move(3, 1, 3, 3); // avancer Pawn WHITE: OK
            controller.move(4, 6, 4, 4); // avancer Pawn BLACK: OK
            controller.move(2, 1, 2, 3); // avancer Pawn WHITE: OK
            controller.move(5, 6, 5, 4); // avancer Pawn BLACK: OK
            controller.move(6, 1, 6, 2); // avancer Pawn WHITE: OK
            controller.move(1, 6, 1, 4); // avancer Pawn BLACK: OK
            controller.move(0, 1, 0, 3); // avancer Pawn WHITE: OK
            controller.move(1, 4, 1, 3); // avancer Pawn BLACK: OK
            controller.move(3, 3, 3, 4); // avancer Pawn WHITE: OK
            controller.move(2, 6, 2, 4); // avancer Pawn BLACK: OK
            controller.move(3, 4, 2, 5); // avancer Pawn WHITE: OK - Prise en passant

            // Déplacement d'un fou :
            controller.move(5, 7, 2, 4); // avancer Bishop BLACK: OK
            controller.move(2, 0, 6, 4); // avancer Bishop WHITE: OK
            controller.move(2, 4, 1, 5); // avancer Bishop BLACK: OK
            controller.move(6, 4, 7, 5); // avancer Bishop WHITE: OK
            controller.move(1, 5, 0, 4); // avancer Bishop BLACK: OK
            controller.move(7, 5, 6, 6); // avancer Bishop WHITE: OK
            controller.move(0, 4, 1, 5); // avancer Bishop BLACK: OK
            controller.move(6, 6, 5, 7); // avancer Bishop WHITE: OK
            controller.move(1, 5, 4, 2); // avancer Bishop BLACK: OK
            controller.move(5, 7, 1, 3); // avancer Bishop WHITE: OK

            // Déplacement d'un cavalier :
            controller.move(6, 7, 7, 5); // avancer Knight BLACK: OK
            controller.move(6, 0, 7, 2); // avancer Knight WHITE: OK
            controller.move(7, 5, 6, 3); // avancer Knight BLACK: OK
            controller.move(7, 2, 6, 4); // avancer Knight WHITE: OK
            controller.move(6, 3, 7, 1); // avancer Knight BLACK: OK
            controller.move(6, 4, 7, 6); // avancer Knight WHITE: OK
            controller.move(7, 1, 5, 0); // avancer Knight BLACK: OK
            controller.move(7, 6, 5, 7); // avancer Knight WHITE: OK
            controller.move(5, 0, 6, 2); // avancer Knight BLACK: OK
            controller.move(5, 7, 6, 5); // avancer Knight WHITE: OK
            controller.move(6, 2, 7, 4); // avancer Knight BLACK: OK
            controller.move(6, 5, 7, 3); // avancer Knight WHITE: OK


            // Déplacement d'une tour :
            controller.move(7, 7, 7, 5); // avancer Rook BLACK:   OK
            controller.move(7, 0, 7, 2); // avancer Rook WHITE:   OK
            controller.move(7, 5, 6, 5); // avancer Rook BLACK:   OK
            controller.move(7, 2, 6, 2); // avancer Rook WHITE:   OK
            controller.move(6, 5, 4, 5); // avancer Rook BLACK:   OK
            controller.move(6, 2, 6, 6); // avancer Rook WHITE:   OK
            controller.move(4, 5, 6, 5); // avancer Rook BLACK:   OK
            controller.move(6, 6, 5, 6); // avancer Rook WHITE:   OK
            controller.move(6, 5, 6, 1); // avancer Rook BLACK:   OK
            controller.move(5, 6, 7, 6); // avancer Rook WHITE:   OK
            controller.move(6, 1, 6, 2); // avancer Rook BLACK:   OK
            controller.move(7, 6, 7, 5); // avancer Rook WHITE:   OK
            controller.move(6, 2, 5, 2); // avancer Rook BLACK:   OK
            controller.move(7, 5, 7, 6); // avancer Rook WHITE:   OK
            controller.move(5, 2, 5, 1); // avancer Rook BLACK:   OK
            controller.move(7, 6, 6, 6); // avancer Rook WHITE:   OK
            controller.move(3, 7, 2, 6); // avancer Queen BLACK:  OK
            controller.move(3, 0, 3, 1); // avancer Queen WHITE:  OK
            controller.move(2, 6, 3, 5); // avancer Queen BLACK:  OK
            controller.move(1, 0, 0, 2); // avancer Knight WHITE: OK
            controller.move(3, 5, 4, 5); // avancer Queen BLACK:  OK
            controller.move(4, 0, 2, 0); // avancer King WHITE:   OK - Grand roque
            controller.move(0, 0, 3, 0); // avancer Rook WHITE:   OK - Grand roque

            // Déplacement d'une reine :
            controller.move(4, 5, 3, 5); // avancer Queen BLACK: OK
            controller.move(3, 1, 3, 3); // avancer Queen WHITE: OK
            controller.move(3, 5, 3, 4); // avancer Queen BLACK: OK
            controller.move(3, 3, 2, 4); // avancer Queen WHITE: OK
            controller.move(3, 4, 3, 3); // avancer Queen BLACK: OK

            // Déplacement d'un roi :
            controller.move(3, 0, 3, 2); // avancer Rook WHITE:  OK
            controller.move(3, 3, 3, 2); // avancer Queen BLACK: OK
            controller.move(4, 1, 3, 2); // avancer Pawn WHITE:  OK
            controller.move(5, 1, 4, 1); // avancer Rook BLACK:  OK - Mise en échec

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}