package plateau;

import jouer.Jouer;
import org.junit.Test;

import static jouer.Jouer.play;
import static org.junit.Assert.assertTrue;

public class TestPlateau {

    @Test
    public void testGagnerAlignementHorizontal() {
        Plateau plateau = new Plateau(7,7);

        play("play black A1", plateau);
        play("play white A2", plateau);
        play("play black B1", plateau);
        play("play white B2", plateau);
        play("play black C1", plateau);
        play("play white C2", plateau);
        play("play black D1", plateau);
        play("play white D2", plateau);
        play("play black E1", plateau);

        assertTrue(plateau.verifierAlignement('X'));

        plateau.afficherPlateau();
    }
}
