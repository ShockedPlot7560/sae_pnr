package modele.donnee;

import java.util.ArrayList;

/**
 * Une Observation
 */
public interface IObs<T> {

	/**
	 * Ajoute une observation à la liste des observations
	 * 
	 * @param obs l'observation à ajouter
	 */
	void ajouteUneObs(T obs);

	/**
	 * Ajouter plusieurs observations en mêmes temps
	 * 
	 * @param obs La liste des observations à ajouter
	 */
	void ajoutePlsObs(ArrayList<T> obs);

	/**
	 * Vide la liste des observations
	 */
	void videObs();

	/**
	 * Retourne le nombre d'observations présentes dans la liste
	 * 
	 * @return le nombre d'observations
	 */
	int nbObs();

	/**
	 * Retire une observation de la liste
	 * 
	 * @param idObs l'identifiant de l'observation à retirer
	 * @return true si l'observation a été retirée, false sinon
	 */
	boolean retireObs(int idObs);
}