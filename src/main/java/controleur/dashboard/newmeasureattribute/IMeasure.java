package controleur.dashboard.newmeasureattribute;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;

import controleur.dashboard.InsertFailedException;
import modele.donnee.Lieu;
import modele.donnee.Observateur;

public interface IMeasure {
    public void addMesure(Date date, Time heure, Lieu lieu, ArrayList<Observateur> observateurs)
            throws InsertFailedException;
}
