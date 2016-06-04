/**
 *
 * @author coreut43
 *
 *         Class : Coord, objet représentant des coordonnées de tableau en 2
 *         dimensions (permet de renvoyer x et y en une seule fois dans une
 *         méthode)
 */
public class Coord {

	private int x; // abscisse de la coordonnée
	private int y; // ordonnée de la coordonnée

	/**
	 * constructeur par défaut -> donne le Coord (0,0)
	 */
	public Coord() {
		x = 0;
		y = 0;
	}

	/**
	 * constructeur parametré : donne le Coord(xx,yy)
	 * 
	 * @param xx
	 *            abscisse du Coord à créer
	 * @param yy
	 *            ordonnée du Coord à créer
	 */
	public Coord(int xx, int yy) {
		x = xx;
		y = yy;
	}

	/// Getters and Setters : ///

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

}
