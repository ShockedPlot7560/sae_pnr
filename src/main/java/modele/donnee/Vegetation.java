package modele.donnee;

/**
 * Une végétation
 */
public class Vegetation {
    /** L'identifiant d'une végétation */
    private int idVege;
    /** la nature de la végétation */
    private NatureVege natureVege;
    /** une végétation */
    private String vegetation;

    /**
     * @param idVege l'identifiant d'une végétation
     * @param natureVege la nature de la végétation
     * @param vegetation une végétation
     */
    public Vegetation(int idVege, NatureVege natureVege, String vegetation) {
        this.idVege = idVege;
        this.natureVege = natureVege;
        this.vegetation = vegetation;
    }

    /**
     * @return l'identifiant de la végétation
     */
    public int getId() {
        return idVege;
    }

    /**
     * @return la nature de la végétation
     */
    public NatureVege getNatureVege() {
        return natureVege;
    }

    /**
     * @return une végétation
     */
    public String getVegetation() {
        return vegetation;
    }

    /**
     * @param idVege l'identifiant de la végétation
     */
    public void setIdVege(int idVege) {
        this.idVege = idVege;
    }

    /**
     * @param natureVege la nature de la végétation
     */
    public void setNatureVege(NatureVege natureVege) {
        this.natureVege = natureVege;
    }

    /**
     * @param vegetation une végétation
     */
    public void setVegetation(String vegetation) {
        this.vegetation = vegetation;
    }
}
