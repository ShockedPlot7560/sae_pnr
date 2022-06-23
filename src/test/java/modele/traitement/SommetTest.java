package modele.traitement;

import static org.junit.Assert.assertEquals;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map.Entry;

import org.junit.Test;

import modele.donnee.EspeceObservee;
import modele.donnee.Lieu;
import modele.donnee.Observateur;
import modele.donnee.Observation;
import modele.donnee.TestObservation;

public class SommetTest {
    @Test
    public void testConstructeurNormalCase(){
        Observation obs = new TestObservation(10, new Date(10), new Time(30), new Lieu(10.3, 10.2, true), new ArrayList<Observateur>());
        Sommet sommet = new Sommet(obs.idObs(), obs.lieuObs(), obs.dateObs(), obs.especeObs());
        assertEquals(sommet.getId(), 10);
        assert(sommet.getCoordLieu().getxCoord() == 10.3);
        assert(sommet.getCoordLieu().getyCoord() == 10.2);
        assert(sommet.getCoordLieu().estLambert());
        assert(sommet.getDate().getTime() == 10);
        assert(sommet.getEspece().equals(EspeceObservee.BATRACIEN));

        Sommet sommet2 = new Sommet(obs);
        assertEquals(sommet2.getId(), 10);
        assert(sommet2.getCoordLieu().getxCoord() == 10.3);
        assert(sommet2.getCoordLieu().getyCoord() == 10.2);
        assert(sommet2.getCoordLieu().estLambert());
        assert(sommet2.getDate().getTime() == 10);
        assert(sommet2.getEspece().equals(EspeceObservee.BATRACIEN));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructeurIdNegative(){
        new Sommet(-10, new Lieu(10.3, 10.2, true), new Date(10), EspeceObservee.BATRACIEN);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructeurLieuNull(){
        new Sommet(10, null, new Date(10), EspeceObservee.BATRACIEN);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructeurDateNull(){
        new Sommet(10, new Lieu(10.3, 10.2, true), null, EspeceObservee.BATRACIEN);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructeurEspeceNull(){
        new Sommet(10, new Lieu(10.3, 10.2, true), new Date(10), null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructeurObsNull(){
        new Sommet(null);
    }

    @Test
    public void testCalculeDist(){
        HashMap<Double, ArrayList<Lieu>> hashMap = new HashMap<Double, ArrayList<Lieu>>();
        hashMap.put(1D, new ArrayList<Lieu>(Arrays.asList(new Lieu(0, 0, true), new Lieu(1, 0, true))));
        hashMap.put(1D, new ArrayList<Lieu>(Arrays.asList(new Lieu(0, 0, true), new Lieu(0,1, true))));
        hashMap.put(1D, new ArrayList<Lieu>(Arrays.asList(new Lieu(0, 0, true), new Lieu(-1,0, true))));
        hashMap.put(1D, new ArrayList<Lieu>(Arrays.asList(new Lieu(0, 0, true), new Lieu(0,-1, true))));

        hashMap.put(Math.sqrt(2), new ArrayList<Lieu>(Arrays.asList(new Lieu(0, 0, true), new Lieu(1,1, true))));
        hashMap.put(Math.sqrt(2), new ArrayList<Lieu>(Arrays.asList(new Lieu(0, 0, true), new Lieu(-1,1, true))));
        hashMap.put(Math.sqrt(2), new ArrayList<Lieu>(Arrays.asList(new Lieu(0, 0, true), new Lieu(1,-1, true))));
        hashMap.put(Math.sqrt(2), new ArrayList<Lieu>(Arrays.asList(new Lieu(0, 0, true), new Lieu(-1,-1, true))));

        hashMap.put(Math.sqrt(1 + 2*2), new ArrayList<Lieu>(Arrays.asList(new Lieu(0, 0, true), new Lieu(1,2, true))));
        hashMap.put(Math.sqrt(1 + 2*2), new ArrayList<Lieu>(Arrays.asList(new Lieu(0, 0, true), new Lieu(-1,2, true))));
        hashMap.put(Math.sqrt(1 + 2*2), new ArrayList<Lieu>(Arrays.asList(new Lieu(0, 0, true), new Lieu(1,-2, true))));
        hashMap.put(Math.sqrt(1 + 2*2), new ArrayList<Lieu>(Arrays.asList(new Lieu(0, 0, true), new Lieu(-1,-2, true))));

        for(Entry<Double, ArrayList<Lieu>> entry: hashMap.entrySet()) {
            Sommet som1 = new Sommet(10, entry.getValue().get(0), new Date(10), EspeceObservee.BATRACIEN);
            Sommet som2 = new Sommet(11, entry.getValue().get(1), new Date(10), EspeceObservee.BATRACIEN);
            assertEquals(som1.calculeDist(som2), (double) entry.getKey(), 0.0001);
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCalculeDistSommetNull(){
        Sommet som1 = new Sommet(10, new Lieu(0, 0, true), new Date(10), EspeceObservee.BATRACIEN);
        som1.calculeDist(null);
    }
}
