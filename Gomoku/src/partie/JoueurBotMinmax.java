package partie;

import jouer.Jouer;
import joueur.JoueurBlack;
import joueur.BotMinimax;
import plateau.Plateau;
import java.util.Scanner;

public class JoueurBotMinmax {
    private static boolean PartieContinue;

    public static void main(String[] args) {

        Plateau plateau = new Plateau();
        Scanner scanner = new Scanner(System.in);

        // Création du joueur Black (humain)
        System.out.print("Veuillez entrer le nom du joueur Black (X) : ");
        String nomJoueurBlack = scanner.nextLine().trim();

        while (nomJoueurBlack.isEmpty()) {
            System.out.println("Le nom ne peut pas être vide. Veuillez réessayer.");
            System.out.print("Veuillez entrer le nom du joueur Black (X) : ");
            nomJoueurBlack = scanner.nextLine().trim();
        }

        JoueurBlack joueurBlack = new JoueurBlack(nomJoueurBlack);

        // Demander la profondeur de réflexion du bot
        int profondeur = 4; // Profondeur par défaut
        System.out.print("\nVeuillez entrer la profondeur de réflexion pour le bot (par exemple, 4) : ");
        String profondeurInput = scanner.nextLine().trim();

        // Vérifier si l'entrée est un nombre entier valide
        try {
            profondeur = Integer.parseInt(profondeurInput);
            if (profondeur < 1) {
                System.out.println("La profondeur doit être un nombre entier positif. La profondeur par défaut (4) sera utilisée.");
                profondeur = 4;
            }
        } catch (NumberFormatException e) {
            System.out.println("Entrée invalide. La profondeur par défaut (4) sera utilisée.");
        }

        // Création du bot Minimax (White) avec la profondeur choisie
        BotMinimax botMinimax = new BotMinimax(plateau, 'O', 'X', profondeur); // Profondeur définie par l'utilisateur

        // Affichage des informations des joueurs
        System.out.println("\nInformations des joueurs :");
        joueurBlack.afficheInfos();
        System.out.println("Bot : Joueur White (O) - Profondeur de réflexion : " + profondeur);

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
                System.out.println("\nC'est le tour du joueur Black (X).");
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
                // Tour du bot Minimax (White)
                System.out.println("\nLe bot réfléchit...");

                // Le bot joue
                String coupBot = botMinimax.jouer(plateau, 'O');
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
                System.out.println("Félicitations ! " + joueurBlack.getName() + " a gagné !");
                plateau.showboard();
                PartieContinue = false;
            } else if (plateau.verifierAlignement('O')) {
                System.out.println("Le Bot (O) a gagné !");
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
