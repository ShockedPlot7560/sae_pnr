package modele.donnee;

import java.sql.Date;
import java.util.ArrayList;

/**
 * Classe représantant un nid
 */
public class NidGCI implements IObs<ObsGCI> {

	/** les observations */
	private ArrayList<ObsGCI> lesObservations;
	/** l'identifiant du nid */
	private int idNid;
	/** le nombre d'envol */
	private int nbEnvol;
	/** le nom de la plage */
	private String nomPlage;
	/** la raison d'arret */
	private RaisonArretObservation raisonArret;
	/** la protection du nid */
	private boolean protection;
	/** la bague du male */
	private String bagueMale;
	/** la bague de la femelle */
	private String bagueFemelle;
	/** le nom de la plage */
	private String plage;

	/**
	 * Constructeur de la classe NidGCI
	 * @param id 	identifiant du nid
	 * @param raisonArret raison d'arret de l'observation
	 * @param nbEnvol	le nombre d'envol au nid
	 * @param protection la protection du nid
	 * @param bagueMale la bague du male
	 * @param bagueFemelle la bague de la femelle
	 * @param plage la plage
	 */
	public NidGCI(int id, RaisonArretObservation raisonArret, int nbEnvol, boolean protection, String bagueMale, String bagueFemelle, String plage) {
		this.setIdNid(id);
		this.setNbEnvol(nbEnvol);
		this.setNomPlage(plage);
		this.setRaisonArret(raisonArret);
		this.setProtection(protection);
		this.setBagueMale(bagueMale);
		this.setBagueFemelle(bagueFemelle);
		this.setPlage(plage);
		this.lesObservations = new ArrayList<ObsGCI>();
	}

	/**
	 * Constructeur de la classe NidGCI
	 * @param id 	identifiant du nid
	 * @param raisonArret raison d'arret de l'observation
	 * @param nbEnvol	le nombre d'envol au nid
	 * @param protection la protection du nid
	 * @param bagueMale la bague du male
	 * @param bagueFemelle la bague de la femelle
	 * @param plage la plage
	 */
	public NidGCI(int id, String raisonArret, int nbEnvol, boolean protection, String bagueMale, String bagueFemelle, String plage){
		this(id, RaisonArretObservation.valueOf(raisonArret), nbEnvol, protection, bagueMale, bagueFemelle, plage);
	}

	/**
	 * Renvoie la date de la première observation
	 * 
	 * @return la date de la première observation, null si aucune observation
	 */
	public Date dateDebutObs() {
		Date date = null;
		if (this.lesObservations != null) {
			for (ObsGCI obs : this.lesObservations) {
				if (date == null) {
					date = obs.dateObs();
				} else {
					if (date.getTime() > obs.dateObs().getTime()) {
						date = obs.dateObs();
					}
				}
			}
		}
		return date;
	}

	/**
	 * Renvoie la date de la dernière observation
	 * 
	 * @return la date de la dernière observation, null si aucune observation
	 */
	public Date dateFinObs() {
		Date date = null;
		if (this.lesObservations != null) {
			for (ObsGCI obs : this.lesObservations) {
				if (date == null) {
					date = obs.dateObs();
				} else {
					if (date.getTime() < obs.dateObs().getTime()) {
						date = obs.dateObs();
					}
				}
			}
		}
		return date;
	}

	public void ajouteUneObs(ObsGCI obs) {
		if (obs == null) {
			throw new IllegalArgumentException("L'observation est null");
		}
		this.lesObservations.add((ObsGCI) obs);
	}

	public void ajoutePlsObs(ArrayList<ObsGCI> obs) {
		if (obs == null) {
			throw new IllegalArgumentException("L'observation est null");
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
				ObsGCI obs = this.lesObservations.get(i);
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
	 * Définit l'identifiant du nid
	 * 
	 * @param id identifiant du nid
	 */
	public void setIdNid(int id) {
		if (id < 0) {
			throw new IllegalArgumentException("id must be positive");
		}
		this.idNid = id;
	}

	/**
	 * Retourne l'identifiant du nid
	 * 
	 * @return l'identifiant du nid
	 */
	public int getIdNid() {
		return this.idNid;
	}

	/**
	 * Définit le nom de la plage où se situe le nid
	 * 
	 * @param nom le nom de la plage où se situe le nid
	 */
	public void setNomPlage(String nom) {
		if (nom == null) {
			throw new IllegalArgumentException("nom must be not null");
		}
		this.nomPlage = nom;
	}

	/**
	 * Retourne le nom de la plage où se situe le nid
	 * 
	 * @return le nom de la plage où se situe le nid
	 */
	public String getNomPlage() {
		return this.nomPlage;
	}

	/**
	 * Définit le nombre d'envol
	 * 
	 * @param nbEnvol le nombre d'envol
	 */
	public void setNbEnvol(int nbEnvol) {
		if (nbEnvol < 0) {
			throw new IllegalArgumentException("nbEnvol must be positive");
		}
		this.nbEnvol = nbEnvol;
	}

	/**
	 * Retourne le nombre d'envol
	 * 
	 * @return le nombre d'envol
	 */
	public int getNbEnvol() {
		return this.nbEnvol;
	}

	/**
	 * Définit la raison d'arrêt de l'observation
	 * 
	 * @param raisonArret la raison d'arrêt de l'observation
	 */
	public void setRaisonArret(RaisonArretObservation raisonArret) {
		this.raisonArret = raisonArret;
	}

	/**
	 * Retourne la raison d'arrêt de l'observation
	 * @return la raison
	 */
	public RaisonArretObservation getRaisonArret() {
		return this.raisonArret;
	}

	/**
	 * Définit si le nid est protégé ou non
	 * @param protection true si le nid est protégé, false sinon
	 */
	public void setProtection(boolean protection){
		this.protection = protection;
	}

	/**
	 * Retourne si le nid est protégé ou non
	 * @return true si le nid est protégé, false sinon
	 */
	public boolean getProtection(){
		return this.protection;
	}
	
	/**
	 * Définit la bague du male
	 * @param bagueMale Le numéro de la bague
	 */
	public void setBagueMale(String bagueMale){
		if(bagueMale == null){
			throw new IllegalArgumentException("bagueMale must be not null");
		}
		this.bagueMale = bagueMale;
	}

	/**
	 * Retourne la bague du male
	 * @return le numéro de la bague
	 */
	public String getBagueMale(){
		return this.bagueMale;
	}

	/**
	 * Définit la bague de la femelle
	 * @param bagueFemelle le numéro de la bague de la femelle
	 */
	public void setBagueFemelle(String bagueFemelle){
		if(bagueFemelle == null){
			throw new IllegalArgumentException("bagueFemelle must be not null");
		}
		this.bagueFemelle = bagueFemelle;
	}

	/**
	 * Retourne la bague de la femelle
	 * @return le numéro de la bague de la femelle
	 */
	public String getBagueFemelle(){
		return this.bagueFemelle;
	}

	/**
	 * Définit le nom de la plage où se situe le nid
	 * @param plage le nom de la plage où se situe le nid
	 */
	public void setPlage(String plage){
		if(plage == null){
			throw new IllegalArgumentException("plage must be not null");
		}
		this.plage = plage;
	}

	/**
	 * Retourne le nom de la plage où se situe le nid
	 * @return le nom de la plage où se situe le nid
	 */
	public String getPlage(){
		return this.plage;
	}
}