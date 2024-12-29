package jouer;

import plateau.Plateau;

public class Jouer {

    private static final String MESSAGE_COMMANDE_INVALIDE = "Commande invalide. Format attendu : 'play <black|white> <position>'";
    private static final String MESSAGE_ACTION_INCONNUE = "Commande inconnue : ";
    private static final String MESSAGE_COULEUR_INVALIDE = "Couleur invalide : ";
    private static final String MESSAGE_POSITION_INVALIDE = "Position invalide. Utilisez un format comme 'C3'.";
    private static final String MESSAGE_POSITION_HORS_LIMITE = "Position en dehors des limites du plateau.";
    private static final String MESSAGE_POSITION_OCCUPEE = "La case est déjà occupée.";

    public static boolean play(String commande, Plateau plateau) {

        String[] parties = commande.split(" ");
        if (parties.length != 3) {
            System.out.println(MESSAGE_COMMANDE_INVALIDE);
            return false;
        }

        String action = parties[0];
        String couleur = parties[1];
        String position = parties[2];

        if (!action.equals("play")) {
            System.out.println(MESSAGE_ACTION_INCONNUE + action);
            return false;
        }

        char symbole = getSymbole(couleur);
        if (symbole == '\0') {
            System.out.println(MESSAGE_COULEUR_INVALIDE + couleur);
            return false;
        }

        boolean coupValide = placerPiece(position, symbole, plateau);
        if (coupValide) {
            if (plateau.verifierAlignement(symbole)) {
                System.out.println("Le joueur " + couleur + " a gagné !");
            } else if (plateau.estPlein()) {
                System.out.println("Match nul ! Le plateau est plein sans gagnant.");
            }
        }
        return coupValide;
    }

    private static char getSymbole(String couleur) {
        return switch (couleur) {
            case "black" -> 'O';
            case "white" -> 'X';
            default -> '\0';
        };
    }

    public static boolean placerPiece(String position, char symbole, Plateau plateau) {
        if (!position.matches("^[A-Z]+[0-9]+$")) {
            System.out.println(MESSAGE_POSITION_INVALIDE);
            return false;
        }

        char colonne = position.charAt(0);
        String ligneStr = position.substring(1);  // Prendre la partie numérique de la position
        int colIndex = colonne - 'A';
        int rowIndex;

        try {
            rowIndex = Integer.parseInt(ligneStr) - 1; // Convertir la ligne à un index
        } catch (NumberFormatException e) {
            System.out.println(MESSAGE_POSITION_INVALIDE);
            return false;
        }

        if (colIndex < 0 || colIndex >= plateau.getNbColonnes() || rowIndex < 0 || rowIndex >= plateau.getNbLignes()) {
            System.out.println(MESSAGE_POSITION_HORS_LIMITE);
            return false;
        }

        if (plateau.getCase(rowIndex, colIndex) == '.') {
            plateau.setCase(rowIndex, colIndex, symbole);
            return true;
        } else {
            System.out.println(MESSAGE_POSITION_OCCUPEE);
            return false;
        }
    }
}
