package plateau;

public class Plateau {

    private char[][] plateauSize;

    public Plateau() {
        plateauSize = new char[7][7]; // Plateau 7x7
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

    public char[][] getPlateauSize() {
        return plateauSize;
    }

    public char getCase(int ligne, int colonne) {
        return plateauSize[ligne][colonne];
    }

    public void setCase(int ligne, int colonne, char valeur) {
        plateauSize[ligne][colonne] = valeur;
    }

    public void afficherPlateau() {

        System.out.print("  ");
        for (char c = 'A'; c < 'A' + plateauSize[0].length; c++) {
            System.out.print(c + " ");
        }
        System.out.println();

        for (int i = 0; i < plateauSize.length; i++) {
            System.out.print((i + 1) + " ");
            for (int j = 0; j < plateauSize[i].length; j++) {
                System.out.print(plateauSize[i][j] + " ");
            }
            System.out.println((i + 1));
        }

        System.out.print("  ");
        for (char c = 'A'; c < 'A' + plateauSize[0].length; c++) {
            System.out.print(c + " ");
        }
        System.out.println();
    }
}
