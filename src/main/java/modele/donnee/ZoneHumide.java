package modele.donnee;

/** Une zone humide */
public class ZoneHumide {
    /** l'id de la zone humide */
    private int id;
    /** une zone temporaire  */
    private boolean temp;
    /** la profondeur */
    private double profondeur;
    /** la surface */
    private double surface;
    /** le type de mare */
    private TypeMare typeMare;
    /** le type de pente */
    private Pente pente;
    /** le type d'ouverture */
    private Ouverture ouverture;

    /**
     * @param id l'id de la zone humide
     * @param temp une zone temporaire
     * @param profondeur la profondeur
     * @param surface la surface
     * @param typeMare le type de mare
     * @param pente le type de pente
     * @param ouverture le type d'ouverture
    */
    public ZoneHumide(int id, boolean temp, double profondeur, double surface, TypeMare typeMare, Pente pente,
            Ouverture ouverture) {
        this.id = id;
        this.temp = temp;
        this.profondeur = profondeur;
        this.surface = surface;
        this.typeMare = typeMare;
        this.pente = pente;
        this.ouverture = ouverture;
    }

    /**
     * @return l'id de la zone humide
     */
    public int getId() {
        return id;
    }

    /**
     * @return une zone temporaire
     */
    public boolean getTemp() {
        return temp;
    }

    /**
     * @return la profondeur
     */
    public double getProfondeur() {
        return profondeur;
    }

    /**
     * @return la surface
     */
    public double getSurface() {
        return surface;
    }

    /**
     * @return le type de mare
     */
    public TypeMare getTypeMare() {
        return typeMare;
    }

    /**
     * @return le type de pente
     */
    public Pente getPente() {
        return pente;
    }

    /**
     * @return le type d'ouverture
     */
    public Ouverture getOuverture() {
        return ouverture;
    }

    /**
     * @param id l'id de la zone humide
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @param temp une zone temporaire
     */
    public void setTemp(boolean temp) {
        this.temp = temp;
    }

    /**
     * @param profondeur la profondeur
     */
    public void setProfondeur(double profondeur) {
        this.profondeur = profondeur;
    }

    /**
     * @param surface la surface
     */
    public void setSurface(double surface) {
        this.surface = surface;
    }

    /**
     * @param typeMare le type de mare
     */
    public void setTypeMare(TypeMare typeMare) {
        this.typeMare = typeMare;
    }

    /**
     * @param pente le type de pente
     */
    public void setPente(Pente pente) {
        this.pente = pente;
    }

    /**
     * @param ouverture le type d'ouverture
     */
    public void setOuverture(Ouverture ouverture) {
        this.ouverture = ouverture;
    }
}
