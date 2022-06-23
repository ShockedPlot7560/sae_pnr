package modele.donnee;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class ObservateurTest {
    @Test
    public void testConstructeurNormalCase() {
        new Observateur(12, "leNom", "lePrenom", null, null);
        new Observateur(12, null, "lePrenom", null, null);
    }

    public void testSingleConstructeur() {
        Observateur obs = new Observateur(12);
        assert(obs.equals(new Observateur(12, "", "", null, null)));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructeurPrenomNull() {
        new Observateur(12, "leNom", null, null, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructeurIdInferiorZero() {
        new Observateur(-6, "leNom", "lePrenom", null, null);
    }
}
