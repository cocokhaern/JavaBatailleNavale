/**
 * 
 * @author CorentinR
 *
 * Repr�sente une case des plateaux (Case[][]), avec plusieurs donn�es
 */
public class Case {
	
	Bateau bat;				//Bateau se trouvant sur la case				
	int typeBat;			//identifiant du bateau se trouvant sur la case
	boolean deja;			//case deja jou�e ou non
	Etat how;				//Etat de la case
	
	/**
	 * Constructeur param�tr�
	 * 
	 * @param etat Etat � initialiser sur la case
	 * @param i identifiant du bateau � initialiser sur la case
	 */
	public Case(Etat etat, int i)
	{
		how = etat;
		bat = null;
		deja= false;
		typeBat = i;
	}

	/// Get - Set /// 
	
	public Bateau getBat() {
		return bat;
	}

	public void setBat(Bateau bat) {
		this.bat = bat;
	}

	public boolean getDeja() {
		return deja;
	}

	public void setDeja(boolean deja) {
		this.deja = deja;
	}

	public Etat getHow() {
		return how;
	}

	public void setHow(Etat how) {
		this.how = how;
	}
	
	public int getTypeBat()
	{
		return typeBat;
	}

	public void setTypeBat(int typeBat)
	{
		this.typeBat = typeBat;
	}


}
