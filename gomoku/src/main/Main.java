package main;

import plateau.Plateau;
import jouer.Jouer;

public class Main {
    public static void main(String[] args) {
        Plateau plateau = new Plateau();
        plateau.afficherPlateau();


        Jouer.play("play black C3", plateau);
        plateau.afficherPlateau();


        Jouer.play("play white D4", plateau);
        plateau.afficherPlateau();


        Jouer.play("play black C3", plateau);
        plateau.afficherPlateau();


        Jouer.play("play black A1", plateau);
        plateau.afficherPlateau();
        
        plateau.clearPlateau();

        Jouer.play("play black A9", plateau);
        plateau.afficherPlateau();

        Jouer.play("play red A5", plateau);
        plateau.afficherPlateau();

        Jouer.play("play black CC7", plateau);
        plateau.afficherPlateau();

        plateau.quit();

    }
}
