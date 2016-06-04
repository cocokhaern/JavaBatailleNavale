/**
 * * @author CorentinR
 * 
 * Représente les joueurs humains avec nom, plateau, etc. Méthodes permettant de
 * tirer, actualiser, afficher les infos connues, annoncer le résultat d'un tir,
 * etc.
 * 
 */
public class JoueurHumain implements Joueur {

    /// Champs ///
    // plateau du joueur, mis à jour avec les coups joués par l'adversaire
    private Case[][] plateau;

    // plateau de l'adversaire, inconnu, mis à jour avec les coups joués par le
    // joueur
    private Case[][] plateauAdv;

    // taille des plateau X*X
    int X;

    // nom du joueur
    private String name;

    // nombre de bateau restant à l'adversaire (permet de détecter la victoire)
    int nombreBatAdv;

    /// Méthodes ///

    /**
     * Constructeur : initalise les plateaux, demande un nom
     * 
     * @param nX
     *            entier, taille du plateau, carré
     */
    public JoueurHumain(int nX) {
	X = nX;
	nombreBatAdv = -1;

	// remplit le plateau de case EAU, sans bateau
	plateau = new Case[X][X];
	for (int i = 0; i < X; i++) {
	    for (int j = 0; j < X; j++) {
		plateau[i][j] = new Case(Etat.EAU, -1);
	    }
	}

	// remplit le plateauAdv de case INC, sans bateau
	plateauAdv = new Case[X][X];
	for (int i = 0; i < X; i++) {
	    for (int j = 0; j < X; j++) {
		plateauAdv[i][j] = new Case(Etat.INC, -1);
	    }
	}

	// demande au joueur son nom
	System.out.println("\nQuel est votre nom ? :");
	name = Jeu.sc.next();
	System.out.println("Bienvenue " + name + " !\n");
    }

    /**
     * Parcours les bateaux de la flotte et les place un à un sur le plateau
     * (méthode Bateau.placer)
     */
    public void initialiser(Bateau[] flotte, int paramAuto, int paramCpu) {
	// parcours flotte et appelle Bateau.placer
	for (int i = 0; i < flotte.length; i++) {
	    if (paramAuto == 1) {
		flotte[i].placerManuel(plateau, i, paramCpu);
		System.out.println(this.affPlateau());
	    } else if (paramAuto == 2)
		flotte[i].placerAuto(plateau, i, paramCpu);
	}
	// initialise nombreBatAdv avec le nombre réel de bateau dans la flotte
	nombreBatAdv = flotte.length;
	System.out.println("\nVoici donc votre plateau de jeu ! :\n");
	System.out.println(this.affPlateau());
	System.out.println("\nEntrez une touche pour continuer :");
	char temp = Jeu.sc.next().charAt(0);
    }

    /**
     * Affiche le dernier coup de l'adversaire avant de jouer, en informant des
     * coordonnées, du resultat du tir et du nom du bateau touché/coulé si il y
     * a lieu + affiche les plateaux mis à jour.
     * 
     * @param etatTir
     *            Etat du tir renvoyé par l'adversaire (MANQUE, TOUCHE, COULE)
     * @param coordTir
     *            coordonnées du tir de l'adversaire
     */
    public void recap(Etat etatTir, Coord coordTir) {
	if (coordTir == null)
	    System.out.print("Vous étes le premier à jouer, c'est parti !\n\n");
	else {
	    System.out.print(
		    "Votre adversaire a tiré en " + Jeu.convertNL(coordTir.getX()) + "-" + coordTir.getY() + " et ");
	    switch (etatTir) {
	    case MANQUE:
		System.out.println("n'a rien touché, ouf!");
		break;
	    case TOUCHE:
		System.out.println(
			"a touché votre " + (plateau[coordTir.getX()][coordTir.getY()].getBat()).getNom() + "!");
		break;
	    case COULE:
		System.out.println(
			"a coulé votre " + (plateau[coordTir.getX()][coordTir.getY()].getBat()).getNom() + "! Aée...");
		break;
	    default:
		System.out.println("ERROR : etatTir non valide");
		break; // debug
	    }
	    // affiche les plateaux mis à jour
	    System.out.println("\n\n" + this + "\n");
	}
    }

    /**
     * Invite le joueur à tirer. Demande des coordonnées valides et les renvoit
     * dans un Coord seulement si elle n'ont pas déjà été jouées. Boucle sinon.
     * 
     * @return Coord (x,y) contenant les coordonées x et y du tir
     */
    public Coord tirer() {
	boolean testDeja;
	int x = 0;
	int y = 0;
	do {
	    testDeja = false;
	    System.out.println(name + " : rentrez les coordonnées de la cible (ex: A6) :");
	    do {
		do {
		    System.out.println("X:");
		    x = Jeu.convertLN(Jeu.sc.next().charAt(0));
		    if (x >= X || x < 0) // validité : dans le plateau
			System.out.println("Entrée incorrecte, recommencez :\n");
		} while (x >= X || x < 0);

		y = Jeu.integerExceptionMethod("Y:");

		// validité : dans le plateau
		if (y >= X || y < 0)
		    System.out.println("Entrée incorrecte, recommencez :\n");
	    } while (y >= X || y < 0 || x >= X || x < 0);

	    // test si le coup n'a pas deja été joué, boucle sinon
	    if (plateauAdv[x][y].getDeja() == true) {
		System.out.println("Vous avez déjà joué ce coup, recommencez :");
		testDeja = true;
	    }
	} while (testDeja == true);

	Coord coordTir = new Coord(x, y);
	return coordTir;
    }

