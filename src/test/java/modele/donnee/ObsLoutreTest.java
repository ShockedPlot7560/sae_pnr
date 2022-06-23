package modele.donnee;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

public class ObsLoutreTest {

    private ObsLoutre obs;

    @Before
    public void initLoutre() {
        this.obs = new ObsLoutre(
                12,
                new Date(1233),
                new Time(1234),
                new Lieu(1.12, 1.1, true),
                new ArrayList<Observateur>(),
                IndiceLoutre.NEGATIF);
    }

    @Test
    public void testConstructeurNotNull() {
        new ObsLoutre(
                12,
                new Date(1233),
                new Time(1234),
                new Lieu(1.12, 1.1, true),
                new ArrayList<Observateur>(),
                IndiceLoutre.NEGATIF);
        new ObsLoutre(
                12,
                null,
                null,
                new Lieu(1.12, 1.1, true),
                new ArrayList<Observateur>(),
                IndiceLoutre.POSITIF);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructeurLieuNull() {
        new ObsLoutre(
                12,
                new Date(1233),
                new Time(1234),
                null,
                new ArrayList<Observateur>(),
                IndiceLoutre.NEGATIF);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructeurIndiceNull() {
        new ObsLoutre(
                12,
                new Date(1233),
                new Time(1234),
                new Lieu(1.12, 1.1, true),
                new ArrayList<Observateur>(),
                null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructeurObservateurNull() {
        new ObsLoutre(
                12,
                new Date(1233),
                new Time(1234),
                new Lieu(1.12, 1.1, true),
                null,
                IndiceLoutre.NEGATIF);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructeurIdNegatif() {
        new ObsLoutre(-12, new Date(1233), new Time(1234), new Lieu(1.12, 1.1, true), new ArrayList<Observateur>(),
                IndiceLoutre.NEGATIF);
    }

    @Test
    public void testEspeceObs() {
        assert (this.obs.especeObs() == EspeceObservee.LOUTRE);
    }
}
