package modele.donnee;

import java.util.ArrayList;

/**
 * Une observation de batracien
 */
public class ObsBatracien extends Observation {
	/** l'espèce de batracien */
	private EspeceBatracien espece;
	/** le nombre d'adulte */
	private int nombreAdultes;
	/** le nombre d'amplexus */
	private int nombreAmplexus;
	/** le nombre de tetard */
	private int nombreTetard;
	/** le nombre de ponte */
	private int nombrePonte;
	/** la température */
	private double temperature;
	/** la météo du ciel */
	private MeteoCiel ciel;
	/** le type de température */
	private MeteoTemp temp;
	/** le type de vent */
	private MeteoVent vent;
	/** le type de pluie */
	private MeteoPluie pluie;
	/** le lieu de la végétation */
	private LieuVegetation lieuVege;
	/** une zone humide */
	private ZoneHumide zone;
	

	/**
	 * Constructeur de la classe Observation
	 * 
	 * @param id           identifiant de l'observation
	 * @param date         date de l'observation
	 * @param heure        heure de l'observation
	 * @param lieu         lieu de l'observation
	 * @param observateurs liste des observateurs
	 * @param resObs       résultat de l'observation
	 * @param temperature  la temperature de l'eau
	 * @param lEspece      espece de l'observation
	 */
	public ObsBatracien(int id, java.sql.Date date, java.sql.Time heure, Lieu lieu, ArrayList<Observateur> observateurs,
			int[] resObs, double temperature, EspeceBatracien lEspece) {
		this(id, date, heure, lieu, observateurs, resObs, temperature, null, null,
			null, null, null, null, lEspece);
	}

	/**
	 * Constructeur de la classe Observation
	 * 
	 * @param id           identifiant de l'observation
	 * @param date         date de l'observation
	 * @param heure        heure de l'observation
	 * @param lieu         lieu de l'observation
	 * @param observateurs liste des observateurs
	 * @param resObs       résultat de l'observation
	 * @param temperature  la temperature de l'eau
	 * @param lEspece      espece de l'observation
	 * @param ciel         la météo du ciel
	 * @param temp         le type de température
	 * @param vent         le type de vent
	 * @param pluie        le type de pluie
	 * @param lieuVege     le lieu de la végétation
	 * @param zone         une zone humide
	 */
	public ObsBatracien(int id, java.sql.Date date, java.sql.Time heure, Lieu lieu, ArrayList<Observateur> observateurs,
			int[] resObs, double temperature, MeteoCiel ciel, MeteoTemp temp, MeteoVent vent, MeteoPluie pluie,
			LieuVegetation lieuVege, ZoneHumide zone, EspeceBatracien lEspece) {
		super(id, date, heure, lieu, observateurs);
		this.setEspece(lEspece);
		this.setResObs(resObs);
		this.setTemperature(temperature);
		this.setCiel(ciel);
		this.setTemp(temp);
		this.setVent(vent);
		this.setPluie(pluie);
		this.setLieuVege(lieuVege);
		this.setZone(zone);
	}

	/**
	 * Récupère l'espece de l'observation
	 * 
	 * @return l'espece de l'observation
	 */
	public EspeceBatracien getEspece() {
		return this.espece;
	}

	/**
	 * Récupère le nombre d'adultes
	 * 
	 * @return le nombre d'adulte
	 */
	public int getNombreAdultes() {
		return this.nombreAdultes;
	}

	/**
	 * Récupère le nombre d'amplexus
	 * 
	 * @return le nombre d'amplexus
	 */
	public int getNombreAmplexus() {
		return this.nombreAmplexus;
	}

	/**
	 * Récupère le nombre de ponte
	 * 
	 * @return le nombre de ponte
	 */
	public int getNombrePonte() {
		return this.nombrePonte;
	}

	/**
	 * Récupère le nombre de tetards
	 * 
	 * @return le nombre de tetards
	 */
	public int getNombreTetard() {
		return this.nombreTetard;
	}

	/**
	 * Récupère la temperature de l'eau
	 * 
	 * @return la temperature de l'eau
	 */
	public double getTemperature() {
		return this.temperature;
	}

	/**
	 * Récupère le ciel
	 * 
	 * @return le ciel
	 */
	public MeteoCiel getCiel() {
		return this.ciel;
	}

	/**
	 * Récupère la température
	 * 
	 * @return la température
	 */
	public MeteoTemp getTemp() {
		return this.temp;
	}