    /**
     * Reçoit les coordonnées du tir adverse et renvoit s'il y a un bateau ou
     * non. ("accesseur" évitant d'accéder directement aux champs de l'autre
     * joueurs)
     * 
     * @param coordTir
     *            coordonnées du tir adverse (return de adv.tirer)
     * 
     * @return Etat BATEAU ou EAU de la case visée par l'adversaire (param de
     *         adv.resultatTir)
     */
    public Etat recevoir(Coord coordTir) {
	return plateau[coordTir.getX()][coordTir.getY()].getHow();
    }

    /**
     * Informe le joueur du résultat de son tir, teste si le bateau est coulé
     * (appelle adv.testCouler), et actualise son plateauAdv et le plateau de
     * l'adversaire (appelle adv.refresh)
     * 
     * @param adv
     *            Joueur adverse (permet d'appeler adv.refresh)
     * @param result
     *            Etat EAU ou BATEAU de la case visée (return de adv.recevoir)
     * @param coordTir
     *            coordonnées du tir adverse (return de adv.tirer)
     * 
     * @return Etat MANQUE, TOUCHE ou COULE (param de adv.refresh et adv.recap)
     */
    public Etat resultatTir(Joueur adv, Etat result, Coord coordTir) {
	System.out.print("\nTir en " + Jeu.convertNL(coordTir.getX()) + "-" + coordTir.getY() + " : ");
	Etat etatRefresh = Etat.INVALIDE;

	// si Etat.EAU : une seule possibilité, pas de test couler, etc
	if (result == Etat.EAU) {
	    System.out.println(" raté ! ...bloup...");
	    etatRefresh = Etat.MANQUE;
	    // met le joueur à jour
	    plateauAdv[coordTir.getX()][coordTir.getY()].setHow(etatRefresh);
	    // met à jour l'adversaire
	    adv.refresh(etatRefresh, coordTir);
	}
	// si Etat.BATEAU : test couler -> 2 possibilités
	else if (result == Etat.BATEAU) {
	    boolean couler = adv.testCouler(coordTir); // test si le bateau
	    // touché est coulé
	    // (pV=0)
	    if (couler == false) // si NON -> TOUCHE
	    {
		System.out.println(" touché ! ...BOOM!");
		etatRefresh = Etat.TOUCHE;
		plateauAdv[coordTir.getX()][coordTir.getY()].setHow(etatRefresh); // met
		// à
		// jour
		// le
		// joueur
		int type = adv.refresh(etatRefresh, coordTir); // met à jour
		// l'adversaire
		// et renvoit
		// l'identifiant
		// du bateau
		// touché
		plateauAdv[coordTir.getX()][coordTir.getY()].setTypeBat(type); // met
		// à
		// jour
		// le
		// joueur
		// :
		// type
		// de
		// bateau
		// touché
	    }
	    if (couler == true) // si OUI -> COULE
	    {
		System.out.println(" touché coulé! ...BOOOOOM!!!");
		etatRefresh = Etat.COULE;
		int type = adv.refresh(etatRefresh, coordTir); // idem
		plateauAdv[coordTir.getX()][coordTir.getY()].setTypeBat(type); // idem

		for (int i = 0; i < X; i++) // parcours tout le plateau et passe
		// toutes les cases TOUCHE du bateau
		// coulé en COULE
		{
		    for (int j = 0; j < X; j++) {
			if (plateauAdv[i][j].getTypeBat() == type)
			    plateauAdv[i][j].setHow(etatRefresh);

		    }
		}
		nombreBatAdv--; // décrémente le nombre de bateaux restant à
		// l'adversaire
	    }
	} else
	    System.out.println(" ERROR : case deja jouée ou état invalide, probleme de test \"deja\""); // debug

	plateauAdv[coordTir.getX()][coordTir.getY()].setDeja(true); // =>cette
	// case ne
	// pourra
	// plus etre
	// jouée
	System.out.println(this);
	return etatRefresh;
    }

    /**
     * Test si le bateau occupant la case visée a 0 pV (coulé) ou non (seulement
     * touché)
     * 
     * @param coordTir
     *            coordonnées de la case visée par l'adversaire (return de
     *            adv.tirer)
     * 
     * @return booléen : true = coulé, false = pas coulé
     */
    public boolean testCouler(Coord coordTir) {
	return plateau[coordTir.getX()][coordTir.getY()].getBat().encaisser();
    }

