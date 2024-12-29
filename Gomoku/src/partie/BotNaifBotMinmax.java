package partie;

import joueur.BotNaif;
import joueur.BotMinimax;
import plateau.Plateau;

import java.util.Scanner;

public class BotNaifBotMinmax {

    public static void main(String[] args) {
        Plateau plateau = new Plateau();
        Scanner scanner = new Scanner(System.in);

        // Création des bots
        BotNaif botNaif = new BotNaif();
        BotMinimax botMinimax = new BotMinimax(plateau, 'X', 'O', 5);  // Profondeur 4 pour le Minimax

        // Demander la taille du plateau et l'initialiser
        System.out.print("Entrez la taille du plateau (par exemple, 'boardsize 4') : ");
        String commande = scanner.nextLine().trim();

        if (commande.startsWith("boardsize")) {
            String[] parts = commande.split(" ");
            if (parts.length == 2) {
                try {
                    int taille = Integer.parseInt(parts[1]);
                    plateau.boardsize(taille);  // Initialisation du plateau avec la taille donnée
                    System.out.println("Taille du plateau définie à : " + taille);
                } catch (NumberFormatException e) {
                    System.out.println("Erreur : Taille du plateau invalide.");
                }
            } else {
                System.out.println("Erreur : Format de la commande invalide.");
            }
        }

        // Afficher le début de la partie
        System.out.println("\nDébut de la partie entre Bot Naif (X) et Bot Minimax (O)");

        boolean partieEnCours = true;
        boolean tourBotNaif = true;  // Le Bot Naif commence

        while (partieEnCours) {
            if (tourBotNaif) {
                // Tour du Bot Naif
                System.out.println("\nC'est au tour du Bot Naif (X).");
                String coup = botNaif.jouer(plateau, 'X');
                if (!coup.isEmpty()) {
                    System.out.println("Bot Naif a joué en position : " + coup);
                    plateau.showboard();
                    if (plateau.verifierAlignement('X')) {
                        System.out.println("Le Bot Naif (X) a gagné !");
                        plateau.showboard();
                        partieEnCours = false;
                    }
                }
                tourBotNaif = false;  // Passer au tour du Bot Minimax
            } else {
                // Tour du Bot Minimax
                System.out.println("\nC'est au tour du Bot Minimax (O).");
                int[] coup = botMinimax.choisirCoup();
                if (coup[0] != -1 && coup[1] != -1) {
                    System.out.println("Bot Minimax a joué en position : " + (char)('A' + coup[1]) + (coup[0] + 1));
                    plateau.setCase(coup[0], coup[1], 'O');
                    plateau.showboard();
                    if (plateau.verifierAlignement('O')) {
                        System.out.println("Le Bot Minimax (O) a gagné !");
                        plateau.showboard();
                        partieEnCours = false;
                    }
                }
                tourBotNaif = true;  // Passer au tour du Bot Naif
            }

            // Vérification de la fin de partie
            if (plateau.estPlein()) {
                System.out.println("Match nul ! Le plateau est plein.");
                plateau.showboard();
                partieEnCours = false;
            }
        }

        scanner.close();
    }
}
