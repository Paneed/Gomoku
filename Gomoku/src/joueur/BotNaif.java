package joueur;
import plateau.Plateau;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BotNaif {
    public BotNaif() {
    }

    public String jouer(Plateau plateau, char symboleBot) {
        List<String> positionsLibres = trouverCasesLibres(plateau);

        if (positionsLibres.isEmpty()) {
            System.out.println("Le plateau est plein. Aucun coup possible.");
            return "";
        }

        // Choix aléatoire d'une position libre
        Random random = new Random();
        String positionChoisie = positionsLibres.get(random.nextInt(positionsLibres.size()));

        // Placement de la pièce
        char colonne = positionChoisie.charAt(0);
        int ligne = Character.getNumericValue(positionChoisie.charAt(1)) - 1;

        plateau.setCase(ligne, colonne - 'A', symboleBot);  // Utilisation du symbole du bot passé en paramètre

        if (plateau.verifierAlignement(symboleBot)) {
            if(symboleBot == 'O') {
                System.out.println("le joueur White a gagné");
            }
            else {
                System.out.println("le joueur Black a gagné");
            }
        }
        return positionChoisie;
    }

    private List<String> trouverCasesLibres(Plateau plateau) {
        List<String> positionsLibres = new ArrayList<>();

        for (int i = 0; i < plateau.getNbLignes(); i++) {
            for (int j = 0; j < plateau.getNbColonnes(); j++) {
                if (plateau.getCase(i, j) == Plateau.CASE_VIDE) {  // Si la case est libre
                    char colonne = (char) ('A' + j);
                    int ligne = i + 1; // Ligne commence à 1 pour l'utilisateur
                    positionsLibres.add("" + colonne + ligne);
                }
            }
        }

        return positionsLibres;
    }
}
