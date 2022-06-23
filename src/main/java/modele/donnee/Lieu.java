package modele.donnee;

/**
 * Un lieu représenter par une coordonnée en lambert ou GPS
 */
public class Lieu {
	/** coordonnée X */
	private double xCoord;
	/** coordonnée Y */
	private double yCoord;
	/** est lambert */
	private boolean estLambert;

	/**
	 * @param x       la coordonnée x
	 * @param y       la coordonnée y
	 * @param lambert true si le lieu est dans le système Lambert, false sinon
	 */
	public Lieu(double x, double y, boolean lambert) {
		this.xCoord = x;
		this.yCoord = y;
		this.estLambert = lambert;
	}

	/**
	 * @param lieu le lieu
	 */
	public Lieu(Lieu lieu){
		this.xCoord = lieu.getxCoord();
		this.yCoord = lieu.getyCoord();
		this.estLambert = lieu.estLambert();
	}

	/**
	 * Retourne la coordonnée x
	 * 
	 * @return la coordonnée x
	 */
	public double getxCoord() {
		return xCoord;
	}

	/**
	 * Définit la coordonnée x
	 * 
	 * @param xCoord la coordonnée x
	 */
	public void setxCoord(double xCoord) {
		this.xCoord = xCoord;
	}

	/**
	 * Retourne la coordonnée y
	 * 
	 * @return la coordonnée y
	 */
	public double getyCoord() {
		return yCoord;
	}

	/**
	 * Définit la coordonnée y
	 * 
	 * @param yCoord la coordonnée y
	 */
	public void setyCoord(double yCoord) {
		this.yCoord = yCoord;
	}

	/**
	 * Retourne true si le lieu est dans le système Lambert, false sinon
	 * 
	 * @return true si le lieu est dans le système Lambert, false sinon
	 */
	public boolean estLambert() {
		return this.estLambert;
	}

	/**
	 * Définit si le lieu est dans le système Lambert
	 * 
	 * @param estLambert true si le lieu est dans le système Lambert, false sinon
	 */
	public void setEstLambert(boolean estLambert) {
		this.estLambert = estLambert;
	}

	/**
	 * Convertit la position du Lieu en latitude/longitude. Ne fais rien si les
	 * coordonnées sont déjà dans le système GPS.
	 * 
	 * @return true si la conversion a été faite, false si les coordonnées étaient
	 *         déjà en lat/long
	 */
	public boolean toLatLong() {
		if (!this.estLambert) {
			return false;
		}

		final double GRS80E = 0.081819191042816;
		final double LONG_0 = 3;
		final double XS = 700000;
		final double YS = 12655612.0499;
		final double n = 0.7256077650532670;
		final double C = 11754255.4261;

		double delX = this.xCoord - XS;
		double delY = this.yCoord - YS;
		double gamma = Math.atan(-delX / delY);
		double R = Math.sqrt(delX * delX + delY * delY);
		double latiso = Math.log(C / R) / n;
		double sinPhiit0 = tanh(latiso + GRS80E * atanh(GRS80E * Math.sin(1)));
		double sinPhiit1 = tanh(latiso + GRS80E * atanh(GRS80E * sinPhiit0));
		double sinPhiit2 = tanh(latiso + GRS80E * atanh(GRS80E * sinPhiit1));
		double sinPhiit3 = tanh(latiso + GRS80E * atanh(GRS80E * sinPhiit2));
		double sinPhiit4 = tanh(latiso + GRS80E * atanh(GRS80E * sinPhiit3));
		double sinPhiit5 = tanh(latiso + GRS80E * atanh(GRS80E * sinPhiit4));
		double sinPhiit6 = tanh(latiso + GRS80E * atanh(GRS80E * sinPhiit5));

		double longRad = Math.asin(sinPhiit6);
		double latRad = gamma / n + LONG_0 / 180 * Math.PI;

		double longitude = latRad / Math.PI * 180;
		double latitude = longRad / Math.PI * 180;

		this.xCoord = longitude;
		this.yCoord = latitude;

		this.setEstLambert(false);
		return true;
	}

	/**
	 * Convertit la position du Lieu en Lambert-93. Ne fais rien si les
	 * coordonnées sont déjà dans le système Lambert.
	 * 
	 * @return true si la conversion a été faite, false si les coordonnées étaient
	 *         déjà en Lambert-93
	 */
	public boolean toLambert() {
		if (this.estLambert) {
			return false;
		}

		final double C = 11754255.426096; // constante de la projection
		final double E = 0.0818191910428158; // première exentricité de l'ellipsoïde
		final double N = 0.725607765053267; // exposant de la projection
		final double XS = 700000; // coordonnées en projection du pole
		final double YS = 12655612.049876; // coordonnées en projection du pole

		final double latRad = this.yCoord / 180 * Math.PI; // latitude en rad
		final double latIso = atanh(Math.sin(latRad)) - E * atanh(E * Math.sin(latRad)); // latitude isométrique

		final double longitude = this.xCoord;
		this.xCoord = ((C * Math.exp(-N * (latIso))) * Math.sin(N * (longitude - 3) / 180 * Math.PI) + XS);
		this.yCoord = (YS - (C * Math.exp(-N * (latIso))) * Math.cos(N * (longitude - 3) / 180 * Math.PI));

		this.setEstLambert(true);
		return true;
	}

	/**
	 * @param x le nombre à calculer
	 * @return tanh de x
	 */
	private static double tanh(double x) {
		return (Math.exp(x) - Math.exp(-x)) / (Math.exp(x) + Math.exp(-x));
	}

	/**
	 * @param x le nombre à calculer
	 * @return atanh de x
	 */
	private static double atanh(double x) {
		return Math.log((1 + x) / (1 - x)) / 2;
	}
}