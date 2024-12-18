package main;

import plateau.Plateau;
import jouer.Jouer;

public class Main {
    public static void main(String[] args) {
        Plateau plateau = new Plateau();

        Jouer.play("play black C3", plateau);


        Jouer.play("play white D4", plateau);
        plateau.afficherPlateau();
        
        Jouer.play("play white C3", plateau);

        Jouer.play("play black A1", plateau);
        plateau.afficherPlateau();

        plateau.clearPlateau();
        System.out.println();
        plateau.afficherPlateau();
        
    }
}
