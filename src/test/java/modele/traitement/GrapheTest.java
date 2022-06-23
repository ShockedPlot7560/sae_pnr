package modele.traitement;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import org.junit.Before;
import org.junit.Test;

import modele.donnee.EspeceObservee;
import modele.donnee.Lieu;

public class GrapheTest {
    private Graphe graphe;
    private Graphe graphe2;
    private HashMap<Sommet, ArrayList<Sommet>> voisins;
    Sommet som1;
    Sommet som2;
    Sommet som3;
    Sommet som4;
    Sommet som5;
    Sommet som6;

    @Test(expected = IllegalArgumentException.class)
    public void testConstructeurSommetsNull(){
        new Graphe(null, 10);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructeurDistanceNegatif(){
        new Graphe(new ArrayList<Sommet>(), -10);
    }

    @Before
    public void init(){
        ArrayList<Sommet> sommets = new ArrayList<Sommet>(0);
        som1 = new Sommet(1, new Lieu(0,0, false), new Date(1), EspeceObservee.BATRACIEN);
        som2 = new Sommet(2, new Lieu(1,0, false), new Date(2), EspeceObservee.BATRACIEN);
        som3 = new Sommet(3, new Lieu(-3,-4, false), new Date(3), EspeceObservee.BATRACIEN);
        som4 = new Sommet(4, new Lieu(-3,0, false), new Date(4), EspeceObservee.BATRACIEN); 
        som5 = new Sommet(5, new Lieu(-3,100, false), new Date(5), EspeceObservee.BATRACIEN);
        som6 = new Sommet(6, new Lieu(-5,0, false), new Date(6), EspeceObservee.BATRACIEN);
        sommets.add(som1);
        sommets.add(som2);
        sommets.add(som3);
        sommets.add(som4);
        sommets.add(som5);
        sommets.add(som6);
        
        this.graphe = new Graphe(sommets, 4);

        this.voisins = new HashMap<Sommet, ArrayList<Sommet>>(0);
        this.voisins.put(som1, new ArrayList<Sommet>(Arrays.asList(som2, som4)));
        this.voisins.put(som2, new ArrayList<Sommet>(Arrays.asList(som1, som4)));
        this.voisins.put(som3, new ArrayList<Sommet>(Arrays.asList(som4)));
        this.voisins.put(som4, new ArrayList<Sommet>(Arrays.asList(som1, som2, som3, som6)));
        this.voisins.put(som5, new ArrayList<Sommet>());
        this.voisins.put(som6, new ArrayList<Sommet>(Arrays.asList(som4)));

        this.graphe2 = new Graphe(this.voisins);
    }

    @Test
    public void testNbSommetsNormalCase(){
        assertEquals(6, this.graphe.nbSommets(), 6);
        assertEquals(6, this.graphe2.nbSommets(), 6);
        assertEquals(0, new Graphe(new ArrayList<Sommet>(0), 5).nbSommets());
    }

    @Test
    public void testNbAretesNormalCase(){
        assertEquals(5, this.graphe.nbAretes());
        assertEquals(5, this.graphe2.nbAretes());
        assertEquals(0, new Graphe(new ArrayList<Sommet>(0), 5).nbAretes());
    }

    @Test
    public void testEstDansGrapheNormalCase(){
        assertEquals("Sommet 7", false, this.graphe.estDansGraphe(7));
        assertEquals("Sommet 4", true, this.graphe.estDansGraphe(4));
        assertEquals("Sommet 5", true, this.graphe.estDansGraphe(5));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testEstDansGrapheSommetNegatif(){
        this.graphe.estDansGraphe(-1);
    }

    @Test
    public void testCalculeDegreNormalCase(){
        assertEquals("Sommet 3", 1, this.graphe.calculeDegre(3));
        assertEquals("Sommet 4", 4, this.graphe.calculeDegre(4));
        assertEquals("Sommet 1528547", -1, this.graphe.calculeDegre(1528547));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCalculeDegreSommetNegatif(){
        this.graphe.estDansGraphe(-1);
    }

    @Test
    public void testCalculeDegresNormalCase(){
        HashMap<Sommet, Integer> degres = this.graphe.calculeDegres();
        assertEquals("Taille", 6, degres.size());
        assertEquals("Sommet 1", 2, degres.get(som1).intValue());
        assertEquals("Sommet 2", 2, degres.get(som2).intValue());
        assertEquals("Sommet 3", 1, degres.get(som3).intValue());
        assertEquals("Sommet 4", 4, degres.get(som4).intValue());
        assertEquals("Sommet 5", 0, degres.get(som5).intValue());
        assertEquals("Sommet 6", 1, degres.get(som6).intValue());
    }

    @Test
    public void testSontVoisinsNormalCase(){
        assertEquals("Sommet 3 et 4", true, this.graphe.sontVoisins(3, 4));
        assertEquals("Sommet 6 et 2", false, this.graphe.sontVoisins(6, 2));
        assertEquals("Sommet 5 et 3", false, this.graphe.sontVoisins(5, 3));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSontVoisinsSommet1Negatif(){
        this.graphe.sontVoisins(-1, 2);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSontVoisinsSommet2Negatif(){
        this.graphe.sontVoisins(3, -1);
    }

    @Test
    public void testExisteCheminNormalCase(){
        assertEquals("Sommet 5 et 6", false, this.graphe.existeChemin(5, 6));
        assertEquals("Sommet 6 et 2", true, this.graphe.existeChemin(6, 2));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testExisteCheminSommet1Negatif(){
        this.graphe.existeChemin(-1, 2);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testExisteCheminSommet2Negatif(){
        this.graphe.existeChemin(3, -1);
    }

    @Test
    public void testEstConnexeNormalCase(){
        assertEquals(false, this.graphe.estConnexe());
    }

    @Test
    public void testMatriceAdjacenceNormalCase(){
        int[][] result = this.graphe.matriceAdjacence();
        assertArrayEquals("Header", new int[]{1,2,3,4,5,6}, result[0]);
        assertArrayEquals("sommet 1", new int[]{0,1,0,1,0,0}, result[1]);
        assertArrayEquals("sommet 2", new int[]{1,0,0,1,0,0}, result[2]);
        assertArrayEquals("sommet 3", new int[]{0,0,0,1,0,0}, result[3]);
        assertArrayEquals("sommet 4", new int[]{1,1,1,0,0,1}, result[4]);
        assertArrayEquals("sommet 5", new int[]{0,0,0,0,0,0}, result[5]);
        assertArrayEquals("sommet 6", new int[]{0,0,0,1,0,0}, result[6]);
    }

    @Test
    public void testComposanteConnexe(){
        ArrayList<Graphe> composantes = this.graphe.composanteConnexe();
        assertEquals("Taille ArrayList", 2, composantes.size());
        assertEquals("Taille premier graphe", 5, composantes.get(0).nbSommets());
        assertEquals("Taille deuxième graphe", 1, composantes.get(1).nbSommets());
        assertEquals("Connexe premier graphe", true, composantes.get(0).estConnexe());
        assertEquals("Connexe deuxième graphe", true, composantes.get(1).estConnexe());
        assertEquals("Nombre arêtes premier graphe", 5, composantes.get(0).nbAretes());
        assertEquals("Nombre arêtes deuxième graphe", 0, composantes.get(1).nbAretes());

        Graphe newGraphe = composantes.get(0);
        assertEquals("Excentricité sommet 6", 2, newGraphe.excentricite(6));
        assertEquals("Excentricité sommet 4", 1, newGraphe.excentricite(4));
        assertEquals("Diamètre", 2, newGraphe.diametre());
        assertEquals("Rayon", 1, newGraphe.rayon());
        assertEquals("Distance 6, 2", 2, newGraphe.distAretes(6, 2));
        assertEquals("Distance 3, 2", 2, newGraphe.distAretes(3, 2));
        /*
        TODO
        assertEquals("ExcentriciteDist 6", 6.0D, newGraphe.excentriciteDist(6), 0.0001);
        assertEquals("Diamètre distance", 8.0D, newGraphe.diametreDist(), 0.0001);
        assertEquals("Rayon distance", 4.0D, newGraphe.rayonDist(), 0.0001);
        */
    }

    @Test
    public void testVoisinsNormalCase(){
        ArrayList<Sommet> result = this.graphe.voisins(3);
        assertEquals("Taille ArrayList", 1, result.size());
        assertEquals("Sommet 1", false, result.contains(som1));
        assertEquals("Sommet 2", false, result.contains(som2));
        assertEquals("Sommet 3", false, result.contains(som3));
        assertEquals("Sommet 4", true, result.contains(som4));
        assertEquals("Sommet 5", false, result.contains(som5));
        assertEquals("Sommet 6", false, result.contains(som6));
        
        result = this.graphe.voisins(5);
        assertEquals("Taille ArrayList", 0, result.size());
        assertEquals("Sommet 1", false, result.contains(som1));
        assertEquals("Sommet 2", false, result.contains(som2));
        assertEquals("Sommet 3", false, result.contains(som3));
        assertEquals("Sommet 4", false, result.contains(som4));
        assertEquals("Sommet 5", false, result.contains(som5));
        assertEquals("Sommet 6", false, result.contains(som6));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testVoisinsSommetNegatif(){
        this.graphe.voisins(-1);
    }

    @Test
    public void testAjouteArete(){
        assertEquals("Ajoute arrete (3, 1)", true, this.graphe.ajouteArete(3, 1));
        assertEquals("Existe chemin (3, 1)", true, this.graphe.existeChemin(3, 1));
        assertEquals(false, this.graphe.ajouteArete(7, 1));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAjouteAreteSommet1Negatif(){
        this.graphe.ajouteArete(-1, 1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAjouteAreteSommet2Negatif(){
        this.graphe.ajouteArete(1, -1);
    }

    @Test
    public void testRetireArete(){
        assertEquals("Existe chemin (3, 4) avant retrait", true, this.graphe.existeChemin(3,4));
        assertEquals("Retire arrete (3, 4)", true, this.graphe.retireArete(3,4));
        assertEquals("Existe chemin (3, 4)", false, this.graphe.existeChemin(3,4));
        assertEquals(false, this.graphe.retireArete(7, 1));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRetireAreteSommet1Negatif(){
        this.graphe.retireArete(-1, 1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRetireAreteSommet2Negatif(){
        this.graphe.retireArete(1, -1);
    }

    @Test
    public void testDistAretes(){
        assertEquals("Distance: (1, 4)", 1, this.graphe.distAretes(1, 4));
        assertEquals("Distance: (2, 6)", 2, this.graphe.distAretes(2,6));
        assertEquals("Distance: (2, 5)", 0, this.graphe.distAretes(2, 5));
        assertEquals("Distance: (2, 9)", -1, this.graphe.distAretes(2, 9));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDistAretesSommet1Negatif(){
        this.graphe.distAretes(-1, 1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDistAretesSommet2Negatif(){
        this.graphe.distAretes(1, -1);
    }

    @Test
    public void testExcentricite(){
        assertEquals(-1, this.graphe.excentricite(1));
        assertEquals(-1, this.graphe.excentricite(5));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testExcentriciteSommetNegatif(){
        this.graphe.excentricite(-1);
    }

    @Test
    public void testDiametre(){
        assertEquals(-1, this.graphe.diametre());
    }

    @Test
    public void testRayon(){
        assertEquals(-1, this.graphe.rayon());
    }
}
