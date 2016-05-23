import java.util.Scanner;

	
/**
 * @author CorentinR
 *
 * Classe qui contient la méthode main, et qui gère tout le déroulement du jeu jusqu'à sa fin (victoire d'un joueur)
 * 
 * 
 *
 */
public class Jeu {
	
	/// Champs ///
	
	public static Scanner sc = new Scanner(System.in);		//scanner
	public static int tour=0;								//compteur de tour
	public static Coord tir=null;							//coordonnées du tir en cours
	public static Etat etatTir = Etat.INVALIDE;				//etat du tir en cours (EAU/BATEAU , puis MANQUE/TOUCHE/COULE)
	public static char ready;								//permet uniquement de demander au joueur s'il est pret, valeur insignifiante

	
	/// Méthodes /// 
	
	/**
	 * Permet de convertir un nombre de 0 à 25 en sa lettre d'alphabet correspondante (ordre alphanumérique, ex : 24=Y)
	 * 
	 * @param a entier, chiffre à convertir
	 * 
	 * @return char, lettre correspondante 
	 */
	public static char convertNL(int a)
	{
		char b='?';
		switch(a)
		{
			case 0 : b= 'A'; break;
			case 1 : b= 'B';break;
			case 2 : b= 'C';break;
			case 3 : b= 'D';break;
			case 4 : b= 'E';break;
			case 5 : b= 'F';break;
			case 6 : b= 'G';break;
			case 7 : b= 'H';break;
			case 8 : b= 'I';break;
			case 9 : b= 'J';break;
			case 10 : b= 'K';break;
			case 11 : b= 'L';break;
			case 12 : b= 'M';break;
			case 13 : b= 'N';break;
			case 14 : b= 'O';break;
			case 15 : b= 'P';break;
			case 16 : b= 'Q';break;
			case 17 : b= 'R';break;
			case 18 : b= 'S';break;
			case 19 : b= 'T';break;
			case 20 : b= 'U';break;
			case 21 : b= 'V';break;
			case 22 : b= 'W';break;
			case 23 : b= 'X';break;
			case 24 : b= 'Y';break;
			case 25 : b= 'Z';break;
		}
		return b;
	}
	
	
	/**
	 * Permet de convertir une lettre de A à Z en sa place dans l'ordre alphanumérique (ex : W=23)
	 * 
	 * @param a char, lettre à convertir
	 * 
	 * @return entier, entier correspondant 
	 */
	public static int convertLN(char a)
	{
		int b=-1;
		String C = "";
		C+=a;
		C=C.toUpperCase();  //permet de prendre en compte minuscule et majuscule (minimise les bugs de saisie clavier)
		a=C.charAt(0);
		switch(a)
		{
			case 'A' : b= 0;break;
			case 'B' : b= 1;break;
			case 'C' : b= 2;break;
			case 'D' : b= 3;break;
			case 'E' : b= 4;break;
			case 'F' : b= 5;break;
			case 'G' : b= 6;break;
			case 'H' : b= 7;break;
			case 'I' : b= 8;break;
			case 'J' : b= 9;break;
			case 'K' : b= 10;break;
			case 'L' : b= 11;break;
			case 'M' : b= 12;break;
			case 'N' : b= 13;break;
			case 'O' : b= 14;break;
			case 'P' : b= 15;break;
			case 'Q' : b= 16;break;
			case 'R' : b= 17;break;
			case 'S' : b= 18;break;
			case 'T' : b= 19;break;
			case 'U' : b= 20;break;
			case 'V' : b= 21;break;
			case 'W' : b= 22;break;
			case 'X' : b= 23;break;
			case 'Y' : b= 24;break;
			case 'Z' : b= 25;break;
			case '+' : b= -1;break;
			case '=' : b= -2;break;
		}
		return b;
	}

	
	/**
	 * Méthode appelée en premier dans le main, au lancement du programme.
	 * Titre, puis paramétrage de la partie
	 * 
	 * cpuOrPlayer -> joueur contre joueur ou joueur contre ordinateur
	 * autoOrNor -> placement des bateaux aléatoire ou manuel
	 * basicOrAdvanced -> regles de jeu basique (bateaux droit qui ne se touchent pas) ou avancées
	 * typeFlotte -> selection d'un groupe de Bateau parmis ceux proposés
	 * X -> taille de la grille (10 à 26)
	 * 
	 * 
	 * @return tableau d'entier des paramètres de la partie, taille fixe
	 */
	public static int[] launch()
	{   System.out.println(" _______________________________________________ \n" +        //titre d'ouverture
	                       "|                                               |\n" +
	                       "|  °*¤  Bienvenue dans la Bataille Navale  ¤*°  |\n" +
	                       "|                                               |\n" +
	                       "|       Conception : Corentin REUT - 2011       |\n" +
	                       "|             Pour : l'EIDD - Paris7            |\n" +
	                       "|                                               |\n" +
	                       "|                °*¤  Bon jeu!  ¤*°             |\n" +
	                       "|_______________________________________________|");
		System.out.println("\n\nRentrez -1 durant le parametrage pour accéder à l'aide complet\n");

		int cpuOrPlayer=0;								//param 0 : jouer contre un humain ou l'ordinateur
		do{
			System.out.println("\nVous désirez jouer :\n  1. contre l'ordinateur\n  2. joueur contre joueur");
			cpuOrPlayer = sc.nextInt();
			if (cpuOrPlayer!=1 && cpuOrPlayer!=2)
				System.out.println("Entrée incorrecte, recommencez (1 ou 2 ?) :");
		}while(cpuOrPlayer!=1 && cpuOrPlayer!=2);
		
		int autoOrNot=0;								//param 1 : placement automatique ou manuel des bateaux
		do{
			System.out.println("\nPlacement des bateaux :\n  1. manuel\n  2. automatique");
			autoOrNot = sc.nextInt();
			if (autoOrNot!=1 && autoOrNot!=2)
				System.out.println("Entrée incorrecte, recommencez (1 ou 2 ?) :");
		}while(autoOrNot!=1 && autoOrNot!=2);
		
		int basicOrAdvanced=0;							//param 2 : regles basiques ou avancées
		if(cpuOrPlayer==1)					//si jeu contre l'ordinateur, les regles basiques sont imposées par soucis d'une IA cohérente
			basicOrAdvanced=1;
		if(cpuOrPlayer==2)
		{
			do{
				System.out.println("\nQuelle règle de jeu ? : :\n  1. classiques (bateaux droits qui ne se touchent pas)\n  2. avancées (bateaux quelconques qui peuvent se toucher");
				basicOrAdvanced = sc.nextInt();
				if (basicOrAdvanced!=1 && basicOrAdvanced!=2 && basicOrAdvanced!=-1)
					System.out.println("Entrée incorrecte, recommencez (1 ou 2 ?) :");
			}while(basicOrAdvanced!=1 && basicOrAdvanced!=2);
		}
		
		int typeFlotte=0;								// param 3 : type de flotte
		System.out.println("\nAvec quelle flotte voulez-bous jouer ? \n" +
						     "  1- Classique  (1 porte-avion, 2 croiseurs, 2 contre-torpilleurs, 2 torpilleurs) \n" + 
						     "  2- Classique Rapide (1 porte-avion, 1 croiseur, 1 contre-torpilleur, 1 torpilleur) \n" +
						     "  3- Classique 3Torpilleurs (1 porte-avion, 1 croiseur, 1 contre-torpilleur, 3 torpilleurs)");
		if (basicOrAdvanced==1)				//si regles basiques, seulement 3 flottes (avec '.' pour éviter la tangence des bateaux)
			do
			{	
				typeFlotte = sc.nextInt();
				if(typeFlotte<1 || typeFlotte>3)
					System.out.println("Entrée incorrecte, recommencez:\n");
			}while(typeFlotte<1 || typeFlotte>3);
		if (basicOrAdvanced==2)			 	//si regles avancées, 6 flottes (sans '.' car tangence possible)
		{
			do
			{	
				System.out.println("  4- Variante Navale  (1 destroyer, 1 porte-DCA, 1 croiseur, 1 torpilleur, 1 contre-torpilleur) \n" + 
								   "  5- Variante Spatiale (1 vaisseau amiral, 2 speeders, 1 bombardier plasma, 1 transporteur) \n" +
								   "  6- Variante Tetris (1 T-ship, 1 I-ship, 1 O-ship, 1 S-ship, 2 L-ship)\n");							
				typeFlotte = sc.nextInt();
				if(typeFlotte<1|| typeFlotte>6)
					System.out.println("Entrée incorrecte, recommencez:\n");
			}while(typeFlotte<1 || typeFlotte>6);
		}
		
		int X=10;								//param 4 : taille de la grille (de 10 à 26)
		do
		{
			System.out.println("\nLongueur de grille ? (de 10 à 26) :");
			X = sc.nextInt();
			if(X<10 || X>26)
				System.out.println("Entrée incorrecte, recommencez:\n");
		}while(X<10 || X>26);
		
		int[] param = {cpuOrPlayer, autoOrNot, X, typeFlotte, basicOrAdvanced}; //liste complètes des paramètres à renvoyer
		return param;	
	}

	
	/**
	 * Crée les deux flottes (tableau de Bateau) qui seront utilisés pour la partie, suivant les paramètres de l'utilisateur
	 * allège le code de launch() et permet d'initialiser uniquement les bateaux qui seront joués
	 * 
	 * @param paramCpu entier, 1 si playerVScpu, 2 si playerVSplayer
	 * @param paramFlotte entier, détermine la flotte à initialiser
	 * @return les deux flottes identiques suivant les paramètres en entrée
	 */
	public static Bateau[][] creationFlotte(int paramRegle, int paramFlotte)
	{

		
		if (paramRegle==1)       		// jeu contre l'ordinateur
		{							//les '.' permettent d'éviter la tangence des bateaux et n'apparaissent pas sur les plateaux
			if (paramFlotte==1)
			{
				char[][] tab1 = {{' ','.','.','.','.','.',' '},{'.','X','X','X','X','X','.'},{' ','.','.','.','.','.',' '}};
				Bateau bat1 = new Bateau("porte-avion", 5, tab1);
				Bateau bat12 = new Bateau("porte-avion", 5, tab1);
				char[][] tab2 = {{' ','.','.','.','.',' '},{'.','X','X','X','X','.'},{' ','.','.','.','.',' '}};
				Bateau bat2 = new Bateau("croiseur1", 4, tab2);
				Bateau bat22 = new Bateau("croiseur1", 4, tab2);
				char[][] tab3 = {{' ','.','.','.','.',' '},{'.','X','X','X','X','.'},{' ','.','.','.','.',' '}};
				Bateau bat3 = new Bateau("croiseur2", 4, tab3);
				Bateau bat32 = new Bateau("croiseur2", 4, tab3);
				char[][] tab4 = {{' ','.','.','.',' '},{'.','X','X','X','.'},{' ','.','.','.',' '}};
				Bateau bat4 = new Bateau("contre-torpilleur", 3, tab4);
				Bateau bat42 = new Bateau("contre-torpilleur", 3, tab4);
				char[][] tab5 =  {{' ','.','.','.',' '},{'.','X','X','X','.'},{' ','.','.','.',' '}};
				Bateau bat5 = new Bateau("contre-torpilleur", 3, tab5);
				Bateau bat52 = new Bateau("contre-torpilleur", 3, tab5);
				char[][] tab6 =  {{' ','.','.',' '},{'.','X','X','.'},{' ','.','.',' '}};
				Bateau bat6 = new Bateau("torpilleur", 2, tab6);
				Bateau bat62 = new Bateau("torpilleur", 2, tab6);
				char[][] tab7 =  {{' ','.','.',' '},{'.','X','X','.'},{' ','.','.',' '}};
				Bateau bat7 = new Bateau("torpilleur", 2, tab7);
				Bateau bat72 = new Bateau("torpilleur", 2, tab7);
				Bateau[][] flottes = {{bat1, bat2, bat3, bat4, bat5, bat6, bat7},{bat12, bat22, bat32, bat42, bat52, bat62, bat72}};
				return flottes;
			}
			if (paramFlotte==2)
			{
				char[][] tab1 = {{' ','.','.','.','.','.',' '},{'.','X','X','X','X','X','.'},{' ','.','.','.','.','.',' '}};
				Bateau bat1 = new Bateau("porte-avion", 5, tab1);
				Bateau bat12 = new Bateau("porte-avion", 5, tab1);
				char[][] tab2 = {{' ','.','.','.','.',' '},{'.','X','X','X','X','.'},{' ','.','.','.','.',' '}};
				Bateau bat2 = new Bateau("croiseur", 4, tab2);
				Bateau bat22 = new Bateau("croiseur", 4, tab2);
				char[][] tab3 = {{' ','.','.','.',' '},{'.','X','X','X','.'},{' ','.','.','.',' '}};
				Bateau bat3 = new Bateau("contre-torpilleur", 3, tab3);
				Bateau bat32 = new Bateau("contre-torpilleur", 3, tab3);
				char[][] tab4 =  {{' ','.','.',' '},{'.','X','X','.'},{' ','.','.',' '}};
				Bateau bat4 = new Bateau("torpilleur", 2, tab4);
				Bateau bat42 = new Bateau("torpilleur", 2, tab4);
				Bateau[][] flottes = {{bat1, bat2, bat3, bat4},{bat12, bat22, bat32, bat42}};
				return flottes;
			}
			if (paramFlotte==3)
			{
				char[][] tab1 = {{' ','.','.','.','.','.',' '},{'.','X','X','X','X','X','.'},{' ','.','.','.','.','.',' '}};
				Bateau bat1 = new Bateau("porte-avion", 5, tab1);
				Bateau bat12 = new Bateau("porte-avion", 5, tab1);
				char[][] tab2 = {{' ','.','.','.','.',' '},{'.','X','X','X','X','.'},{' ','.','.','.','.',' '}};
				Bateau bat2 = new Bateau("croiseur", 4, tab2);
				Bateau bat22 = new Bateau("croiseur", 4, tab2);
				char[][] tab3 = {{' ','.','.','.',' '},{'.','X','X','X','.'},{' ','.','.','.',' '}};
				Bateau bat3 = new Bateau("contre-torpilleur", 3, tab3);
				Bateau bat32 = new Bateau("contre-torpilleur", 3, tab3);
				char[][] tab4 =  {{' ','.','.',' '},{'.','X','X','.'},{' ','.','.',' '}};
				Bateau bat4 = new Bateau("torpilleur1", 2, tab4);
				Bateau bat42 = new Bateau("torpilleur1", 2, tab4);
				char[][] tab5 =  {{' ','.','.',' '},{'.','X','X','.'},{' ','.','.',' '}};
				Bateau bat5 = new Bateau("torpilleur2", 1, tab5);
				Bateau bat52 = new Bateau("torpilleur2", 1, tab5);
				char[][] tab6 =  {{' ','.','.',' '},{'.','X','X','.'},{' ','.','.',' '}};
				Bateau bat6 = new Bateau("torpilleur3", 1, tab6);
				Bateau bat62 = new Bateau("torpilleur3", 1, tab6);
				Bateau[][] flottes = {{bat1, bat2, bat3, bat4, bat5, bat6},{bat12, bat22, bat32, bat42, bat52, bat62}};
				return flottes;
			}
		}
		if (paramRegle==2)	   // jeu joueur contre joueur
		{
			if (paramFlotte==1)
			{
				char[][] tab1 = {{'X','X','X','X','X'}};
				Bateau bat1 = new Bateau("porte-avion", 5, tab1);
				Bateau bat12 = new Bateau("porte-avion", 5, tab1);
				char[][] tab2 = {{'X','X','X','X'}};
				Bateau bat2 = new Bateau("croiseur1", 4, tab2);
				Bateau bat22 = new Bateau("croiseur1", 4, tab2);
				char[][] tab3 = {{'X','X','X','X'}};
				Bateau bat3 = new Bateau("croiseur2", 4, tab3);
				Bateau bat32 = new Bateau("croiseur2", 4, tab3);
				char[][] tab4 = {{'X','X','X'}};
				Bateau bat4 = new Bateau("contre-torpilleur", 3, tab4);
				Bateau bat42 = new Bateau("contre-torpilleur", 3, tab4);
				char[][] tab5 = {{'X','X','X'}};
				Bateau bat5 = new Bateau("contre-torpilleur", 3, tab5);
				Bateau bat52 = new Bateau("contre-torpilleur", 3, tab5);
				char[][] tab6 = {{'X','X'}};
				Bateau bat6 = new Bateau("torpilleur", 2, tab6);
				Bateau bat62 = new Bateau("torpilleur", 2, tab6);
				char[][] tab7 = {{'X','X'}};
				Bateau bat7 = new Bateau("torpilleur", 2, tab7);
				Bateau bat72 = new Bateau("torpilleur", 2, tab7);
				Bateau[][] flottes = {{bat1, bat2, bat3, bat4, bat5, bat6, bat7},{bat12, bat22, bat32, bat42, bat52, bat62, bat72}};
				return flottes;
			}
			if (paramFlotte==2)
			{
				char[][] tab1 = {{'X','X','X','X','X'}};
				Bateau bat1 = new Bateau("porte-avion", 5, tab1);
				Bateau bat12 = new Bateau("porte-avion", 5, tab1);
				char[][] tab2 = {{'X','X','X','X'}};
				Bateau bat2 = new Bateau("croiseur", 4, tab2);
				Bateau bat22 = new Bateau("croiseur", 4, tab2);
				char[][] tab3 = {{'X','X','X'}};
				Bateau bat3 = new Bateau("contre-torpilleur", 3, tab3);
				Bateau bat32 = new Bateau("contre-torpilleur", 3, tab3);
				char[][] tab4 = {{'X','X'}};
				Bateau bat4 = new Bateau("torpilleur", 2, tab4);
				Bateau bat42 = new Bateau("torpilleur", 2, tab4);
				Bateau[][] flottes = {{bat1, bat2, bat3, bat4},{bat12, bat22, bat32, bat42}};
				return flottes;
			}
			if (paramFlotte==3)
			{
				char[][] tab1 = {{'X','X','X','X','X'}};
				Bateau bat1 = new Bateau("porte-avion", 5, tab1);
				Bateau bat12 = new Bateau("porte-avion", 5, tab1);
				char[][] tab2 = {{'X','X','X','X'}};
				Bateau bat2 = new Bateau("croiseur", 4, tab2);
				Bateau bat22 = new Bateau("croiseur", 4, tab2);
				char[][] tab3 = {{'X','X','X'}};
				Bateau bat3 = new Bateau("contre-torpilleur", 3, tab3);
				Bateau bat32 = new Bateau("contre-torpilleur", 3, tab3);
				char[][] tab4 = {{'X','X'}};
				Bateau bat4 = new Bateau("torpilleur", 2, tab4);
				Bateau bat42 = new Bateau("torpilleur", 2, tab4);
				char[][] tab5 = {{'X'}};
				Bateau bat5 = new Bateau("drone espion1", 1, tab5);
				Bateau bat52 = new Bateau("drone espion1", 1, tab5);
				char[][] tab6 = {{'X'}};
				Bateau bat6 = new Bateau("drone espion2", 1, tab6);
				Bateau bat62 = new Bateau("drone espion2", 1, tab6);
				Bateau[][] flottes = {{bat1, bat2, bat3, bat4, bat5, bat6},{bat12, bat22, bat32, bat42, bat52, bat62}};
				return flottes;
			}
			if (paramFlotte==4)
			{
				char[][] tab1 = {{' ','X',' '},{' ','X',' '},{'X','X','X'}};
				Bateau bat1 = new Bateau("destroyer", 5, tab1);
				Bateau bat12 = new Bateau("destroyer", 5, tab1);
				char[][] tab2 = {{' ','X','X'},{'X','X','X'}};
				Bateau bat2 = new Bateau("porte-DCA", 5, tab2);
				Bateau bat22 = new Bateau("porte-DCA", 5, tab2);
				char[][] tab3 = {{'X','X','X','X'}};
				Bateau bat3 = new Bateau("croiseur", 4, tab3);
				Bateau bat32 = new Bateau("croiseur", 4, tab3);
				char[][] tab4 = {{'X','X'}};
				Bateau bat4 = new Bateau("torpilleur", 2, tab4);
				Bateau bat42 = new Bateau("torpilleur", 2, tab4);
				char[][] tab5 = {{'X','X','X'}};
				Bateau bat5 = new Bateau("contre-torpilleur", 3, tab5);
				Bateau bat52 = new Bateau("contre-torpilleur", 3, tab5);
				Bateau[][] flottes = {{bat1, bat2, bat3, bat4, bat5},{bat12, bat22, bat32, bat42, bat52}};
				return flottes;
			}
			if (paramFlotte==5)
			{
				char[][] tab1 = {{' ','X',' '},{'X','X','X'},{' ','X',' '}};
				Bateau bat1 = new Bateau("vaisseau amiral", 5, tab1);
				Bateau bat12 = new Bateau("vaisseau amiral", 5, tab1);
				char[][] tab2 = {{'X',' ','X'},{' ','X',' '},{' ','X',' '}};
				Bateau bat2 = new Bateau("speeder1", 4, tab2);
				Bateau bat22 = new Bateau("speeder1", 4, tab2);
				char[][] tab3 = {{'X',' ','X'},{' ','X',' '},{' ','X',' '}};
				Bateau bat3 = new Bateau("speeder2", 4, tab3);
				Bateau bat32 = new Bateau("speeder2", 4, tab3);
				char[][] tab4 = {{' ','X',' '},{'X','X','X'},{'X',' ','X'}};
				Bateau bat4 = new Bateau("bombardier plasma", 6, tab4);
				Bateau bat42 = new Bateau("bombardier plasma", 6, tab4);
				char[][] tab5 = {{'X',' ',' ','X'},{' ','X','X',' '}};
				Bateau bat5 = new Bateau("transporteur", 4, tab5);
				Bateau bat52 = new Bateau("transporteur", 4, tab5);
				Bateau[][] flottes = {{bat1, bat2, bat3, bat4, bat5},{bat12, bat22, bat32, bat42, bat52}};
				return flottes;
			}
			if (paramFlotte==6)
			{
				char[][] tab1 = {{' ','X',' '},{'X','X','X'}};
				Bateau bat1 = new Bateau("T-ship", 4, tab1);
				Bateau bat12 = new Bateau("T-ship", 4, tab1);
				char[][] tab2 = {{'X','X','X','X'}};
				Bateau bat2 = new Bateau("I-ship", 4, tab2);
				Bateau bat22 = new Bateau("I-ship", 4, tab2);
				char[][] tab3 = {{'X','X'},{'X','X'}};
				Bateau bat3 = new Bateau("O-ship", 4, tab3);
				Bateau bat32 = new Bateau("O-ship", 4, tab3);
				char[][] tab4 = {{' ','X','X'},{'X','X',' '}};
				Bateau bat4 = new Bateau("S-ship", 4, tab4);
				Bateau bat42 = new Bateau("S-ship", 4, tab4);
				char[][] tab5 = {{' ',' ','X'},{'X','X','X'}};
				Bateau bat5 = new Bateau("L-ship", 4, tab5);
				Bateau bat52 = new Bateau("L-ship", 4, tab5);
				Bateau[][] flottes = {{bat1, bat2, bat3, bat4, bat5},{bat12, bat22, bat32, bat42, bat52}};
				return flottes;
			}
		}
		
		return null;	
	}
	
	
	/**
	 *  Gère un tour de partie complet, se déroulant comme suit :
	 * 		*Incrémentation du nombre de tour
	 * 		*Premier joueur : 
	 * 			-confirmation pour jouer
	 * 			-information du tir précédent de l'adversaire (plateaux)
	 * 			-invite le joueur à tirer
	 * 			-traitement du tir (recevoir(encaisser), etc)
	 * 			-résultat du tir (plateaux)
	 * 			SI victoire -> return
	 * 		*Deuxième joueur : idem
	 * 
	 * @param j1 Joueur, premier joueur humain à jouer
	 * @param j2 Joueur, deuxieme joueur humain à jouer
	 * @return true si la partie est finie, false sinon (->detecteur de fin de partie)
	 */
	public static boolean tour(Joueur j1, Joueur j2)
	{
		j1.recap(etatTir, tir);
		tir = j1.tirer();
		etatTir = j2.recevoir(tir);
		etatTir = j1.resultatTir(j2, etatTir, tir);
		if (j1.victoire()==true)
			return true;
		System.out.println("Continuer ?");
		ready = sc.next().charAt(0);
		
		return false;
	}
	
	
	/// MAIN ///
	
