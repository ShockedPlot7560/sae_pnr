package modele.donnee;

import static org.junit.Assert.assertEquals;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

public class ObservationTest {

    private Observateur observateur1;
    private Observateur observateur2;

    private TestObservation observation;

    @Before
    public void setUp() {
        this.observateur1 = new Observateur(1, "nom1", "prenom1", null, null);
        this.observateur2 = new Observateur(2, "nom2", "prenom2", null, null);
        ArrayList<Observateur> observateurs = new ArrayList<Observateur>();
        observateurs.add(this.observateur1);
        this.observation = new TestObservation(12, new Date(0), new Time(0), new Lieu(0.0, 0.0, true),
                observateurs);
    }

    @Test
    public void testConstructeurNotNull() {
        new TestObservation(12, new Date(0), new Time(0), new Lieu(0.0, 0.0, true), new ArrayList<Observateur>());
        new TestObservation(12, null, null, new Lieu(0.0, 0.0, true), new ArrayList<Observateur>());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructeurIdNegatif() {
        new TestObservation(-12, new Date(0), new Time(0), new Lieu(0.0, 0.0, true), new ArrayList<Observateur>());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructeurLieuNull() {
        new TestObservation(6, new Date(0), new Time(0), null, new ArrayList<Observateur>());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructeurObservateursNull() {
        new TestObservation(6, new Date(0), new Time(0), new Lieu(0.0, 0.0, true), null);
    }

    @Test
    public void testAjouteObservateurNormal() {
        this.observation.ajouteObservateur(this.observateur2);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAjouteObservateurNull() {
        this.observation.ajouteObservateur(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAjouteObservateurDejaPresent() {
        this.observation.ajouteObservateur(this.observateur1);
    }

    @Test
    public void testSupprimeObservateurNormal() {
        this.observation.retireObservateur(this.observateur1.getIdObservateur());
        assertEquals(0, this.observation.lesObservateurs().size());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSupprimeObservateurExistePas() {
        this.observation.retireObservateur(56);
    }
}
