/* ---------------------------
Laboratoire : 08
Fichier :     
Auteur(s) :   Glover Santos
Date :        10-01-2021

But : Programme de tests du jeu d'échec

Compilateur : javac 11.0.4
--------------------------- */

import chess.ChessController;
import chess.ChessView;
import chess.engine.Board;
import chess.engine.Tile;
import chess.views.gui.GUIView;

public class Test {
    public static void main(String[] args) {
        try {
            // Création du contrôleur pour gérer le jeu d'échec
            ChessController controller = new Board(); // Board est une classe faite et nommée par les étudiant

            // Création de la vue désirée
            ChessView view = new GUIView(controller); // ou console
            //ChessView view = new ConsoleView(controller); // ou GUI

            // Lancement du programme
            controller.start(view);

            // Création d'une nouvelle partie
            controller.newGame();

            // Vérification des mouvements du pion
            controller.move(0, 1, 0, 2); // avancer pion blanc : OK
            controller.move(0, 6, 0, 5); // avancer pion noir : OK
            controller.move(0, 2, 0, 4); // avancer 2 fois pion noir déjà avancé : KO
            controller.move(0, 5, 0, 3); // avancer 2 fois pion noir déjà avancé : KO
            controller.move(1, 1, 1, 3); // avancer 2 fois pion noir pas encore avancé : OK
            controller.move(1, 6, 1, 4); // avancer 2 fois pion noir pas encore avancé : OK
            controller.move(1, 3, 1, 4); // avancer si un pion bloque : KO
            controller.move(1, 3, 1, 2); // reculer : KO
            controller.move(0, 2, 0, 3);
            controller.move(1, 4, 0, 3); // manger pièce en diagonale : OK

            // Vérification des mouvements de la tour
            controller.move(0, 0, 0, 2); // déplacement vertical bas-haut : OK
            controller.move(0, 7, 0, 6); // déplacement vertical haut-bas : OK
            controller.move(0, 2, 4, 2); // déplacement horizontal gauche-droite : OK
            controller.move(0, 3, 0, 2);
            controller.move(4, 2, 2, 2); // déplacement horizontal droite-gauche : OK
            controller.move(0, 6, 1, 5); // déplacement en diagonale : KO
            controller.move(0, 6, 2, 6); // déplacement sur une case occupée du joueur : KO
            controller.move(0, 2, 0, 1);
            controller.move(2, 2, 2, 6); // Manger pion : OK

            // Vérification des mouvements du fou
            controller.move(2, 7, 1, 6); // Déplacement diagonale bas-gauche : OK
            controller.move(2, 0, 1, 1); // Déplacement diagonale haut-gauche : OK
            controller.move(1, 6, 3, 4); // Déplacement diagonale bas-droite : OK
            controller.move(1, 1, 3, 3); // Déplacement diagonale haut-droite : OK
            controller.move(3, 4, 4, 4); // Déplacement horizontal : KO
            controller.move(3, 4, 1, 5); // Déplacement place occupée du joueur : KO
            controller.move(3, 4, 6, 1); // Manger pion : OK

            // Vérification des mouvements du cheval
            controller.move(6, 0, 5, 2); // Déplacement en L avec saut : OK
            controller.move(1, 7, 2, 5); // Déplacement en L : OK
            controller.move(5, 2, 6, 4); // Déplacement en L : OK
            controller.move(2, 5, 1, 3); // Déplacement en L avec manger pion : OK
            controller.move(6, 4, 4, 5); // Déplacement en L : OK
            controller.move(1, 3, 3, 2); // Déplacement en L : OK
            controller.move(4, 5, 2, 6); // Déplacement en L sur case occupée du joueur : KO
            controller.move(4, 5, 4, 4); // Autre déplacement : KO

            // Vérification des mouvements de la reine
                // Non vérifiés car ils utilisent le même algorithme que la tour et les fou ==> déjà validé

            // Vérification des mouvements du roi
            controller.move(4, 1, 3, 2); // Ouverture du pion blanc
            controller.move(5, 6, 5, 4); // Ouverture du pion noir
            controller.move(4, 0, 4, 1); // Déplacement vertical haut : OK
            controller.move(4, 7, 5, 6); // Déplacement diagonal bas-droite : OK
            controller.move(4, 1, 4, 0); // Déplacement vertical bas : OK
            controller.move(5, 6, 4, 5); // Déplacement diagonal bas-gauche : OK
            controller.move(2, 1, 2, 3);
            controller.move(4, 5, 3, 5); // Déplacement horizontal droite : OK
            controller.move(2, 3, 2, 4);
            controller.move(3, 5, 4, 5); // Déplacement horizontal gauche : OK
            controller.move(4, 0, 4, 1); // Déplacement vertical haut : OK
            controller.move(4, 5, 5, 6); // Déplacement diagonal haut-droite : OK

            // Ne pas se mettre en échec
            controller.move(4, 1, 5, 2); // Annulation du mouvement : OK

            // Mettre joueur adverse en échec
            controller.move(3, 0, 1, 2); // Message échec : OK
            controller.move(7, 6, 7, 5); // Mouvement qui n'enlève pas l'échec : KO
            controller.move(4, 6, 4, 5); // Mouvement qui annule l'échec : OK

            controller.move(4, 1, 4, 0);

            // Promotion de pion
            controller.move(0, 1, 0, 0); // Choix pièce : OK

            controller.move(5, 0, 4, 1);
            controller.move(3, 7, 2, 6);
            controller.move(1, 2, 1, 3);

            // Prise en passant
            controller.move(3, 6, 3, 4); // Avancer de deux pion noir : OK
            controller.move(2, 4, 3, 5); // Prise en passant : OK

            controller.move(6, 1, 5, 2);

            // Grand/Petit roque
            controller.move(4, 0, 6, 0); // Roi à déjà bougé / Case mise en échec : KO

            controller.newGame();
            controller.move(4, 1, 4, 2);
            controller.move(3, 6, 3, 5);
            controller.move(5, 0, 3, 2);
            controller.move(2, 7, 4, 5);
            controller.move(6, 0, 7, 2);
            controller.move(3, 7, 3, 6);
            controller.move(4, 0, 6, 0);
            controller.move(1, 7, 2, 5); // Petit roque : OK
            controller.move(3, 0, 4, 0);
            controller.move(4, 7, 2, 7); // Grand roque : OK

            // Validité des paramètres
            Tile tile = new Tile(1, 10); // RuntimeException: La case doit être comprise entre 0 et 7

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
