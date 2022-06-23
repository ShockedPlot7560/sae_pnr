package modele.traitement;

/**
 * Arrete
 */
public class Arrete implements Comparable<Arrete> {
    /** l'arrete 1 */
    private Sommet sommet1;
    /** l'arrete 2 */
    private Sommet sommet2;

    /**
     * @param sommet1 l'arrete 1
     * @param sommet2 l'arrete 2
     */
    public Arrete(Sommet sommet1, Sommet sommet2) {
        this.setSommet1(sommet1);
        this.setSommet2(sommet2);
    }

    /**
     * @return le sommet1
     */
    public Sommet getSommet1() {
        return sommet1;
    }

    /**
     * @return le sommet 2
     */
    public Sommet getSommet2() {
        return sommet2;
    }

    /**
     * @param sommet1 le sommet1
     */
    public void setSommet1(Sommet sommet1) {
        if (sommet1 == null) {
            throw new IllegalArgumentException("setSommet : Le sommet ne peut pas être null");
        }
        this.sommet1 = sommet1;
    }

    /**
     * @param sommet2 le sommet2
     */
    public void setSommet2(Sommet sommet2) {
        if (sommet1 == null) {
            throw new IllegalArgumentException("setSommet : Le sommet ne peut pas être null");
        }
        this.sommet2 = sommet2;
    }

    @Override
    public int compareTo(Arrete arrete) {
        if ((this.sommet1.compareTo(arrete.sommet1) == 0 && this.sommet2.compareTo(arrete.sommet2) == 0)
                || (this.sommet1.compareTo(arrete.sommet2) == 0 && this.sommet2.compareTo(arrete.sommet1) == 0)) {
            return 0;
        }
        return -1;
    }
    
}