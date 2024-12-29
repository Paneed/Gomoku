package joueur;

import plateau.Plateau;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BotNaif {

    private final char symbole;

    public BotNaif(char symbole) {
        this.symbole = symbole;
    }

    public boolean jouer(Plateau plateau) {
        List<String> positionsLibres = trouverCasesLibres(plateau);

        if (positionsLibres.isEmpty()) {
            System.out.println("Le plateau est plein. Aucun coup possible.");
            return false;
        }

        // Choix aléatoire d'une position libre
        Random random = new Random();
        String positionChoisie = positionsLibres.get(random.nextInt(positionsLibres.size()));

        // Placement de la pièce
        char colonne = positionChoisie.charAt(0);
        int ligne = Character.getNumericValue(positionChoisie.charAt(1)) - 1;

        plateau.setCase(ligne, colonne - 'A', symbole);
        System.out.println("BotNaif joue en position : " + positionChoisie);
        return true;
    }


    private List<String> trouverCasesLibres(Plateau plateau) {
        List<String> positionsLibres = new ArrayList<>();

        for (int i = 0; i < plateau.getNbLignes(); i++) {
            for (int j = 0; j < plateau.getNbColonnes(); j++) {
                if (plateau.getCase(i, j) == '.') { // Si la case est libre
                    char colonne = (char) ('A' + j);
                    int ligne = i + 1; // Ligne commence à 1 pour l'utilisateur
                    positionsLibres.add("" + colonne + ligne);
                }
            }
        }

        return positionsLibres;
    }
}