    /**
     * Actualise le plateau de l'adversaire en meme temps que celui du joueur
     * (appelle par adv.resultatTir) et renvoit, si touché, l'identifiant du
     * bateau touché
     * 
     * @param result
     *            Etat MANQUE, TOUCHE ou COULE du coup de l'adversaire
     * @param coordTir
     *            coordonnées de la case visée par l'adversaire (return de
     *            adv.tirer)
     * 
     * @return entier informant, si touché/coulé, de l'identifiant du bateau
     *         visé
     */
    public int refresh(Etat result, Coord coordTir) {
	plateau[coordTir.getX()][coordTir.getY()].setDeja(true);
	if (result == Etat.COULE) {
	    for (int i = 0; i < X; i++) {
		for (int j = 0; j < X; j++) {
		    if (plateau[i][j].getTypeBat() == plateau[coordTir.getX()][coordTir.getY()].getTypeBat())
			plateau[i][j].setHow(result);
		}
	    }
	} else
	    plateau[coordTir.getX()][coordTir.getY()].setHow(result);

	return plateau[coordTir.getX()][coordTir.getY()].getTypeBat();
    }

    /**
     * Test si le joueur a gagné ou non (nombreBatAdv=0), l'informe dans ce cas,
     * et permet de stopper la partie
     * 
     * @return booléen true si le joueur a gagné : permet de sortir de la boucle
     *         de jeu
     */
    public boolean victoire() {
	if (nombreBatAdv == 0) {
	    System.out.println("C'est gagné ! C'est gagné ! \né*o Bravo " + name + "!!! o*é \n\n Vous avez gagné en "
		    + Jeu.tour + " tours.");
	    return true;
	}

	if (nombreBatAdv > 0)
	    return false;

	else {
	    System.out.println("ERROR : nombreBatAdv<0 !");
	    return true;
	}
    }

    /**
     * toString renvoyant le plateau inconnu de l'adversaire et les coups joués
     * ET le plateau du joueur avec les coups de l'adversaire, côte à côte avec
     * affichage dynamique en fonction de la taille du plateau
     */
    public String toString() {
	String aff = "";

	aff = "\nTour n°" + Jeu.tour + " : \n";
	aff += "Adversaire:  ";
	for (int i = 4; i < X; i++) // les boucles ici permettent l'affichage
	// dynamique,
	{ // le bon nombre d'espaces, etc en fonction de X
	    aff += "   ";
	}

	aff += "           ";
	aff += "Vous:\n ";

	for (int i = 0; i < Math.min(X, 10); i++) {
	    aff += "  " + i;
	}
	for (int i = 10; i < X; i++) {
	    aff += " " + i;
	}
	aff += "            ";
	for (int i = 0; i < Math.min(X, 10); i++) {
	    aff += "  " + i;
	}
	for (int i = 10; i < X; i++) {
	    aff += " " + i;
	}
	for (int i = 0; i < X; i++) // plateauAdv
	{
	    aff += "\n" + Jeu.convertNL(i) + " ";
	    for (int j = 0; j < X; j++) {
		switch (plateauAdv[i][j].getHow()) {
		case INC:
		    aff += " . ";
		    break;
		case MANQUE:
		    aff += " o ";
		    break;
		case BATEAU:
		    aff += " x ";
		    break;
		case TOUCHE:
		    aff += " T ";
		    break;
		case COULE:
		    aff += " C ";
		    break;
		default:
		    aff += " ? ";
		    break;
		}
	    }
	    aff += "          ";
	    aff += Jeu.convertNL(i) + " ";
	    for (int j = 0; j < X; j++) {
		switch (plateau[i][j].getHow()) // plateau
		{
		case EAU:
		    aff += " . ";
		    break;
		case MANQUE:
		    aff += " o ";
		    break;
		case BATEAU:
		    aff += " x ";
		    break;
		case TOUCHE:
		    aff += " T ";
		    break;
		case COULE:
		    aff += " C ";
		    break;
		default:
		    aff += " ? ";
		    break;
		}
	    }
	}

	aff += "\n\n";

	return aff;
    }

    /**
     * second "toString" n'affichant que le plateau du joueur, utilisé pendant
     * le placement des bateaux
     * 
     * @return String pour etre affiché à l'écran
     */
    public String affPlateau() {
	String aff = " ";
	for (int i = 0; i < Math.min(X, 10); i++) {
	    aff += "  " + i;
	}
	for (int i = 10; i < X; i++) {
	    aff += " " + i;
	}
	for (int i = 0; i < plateau.length; i++) {
	    aff += "\n" + Jeu.convertNL(i) + " ";
	    for (int j = 0; j < plateau[0].length; j++) {
		switch (plateau[i][j].getHow()) {
		case EAU:
		    aff += " . ";
		    break;
		case MANQUE:
		    aff += " o ";
		    break;
		case BATEAU:
		    aff += " x ";
		    break;
		case TOUCHE:
		    aff += " T ";
		    break;
		case COULE:
		    aff += " C ";
		    break;
		default:
		    aff += " ? ";
		    break;
		}
	    }
	}

	return aff;
    }

    /// Get - Set ///

    public String getName() // permet d'accéder au nom du joueur dans Jeu, pour
    // demander clairement si le joueur nommé est prét
    { // ceci évite les confusions et le dévoilement non volontaire du plateau
      // adverse
	return name;
    }

}
