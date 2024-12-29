package plateau;

import org.junit.Test;

import static org.junit.Assert.*;

public class TestPlateau {
    @Test
    public void testBoardSize() {
        Plateau plateau = new Plateau();
        plateau.boardsize(7);
        assertEquals(7, plateau.getNbLignes());
        assertEquals(7, plateau.getNbColonnes());
    }

    @Test
    public void testInvalidBoardSize() {
        Plateau plateau = new Plateau();
        assertThrows(IllegalArgumentException.class, () -> {
            plateau.boardsize(-1);
        });
    }

    @Test
    public void testClearPlateau() {
        Plateau plateau = new Plateau();
        plateau.boardsize(5);
        plateau.setCase(2, 2, 'X');
        plateau.clearPlateau();
        assertEquals('.', plateau.getCase(2, 2));
    }

    @Test
    public void testSetAndGetCase() {
        Plateau plateau = new Plateau();
        plateau.boardsize(5);
        plateau.setCase(1, 1, 'X');
        assertEquals('X', plateau.getCase(1, 1));
    }

    @Test
    public void testSetCaseOutOfBounds() {
        Plateau plateau = new Plateau();
        plateau.boardsize(5);
        assertThrows(IllegalArgumentException.class, () -> {
            plateau.setCase(10, 10, 'X'); // Position hors limites
        });

    }

    @Test
    public void testEstPlein() {
        Plateau plateau = new Plateau();
        plateau.boardsize(3);
        for (int i = 0; i < plateau.getNbLignes(); i++) {
            for (int j = 0; j < plateau.getNbColonnes(); j++) {
                plateau.setCase(i, j, 'X');
            }
        }
        assertTrue(plateau.estPlein());
    }

    @Test
    public void testVerifierConditionDeVictoire() {//Vérifie si les cases sont bien aligner
        Plateau plateau = new Plateau();
        plateau.boardsize(5);
        plateau.setCase(0, 0, 'X');
        plateau.setCase(0, 1, 'X');
        plateau.setCase(0, 2, 'X');
        plateau.setCase(0, 3, 'X');
        plateau.setCase(0, 4, 'X');
        assertTrue(plateau.verifierAlignement('X'));
    }

}
