/**
 * 
 * @author coreut43
 *
 *         Type enuméré : représente les différents états sur peuvent prendre
 *         une case des plateaux INVALIDE : debug INC : état inconnu (pas encore
 *         tiré) BATEAU : on sait qu'un bateau est sur cette case EAU : on sait
 *         que de l'eau est sur cette case MANQUE : case EAU découverte après
 *         tir TOUCHE : case BATEAU découverte après tir COULE : case BATEAU
 *         découverte après tir et bateau coulé (toutes les cases TOUCHE du
 *         bateau deviennent COULE)
 */

public enum Etat {
	INVALIDE, INC, BATEAU, EAU, MANQUE, TOUCHE, COULE
};