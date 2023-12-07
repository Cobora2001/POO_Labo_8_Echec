/**
 * Classe Main
 *
 * @author : Valentin Bugna, Theodros Mulugeta et Stéphane Nascimento Santos
 * @since  : JDK 17.0.4
 */

import chess.ChessController;
import chess.ChessView;
import chess.views.console.ConsoleView;
import chess.views.gui.GUIView;

public class Main {
    public static void main(String[] args) {
        // 1. Création du contrôleur pour gérer le jeu d’échec
        ChessController controller = new engine.Controller();

        // 2. Création de la vue
        ChessView gui = new GUIView(controller);         // mode GUI
        ChessView console = new ConsoleView(controller); // mode Console ("a2a3")

        // 3. Lancement du programme
        controller.start(gui);
        controller.newGame();
    }
}