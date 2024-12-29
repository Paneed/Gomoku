package plateau;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TestPlateau {

    @Test
    void testInitialisationPlateau() {
        Plateau plateau = new Plateau();
        plateau.boardsize(10);

        assertEquals(10, plateau.getNbLignes());
        assertEquals(10, plateau.getNbColonnes());
        assertEquals('.', plateau.getCase(0, 0));
    }

    @Test
    void testSetCaseEtGetCase() {
        Plateau plateau = new Plateau();
        plateau.boardsize(10);

        plateau.setCase(0, 0, 'X');
        assertEquals('X', plateau.getCase(0, 0));
    }

    @Test
    void testVerifierAlignement() {
        Plateau plateau = new Plateau();
        plateau.boardsize(5);

        plateau.setCase(0, 0, 'X');
        plateau.setCase(0, 1, 'X');
        plateau.setCase(0, 2, 'X');
        plateau.setCase(0, 3, 'X');
        plateau.setCase(0, 4, 'X');

        assertTrue(plateau.verifierAlignement('X'));
        assertFalse(plateau.verifierAlignement('O'));
    }

    @Test
    void testEstPlein() {
        Plateau plateau = new Plateau();
        plateau.boardsize(3);

        assertFalse(plateau.estPlein());
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                plateau.setCase(i, j, 'X');
            }
        }
        assertTrue(plateau.estPlein());
    }
}

