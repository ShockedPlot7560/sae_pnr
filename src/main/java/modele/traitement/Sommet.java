package modele.traitement;

import java.sql.Date;

import modele.donnee.EspeceObservee;
import modele.donnee.Lieu;
import modele.donnee.Observation;

/**
 * Classe représentant une observation très simplifié.
 */
public class Sommet implements Comparable<Sommet> {
    /** idnetifiant du sommet */
    private int id;
    /** les coordonée du sommet */
    private Lieu coordLieu;
    /** la date du sommet */
    private Date date;
    /** lespece du somme */
    private EspeceObservee espece;

    /**
     * Constructeur de la classe Sommet
     * @param id identifiant de l'observation
     * @param coordLieu coordonnées du lieu
     * @param date date de l'observation
     * @param espece espece observee
     */
    public Sommet(int id, Lieu coordLieu, Date date, EspeceObservee espece) {
        this.setId(id);
        this.setCoordLieu(coordLieu);
        this.setDate(date);
        this.setEspece(espece);
    }

    /**
     * Constructeur de la classe Sommet en extrayant les données intéressantes
     * @param obs l'observation
     */
    public Sommet(Observation obs){
        if (obs == null) {
            throw new IllegalArgumentException("Sommet : L'observation ne peut être null");
        }
        this.setId(obs.idObs());
        this.setCoordLieu(obs.lieuObs());
        this.setDate(obs.dateObs());
        this.setEspece(obs.especeObs());
    }

    /**
     * Getter de l'identifiant de l'observation
     * @return l'identifiant de l'observation
     */
    public int getId() {
        return this.id;
    }

    /**
     * Getter du lieu
     * @return le lieu
     */
    public Lieu getCoordLieu() {
        return this.coordLieu;
    }

    /**
     * Getter de la date de l'observation
     * @return la date de l'observation
     */
    public Date getDate() {
        return this.date;
    }

    /**
     * Getter de l'espece observee
     * @return l'espece observee
     */
    public EspeceObservee getEspece() {
        return this.espece;
    }

    /**
     * Définit l'identifiant de l'observation
     * @param id entier supérieur à 0
     */
    public void setId(int id){
        if (id < 0) {
            throw new IllegalArgumentException("setId : L'identifiant doit être supérieur à 0");
        }
        this.id = id;
    }

    /**
     * Définit le lieu
     * @param coordLieu un Lieu non null
     */
    public void setCoordLieu(Lieu coordLieu){
        if (coordLieu == null) {
            throw new IllegalArgumentException("setCoordLieu : Le lieu ne peut être null");
        }
        this.coordLieu = coordLieu;
    }

    /**
     * Définit la date de l'observation
     * @param date une date non null
     */
    public void setDate(Date date){
        if (date == null) {
            throw new IllegalArgumentException("setDate : La date ne peut être null");
        }
        this.date = date;
    }

    /**
     * Définit l'espece observee
     * @param espece une EspeceObservee non null
     */
    public void setEspece(EspeceObservee espece){
        if (espece == null) {
            throw new IllegalArgumentException("setEspece : L'espece ne peut être null");
        }
        this.espece = espece;
    }

    /**
     * Renvoie la distance entre le sommet actuel et le paramètre som en utilisant la localisation de chacun
     * @param som le sommet avec lequel on souhaite calculer la distance, non nul l
     * @return un double représentant la distance entre les deux sommets
     */
    public double calculeDist(Sommet som){
        double dist = 0;

        if (som == null) {
            throw new IllegalArgumentException("calculeDist : Le sommet ne peut être null");
        }
        dist = Math.sqrt(Math.pow(this.coordLieu.getxCoord() - som.getCoordLieu().getxCoord(), 2)
            + Math.pow(this.coordLieu.getyCoord() - som.getCoordLieu().getyCoord(), 2));

        return dist;
    }

    @Override
    public int compareTo(Sommet sommet) {
        if (this.id < sommet.id) {
            return -1;
        }
        else if (this.id > sommet.id) {
            return 1;
        }
        else {
            return 0;
        }
    }
}
