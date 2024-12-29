package main;

import java.util.Scanner;

import jouer.IHumain;
import jouer.Jouer;
import joueur.BotNaif;
import joueur.JoueurBlack;
import joueur.JoueurWhite;
import plateau.Plateau;

public class Main {

    private static boolean PartieContinue;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Plateau plateau = new Plateau();
        IHumain joueurBlack = new JoueurBlack("Ogui");
        IHumain joueurWhite = new JoueurWhite("Phuong");
        BotNaif bot = new BotNaif();


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
                        System.out.println("Taille du plateau définie à : " + taille);
                    } catch (NumberFormatException e) {
                        System.out.println("Erreur : Taille du plateau invalide.");
                    }
                } else {
                    System.out.println("Format attendu : 'boardsize <taille>'");
                }
            } else if (commande.equals("showboard")) {
                plateau.showboard();
            } else if (commande.equals("clearboard")) {
                plateau.clearPlateau();
                System.out.println("Plateau réinitialisé. Joueur Black commence.");
                tourJoueurBlack = true; // Réinitialiser le tour
            } else if (commande.equals("quit")) {
                plateau.quit();
                PartieContinue = false;

            } else if (commande.startsWith("play")) {
                String[] joueurs = commande.split(" ");

                // Gestion stricte des toursbo
                if (tourJoueurBlack) { // Tour de Joueur Black
                    if (joueurs.length == 3 && joueurs[1].equalsIgnoreCase("Black")) {
                        if (Jouer.play(commande, joueurBlack, plateau)) {
                            tourJoueurBlack = false;
                            System.out.println("Tour de Joueur White :");
                        }
                    } else {
                        System.out.println("Erreur : C'est au tour du joueur Black de jouer !");
                    }
                } else {
                    if (joueurs.length == 3 && joueurs[1].equalsIgnoreCase("White")) {
                        if (Jouer.play(commande, joueurWhite, plateau)) {
                            tourJoueurBlack = true;
                            System.out.println("Tour de Joueur Black :");
                        }
                    } else {
                        System.out.println("Erreur : C'est au tour du joueur White de jouer !");
                    }
                }
            } else if (commande.equals("genmov")) {
                // Le bot joue en utilisant le symbole du joueur actif
                String positionChoisie;
                if (tourJoueurBlack) {
                    System.out.println("Bot joue pour Joueur Black :");
                    positionChoisie = bot.jouer(plateau, joueurBlack.getSymbole());
                    if (!positionChoisie.equals("")) {
                        tourJoueurBlack = false; // Passer au joueur suivant
                    }
                } else {
                    System.out.println("Bot joue pour Joueur White :");
                    positionChoisie = bot.jouer(plateau, joueurWhite.getSymbole());
                    if (!positionChoisie.equals("")) {
                        tourJoueurBlack = true; // Passer au joueur suivant
                    }
                }
            } else {
                System.out.println("Commande invalide. Utilisez 'boardsize', 'play', 'showboard', 'genmov', ou 'quit'.");
            }
        }
        scanner.close();
    }
}
