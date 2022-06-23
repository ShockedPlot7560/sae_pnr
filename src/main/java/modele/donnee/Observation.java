package modele.donnee;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;

/**
 * Une observation générique
 */
public abstract class Observation {
	/** le lieu de l'observation */
	protected Lieu lieuObs;
	/** les observateurs */
	protected ArrayList<Observateur> lesObservateurs;
	/** l'identifiant de l'observateur */
	protected int idObs;
	/** la date de l'observation */
	protected java.sql.Date dateObs;
	/** l'heure de l'observation */
	protected java.sql.Time heureObs;

	/**
	 * Constructeur de la classe Observation
	 * 
	 * @param id           identifiant de l'observation
	 * @param date         date de l'observation, peut-être null
	 * @param heure        heure de l'observation, peut-être null
	 * @param lieu         lieu de l'observation
	 * @param observateurs liste des observateurs
	 */
	public Observation(int id, Date date, Time heure, Lieu lieu, ArrayList<Observateur> observateurs) {
		this.setIdObs(id);
		this.setDateObs(date);
		this.setHeureObs(heure);
		this.setLieuObs(lieu);
		this.setLesObservateurs(observateurs);
	}

	/**
	 * Ajoute un observateur à la liste
	 * 
	 * @param o L'observateur a ajouter
	 */
	public void ajouteObservateur(Observateur o) {
		if (o == null) {
			throw new IllegalArgumentException("observateur must be not null");
		}
		for (Observateur observateur : lesObservateurs) {
			if (observateur.getIdObservateur() == o.getIdObservateur()) {
				throw new IllegalArgumentException("observateur must be different of the others");
			}
		}
		this.lesObservateurs.add(o);
	}

	/**
	 * Supprime un observateur de la liste
	 * 
	 * @param idObservateur L'identifiant de l'observateur à supprimer
	 */
	public void retireObservateur(int idObservateur) {
		for (Observateur o : this.lesObservateurs) {
			if (o.getIdObservateur() == idObservateur) {
				this.lesObservateurs.remove(o);
				return;
			}
		}
		throw new IllegalArgumentException("observateur must be present in the list");
	}

	/**
	 * Récupère le type de l'observation
	 * 
	 * @return Le type de l'observation
	 */
	public abstract EspeceObservee especeObs();

	/**
	 * Getter du lieu de l'observation
	 * @return le lieu de l'observation
	 */
	public Lieu lieuObs() {
		return this.lieuObs;
	}

	/**
	 * Récupère la liste des observateurs
	 * 
	 * @return La liste des observateurs
	 */
	public ArrayList<Observateur> lesObservateurs() {
		return this.lesObservateurs;
	}

	/**
	 * Récupère l'identifiant de l'observation
	 * 
	 * @return L'identifiant de l'observation
	 */
	public int idObs() {
		return this.idObs;
	}

	/**
	 * Récupère la date de l'observation
	 * 
	 * @return La date de l'observation
	 */
	public Date dateObs() {
		return this.dateObs;
	}

	/**
	 * Récupère l'heure de l'observation
	 * 
	 * @return L'heure de l'observation
	 */
	public Time heureObs() {
		return this.heureObs;
	}

	/**
	 * Définit le lieu de l'observation
	 * 
	 * @param lieu Le lieu de l'observation
	 */
	public void setLieuObs(Lieu lieu) {
		if (lieu == null) {
			throw new IllegalArgumentException("lieu must be not null");
		}
		this.lieuObs = lieu;
	}

	/**
	 * Définit la liste des observateurs
	 * 
	 * @param observateurs La liste des observateurs
	 */
	public void setLesObservateurs(ArrayList<Observateur> observateurs) {
		if (observateurs == null) {
			throw new IllegalArgumentException("observateurs must be not null");
		}
		this.lesObservateurs = observateurs;
	}

	/**
	 * Définit l'identifiant de l'observation
	 * 
	 * @param id L'identifiant de l'observation
	 */
	public void setIdObs(int id) {
		if (id < 0) {
			throw new IllegalArgumentException("id must be positive");
		}
		this.idObs = id;
	}

	/**
	 * Définit la date de l'observation
	 * 
	 * @param date La date de l'observation
	 */
	public void setDateObs(java.sql.Date date) {
		this.dateObs = date;
	}

	/**
	 * Définit l'heure de l'observation
	 * 
	 * @param heure L'heure de l'observation
	 */
	public void setHeureObs(java.sql.Time heure) {
		this.heureObs = heure;
	}
}