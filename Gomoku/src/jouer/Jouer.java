package jouer;

import plateau.Plateau;

public class Jouer {

    private static final String MESSAGE_COMMANDE_INVALIDE = "Commande invalide. Format attendu : 'play <black|white> <position>'";
    private static final String MESSAGE_POSITION_INVALIDE = "Position invalide. Utilisez un format comme 'C3'.";
    private static final String MESSAGE_POSITION_HORS_LIMITE = "Position en dehors des limites du plateau.";
    private static final String MESSAGE_POSITION_OCCUPEE = "La case est déjà occupée.";

    public static boolean play(String commande, IHumain joueur, Plateau plateau) {

        // Découper la commande
        String[] parties = commande.split(" ");
        if (parties.length != 3 || !parties[0].equals("play")) {
            System.out.println(MESSAGE_COMMANDE_INVALIDE);
            return false;
        }

        String couleur = parties[1];
        String position = parties[2];

        // Vérifier si la couleur correspond au joueur
        if (!couleur.equalsIgnoreCase("black") && !couleur.equalsIgnoreCase("white")) {
            System.err.println("? Couleur invalide : " + couleur);
            return false;
        }

        if ((couleur.equals("black") && joueur.getSymbole() != 'X') ||
                (couleur.equals("white") && joueur.getSymbole() != 'O')) {
            System.out.println("Erreur : La couleur " + couleur + " ne correspond pas au joueur " + joueur.getName());
            return false;
        }

        // Tenter de placer la pièce
        boolean coupValide = placerPiece(position, joueur.getSymbole(), plateau);
        if (coupValide) {
            if (plateau.verifierAlignement(joueur.getSymbole())) {
                System.out.println("Félicitations ! Le joueur " + joueur.getName() + " a gagné !");
            } else if (plateau.estPlein()) {
                System.out.println("Match nul ! Le plateau est plein sans gagnant.");
            }
        }
        return coupValide;
    }


    public static boolean placerPiece(String position, char symbole, Plateau plateau) {
        if (!position.matches("^[A-Z]+[0-9]+$")) {
            System.out.println(MESSAGE_POSITION_INVALIDE);
            return false;
        }

        char colonne = position.charAt(0);
        String ligneStr = position.substring(1); // Prendre la partie numérique de la position
        int colIndex = colonne - 'A';
        int rowIndex;

        try {
            rowIndex = Integer.parseInt(ligneStr) - 1; // Convertir la ligne à un index
        } catch (NumberFormatException e) {
            System.out.println(MESSAGE_POSITION_INVALIDE);
            return false;
        }

        // Vérification si la position est hors des limites du plateau
        if (colIndex < 0 || colIndex >= plateau.getNbColonnes() || rowIndex < 0 || rowIndex >= plateau.getNbLignes()) {
            System.out.println(MESSAGE_POSITION_HORS_LIMITE);
            return false;
        }

        // Vérifier si la case est occupée
        if (plateau.getCase(rowIndex, colIndex) != Plateau.CASE_VIDE) {
            System.out.println(MESSAGE_POSITION_OCCUPEE);
            return false;
        }

        // Placer la pièce dans la case
        plateau.setCase(rowIndex, colIndex, symbole);
        return true;
    }
}
