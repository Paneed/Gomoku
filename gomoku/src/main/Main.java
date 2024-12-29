package main;

import joueur.Humain;
import plateau.Plateau;
import jouer.Jouer;

import java.util.Scanner;

import static jouer.Jouer.play;

public class Main {

    public static void main(String[] args) {

        Plateau plateau = new Plateau(3, 3);


        Humain joueur1 = new Humain("Charene", 'O');
        Humain joueur2 = new Humain("Eric", 'X');

        // Scanner pour lire les commandes des joueurs
        Scanner scanner = new Scanner(System.in);

        System.out.println("Début de la partie !");
        joueur1.afficherInfo();
        joueur2.afficherInfo();

        // Variables de gestion du tour
        Humain joueurActuel = joueur1; // Le joueur actuel commence par joueur1
        boolean partieTerminee = false;

        // Boucle principale pour jouer
        while (!partieTerminee) {
            // Afficher le plateau actuel
            plateau.afficherPlateau();

            // Demander au joueur actuel de jouer
            System.out.println(joueurActuel.getNom() + " (" + joueurActuel.getSymbole() + "), entrez votre coup (ex : play <black|white> <position>) :");
            String commande = scanner.nextLine();

            // Valider la commande
            boolean coupValide = play(commande, plateau);

            // Si le coup est valide, vérifier si ce joueur a gagné
            if (coupValide) {
                if (plateau.verifierAlignement(joueurActuel.getSymbole())) {
                    System.out.println("🎉 Félicitations " + joueurActuel.getNom() + ", vous avez gagné !");
                    plateau.afficherPlateau();
                    partieTerminee = true;
                } else if (plateau.estPlein()) {
                    // Vérifier si le plateau est plein
                    System.out.println("Match nul ! Le plateau est rempli et aucun joueur n'a gagné.");
                    plateau.afficherPlateau();
                    partieTerminee = true;
                } else {
                    // Alterner le joueur
                    joueurActuel = (joueurActuel == joueur1) ? joueur2 : joueur1;
                }
            } else {
                System.out.println("Coup invalide. Réessayez !");
            }
        }

        System.out.println("Fin de la partie. Merci d'avoir joué !");
        scanner.close();
    }
}
