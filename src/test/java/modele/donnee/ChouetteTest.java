package modele.donnee;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

public class ChouetteTest {

    private Chouette chouette;

    @Before
    public void initChouette() {
        this.chouette = new Chouette("id", Sexe.FEMELLE, EspeceChouette.CHEVECHE);
    }

    @Test
    public void testConstructeurNotNull() {
        new Chouette("123", Sexe.FEMELLE, EspeceChouette.CHEVECHE);
        new Chouette("123", Sexe.FEMELLE, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructeurIdNull() {
        new Chouette(null, Sexe.FEMELLE, EspeceChouette.CHEVECHE);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructeurSexeNull() {
        new Chouette("123", null, EspeceChouette.CHEVECHE);
    }

    public void testObsList() {
        ObsChouette obs = new ObsChouette(
                12,
                new Date(1233),
                new Time(1234),
                new Lieu(1.12, 1.1, true),
                new ArrayList<Observateur>(),
                TypeObservation.SONORE,
                "13");

        ArrayList<ObsChouette> expected = new ArrayList<ObsChouette>(0);
        expected.add(obs);
        this.chouette.ajouteUneObs(obs);
        assertArrayEquals(expected.toArray(), this.chouette.getLesObservations().toArray());

        expected.add(obs);
        this.chouette.ajoutePlsObs(expected);
        expected.addAll(expected);
        assertArrayEquals(expected.toArray(), this.chouette.getLesObservations().toArray());

        assertEquals(4, this.chouette.nbObs());
        this.chouette.videObs();
        assertEquals(0, this.chouette.nbObs());
    }
}
