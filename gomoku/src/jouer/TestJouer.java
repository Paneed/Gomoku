package jouer;

import joueur.JoueurBlack;
import joueur.JoueurWhite;
import org.junit.Test;
import plateau.Plateau;

import static org.junit.Assert.assertEquals;

public class TestJouer {
    @Test

    public void test() {
       Plateau plateau = new Plateau();
       plateau.setCase(1,1,'O');
        assertEquals(plateau.getCase(1,1), 'O');

    }
}
