package modele.traitement;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

/** 
 * Une classe utilisataire pour manipuler des graphes
 */
public class Graphe {
    /** les sommets voisins */
    private HashMap<Sommet, ArrayList<Sommet>> sommetsVoisins;

    /**
     * Constructeur de la classe Graphe
     * Deux sommets sont reliés par une arrête (sont voisins) si la distance entre eux est inférieure à dist
     * @param sommets les sommets du graphe
     * @param dist la distance maximale entre deux sommets
     */
    public Graphe(ArrayList<Sommet> sommets, double dist){
        if (sommets == null) {
            throw new IllegalArgumentException("Graphe : Les sommets ne peuvent être null");
        }
        else if (dist < 0) {
            throw new IllegalArgumentException("Graphe : La distance doit être positive");
        } 
        this.sommetsVoisins = new HashMap<Sommet, ArrayList<Sommet>>();

        for (int i = 0; i < sommets.size(); i++) {
            for (int j = 0; j < sommets.size(); j++) {
                if (i != j) {
                    if (!sommetsVoisins.containsKey(sommets.get(i))) {
                        sommetsVoisins.put(sommets.get(i), new ArrayList<Sommet>());
                    }
                    if (sommets.get(i).calculeDist(sommets.get(j)) <= dist) { 
                        sommetsVoisins.get(sommets.get(i)).add(sommets.get(j));
                    }
                }
            }
        }
    }

    /**
     * Constructeur de la classe Graphe
     * @param somVoisins les sommets voisins
     */
    public Graphe(HashMap<Sommet, ArrayList<Sommet>> somVoisins){
        if (somVoisins == null) {
            throw new IllegalArgumentException("Graphe : Les sommets voisins ne peuvent être null");
        }
        this.sommetsVoisins = somVoisins;
    }

    /**
     * Constructeur par copie
     * @param g le graphe à copier
     */
    public Graphe(Graphe g){
        if (g == null) {
            throw new IllegalArgumentException("Graphe : Le graphe ne peut être null");
        }
        this.sommetsVoisins = g.sommetsVoisins;
    }

    /**
     * Retourne le nombre de sommets présents dans le graphe
     * @return un entier supérieur ou égal à 0
     */
    public int nbSommets(){
        return this.sommetsVoisins.size();
    }

    /**
     * Retourne le nombre d'arrêtes uniques présentes dans le graphe
     * @return un entier supérieur ou égal à 0
     */
    public int nbAretes(){
        ArrayList<Arrete> visite = new ArrayList<Arrete>();
        int nbAretes = 0;

        // Pour chaque sommet avec ses voisins
        for (Map.Entry<Sommet, ArrayList<Sommet>> sommet : this.sommetsVoisins.entrySet()) {
            Sommet s = sommet.getKey();
            ArrayList<Sommet> voisins = sommet.getValue();

            // Pour chaque voisin du sommet
            for (Sommet voisin : voisins) {
                Arrete arr = new Arrete(s, voisin);
                boolean existe = false;
                
                // On vérifie si l'arrete a deja ete visitee
                for (Arrete a : visite) {
                    if (a.compareTo(arr) == 0) {
                        existe = true;
                        break;
                    }
                }
                if (!existe) {
                    visite.add(arr);
                    nbAretes++;
                }
            }
        }
        return nbAretes;
    }

    /**
     * Permet de savoir si un Sommet (désigné par son id) est dans le graphe
     * @param idSomm l'id du sommet à tester
     * @return true si le sommet est dans le graphe, false sinon
     */
    public boolean estDansGraphe(int idSomm){
        if (idSomm < 0) {
            throw new IllegalArgumentException("estDansGraphe : L'id du sommet doit être positif");
        }
        return (this.getEntryById(idSomm) != null);
    }

    /**
     * Calcule le degré (nombre de voisins) du sommet désigné par idSom
     * @param idSom l'id du sommet dont on veut connaître le degré
     * @return un entier supérieur ou égal à 0
     */
    public int calculeDegre(int idSom){
        if (idSom < 0) {
            throw new IllegalArgumentException("calculeDegre : L'id du sommet doit être positif");
        }

        int degre = 0;
        if (!this.estDansGraphe(idSom)) {
            degre = -1;
        }
        else {
            degre = this.getEntryById(idSom).getValue().size();
        }
        return degre;
    }

