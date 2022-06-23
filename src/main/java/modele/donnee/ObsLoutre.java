package modele.donnee;

import java.util.ArrayList;

/**
 * Une observation de loutre
 */
public class ObsLoutre extends Observation {
	/** l'indice */
	private IndiceLoutre indice;

	/**
	 * Constructeur de la classe ObsLoutre
	 * 
	 * @param id           identifiant de l'observation
	 * @param date         date de l'observation
	 * @param heure        heure de l'observation
	 * @param lieu         lieu de l'observation
	 * @param observateurs liste des observateurs
	 * @param lIndice      liste des indices
	 */
	public ObsLoutre(int id, java.sql.Date date, java.sql.Time heure, Lieu lieu, ArrayList<Observateur> observateurs,
			IndiceLoutre lIndice) {
		super(id, date, heure, lieu, observateurs);
		this.setIndice(lIndice);
	}

	/**
	 * Récupère l'indice de l'observation
	 * 
	 * @return l'indice de l'observation
	 */
	public IndiceLoutre getIndice() {
		return this.indice;
	}

	/**
	 * Définit l'indice de l'observation
	 * 
	 * @param indice l'indice de l'observation
	 */
	public void setIndice(IndiceLoutre indice) {
		if (indice == null) {
			throw new IllegalArgumentException("lIndice must be not null");
		}
		this.indice = indice;
	}

	public EspeceObservee especeObs() {
		return EspeceObservee.LOUTRE;
	}

}