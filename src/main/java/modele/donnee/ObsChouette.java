package modele.donnee;

import java.util.ArrayList;

/**
 * Une observation de chouette
 */
public class ObsChouette extends Observation {
	/** le type d'observation */
	private TypeObservation typeObs;
	/** le numéro de l'individu */
	private String numIndividu;

	/**
	 * Constructeur de la classe Observation
	 * 
	 * @param id           identifiant de l'observation
	 * @param date         date de l'observation
	 * @param heure        heure de l'observation
	 * @param lieu         lieu de l'observation
	 * @param observateurs liste des observateurs
	 * @param type         type de l'observation
	 * @param numIndividu  numéro de l'individu
	 */
	public ObsChouette(int id, java.sql.Date date, java.sql.Time heure, Lieu lieu, ArrayList<Observateur> observateurs,
			TypeObservation type, String numIndividu) {
		super(id, date, heure, lieu, observateurs);
		this.setTypeObservation(type);
		this.setNumIndividu(numIndividu);
	}

	/**
	 * Récupère le type d'observation
	 * 
	 * @return Le type d'observation
	 */
	public TypeObservation getTypeObservation() {
		return this.typeObs;
	}

	/**
	 * Définit le type d'observation
	 * 
	 * @param type Le type d'observation
	 */
	public void setTypeObservation(TypeObservation type) {
		if (type == null) {
			throw new IllegalArgumentException("type must be not null");
		}
		this.typeObs = type;
	}

	/**
	 * Récupère le numéro d'individu
	 * 
	 * @return Le numéro d'individu
	 */
	public String getNumIndividu() {
		return this.numIndividu;
	}

	/**
	 * Définit le numéro d'individu
	 * 
	 * @param numIndividu Le numéro d'individu
	 */
	public void setNumIndividu(String numIndividu) {
		if (numIndividu == null) {
			throw new IllegalArgumentException("numIndividu must be not null");
		}
		this.numIndividu = numIndividu;
	}

	public EspeceObservee especeObs() {
		return EspeceObservee.CHOUETTE;
	}

}