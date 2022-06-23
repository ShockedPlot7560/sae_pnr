package modele.donnee;

/**
 * Un observateur
 */
public class Observateur {
	/** L'identifiant de l'observateur */
	private int idObservateur;
	/** le nom de famille */
	private String nom;
	/** le prenom */
	private String prenom;
	/** le nom d'utilisateur pour se connecter */
	private String utilisateur;
	/** l'hash du password */
	private String passwordHash;

	/**
	 * Constructeur de la classe Observateur
	 * 
	 * @param id       identifiant de l'observateur
	 * @param leNom    nom de l'observateur
	 * @param lePrenom prenom de l'observateur
	 * @param utilisateur nom d'utilisateur pour se connecter
	 * @param passwordHash hash du password
	 */
	public Observateur(int id, String leNom, String lePrenom, String utilisateur, String passwordHash) {
		this.setIdObservateur(id);
		this.setNom(leNom);
		this.setPrenom(lePrenom);
		this.utilisateur = utilisateur;
		this.passwordHash = passwordHash;
	}

	/**
	 * @param id l'idenfitidant de l'observateur
	 */
	public Observateur(int id) {
		this(id, "", "", "", "");
	}

	/**
	 * Retourne l'identifiant de l'observateur
	 * 
	 * @return l'identifiant de l'observateur
	 */
	public int getIdObservateur() {
		return this.idObservateur;
	}

	/**
	 * Retourne le nom de l'observateur
	 * 
	 * @return le nom de l'observateur
	 */
	public String getNom() {
		return this.nom;
	}

	/**
	 * Retourne le prénom de l'observateur
	 * 
	 * @return le prénom de l'observateur
	 */
	public String getPrenom() {
		return this.prenom;
	}

	/**
	 * Définit l'identifiant de l'observateur
	 * 
	 * @param id identifiant de l'observateur
	 */
	public void setIdObservateur(int id) {
		if (id < 0) {
			throw new IllegalArgumentException("id must be positive");
		}
		this.idObservateur = id;
	}

	/**
	 * Définit le nom de l'observateur
	 * 
	 * @param leNom le nom de l'observateur
	 */
	public void setNom(String leNom) {
		this.nom = leNom;
	}

	/**
	 * Définit le prénom de l'observateur
	 * 
	 * @param lePrenom le prénom de l'observateur
	 */
	public void setPrenom(String lePrenom) {
		if (lePrenom == null) {
			throw new IllegalArgumentException("lePrenom must be not null");
		}
		this.prenom = lePrenom;
	}

	public String toString() {
		if(this.nom != null){
			return this.prenom + " " + this.nom;
		}else{
			return this.prenom;
		}
	}

	/**
	 * @param arg0 l'observateur à tester
	 * @return true si les deux observateurs sont identiques
	 */
	public boolean equals(Observateur arg0) {
		boolean ret = true;
		if(this.idObservateur != arg0.getIdObservateur()) {
			ret = false;
		}
		if(this.nom == null && arg0.getNom() != null || this.nom != null && arg0.getNom() != null && !this.nom.equals(arg0.getNom())){
			ret = false;
		}
		if(!this.prenom.equals(arg0.getPrenom())){
			ret = false;
		}
		return ret;
	}

	/**
	 * Retourne le nom d'utilisateur pour se connecter
	 * @return le nom d'utilisateur pour se connecter
	 */
	public String getUsername(){
		return this.utilisateur;
	}

	/**
	 * Retourne le hash du password
	 * @return le hash du password
	 */
	public String getPasswordHash(){
		return this.passwordHash;
	}
}