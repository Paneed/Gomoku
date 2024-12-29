package plateau;

public class Plateau {

    private char[][] plateau;

    public void boardsize (int n){
        if (n > 26 && n <=0) {
            throw new IllegalArgumentException("La taille du plateau ne peut dépasser 26");
        }
        plateau = new char[n][n];
        createPlateau();
    }

    public void createPlateau() {
        for (int i = 0; i < plateau.length; i++) {
            for (int j = 0; j < plateau[i].length; j++) {
                plateau[i][j] = '.';
            }
        }
    }

    public void clearPlateau() {
        createPlateau();
    }

    public void quit(){
        System.exit(0);
    }

    public char getCase(int ligne, int colonne) {
        if (estPositionValide(ligne, colonne)) {
            return plateau[ligne][colonne];
        }
        throw new IllegalArgumentException("Position invalide : " + ligne + ", " + colonne);
    }

    public void setCase(int ligne, int colonne, char valeur) {
        if (estPositionValide(ligne, colonne)) {
            plateau[ligne][colonne] = valeur;
        } else {
            throw new IllegalArgumentException("Position invalide : " + ligne + ", " + colonne);
        }
    }

    public boolean estPositionValide(int ligne, int colonne) {
        return ligne >= 0 && ligne < plateau.length && colonne >= 0 && colonne < plateau[0].length;
    }

    public int getNbLignes() {
        return plateau.length;
    }

    public int getNbColonnes() {
        return plateau[0].length;
    }

    public int getLongueurAlignement() {
        int minDimension = Math.min(plateau.length, plateau[0].length);
        return Math.min(minDimension, 5); // Alignement maximal de 5 pièces
    }

    public void showboard() {
        System.out.print("   ");
        for (char c = 'A'; c < 'A' + plateau[0].length; c++) {
            System.out.printf("%2c", c);
        }
        System.out.println();

        for (int i = plateau.length - 1; i >= 0; i--) {
            System.out.printf("%2d ", i + 1);
            for (int j = 0; j < plateau[i].length; j++) {
                System.out.printf("%2c", plateau[i][j]);
            }
            System.out.printf(" %2d\n", i + 1);
        }

        System.out.print("   ");
        for (char c = 'A'; c < 'A' + plateau[0].length; c++) {
            System.out.printf("%2c", c);
        }
        System.out.println();
    }

    public boolean verifierAlignement(char joueur) {
        int longueurAlignement = getLongueurAlignement();

        // Vérification horizontale
        for (int i = 0; i < plateau.length; i++) {
            for (int j = 0; j <= plateau[i].length - longueurAlignement; j++) {
                if (checkAlignementHorizontale(i, j, joueur, longueurAlignement)) {
                    return true;
                }
            }
        }

        // Vérification verticale
        for (int i = 0; i <= plateau.length - longueurAlignement; i++) {
            for (int j = 0; j < plateau[i].length; j++) {
                if (checkAlignementVerticale(i, j, joueur, longueurAlignement)) {
                    return true;
                }
            }
        }

        // Vérification diagonale descendante
        for (int i = 0; i <= plateau.length - longueurAlignement; i++) {
            for (int j = 0; j <= plateau[i].length - longueurAlignement; j++) {
                if (checkAlignementDiagonaleDescendante(i, j, joueur, longueurAlignement)) {
                    return true;
                }
            }
        }

        // Vérification diagonale montante
        for (int i = longueurAlignement - 1; i < plateau.length; i++) {
            for (int j = 0; j <= plateau[i].length - longueurAlignement; j++) {
                if (checkAlignementDiagonaleMontante(i, j, joueur, longueurAlignement)) {
                    return true;
                }
            }
        }

        return false; // Aucun alignement trouvé
    }

    private boolean checkAlignementHorizontale(int ligne, int colonne, char joueur, int longueur) {
        for (int i = 0; i < longueur; i++) {
            if (plateau[ligne][colonne + i] != joueur) {
                return false;
            }
        }
        return true;
    }

    private boolean checkAlignementVerticale(int ligne, int colonne, char joueur, int longueur) {
        for (int i = 0; i < longueur; i++) {
            if (plateau[ligne + i][colonne] != joueur) {
                return false;
            }
        }
        return true;
    }

    private boolean checkAlignementDiagonaleDescendante(int ligne, int colonne, char joueur, int longueur) {
        for (int i = 0; i < longueur; i++) {
            if (plateau[ligne + i][colonne + i] != joueur) {
                return false;
            }
        }
        return true;
    }

    private boolean checkAlignementDiagonaleMontante(int ligne, int colonne, char joueur, int longueur) {
        for (int i = 0; i < longueur; i++) {
            if (plateau[ligne - i][colonne + i] != joueur) {
                return false;
            }
        }
        return true;
    }

    public boolean estPlein() {
        for (int i = 0; i < plateau.length; i++) {
            for (int j = 0; j < plateau[i].length; j++) {
                if (plateau[i][j] == '.') {
                    return false; // Une case vide trouvée, le plateau n'est pas plein
                }
            }
        }
        return true; // Aucune case vide, le plateau est plein
    }

}
