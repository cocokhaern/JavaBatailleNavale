/**
 *
 * @author CorentinR
 *
 *	Interface des Joueurs, implémentée par JoueurHumain et Joueur Robot
 */
public interface Joueur {

	public void initialiser (Bateau[] flotte, int paramAuto, int paramCpu);
	public void recap(Etat etatTir, Coord coordTir);
	public Coord tirer();
	public Etat recevoir(Coord coordTir);
	public Etat resultatTir(Joueur adv, Etat result, Coord coordTir);
	public boolean testCouler(Coord coordTir);
	public int refresh(Etat result, Coord coordTir);
	public boolean victoire(); 
}