    /**
     * Retourne un dictionnaire des degrés pour chaque sommets du graphe
     * @return un dictionnaire des degrés où les clefs sont les sommets et les valeurs leur degrés
     */
    public HashMap<Sommet, Integer> calculeDegres(){
        HashMap<Sommet, Integer> degres = new HashMap<Sommet, Integer>();

        for (Map.Entry<Sommet, ArrayList<Sommet>> sommet : this.sommetsVoisins.entrySet()) {
            degres.put(sommet.getKey(), this.calculeDegre(sommet.getKey().getId()));
        }
        return degres;
    }

    /**
     * Test si les deux sommets désigné par leur id sont voisins
     * @param idSom1 l'id du premier sommet
     * @param idSom2 l'id du second sommet
     * @return true si les deux sommets sont voisins, false sinon
     */
    public boolean sontVoisins(int idSom1, int idSom2){
        if (idSom1 < 0 || idSom2 < 0) {
            throw new IllegalArgumentException("sontVoisins : Les id des sommets doivent être positifs");
        }
        boolean sontVoisins = false;

        for(Sommet voisin : this.voisins(idSom1)){
            if (voisin.getId() == idSom2) {
                sontVoisins = true;
                break;
            }
        }
        return sontVoisins;        
    }

    /**
     * Test si l'on peut passe du sommet désigné par idSom1 au sommet désigné par idSom2 en passant par des arrêtes successives
     * @param idSom1 l'id du premier sommet
     * @param idSom2 l'id du second sommet
     * @return true si un chemin existe, false sinon
     */
    public boolean existeChemin(int idSom1, int idSom2){
        if (idSom1 < 0 || idSom2 < 0) {
            throw new IllegalArgumentException("existeChemin : Les id des sommets doivent être positifs");
        }

        boolean existe = false;
        Stack<Integer> stack = new Stack<Integer>();
        ArrayList<Integer> visited = new ArrayList<Integer>();
        stack.push(idSom1);

        while (!stack.isEmpty()) {
            int current = stack.pop();

            if (current == idSom2) {
                existe = true;
                break;
            }

            if(!visited.contains(current)){
                visited.add(current);
                
                for (Sommet voisin : this.voisins(current)) {
                    if (!visited.contains(voisin.getId())) {
                        stack.push(voisin.getId());
                    }
                }
            }
        }

        if (visited.contains(idSom2)) {
            existe = true;
        }
        return existe;
    }

    /**
     * Récupère tout les voisins d'un sommet donnée désigné par son id
     * @param idSom l'id sommet dont on veut récupérer les voisins
     * @return la liste des voisins du sommet
     */
    public ArrayList<Sommet> voisins(int idSom){
        if (idSom < 0) {
            throw new IllegalArgumentException("voisins : L'id du sommet doit être positif");
        }

        ArrayList<Sommet> voisins;
        if (!estDansGraphe(idSom)) {
            voisins = new ArrayList<Sommet>();
        }
        else {
            voisins = this.getEntryById(idSom).getValue();
        }
        return voisins;
    }

    /**
     * Tente d'ajouter une arrête entre les deux sommets désigné par leurs id
     * @param idSom1 l'id du premier sommet
     * @param idSom2 l'id du second sommet
     * @return si idSom1 et idSom2 font partie du graphe true car l'arrête a été ajoutée, false sinon
     */
    public boolean ajouteArete(int idSom1, int idSom2){
        boolean ajoute;

        if (!this.estDansGraphe(idSom1) || !this.estDansGraphe(idSom2)) {
            ajoute = false;
        }
        else {
            for(Sommet voisin : this.voisins(idSom1)) {
                if (voisin.getId() == idSom2) {
                    ajoute = false;
                    break;
                }
            }
            this.getEntryById(idSom1).getValue().add(this.getEntryById(idSom2).getKey());
            this.getEntryById(idSom2).getValue().add(this.getEntryById(idSom1).getKey());
            ajoute = true;
        }
        return ajoute;
    }

    /**
     * Supprime l'arrête entre les deux sommets désigné par leurs id
     * @param idSom1 l'id du premier sommet
     * @param idSom2 l'id du second sommet
     * @return true si les deux sommets font partie du graphe et qu'ils sont voisins, false sinon
     */
    public boolean retireArete(int idSom1, int idSom2){
        boolean retire;

        if (!this.estDansGraphe(idSom1) || !this.estDansGraphe(idSom2)) {
            retire = false;
        }
        else {
            retire = false;
            for(Sommet voisin : this.voisins(idSom1)) {
                if (voisin.getId() == idSom2) {
                    this.getEntryById(idSom1).getValue().remove(this.getEntryById(idSom2).getKey());
                    this.getEntryById(idSom2).getValue().remove(this.getEntryById(idSom1).getKey());
                    retire = true;
                    break;
                }
            }
        }
        return retire;
    }

