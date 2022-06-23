package modele.donnee;

import java.util.ArrayList;

/**
 * Une chouette
 */
public class Chouette implements IObs<ObsChouette> {

	/** le sexe */
	private Sexe sexe;
	/** l'espèce chouette */
	private EspeceChouette espece;
	/** les observations */
	private ArrayList<ObsChouette> lesObservations;
	/** l'identifiant de la chouette */
	private String idChouette;

	/**
	 * Constructeur de la classe Chouette
	 * 
	 * @param id      identifiant de la chouette
	 * @param leSexe  le sexe de la chouette, ne peut pas être null
	 * @param lEspece l'espece de la chouette
	 */
	public Chouette(String id, Sexe leSexe, EspeceChouette lEspece) {
		this.setSexe(leSexe);
		this.setIdChouette(id);
		this.setEspece(lEspece);
		this.lesObservations = new ArrayList<ObsChouette>(0);
	}

	public void ajouteUneObs(ObsChouette obs) {
		if (obs == null) {
			throw new IllegalArgumentException("L'observation ne peut pas être null");
		}
		this.lesObservations.add(obs);
	}

	public void ajoutePlsObs(ArrayList<ObsChouette> obs) {
		if (obs == null) {
			throw new IllegalArgumentException("Les observations ne peut pas être null");
		}
		this.lesObservations.addAll(obs);

	}

	public int nbObs() {
		return this.lesObservations.size();
	}

	public void videObs() {
		this.lesObservations.clear();
	}

	public boolean retireObs(int idObs) {
		boolean ret = false;
		if (this.lesObservations != null && this.lesObservations.size() > 0) {
			int i = 0;
			while (i < this.lesObservations.size() && !ret) {
				ObsChouette obs = this.lesObservations.get(i);
				if (obs.idObs() == idObs) {
					this.lesObservations.remove(i);
					ret = true;
				}
				i++;
			}
		}
		return ret;
	}

	/**
	 * Retourne le sexe de la chouette
	 * 
	 * @return le sexe de la chouette
	 */
	public Sexe getSexe() {
		return sexe;
	}

	/**
	 * Modifie le sexe de la chouette
	 * 
	 * @param sexe le nouveau sexe de la chouette
	 */
	public void setSexe(Sexe sexe) {
		if (sexe == null) {
			throw new IllegalArgumentException("Le sexe ne peut pas être null");
		}
		this.sexe = sexe;
	}

	/**
	 * Retourne l'espece de la chouette
	 * 
	 * @return l'espece de la chouette
	 */
	public EspeceChouette getEspece() {
		return espece;
	}

	/**
	 * Modifie l'espece de la chouette
	 * 
	 * @param espece la nouvelle espece de la chouette
	 */
	public void setEspece(EspeceChouette espece) {
		this.espece = espece;
	}

	/**
	 * Retourne la liste des observations
	 * 
	 * @return la liste des observations
	 */
	public ArrayList<ObsChouette> getLesObservations() {
		return lesObservations;
	}

	/**
	 * Retourne l'identifiant de la chouette
	 * 
	 * @return l'identifiant de la chouette
	 */
	public String getIdChouette() {
		return idChouette;
	}

	/**
	 * Modifie l'identifiant de la chouette
	 * 
	 * @param idChouette le nouvel identifiant de la chouette
	 */
	public void setIdChouette(String idChouette) {
		if (idChouette == null) {
			throw new IllegalArgumentException("L'id ne peut pas être null");
		}
		this.idChouette = idChouette;
	}

	@Override
	public String toString() {
		return "Chouette [sexe=" + sexe + ", espece=" + espece + ", idChouette="
				+ idChouette + "]";
	}

}