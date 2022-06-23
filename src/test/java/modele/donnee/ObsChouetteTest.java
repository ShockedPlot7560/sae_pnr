package modele.donnee;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;

import org.junit.Test;

public class ObsChouetteTest {
    @Test
    public void testConstructeurNotNull() {
        new ObsChouette(
                12,
                new Date(1233),
                new Time(1234),
                new Lieu(1.12, 1.1, true),
                new ArrayList<Observateur>(),
                TypeObservation.SONORE, "123");
        new ObsChouette(
                12,
                null,
                null,
                new Lieu(1.12, 1.1, true),
                new ArrayList<Observateur>(),
                TypeObservation.SONORE, "123");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructeurLieuNull() {
        new ObsChouette(123, null, null, null, new ArrayList<Observateur>(), TypeObservation.SONORE, "45");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructeurTypeNull() {
        new ObsChouette(123, null, null, new Lieu(1.12, 1.1, true), new ArrayList<Observateur>(), null, "45");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructeurObsNull() {
        new ObsChouette(123, null, null, new Lieu(1.12, 1.1, true), null, TypeObservation.SONORE, "45");
    }

    @Test
    public void testEspeceObs() {
        ObsChouette obs = new ObsChouette(123, null, null, new Lieu(1.12, 1.1, true), new ArrayList<Observateur>(),
                TypeObservation.SONORE, "2");
        assert (EspeceObservee.CHOUETTE == obs.especeObs());
    }
}
