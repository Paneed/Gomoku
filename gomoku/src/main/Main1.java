package main;

import joueur.BotMinmax;
import plateau.Plateau;

import java.util.Scanner;

public class Main1{

    private static boolean PartieContinue;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Plateau plateau = new Plateau();

        System.out.print("Entrez la taille du plateau (par exemple, 'boardsize 5') : ");
        String commande = scanner.nextLine();
        if (commande.startsWith("boardsize")) {
            String[] parts = commande.split(" ");
            if (parts.length == 2) {
                try {
                    int taille = Integer.parseInt(parts[1]);
                    plateau.boardsize(taille);
                    System.out.println("Taille du plateau définie à : " + taille);
                } catch (NumberFormatException e) {
                    System.out.println("Erreur : Taille du plateau invalide.");
                    return;
                }
            } else {
                System.out.println("Format attendu : 'boardsize <taille>'");
                return;
            }
        }

        System.out.print("Choisissez votre symbole (X ou O) : ");
        char symboleHumain = scanner.nextLine().toUpperCase().charAt(0);
        char symboleBot = (symboleHumain == 'X') ? 'O' : 'X';

        System.out.print("Choisissez la profondeur de réflexion du bot (par exemple, 3) : ");
        int profondeurBot = Integer.parseInt(scanner.nextLine());

        BotMinmax bot = new BotMinmax(plateau, symboleBot, symboleHumain, profondeurBot);

        PartieContinue = true;
        boolean tourHumain = true;

        System.out.println("La partie commence !");
        plateau.showboard();

        while (PartieContinue) {
            if (tourHumain) {
                System.out.println("Votre tour. Entrez votre coup (format : ligne colonne, ex: 2 B) : ");
                String[] input = scanner.nextLine().split(" ");
                if (input.length == 2) {
                    try {
                        int ligne = Integer.parseInt(input[0]) - 1; // Convertir en index (0-based)
                        int colonne = input[1].toUpperCase().charAt(0) - 'A'; // Convertir en index (0-based)

                        if (plateau.getCase(ligne, colonne) == '.') {
                            plateau.setCase(ligne, colonne, symboleHumain);
                            tourHumain = false;
                        } else {
                            System.out.println("Cette case est déjà occupée. Essayez encore.");
                        }
                    } catch (Exception e) {
                        System.out.println("Format incorrect. Essayez encore (format : ligne colonne, ex: 2 B).");
                    }
                } else {
                    System.out.println("Format incorrect. Essayez encore.");
                }
            } else {
                System.out.println("Tour du bot...");
                int[] coup = bot.choisirCoup();
                plateau.setCase(coup[0], coup[1], symboleBot);
                System.out.printf("Bot joue en (%d, %c)\n", coup[0] + 1, (char) (coup[1] + 'A'));
                tourHumain = true;
            }

            plateau.showboard();

            // Vérification de l'état de la partie
            if (plateau.verifierAlignement(symboleHumain)) {
                System.out.println("Félicitations ! Vous avez gagné !");
                PartieContinue = false;
            } else if (plateau.verifierAlignement(symboleBot)) {
                System.out.println("Le bot a gagné. Bonne chance la prochaine fois !");
                PartieContinue = false;
            } else if (plateau.estPlein()) {
                System.out.println("Match nul !");
                PartieContinue = false;
            }
        }

        scanner.close();
    }
}