	/**
	 * Méthode main : gère tout le déroulement du jeu : 
	 * 		* intro et paramétrage + création des flottes (launch() et creationFlotte())
	 * 		* tour de jeu en boucle while jusqu'à la victoire			
	 */
	public static void main(String args[])
	{
		
	//lancement (parametrage et création des flottes)
		int[] param = launch();
		int X = param[2];
		Bateau[][] flottes = creationFlotte(param[4],param[3]);

	//Mode Joueur contre Ordinateur
		if(param[0]==1)
		{
			System.out.println("\nMode 1 - joueur contre ordinateur :\n");
			System.out.print("Joueur inconnu : ");
			Joueur j1 = new JoueurHumain(X);
			j1.initialiser(flottes[0], param[1], param[4]);
			Joueur j2 = new JoueurRobot(X);
			j2.initialiser(flottes[1], param[1], param[4]);
			System.out.println("\nBattle ... FIGHT!!!\n");
			
			boolean vic=false;
			do{
				tour++; 
				vic = tour(j1,j2);
				if(vic==false)
					vic = tour(j2,j1);
			}while(vic==false);			
		}
		
	// Mode Joueur contre Joueur
		if(param[0]==2)
		{
			System.out.println("\nMode 2 - joueur contre joueur :\n");
			System.out.print("Joueur 1 : ");
			Joueur j1 = new JoueurHumain(X);
			System.out.println("\nA présent, placez vos bateaux sur la grille " + X + "-" + X);
			j1.initialiser(flottes[0], param[1], param[4]);
			System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
			System.out.print("Joueur 2 : ");
			Joueur j2 = new JoueurHumain(X);
			System.out.println("\nA présent, placez vos bateaux sur la grille " + X + "-" + X);
			j2.initialiser(flottes[1], param[1], param[4]);
	
			boolean vic=false;
			do{
				tour++; 
				System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
				System.out.println("Prêt à jouer " + ((JoueurHumain)j1).getName() + "?");
				ready = sc.next().charAt(0);
				vic = tour(j1,j2);
				if(vic==false)
				{
					System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
					System.out.println("Prêt à jouer " + ((JoueurHumain)j2).getName() + "?");
					ready = sc.next().charAt(0);
					vic = tour(j2,j1);
				}
			}while(vic==false);			
		}
		
		System.out.println("\n\n FIN DE LA PARTIE !");
		
	}
	
}
