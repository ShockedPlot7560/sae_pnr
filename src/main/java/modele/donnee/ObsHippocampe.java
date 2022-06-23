package modele.donnee;

import java.util.ArrayList;

/**
 * Une observation d'hippocampe
 */
public class ObsHippocampe extends Observation {	
	/** l'espèce de l'hippocampe */
	private EspeceHippocampe espece;
	/** le type de peche */
	private Peche typePeche;
	/** le sexe */
	private Sexe sexe;
	/** la taille */
	private double taille;
	/** la temperature */
	private int temperature;
	/** est gestant */
	private boolean estGestant;

	/**
	 * Constructeur de la classe ObsHippocampe
	 * 
	 * @param id           identifiant de l'observation
	 * @param date         date de l'observation
	 * @param heure        heure de l'observation
	 * @param lieu         lieu de l'observation
	 * @param observateurs liste des observateurs
	 * @param laTaille     taille de l'hippocampe
	 * @param laTemperature temperature de l'eau
	 * @param leTypePeche  type de peche
	 * @param lEspece      espece de l'hippocampe
	 * @param leSexe       sexe de l'hippocampe
	 */
	public ObsHippocampe(int id, java.sql.Date date, java.sql.Time heure, Lieu lieu,
			ArrayList<Observateur> observateurs, double laTaille, int laTemperature, Peche leTypePeche, EspeceHippocampe lEspece,
			Sexe leSexe) {
		super(id, date, heure, lieu, observateurs);
		this.setTaille(laTaille);
		this.setTemperature(laTemperature);
		this.setTypePeche(leTypePeche);
		this.setEspece(lEspece);
		this.setSexe(leSexe);
		this.setEstGestant(false);
	}

	/**
	 * Retourne l'espece de l'hippocampe
	 * 
	 * @return l'espece de l'hippocampe
	 */
	public EspeceHippocampe getEspece() {
		return this.espece;
	}

	/**
	 * Retourne le type de pêche utilisé
	 * 
	 * @return le type de pêche utilisé
	 */
	public Peche getTypePeche() {
		return this.typePeche;
	}

	/**
	 * Retourne le sexe de l'hippocampe
	 * 
	 * @return le sexe de l'hippocampe
	 */
	public Sexe getSexe() {
		return this.sexe;
	}

	/**
	 * Retourne la taille de l'hippocampe
	 * 
	 * @return la taille de l'hippocampe
	 */
	public double getTaille() {
		return this.taille;
	}

	/**
	 * Retourne la temperature de l'eau
	 * 
	 * @return la temperature de l'eau
	 */
	public int getTemperature() {
		return this.temperature;
	}

	/**
	 * Retourne si l'hippocampe est gestant
	 * 
	 * @return true si l'hippocampe est gestant, false sinon
	 */
	public boolean estGestant() {
		return this.estGestant;
	}

	/**
	 * Définit l'espèce de l'hyppocampe
	 * 
	 * @param espece l'espèce de l'hyppocampe
	 */
	public void setEspece(EspeceHippocampe espece) {
		this.espece = espece;
	}

	/**
	 * Définit le type de peche
	 * 
	 * @param typePeche le nouveau type de peche
	 */
	public void setTypePeche(Peche typePeche) {
		if (typePeche == null) {
			throw new IllegalArgumentException("leTypePeche must be not null");
		}
		this.typePeche = typePeche;
	}

	/**
	 * Définit le sexe de l'animal
	 * 
	 * @param sexe le sexe de l'animal
	 */
	public void setSexe(Sexe sexe) {
		if (sexe == null) {
			throw new IllegalArgumentException("leSexe must be not null");
		}
		this.sexe = sexe;
	}

	/**
	 * Définit la taille de l'individu
	 * 
	 * @param taille la taille de l'individu, positive
	 */
	public void setTaille(double taille) {
		if (taille < 0) {
			throw new IllegalArgumentException("laTaille must be positive");
		}
		this.taille = taille;
	}

	/**
	 * Définit la temperature de l'eau
	 * 
	 * @param temperature la temperature de l'eau
	 */
	public void setTemperature(int temperature) {
		this.temperature = temperature;
	}

	/**
	 * Retourne si l'animal est gestant ou non
	 * 
	 * @param estGestant true si l'animal est gestant, false sinon
	 */
	public void setEstGestant(boolean estGestant) {
		if (this.getSexe().equals(Sexe.MALE) || !estGestant) {
			this.estGestant = estGestant;
		} else {
			throw new IllegalArgumentException("Seul les mâles peuvent être gestants");
		}
	}

	public EspeceObservee especeObs() {
		return EspeceObservee.HIPPOCAMPE;
	}

}