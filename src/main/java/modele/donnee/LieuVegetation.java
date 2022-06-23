package modele.donnee;

import java.util.ArrayList;

/**
 * Un Lieu avec des végétations
 */
public class LieuVegetation {
    /** l'identifiant du nid */
    private int id;
    /** la liste des végétations présentes */
    private ArrayList<Vegetation> listeVegetation;

    /**
     * @param id       l'identifiant du nid
     * @param listeVegetation la liste des végétations présentes
     */
    public LieuVegetation(int id, ArrayList<Vegetation> listeVegetation) {
        this.id = id;
        this.listeVegetation = listeVegetation;
    }

    /**
     * @param id      l'identifiant du nid
     */
    public LieuVegetation(int id) {
        this.id = id;
        this.listeVegetation = new ArrayList<Vegetation>(0);
    }

    /**
     * @return l'identifiant du nid
     */
    public int getId() {
        return id;
    }

    /**
     * @return la liste des végétations présentes
     */
    public ArrayList<Vegetation> getListeVegetation() {
        return listeVegetation;
    }

    /**
     * @param id      l'identifiant du nid
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @param listeVegetation la liste des végétations présentes
     */
    public void setListeVegetation(ArrayList<Vegetation> listeVegetation) {
        this.listeVegetation = listeVegetation;
    }
}
