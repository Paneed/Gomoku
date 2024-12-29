package jouer;

import joueur.JoueurBlack;
import joueur.JoueurWhite;
import org.junit.Test;
import plateau.Plateau;

import static org.junit.Assert.assertEquals;

public class TestJouer {
    @Test
    public void testSetAndGetCase() {
        Plateau plateau = new Plateau();
        plateau.boardsize(7); // Initialiser un plateau 7x7
        plateau.setCase(1, 1, 'O');
        assertEquals('O', plateau.getCase(1, 1));
    }
    @Test
    public void testPlayValidMove() {
        Plateau plateau = new Plateau();
        plateau.boardsize(7);
        JoueurBlack joueur = new JoueurBlack("B");
        boolean result = Jouer.placerPiece("C3", joueur.getSymbole(), plateau);
        assertEquals(true, result);
        assertEquals('X', plateau.getCase(2, 2)); // Vérifie que la pièce est placée
    }
    @Test
    public void testPlayInvalidMoveOutOfBounds() {
        Plateau plateau = new Plateau();
        plateau.boardsize(7);
        JoueurBlack joueur = new JoueurBlack("B");
        boolean result = Jouer.placerPiece("Z9", joueur.getSymbole(), plateau);
        assertEquals(false, result); // Mouvement invalide
    }

    @Test
    public void testPlayOccupiedPosition() {
        Plateau plateau = new Plateau();
        plateau.boardsize(7);
        plateau.setCase(2, 2, 'X'); // Case déjà occupée
        JoueurWhite joueur = new JoueurWhite("W");
        boolean result = Jouer.placerPiece("C3", joueur.getSymbole(), plateau);
        assertEquals(false, result); // Le coup doit être rejeté
    }

    @Test
    public void testInvalidColor() {
        Plateau plateau = new Plateau();
        plateau.boardsize(7);
        JoueurWhite joueur = new JoueurWhite("W");
        boolean result = Jouer.play("play red A1", joueur, plateau);
        assertEquals(false, result); // Couleur invalide
    }
}
