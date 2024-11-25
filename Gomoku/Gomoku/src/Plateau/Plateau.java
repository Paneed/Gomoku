package Plateau;

public class Plateau {
    private char[][] plateauSize;

    public Plateau() {
        plateauSize = new char[7][7];
        createPlateau();
    }

    public void createPlateau() {
        for (int i = 0; i < plateauSize.length; i++) {
            for (int j = 0; j < plateauSize[i].length; j++) {
                plateauSize[i][j] = '.'; // Case vide
            }
        }
    }

    public void clearPlateau() {
        createPlateau();
    }


    public void afficherPlateau() {
        // Ajouter des labels pour les colonnes
        System.out.print("  ");
        for (char c = 'A'; c < 'A' + plateauSize[0].length; c++) {
            System.out.print(c + " ");
        }
        System.out.println();

        // Afficher chaque ligne avec son numéro
        for (int i = 0; i < plateauSize.length; i++) {
            System.out.print((i + 1) + " "); // Label de la ligne
            for (int j = 0; j < plateauSize[i].length; j++) {
                System.out.print(plateauSize[i][j] + " ");
            }
            System.out.println();
        }
    }
}