    /**
     * Retourne une matrice de dimensions (nbSommet) et (nbSommets + 1) où la première ligne reprend les id des sommets dans l'ordre croissant.
     * Les autres lignes composent la matrice d'adjacence du graphe. 
     * @return un tableau d'entier de dimensions (nbSommets) et (nbSommets + 1) comportant l'identification des sommets et la matrice d'adjacence.
     */
    public int[][] matriceAdjacence(){
        int[][] matrice = new int[this.nbSommets() + 1][this.nbSommets()];
        int i = 1;
        int j = 0;
        ArrayList<Sommet> sommets = this.getSortedSommet();

        for (Sommet sommet : sommets) {
            matrice[0][j] = sommet.getId();
            j++;
        }

        for (Sommet sommet : sommets) {
            int current = sommet.getId();
            j = 0;
            for (Sommet sommet2 : sommets) {
                int testVoisin = sommet2.getId();
                if (this.sontVoisins(current, testVoisin)) {
                    matrice[i][j] = 1;
                }
                j++;
            }
            i++;
        }
        return matrice;
    }

    /**
     * Test si le graphe est connexe ou non (tout sommet peut être relié à tout autre sommet par une arrête ou une suite d'arrêtes)
     * @return true si le graphe est connexe, false sinon
     */
    public boolean estConnexe(){
        boolean connexe = true;

        ArrayList<Sommet> sommets = this.getSortedSommet();
        int dep = sommets.get(0).getId();
    
        for (Sommet sommet : sommets) {
            int testSommet = sommet.getId();
            if (dep != testSommet && !this.existeChemin(dep, testSommet)) {
                connexe = false;
                break;
            }
        }
        return connexe;
    }

    /**
     * Renvoie la liste des graphes connexes composants le graphe actuel
     * @return la liste des graphes connexes
     */
    public ArrayList<Graphe> composanteConnexe(){
        ArrayList<ArrayList<Sommet>> composantes = new ArrayList<ArrayList<Sommet>>();
        ArrayList<Graphe> graphes = new ArrayList<Graphe>();
        ArrayList<Sommet> sommets = this.getSortedSommet();
        
        for (Sommet sommet : sommets) {
            int id = sommet.getId();
            boolean newCompo = true;
            for (ArrayList<Sommet> composante : composantes) {

                if (this.existeChemin(composante.get(0).getId(), id)) {
                    newCompo = false;
                    composante.add(sommet);
                    break;
                }
            }
            if (newCompo) {
                ArrayList<Sommet> composante = new ArrayList<Sommet>();
                composante.add(sommet);
                composantes.add(composante);
            }
        }

        for (ArrayList<Sommet> composante : composantes) {
            HashMap<Sommet, ArrayList<Sommet>> grapheMap = new HashMap<Sommet, ArrayList<Sommet>>();

            for (Sommet sommet : composante) {
                grapheMap.put(sommet, this.voisins(sommet.getId()));
            }

            Graphe graphe = new Graphe(grapheMap);
            graphes.add(graphe);
        }
        return graphes;
    }

    /**
     * Récupère le nombre d'arête minimale entre les deux sommets désignés par leurs id.
     * @param idSom1 l'id du premier sommet
     * @param idSom2 l'id du second sommet
     * @return 0 s'il n'existe pas de chemin entre eux mais qu'ils sont présent dans le graphe, -1 s'il n'appartient pas au graphe, la nombre d'arête sinon
     */
    public int distAretes(int idSom1, int idSom2){
        int dist;

        if (!this.estDansGraphe(idSom1) || !this.estDansGraphe(idSom2)) {
            dist = -1;
        }
        else {
            if (!this.existeChemin(idSom1, idSom2)) {
                dist = 0;
            }
            else {
                dist = this.distanceDijkstra(idSom1, idSom2);
            }
        }
        return dist;
    }

