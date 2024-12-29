package joueur;

import plateau.Plateau;

public class BotMinimax {

    private Plateau plateau;
    private char joueur; // Le symbole du joueur du bot (O ou X)
    private char adversaire; // Le symbole de l'adversaire
    private int profondeur; // Profondeur de recherche

    public BotMinimax(Plateau plateau, char joueur, char adversaire, int profondeur) {
        this.plateau = plateau;
        this.joueur = joueur;
        this.adversaire = adversaire;
        this.profondeur = profondeur;
    }
    public String jouer(Plateau plateau, char symbole) {
        int[] coup = choisirCoup(); // Obtenir le meilleur coup
        int ligne = coup[0];
        int colonne = coup[1];

        // Convertir les coordonnées en format de notation classique comme "A1", "B2", etc.
        String position = (char) ('A' + colonne) + Integer.toString(ligne + 1);
        plateau.setCase(ligne, colonne, symbole); // Jouer le coup sur le plateau
        return position; // Retourner la position du coup joué
    }
    // Méthode principale pour choisir le meilleur coup
    public int[] choisirCoup() {
        int[] meilleurCoup = minimax(profondeur, true, Integer.MIN_VALUE, Integer.MAX_VALUE);
        return new int[]{meilleurCoup[1], meilleurCoup[2]}; // Coordonnées du meilleur coup
    }

    /**
     * Algorithme Minimax avec élagage alpha-bêta.
     * @param profondeur Profondeur de recherche actuelle.
     * @param estMaximisant Vrai si c'est le tour du bot.
     * @param alpha Meilleure valeur pour le joueur maximisant.
     * @param beta Meilleure valeur pour le joueur minimisant.
     * @return Tableau contenant le score et les coordonnées du coup {score, ligne, colonne}.
     */
    private int[] minimax(int profondeur, boolean estMaximisant, int alpha, int beta) {
        if (profondeur == 0 || plateau.verifierAlignement(joueur) || plateau.verifierAlignement(adversaire)) {
            return new int[]{scorePlateau(), -1, -1}; // Score final avec aucune position
        }

        int meilleurScore = estMaximisant ? Integer.MIN_VALUE : Integer.MAX_VALUE;
        int[] meilleurCoup = {-1, -1, -1}; // Initialement aucun coup

        for (int i = 0; i < plateau.getNbLignes(); i++) {
            for (int j = 0; j < plateau.getNbColonnes(); j++) {
                if (plateau.getCase(i, j) == Plateau.CASE_VIDE) { // Case libre
                    plateau.setCase(i, j, estMaximisant ? joueur : adversaire); // Simuler le coup
                    int score = minimax(profondeur - 1, !estMaximisant, alpha, beta)[0];
                    plateau.setCase(i, j, Plateau.CASE_VIDE); // Annuler le coup

                    if (estMaximisant) {
                        if (score > meilleurScore) {
                            meilleurScore = score;
                            meilleurCoup = new int[]{score, i, j};
                        }
                        alpha = Math.max(alpha, score);
                    } else {
                        if (score < meilleurScore) {
                            meilleurScore = score;
                            meilleurCoup = new int[]{score, i, j};
                        }
                        beta = Math.min(beta, score);
                    }

                    if (beta <= alpha) {
                        break; // Élagage
                    }
                }
            }
        }
        return meilleurCoup;
    }

    /**
     * Évalue le plateau pour le joueur actuel.
     * @return Une valeur numérique représentant l'avantage pour le joueur.
     */
    private int scorePlateau() {
        if (plateau.verifierAlignement(joueur)) {
            return 1000; // Grande valeur pour une victoire
        } else if (plateau.verifierAlignement(adversaire)) {
            return -1000; // Grande valeur négative pour une défaite
        }

        return evaluerAlignements(joueur) - evaluerAlignements(adversaire);
    }

    /**
     * Évalue les alignements potentiels pour un joueur donné.
     * @param joueur Le symbole du joueur à évaluer.
     * @return Le score basé sur les alignements ouverts.
     */
    private int evaluerAlignements(char joueur) {
        int score = 0;

        for (int i = 0; i < plateau.getNbLignes(); i++) {
            for (int j = 0; j < plateau.getNbColonnes(); j++) {
                if (plateau.getCase(i, j) == joueur) {
                    score += compterAlignements(i, j, joueur);
                }
            }
        }
        return score;
    }

    /**
     * Compte les alignements ouverts à partir d'une position donnée.
     * @param ligne Ligne de départ.
     * @param colonne Colonne de départ.
     * @param joueur Le symbole du joueur.
     * @return Un score basé sur les alignements possibles.
     */
    private int compterAlignements(int ligne, int colonne, char joueur) {
        int score = 0;

        // Directions : horizontale, verticale, diagonale descendante, diagonale montante
        int[][] directions = {{0, 1}, {1, 0}, {1, 1}, {-1, 1}};
        for (int[] dir : directions) {
            int count = 1; // Inclut la position de départ
            int espaceLibreAvant = 0, espaceLibreApres = 0;

            // Analyser dans les deux sens (avant et arrière)
            count += analyserDirection(ligne, colonne, dir, joueur, true);  // Avancer
            count += analyserDirection(ligne, colonne, dir, joueur, false); // Reculer

            // Si des espaces libres sont trouvés dans les deux sens, on peut calculer le score
            if (espaceLibreAvant > 0 || espaceLibreApres > 0) {
                score += Math.pow(10, count - 1); // Score exponentiel selon l'alignement
            }
        }
        return score;
    }

    // Méthode pour analyser une direction (avancer ou reculer)
    private int analyserDirection(int ligne, int colonne, int[] dir, char joueur, boolean avancer) {
        int count = 1; // Inclut la position de départ
        int espaceLibre = 0;

        // On détermine la direction : avancer (positif) ou reculer (négatif)
        int sens = avancer ? 1 : -1;

        for (int k = 1; k < plateau.getLongueurAlignement(); k++) {
            int x = ligne + k * dir[0] * sens;
            int y = colonne + k * dir[1] * sens;
            if (plateau.estPositionValide(x, y)) {
                if (plateau.getCase(x, y) == joueur) {
                    count++;
                } else if (plateau.getCase(x, y) == Plateau.CASE_VIDE) {
                    espaceLibre++;
                    break;
                } else {
                    break;
                }
            }
        }

        return count;
    }
}