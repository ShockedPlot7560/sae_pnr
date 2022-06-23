package modele.donnee;

import java.util.ArrayList;

/**
 * Une observation de GCI
 */
public class ObsGCI extends Observation {
	/** la nature de l'observation */
	private ContenuNid natureObs;
	/** le nombre de l'observation */
	private int nombre;
	/** présent mais pas utilisé */
	private boolean presentMaisPasObs;

	/**
	 * Constructeur de la classe ObsGCI
	 * 
	 * @param id           identifiant de l'observation
	 * @param date         date de l'observation
	 * @param heure        heure de l'observation
	 * @param lieu         lieu de l'observation
	 * @param observateurs liste des observateurs
	 * @param nature       nature de l'observation
	 * @param leNombre     nombre d'individus
	 * @param presentMaisPasObs présent mais pas observé
	 */
	public ObsGCI(int id, java.sql.Date date, java.sql.Time heure, Lieu lieu, ArrayList<Observateur> observateurs,
			ContenuNid nature, int leNombre, boolean presentMaisPasObs) {
		super(id, date, heure, lieu, observateurs);
		this.setNatureObs(nature);
		this.setNombre(leNombre);
		this.setPresentMaisPasObs(presentMaisPasObs);
	}

	/**
	 * Retourne la nature de l'observation
	 * 
	 * @return la nature de l'observation
	 */
	public ContenuNid getNatureObs() {
		return this.natureObs;
	}

	/**
	 * Définit la nature de l'observation
	 * 
	 * @param nature la nature de l'observation
	 */
	public void setNatureObs(ContenuNid nature) {
		if (nature == null) {
			throw new IllegalArgumentException("nature must be not null");
		}
		this.natureObs = nature;
	}

	/**
	 * Retourne le nombre d'individus observés
	 * 
	 * @return le nombre d'individus observés
	 */
	public int getNombre() {
		return this.nombre;
	}

	/**
	 * Définit le nombre d'individus observés
	 * 
	 * @param nombre le nombre d'individus observés
	 */
	public void setNombre(int nombre) {
		if (nombre < 0) {
			throw new IllegalArgumentException("nombre must be positive");
		}
		this.nombre = nombre;
	}

	/**
	 * Définit si l'observation a été effectué car présent mais non prospecté
	 * @param presentMaisPasObs true si l'observation a été effectué car présent mais non prospecté, false sinon
	 */
	public void setPresentMaisPasObs(boolean presentMaisPasObs) {
		this.presentMaisPasObs = presentMaisPasObs;
	}

	/**
	 * Retourne si l'observation a été effectué car présent mais non prospecté
	 * @return true si l'observation a été effectué car présent mais non prospecté, false sinon
	 */
	public boolean getPresentMaisPasObs() {
		return this.presentMaisPasObs;
	}

	public EspeceObservee especeObs() {
		return EspeceObservee.GCI;
	}
}