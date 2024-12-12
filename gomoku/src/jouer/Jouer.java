package jouer;

import plateau.Plateau;

public class Jouer {


    public static boolean play(String commande, Plateau plateau) {

        String[] parties = commande.split(" ");
        if (parties.length != 3) {
            System.out.println("Commande invalide. Format attendu : 'play <black|white> <position>'");
            return false;
        }

        String action = parties[0];
        String couleur = parties[1];
        String position = parties[2];


        if (!action.equals("play")) {
            System.out.println("Commande inconnue : " + action);
            return false;
        }

        char symbole;
        if (couleur.equals("black")) {
            symbole = 'O';
        } else if (couleur.equals("white")) {
            symbole = 'X';
        } else {
            System.out.println("Couleur invalide : " + couleur);
            return false;
        }


        return placerPiece(position, symbole, plateau);
    }


    public static boolean placerPiece(String position, char symbole, Plateau plateau) {
        if (position.length() != 2) {
            System.out.println("Position invalide. Utilisez un format comme 'C3'.");
            return false;
        }

        char colonne = position.charAt(0);
        char ligne = position.charAt(1);

        int colIndex = colonne - 'A';
        int rowIndex = ligne - '1';


        if (colIndex < 0 || colIndex >= plateau.getPlateauSize().length || rowIndex < 0 || rowIndex >= plateau.getPlateauSize().length) {
            System.out.println("Position en dehors des limites du plateau.");
            return false;
        }


        if (plateau.getCase(rowIndex, colIndex) == '.') {
            plateau.setCase(rowIndex, colIndex, symbole);
            return true;
        } else {
            System.out.println("La case est déjà occupée.");
            return false;
        }
    }
}
