package modele.donnee;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;

/**
 * Classe servant de base pour tester les méthodes d'Observation
 * Elle a pour but unique de tester, à ne pas implémenter
 */
public class TestObservation extends Observation {
    public TestObservation(int id, Date date, Time heure, Lieu lieu, ArrayList<Observateur> observateurs) {
        super(id, date, heure, lieu, observateurs);
    }

    public EspeceObservee especeObs() {
        return EspeceObservee.BATRACIEN;
    }
}
