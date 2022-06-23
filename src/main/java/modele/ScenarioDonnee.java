package modele;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Arrays;

import modele.donnee.Chouette;
import modele.donnee.ContenuNid;
import modele.donnee.EspeceBatracien;
import modele.donnee.EspeceChouette;
import modele.donnee.Lieu;
import modele.donnee.NidGCI;
import modele.donnee.ObsBatracien;
import modele.donnee.ObsChouette;
import modele.donnee.ObsGCI;
import modele.donnee.Observateur;
import modele.donnee.Sexe;
import modele.donnee.TypeObservation;

/**
 * Le scénario de données
 */
public class ScenarioDonnee {
    /**
     * Le point d'entré du programme
     * @param args Les arguments du programme
     */
    public static void main(String[] args) {
        Chouette chouette = new Chouette("id", Sexe.FEMELLE, EspeceChouette.CHEVECHE);
        try {
            System.out.println("CHOUETTE");
            new Chouette("123", Sexe.FEMELLE, EspeceChouette.CHEVECHE);
            new Chouette("123", Sexe.FEMELLE, null);
            System.out.println("\tTest du constructeur: OK");
        } catch (Exception e) {
            System.err.println("\tTest du constructeur: ERREUR");
        }

        ObsChouette obs = new ObsChouette(
                12,
                new Date(1233),
                new Time(1234),
                new Lieu(1.12, 1.1, true),
                new ArrayList<Observateur>(),
                TypeObservation.SONORE, "1");
        ObsChouette obs2 = new ObsChouette(
                13,
                new Date(1233),
                new Time(1234),
                new Lieu(1.12, 1.1, true),
                new ArrayList<Observateur>(),
                TypeObservation.SONORE, "1");
        ObsChouette obs3 = new ObsChouette(
                14,
                new Date(1233),
                new Time(1234),
                new Lieu(1.12, 1.1, true),
                new ArrayList<Observateur>(),
                TypeObservation.SONORE, "1");

        ArrayList<ObsChouette> expected = new ArrayList<ObsChouette>(0);
        expected.add(obs);
        chouette.ajouteUneObs(obs);
        if (Arrays.equals(expected.toArray(), chouette.getLesObservations().toArray())) {
            System.out.println("\tAjout d'observateur: OK");
        } else {
            System.err.println("\tAjout d'observateur: ERREUR");
        }

        expected.add(obs2);
        expected.add(obs3);
        chouette.ajoutePlsObs(expected);
        expected.add(obs);
        if (expected.size() == chouette.getLesObservations().size()) {
            System.out.println("\tAjout de plusieurs observateur: OK");
        } else {
            System.out.println(expected);
            System.out.println(chouette.getLesObservations());
            System.err.println("\tAjout de plusieurs observateur: ERREUR");
        }

        if (chouette.nbObs() == 4) {
            System.out.println("\tNbObs: OK");
        } else {
            System.err.println("\tNbObs: ERREUR");
        }
        chouette.videObs();
        if (chouette.nbObs() == 0) {
            System.out.println("\tVide observateur: OK");
        } else {
            System.err.println("\tVide observateur: ERREUR");
        }

        Date debut = new Date(System.currentTimeMillis());
        Date middle = new Date(System.currentTimeMillis() + System.currentTimeMillis() / 2);
        Date fin = new Date(System.currentTimeMillis() + System.currentTimeMillis());

        System.out.println("BATRACIEN");
        ObsBatracien obsB = new ObsBatracien(12, debut, new Time(123), new Lieu(12.3, 12, false),
                new ArrayList<Observateur>(), new int[] { 1, 2, 3, 4 }, 10, EspeceBatracien.CALAMITE);
        if (obsB.getNombreAdultes() == 1) {
            System.out.println("\tTest nombre adultes: OK");
        } else {
            System.err.println("\tTest nombre adultes: ERREUR");
        }
        obsB.setResObs(new int[] { 2, 5, 6, 8 });
        if (obsB.getNombreAdultes() == 2) {
            System.out.println("\tTest nombre adultes: OK");
        } else {
            System.err.println("\tTest nombre adultes: ERREUR");
        }

        Observateur moi = new Observateur(1, "test", "test", null, null);
        try {
            obsB.ajouteObservateur(moi);
            if (obsB.lesObservateurs().size() == 1) {
                System.out.println("\tTest ajout observateur: OK");
            } else {
                System.err.println("\tTest ajout observateur: ERREUR");
            }
        } catch (Exception e) {
            System.err.println("\tTest ajout observateur: ERREUR");
        }
        try {
            obsB.ajouteObservateur(moi);
            System.err.println("\tTest ajout même observateur: ERREUR");
        } catch (IllegalArgumentException e) {
            System.out.println("\tTest ajout même observateur: OK");
        }
        obsB.retireObservateur(moi.getIdObservateur());
        if (obsB.lesObservateurs().size() == 0) {
            System.out.println("\tTest retirer observateur: OK");
        } else {
            System.err.println("\tTest retirer observateur: ERREUR");
        }
    }
}
