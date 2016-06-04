/**
 *
 * @author coreut43
 *
 *         Class : Coord, objet repr�sentant des coordonn�es de tableau en 2
 *         dimensions (permet de renvoyer x et y en une seule fois dans une
 *         m�thode)
 */
public class Coord {

	private int x; // abscisse de la coordonn�e
	private int y; // ordonn�e de la coordonn�e

	/**
	 * constructeur par d�faut -> donne le Coord (0,0)
	 */
	public Coord() {
		x = 0;
		y = 0;
	}

	/**
	 * constructeur parametr� : donne le Coord(xx,yy)
	 * 
	 * @param xx
	 *            abscisse du Coord � cr�er
	 * @param yy
	 *            ordonn�e du Coord � cr�er
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
