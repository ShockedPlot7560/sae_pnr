package modele.donnee;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

public class ObsHippocampeTest {

    private ObsHippocampe obs;

    @Before
    public void initHippocampe() {
        this.obs = new ObsHippocampe(
                12,
                new Date(1233),
                new Time(1234),
                new Lieu(1.12, 1.1, true),
                new ArrayList<Observateur>(),
                2.3,
                15,
                Peche.CASIER_CREVETTES,
                EspeceHippocampe.ENTERURUS_AEQUOREUS,
                Sexe.FEMELLE);
    }

    @Test
    public void testConstructeurNotNull() {
        new ObsHippocampe(
                12,
                new Date(1233),
                new Time(1234),
                new Lieu(1.12, 1.1, true),
                new ArrayList<Observateur>(),
                2.3,
                15,
                Peche.CASIER_CREVETTES,
                EspeceHippocampe.ENTERURUS_AEQUOREUS,
                Sexe.FEMELLE);
        new ObsHippocampe(
                12,
                null,
                null,
                new Lieu(1.12, 1.1, true),
                new ArrayList<Observateur>(),
                2.3,
                15,
                Peche.CASIER_CREVETTES,
                EspeceHippocampe.ENTERURUS_AEQUOREUS,
                Sexe.FEMELLE);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructeurLieuNull() {
        new ObsHippocampe(
                12,
                new Date(1233),
                new Time(1234),
                null,
                new ArrayList<Observateur>(),
                2.3,
                15,
                Peche.CASIER_CREVETTES,
                EspeceHippocampe.ENTERURUS_AEQUOREUS,
                Sexe.FEMELLE);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructeurIndiceNegatif() {
        new ObsHippocampe(
                12,
                new Date(1233),
                new Time(1234),
                new Lieu(1.12, 1.1, true),
                new ArrayList<Observateur>(),
                -23.3,
                15,
                Peche.CASIER_CREVETTES,
                EspeceHippocampe.ENTERURUS_AEQUOREUS,
                Sexe.FEMELLE);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructeurPecheNull() {
        new ObsHippocampe(
                12,
                new Date(1233),
                new Time(1234),
                new Lieu(1.12, 1.1, true),
                new ArrayList<Observateur>(),
                12.3,
                15,
                null,
                EspeceHippocampe.ENTERURUS_AEQUOREUS,
                Sexe.FEMELLE);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructeurSexeNull() {
        new ObsHippocampe(
                12,
                new Date(1233),
                new Time(1234),
                new Lieu(1.12, 1.1, true),
                new ArrayList<Observateur>(),
                12.3,
                15,
                Peche.CASIER_CREVETTES,
                EspeceHippocampe.ENTERURUS_AEQUOREUS,
                null);
    }

    @Test
    public void testEspeceObs() {
        assert (this.obs.especeObs() == EspeceObservee.HIPPOCAMPE);
    }

    @Test
    public void testSetEstGestantNormalCaser() {
        assert (!this.obs.estGestant());
        this.obs.setSexe(Sexe.MALE);
        this.obs.setEstGestant(true);
        assert (this.obs.estGestant());
        this.obs.setEstGestant(false);
        assert (!this.obs.estGestant());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetEstGestantFemale() {
        this.obs.setSexe(Sexe.FEMELLE);
        this.obs.setEstGestant(true);
    }
}
