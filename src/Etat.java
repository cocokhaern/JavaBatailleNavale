/**
 * 
 * @author coreut43
 *
 *         Type enum�r� : repr�sente les diff�rents �tats sur peuvent prendre
 *         une case des plateaux INVALIDE : debug INC : �tat inconnu (pas encore
 *         tir�) BATEAU : on sait qu'un bateau est sur cette case EAU : on sait
 *         que de l'eau est sur cette case MANQUE : case EAU d�couverte apr�s
 *         tir TOUCHE : case BATEAU d�couverte apr�s tir COULE : case BATEAU
 *         d�couverte apr�s tir et bateau coul� (toutes les cases TOUCHE du
 *         bateau deviennent COULE)
 */

public enum Etat {
	INVALIDE, INC, BATEAU, EAU, MANQUE, TOUCHE, COULE
};