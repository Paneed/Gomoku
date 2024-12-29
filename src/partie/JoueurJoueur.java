package partie;

import jouer.Jouer;
import joueur.JoueurBlack;
import joueur.JoueurWhite;
import java.util.Scanner;
import plateau.Plateau;

public class JoueurJoueur {
    private static boolean PartieContinue;

    public static void main(String[] args) {

        Plateau plateau = new Plateau();
        Scanner scanner = new Scanner(System.in);

        // Création du joueur Black
        System.out.print("Veuillez entrer le nom du joueur Black (X) : ");
        String nomJoueurBlack = scanner.nextLine().trim();

        while (nomJoueurBlack.isEmpty()) {
            System.out.println("Le nom ne peut pas être vide. Veuillez réessayer.");
            System.out.print("Veuillez entrer le nom du joueur Black (X) : ");
            nomJoueurBlack = scanner.nextLine().trim();
        }

        JoueurBlack joueurBlack = new JoueurBlack(nomJoueurBlack);

        // Création du joueur White
        System.out.print("Veuillez entrer le nom du joueur White (O) : ");
        String nomJoueurWhite = scanner.nextLine().trim();

        while (nomJoueurWhite.isEmpty()) {
            System.out.println("Le nom ne peut pas être vide. Veuillez réessayer.");
            System.out.print("Veuillez entrer le nom du joueur White (O) : ");
            nomJoueurWhite = scanner.nextLine().trim();
        }

        JoueurWhite joueurWhite = new JoueurWhite(nomJoueurWhite);

        // Afficher les informations des joueurs
        System.out.println("\nInformations des joueurs :");
        joueurBlack.afficheInfos();
        joueurWhite.afficheInfos();

        // Logique de la partie
        System.out.println("\nLa partie peut commencer !");
        System.out.print("Entrez la taille du plateau (par exemple, 'boardsize 4') : ");

        PartieContinue = true;
        boolean tourJoueurBlack = true; // Le joueur Black commence toujours

        while (PartieContinue) {
            String commande = scanner.nextLine();

            if (commande.startsWith("boardsize")) {
                String[] parts = commande.split(" ");
                if (parts.length == 2) {
                    try {
                        int taille = Integer.parseInt(parts[1]);
                        plateau.boardsize(taille);
                        plateau.showboard();
                        System.out.println("Taille du plateau définie à : " + taille);
                        System.out.print("Utilisez 'boardsize', 'play Black|White Position(D5)', 'showboard', 'genmov', ou 'quit' comme commandes\n");
                    } catch (NumberFormatException e) {
                        System.out.println("Erreur : Taille du plateau invalide.");
                    }
                }
            } else if (commande.equals("showboard")) {
                plateau.showboard();
            } else if (commande.equals("clearboard")) {
                plateau.clearPlateau();
                System.out.println("Plateau réinitialisé. Joueur Black commence.");
                tourJoueurBlack = true;
            } else if (commande.equals("quit")) {
                plateau.quit();
                PartieContinue = false;
            } else if (commande.startsWith("play")) {
                String[] joueurs = commande.split(" ");

                if (tourJoueurBlack) { // Tour de Joueur Black
                    if (joueurs.length == 3 && joueurs[1].equalsIgnoreCase("Black")) {
                        if (Jouer.play(commande, joueurBlack, plateau)) {
                            tourJoueurBlack = false;
                            System.out.println("Tour de Joueur White :");
                        }
                    } else {
                        System.out.println("Erreur : C'est au tour du joueur Black de jouer !");
                    }
                } else { // Tour de Joueur White
                    if (joueurs.length == 3 && joueurs[1].equalsIgnoreCase("White")) {
                        if (Jouer.play(commande, joueurWhite, plateau)) {
                            tourJoueurBlack = true;
                            System.out.println("Tour de Joueur Black :");
                        }
                    } else {
                        System.out.println("Erreur : C'est au tour du joueur White de jouer !");
                    }
                }

                // Vérifier si un joueur a gagné après le coup
                if (plateau.verifierAlignement(joueurBlack.getSymbole())) {
                    System.out.println("Le joueur Black a gagné !");
                    plateau.showboard();
                    PartieContinue = false;
                } else if (plateau.verifierAlignement(joueurWhite.getSymbole())) {
                    System.out.println("Le joueur White a gagné !");
                    plateau.showboard();
                    PartieContinue = false;
                } else if (plateau.estPlein()) { // Vérifie si le plateau est plein
                    System.out.println("Match nul, le plateau est plein !");
                    plateau.showboard();
                    PartieContinue = false;
                }
            } else {
                System.out.println("Commande invalide. Utilisez 'boardsize', 'play', 'showboard', 'genmov', ou 'quit'.");
            }
        }
        scanner.close();
    }
}

