package partie;

import jouer.Jouer;
import joueur.JoueurBlack;
import joueur.BotNaif;
import plateau.Plateau;

import java.util.Scanner;

public class JoueurBotNaif {
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

        // Création du bot White
        BotNaif botWhite = new BotNaif();

        // Afficher les informations des joueurs
        System.out.println("\nInformations des joueurs :");
        joueurBlack.afficheInfos();
        System.out.println("Bot : Joueur White (O)");

        // Demander la taille du plateau et l'initialiser
        System.out.print("\nEntrez la taille du plateau (par exemple, 'boardsize 4') : ");
        String commande = scanner.nextLine().trim();

        if (commande.startsWith("boardsize")) {
            String[] parts = commande.split(" ");
            if (parts.length == 2) {
                try {
                    int taille = Integer.parseInt(parts[1]);
                    plateau.boardsize(taille);  // Initialisation du plateau avec la taille donnée
                    plateau.showboard();
                    System.out.println("Taille du plateau définie à : " + taille);
                    System.out.print("Utilisez 'boardsize', 'play Black Position(D5)', 'showboard', ou 'quit' comme commandes\n");
                } catch (NumberFormatException e) {
                    System.out.println("Erreur : Taille du plateau invalide.");
                }
            } else {
                System.out.println("Erreur : Format de la commande invalide.");
            }
        }

        // Logique de la partie
        System.out.println("\nLa partie peut commencer !");
        PartieContinue = true;
        boolean tourJoueurBlack = true; // Le joueur Black commence toujours

        while (PartieContinue) {
            if (tourJoueurBlack) {
                // Tour du joueur Black
                commande = scanner.nextLine();

                if (commande.equals("quit")) {
                    plateau.quit();
                    PartieContinue = false;
                } else if (commande.startsWith("play Black")) {
                    if (Jouer.play(commande, joueurBlack, plateau)) {
                        tourJoueurBlack = false; // Passer au tour du bot
                    }
                } else if (commande.equals("showboard")) {
                    plateau.showboard();
                } else {
                    System.out.println("Commande invalide. Utilisez 'play', 'showboard', ou 'quit'.");
                }
            } else {
                // Tour du bot White
                System.out.println("Le bot réfléchit...");
                String coupBot = botWhite.jouer(plateau, 'O');
                if (!coupBot.isEmpty()) {
                    System.out.println("Le bot a joué en position : " + coupBot);
                    plateau.showboard();
                    tourJoueurBlack = true; // Passer au tour du joueur Black
                } else {
                    PartieContinue = false; // Si aucun coup n'est possible, la partie s'arrête
                    System.out.println("La partie est terminée.");
                }
            }

            // Vérification de fin de partie
            if (plateau.verifierAlignement('X')) {
                plateau.showboard();
                PartieContinue = false;
            } else if (plateau.verifierAlignement('O')) {
                plateau.showboard();
                PartieContinue = false;
            } else if (plateau.estPlein()) {
                System.out.println("Match nul ! Le plateau est plein.");
                plateau.showboard();
                PartieContinue = false;
            }
        }

        scanner.close();
    }
}