	/**
	 * Récupère le vent
	 * 
	 * @return le vent
	 */
	public MeteoVent getVent() {
		return this.vent;
	}

	/**
	 * Récupère la pluie
	 * 
	 * @return la pluie
	 */
	public MeteoPluie getPluie() {
		return this.pluie;
	}

	/**
	 * Récupère le lieu de la vegetation
	 * 
	 * @return le lieu de la vegetation
	 */
	public LieuVegetation getLieuVege() {
		return this.lieuVege;
	}

	/**
	 * Récupère la zone humide
	 * 
	 * @return la zone humide
	 */
	public ZoneHumide getZone() {
		return this.zone;
	}

	/**
	 * Définit l'espece de l'observation
	 * 
	 * @param lEspece l'espece de l'observation
	 */
	public void setEspece(EspeceBatracien lEspece) {
		if (lEspece == null) {
			throw new IllegalArgumentException("lEspece must be not null");
		}
		this.espece = lEspece;
	}

	/**
	 * Définit le résultat d'observation
	 * 
	 * @param resObs le résultat d'observation
	 */
	public void setResObs(int[] resObs) {
		if (resObs == null) {
			throw new IllegalArgumentException("resObs must be not null");
		}
		if (resObs.length != 4) {
			throw new IllegalArgumentException("resObs must be 4 int");
		}
		this.setNombreAdultes(resObs[0]);
		this.setNombreAmplexus(resObs[1]);
		this.setNombreTetard(resObs[2]);
		this.setNombrePonte(resObs[3]);
	}

	/**
	 * Définit le nombre d'adultes
	 * 
	 * @param nombreAdultes le nombre d'adultes
	 */
	public void setNombreAdultes(int nombreAdultes) {
		if (nombreAdultes < 0) {
			throw new IllegalArgumentException("nombreAdultes must be positive");
		}
		this.nombreAdultes = nombreAdultes;
	}

	/**
	 * Définit le nombre d'amplexus
	 * 
	 * @param nombreAmplexus le nombre d'amplexus
	 */
	public void setNombreAmplexus(int nombreAmplexus) {
		if (nombreAmplexus < 0) {
			throw new IllegalArgumentException("nombreAmplexus must be positive");
		}
		this.nombreAmplexus = nombreAmplexus;
	}

	/**
	 * Définit le nombre de ponte
	 * 
	 * @param nombrePonte le nombre de ponte
	 */
	public void setNombrePonte(int nombrePonte) {
		if (nombrePonte < 0) {
			throw new IllegalArgumentException("nombrePonte must be positive");
		}
		this.nombrePonte = nombrePonte;
	}

	/**
	 * Définit le nombre de tetards
	 * 
	 * @param nombreTetard le nombre de tetards
	 */
	public void setNombreTetard(int nombreTetard) {
		if (nombreTetard < 0) {
			throw new IllegalArgumentException("nombreTetard must be positive");
		}
		this.nombreTetard = nombreTetard;
	}

	/**
	 * Définit la temperature de l'eau
	 * 
	 * @param temperature la temperature de l'eau
	 */
	public void setTemperature(double temperature) {
		this.temperature = temperature;
	}

	/**
	 * Définit le ciel
	 * 
	 * @param ciel le ciel
	 */
	public void setCiel(MeteoCiel ciel) {
		this.ciel = ciel;
	}

	/**
	 * Définit la température
	 * 
	 * @param temp la température
	 */
	public void setTemp(MeteoTemp temp) {
		this.temp = temp;
	}

	/**
	 * Définit le vent
	 * 
	 * @param vent le vent
	 */
	public void setVent(MeteoVent vent) {
		this.vent = vent;
	}

	/**
	 * Définit la pluie
	 * 
	 * @param pluie la pluie
	 */
	public void setPluie(MeteoPluie pluie) {
		this.pluie = pluie;
	}

	/**
	 * Définit le lieu de la vegetation
	 * 
	 * @param lieuVege le lieu de la vegetation
	 */
	public void setLieuVege(LieuVegetation lieuVege) {
		this.lieuVege = lieuVege;
	}

	/**
	 * Définit la zone humide
	 * 
	 * @param zone la zone humide
	 */
	public void setZone(ZoneHumide zone) {
		this.zone = zone;
	}

	public EspeceObservee especeObs() {
		return EspeceObservee.BATRACIEN;
	}
}