    /**
     * Retourne le nombre maximal d'arêtes du chemin entre le sommet désigné par idSom et les autres sommets du graphe.
     * @param idSom l'id du sommet de départ
     * @return l'excentricité si elle est calculé sur un graphe connexe, -1 sinon
     */
    public int excentricite(int idSom){
        int excent;

        if (!this.estDansGraphe(idSom)) {
            excent = -1;
        }
        else {
            if (this.estConnexe()) {
                int maxDist = 0;
                ArrayList<Sommet> sommets = this.getSortedSommet();
                for (Sommet sommet : sommets) {
                    int dist = this.distanceDijkstra(idSom, sommet.getId());
                    if (dist > maxDist) {
                        maxDist = dist;
                    }
                }
                excent = maxDist;
            }
            else {
                excent = -1;
            }
        }
        return excent;
    }

    /**
     * Retourne le diamètre du graphe actuel, (le maximum des excentricités des sommets)
     * @return le diamètre du graphe actuel si connexe, -1 sinon
     */
    public int diametre(){
        int diametre;

        if (this.estConnexe()) {
            diametre = 0;
            for (Sommet sommet : this.getSortedSommet()) {
                int testExcent = this.excentricite(sommet.getId());
                if (testExcent > diametre) {
                    diametre = testExcent;
                }
            }
        }
        else {
            diametre = -1;
        }
        return diametre;
    }

    /**
     * Retourne le rayon du graphe actuel, (le minimum des excentricités des sommets)
     * @return le rayon du graphe actuel si connexe, -1 sinon
     */
    public int rayon(){
        int rayon;

        if (this.estConnexe()) {
            rayon = this.excentricite(this.getSortedSommet().get(0).getId());
            for (Sommet sommet : this.getSortedSommet()) {
                int testExcent = this.excentricite(sommet.getId());
                if (testExcent < rayon) {
                    rayon = testExcent;
                }
            }
        }
        else {
            rayon = -1;
        }
        return rayon;
    }

    /**
     * @param id le id du sommet
     * @return le nombre de voisins du sommet désigné par id
     */
    private Map.Entry<Sommet, ArrayList<Sommet>> getEntryById(int id) {
        for (Map.Entry<Sommet, ArrayList<Sommet>> sommet : this.sommetsVoisins.entrySet()) {
            if (sommet.getKey().getId() == id) {
                return sommet;
            }
        }
        return null;
    }

    /**
     * Trie les sommets
     * @return la liste des sommets triés
     */
    private ArrayList<Sommet> getSortedSommet() {
        ArrayList<Sommet> sortedSommet = new ArrayList<>();
        for (Map.Entry<Sommet, ArrayList<Sommet>> sommet : this.sommetsVoisins.entrySet()) {
            sortedSommet.add(sommet.getKey());
        }
        Collections.sort(sortedSommet);
        return sortedSommet;
    }

    /**
     * 
     * @param idSom1 l'id du premier sommet
     * @param idSom2 l'id du second sommet 
     * @return la distance entre les deux sommets à l'aide de dijsktra
     */
    private int distanceDijkstra(int idSom1, int idSom2) {
        int dist = 0;
        ArrayList<Sommet> sommets = this.getSortedSommet();
        ArrayList<Sommet> marque = new ArrayList<>();
        HashMap<Sommet, Integer> distances = new HashMap<>();
        HashMap<Sommet, Sommet> precedents = new HashMap<>();

        for (Sommet sommet : sommets) {
            if (sommet != this.getEntryById(idSom1).getKey()) {
                distances.put(sommet, Integer.MAX_VALUE);
                precedents.put(sommet, null);
            }
        }
        marque.add(this.getEntryById(idSom1).getKey());
        distances.put(this.getEntryById(idSom1).getKey(), 0);
        Sommet sommetCourant = this.getEntryById(idSom1).getKey();

        while (sommetCourant != null) {
            for (Sommet voisin : this.getEntryById(sommetCourant.getId()).getValue()) {
                if (distances.get(voisin) > (distances.get(sommetCourant) + 1)) {
                    distances.put(voisin, (distances.get(sommetCourant) + 1));
                    precedents.put(voisin, sommetCourant);
                }
            }
            sommetCourant = null;
            int distMin = Integer.MAX_VALUE;
            for (Sommet sommet : sommets) {
                if (distances.get(sommet) < distMin && !marque.contains(sommet)) {
                    distMin = distances.get(sommet);
                    sommetCourant = sommet;
                }
            }
            if (sommetCourant != null) {
                marque.add(sommetCourant);
            }
        }
        dist = distances.get(this.getEntryById(idSom2).getKey());
        return dist;        
    }
}
