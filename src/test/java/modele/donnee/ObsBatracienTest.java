package modele.donnee;

import static org.junit.Assert.assertEquals;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

public class ObsBatracienTest {

    private ObsBatracien obs;

    @Before
    public void initBatracien() {
        this.obs = new ObsBatracien(
                12,
                new Date(1233),
                new Time(1234),
                new Lieu(1.12, 1.1, true),
                new ArrayList<Observateur>(),
                new int[] { 1, 2, 3, 4 },
                10,
                EspeceBatracien.CALAMITE);
    }

    @Test
    public void testConstructeurNotNull() {
        new ObsBatracien(
                12,
                new Date(1233),
                new Time(1234),
                new Lieu(1.12, 1.1, true),
                new ArrayList<Observateur>(),
                new int[] { 1, 2, 3, 4 },
                10,
                EspeceBatracien.CALAMITE);
        new ObsBatracien(
                12,
                null,
                null,
                new Lieu(1.12, 1.1, true),
                new ArrayList<Observateur>(),
                new int[] { 1, 2, 3, 4 },
                10,
                EspeceBatracien.CALAMITE);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructeurLieuNull() {
        new ObsBatracien(
                12,
                new Date(1233),
                new Time(1234),
                null,
                new ArrayList<Observateur>(),
                new int[] { 1, 2, 3, 4 },
                10,
                EspeceBatracien.CALAMITE);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructeurIndiceNull() {
        new ObsBatracien(
                12,
                new Date(1233),
                new Time(1234),
                new Lieu(1.12, 1.1, true),
                new ArrayList<Observateur>(),
                null,
                10,
                EspeceBatracien.CALAMITE);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructeurIndiceTooLong() {
        new ObsBatracien(
                12,
                new Date(1233),
                new Time(1234),
                new Lieu(1.12, 1.1, true),
                new ArrayList<Observateur>(),
                new int[] { 1, 2, 3, 4, 5, 6 },
                10,
                EspeceBatracien.CALAMITE);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructeurIndiceTooShort() {
        new ObsBatracien(
                12,
                new Date(1233),
                new Time(1234),
                new Lieu(1.12, 1.1, true),
                new ArrayList<Observateur>(),
                new int[] { 1 },
                10,
                EspeceBatracien.CALAMITE);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructeurEspeceNull() {
        new ObsBatracien(
                12,
                new Date(1233),
                new Time(1234),
                new Lieu(1.12, 1.1, true),
                new ArrayList<Observateur>(),
                new int[] { 1, 2, 3, 4 },
                10,
                null);
    }

    @Test
    public void testEspeceObs() {
        assert (this.obs.especeObs() == EspeceObservee.BATRACIEN);
    }

    @Test
    public void testSetResObs() {
        assertEquals(1, this.obs.getNombreAdultes());
        assertEquals(2, this.obs.getNombreAmplexus());
        assertEquals(3, this.obs.getNombreTetard());
        assertEquals(4, this.obs.getNombrePonte());
    }

}
