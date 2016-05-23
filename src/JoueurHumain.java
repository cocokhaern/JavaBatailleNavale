/**
 *  * @author CorentinR
 *  
 *  Repr�sente les joueurs humains avec nom, plateau, etc. 
 *  M�thodes permettant de tirer, actualiser, afficher les infos connues, annoncer le r�sultat d'un tir, etc.
 *  
 */
public class JoueurHumain implements Joueur {
	
	
	/// Champs ///

	private Case[][] plateau;    			//plateau du joueur, mis � jour avec les coups jou�s par l'adversaire
	private Case[][] plateauAdv;			//plateau de l'adversaire, inconnu, mis � jour avec les coups jou�s par le joueur
	int X;									//taille des plateau X*X
	private String name;					//nom du joueur
	int nombreBatAdv;						//nombre de bateau restant � l'adversaire (permet de d�tecter la victoire)
	
	
	/// M�thodes ///
	
	/**
	 * Constructeur : initalise les plateaux, demande un nom
	 * @param nX entier, taille du plateau, carr�
	 */
	public JoueurHumain(int nX)
	{
		X=nX;  
		nombreBatAdv = -1;
		
		plateau = new Case[X][X];  			//remplit le plateau de case EAU, sans bateau
		for(int i=0 ; i<X ; i++)
		{
			for(int j=0 ; j<X ; j++)
			{
				plateau[i][j] = new Case(Etat.EAU, -1);
			}
		}
		
		plateauAdv = new Case[X][X]; 		//remplit le plateauAdv de case INC, sans bateau
		for(int i=0 ; i<X ; i++)
		{
			for(int j=0 ; j<X ; j++)
			{
				plateauAdv[i][j] = new Case(Etat.INC, -1);
			}
		}
		
		System.out.println("\nQuel est votre nom ? :");  	//demande au joueur son nom
		name = Jeu.sc.next();
		System.out.println("Bienvenue " + name + " !\n");
	}

	
	/**
	 * Parcours les bateaux de la flotte et les place un � un sur le plateau (m�thode Bateau.placer)
	 */
	public void initialiser (Bateau[] flotte, int paramAuto, int paramCpu)
	{
		for(int i=0 ; i<flotte.length ; i++) //parcours flotte et appelle Bateau.placer
		{
			if (paramAuto==1)
			{
				flotte[i].placerManuel(plateau, i, paramCpu);
				System.out.println(this.affPlateau());
			}
			else if (paramAuto==2)
				flotte[i].placerAuto(plateau, i, paramCpu);
		}
		nombreBatAdv = flotte.length;    //initialise nombreBatAdv avec le nombre r�el de bateau dans la flotte
		System.out.println("\nVoici donc votre plateau de jeu ! :\n");
		System.out.println(this.affPlateau());		
		System.out.println("\nEntrez une touche pour continuer :");
		char temp = Jeu.sc.next().charAt(0);
	}

	
	/**
	 * Affiche le dernier coup de l'adversaire avant de jouer, en informant des coordonn�es,
	 * du resultat du tir et du nom du bateau touch�/coul� si il y a lieu + affiche les plateaux mis � jour.
	 * 
	 * @param etatTir Etat du tir renvoy� par l'adversaire (MANQUE, TOUCHE, COULE)
	 * @param coordTir coordonn�es du tir de l'adversaire
	 */
	public void recap(Etat etatTir, Coord coordTir)
	{
		if(coordTir == null)
			System.out.print("Vous �tes le premier � jouer, c'est parti !\n\n");
		else
		{
			System.out.print("Votre adversaire a tir� en " + Jeu.convertNL(coordTir.getX()) + "-" + coordTir.getY() + " et ");
			switch(etatTir)
			{
				case MANQUE : System.out.println("n'a rien touch�, ouf!");break;
				case TOUCHE : System.out.println("a touch� votre " + (plateau[coordTir.getX()][coordTir.getY()].getBat()).getNom() + "!");break;
				case COULE : System.out.println("a coul� votre " + (plateau[coordTir.getX()][coordTir.getY()].getBat()).getNom() + "! A�e...");break;
				default :  System.out.println("ERROR : etatTir non valide");break; 		 //debug
			}
			System.out.println("\n\n" + this + "\n");   	//affiche les plateaux mis � jour
		}
	}

	
	/**
	 * Invite le joueur � tirer. Demande des coordonn�es valides et les renvoit dans un Coord seulement si elle n'ont pas
	 * d�j� �t� jou�es. Boucle sinon.
	 * 
	 * @return Coord (x,y) contenant les coordon�es x et y du tir 
	 */
	public Coord tirer()
	{
		boolean testDeja;
		int x; int y;
		do
		{
			testDeja=false;
			System.out.println(name + " : rentrez les coordonn�es de la cible (ex: A6) :");
			do{
				do
				{
					System.out.println("X:");
					x = Jeu.convertLN(Jeu.sc.next().charAt(0));
					if(x>=X || x<0)										//validit� : dans le plateau
						System.out.println("Entr�e incorrecte, recommencez :\n");
				}while(x>=X || x<0);
	
					System.out.println("Y:");
					y = Jeu.sc.nextInt();
					if(y>=X || y<0)										//validit� : dans le plateau
						System.out.println("Entr�e incorrecte, recommencez :\n");
			}while(y>=X || y<0 || x>=X || x<0);
			
			if (plateauAdv[x][y].getDeja()==true)					//test si le coup n'a pas deja �t� jou�, boucle sinon
			{
				System.out.println("Vous avez d�j� jou� ce coup, recommencez :");
				testDeja=true;
			}
		}while(testDeja==true);

		Coord coordTir = new Coord(x,y);
		return coordTir;
	}
	
	
	/**
	 *  Re�oit les coordonn�es du tir adverse et renvoit s'il y a un bateau ou non.
	 *  ("accesseur" �vitant d'acc�der directement aux champs de l'autre joueurs)
	 *  
	 *  @param coordTir coordonn�es du tir adverse (return de adv.tirer)
	 *  
	 *  @return Etat BATEAU ou EAU de la case vis�e par l'adversaire (param de adv.resultatTir)
	 */
	public Etat recevoir(Coord coordTir)
	{
		return plateau[coordTir.getX()][coordTir.getY()].getHow();
	}
	
	
	/**
	 * Informe le joueur du r�sultat de son tir, teste si le bateau est coul� (appelle adv.testCouler), et actualise son plateauAdv
	 * et le plateau de l'adversaire (appelle adv.refresh)
	 * 
	 * @param adv Joueur adverse (permet d'appeler adv.refresh)
	 * @param result Etat EAU ou BATEAU de la case vis�e (return de adv.recevoir)
	 * @param coordTir coordonn�es du tir adverse (return de adv.tirer)
	 * 
	 * @return Etat MANQUE, TOUCHE ou COULE (param de adv.refresh et adv.recap)
	 */
	public Etat resultatTir(Joueur adv, Etat result, Coord coordTir)
	{
		System.out.print("\nTir en " + Jeu.convertNL(coordTir.getX()) + "-" + coordTir.getY() + " : ");
		Etat etatRefresh = Etat.INVALIDE;
		
		if (result == Etat.EAU)  		 // si Etat.EAU : une seule possibilit�, pas de test couler, etc
		{
			System.out.println(" rat� ! ...bloup...");
			etatRefresh = Etat.MANQUE;
			plateauAdv[coordTir.getX()][coordTir.getY()].setHow(etatRefresh);	//met � jour le joueur
			adv.refresh(etatRefresh, coordTir);									//met � jour l'adversaire
		}
		else if (result == Etat.BATEAU)  // si Etat.BATEAU : test couler -> 2 possibilit�s
		{
			boolean couler = adv.testCouler(coordTir);  //test si la bateau touch� est coul� (pV=0)
			if (couler == false)   //si NON -> TOUCHE
			{
				System.out.println(" touch� ! ...BOOM!");
				etatRefresh = Etat.TOUCHE;		
				plateauAdv[coordTir.getX()][coordTir.getY()].setHow(etatRefresh); //met � jour le joueur
				int type = adv.refresh(etatRefresh, coordTir); 					  //met � jour l'adversaire et renvoit l'identifiant du bateau touch�
				plateauAdv[coordTir.getX()][coordTir.getY()].setTypeBat(type);	  //met � jour le joueur : type de bateau touch�
			}
			if (couler == true)   //si OUI -> COULE
			{
				System.out.println(" touch� coul�! ...BOOOOOM!!!");
				etatRefresh = Etat.COULE;
				int type = adv.refresh(etatRefresh, coordTir);					  //idem
				plateauAdv[coordTir.getX()][coordTir.getY()].setTypeBat(type);    //idem
				
				for(int i=0 ; i<X ; i++)   					//parcours tout le plateau et passe toutes les cases TOUCHE du bateau coul� en COULE
				{											
					for(int j=0 ; j<X ; j++)	
					{
						if (plateauAdv[i][j].getTypeBat()==type)
								plateauAdv[i][j].setHow(etatRefresh);
						
					}
				}   
				nombreBatAdv--;				//d�cr�mente le nombre de bateaux restant � l'adversaire
			}
		}				
		else
			System.out.println(" ERROR : case deja jou�e ou �tat invalide, probleme de test \"deja\"");   //debug

		plateauAdv[coordTir.getX()][coordTir.getY()].setDeja(true);			// =>cette case ne pourra plus etre jou�e
		System.out.println(this);	
		return etatRefresh;
	}

	
	/**
	 *  Test si le bateau occupant la case vis�e a 0 pV (coul�) ou non (seulement touch�)
	 *  
	 *  @param coordTir coordonn�es de la case vis�e par l'adversaire (return de adv.tirer)
	 *  
	 *  @return bool�en : true = coul�, false = pas coul�
	 */
	public boolean testCouler(Coord coordTir)
	{
		return plateau[coordTir.getX()][coordTir.getY()].getBat().encaisser();
	}

	
	/**
	 *  Actualise le plateau de l'adversaire en meme temps que celui du joueur (appelle par adv.resultatTir) et renvoit, si touch�, l'identifiant du bateau touch�
	 *  
	 *  @param result Etat MANQUE, TOUCHE ou COULE du coup de l'adversaire
	 *  @param coordTir coordonn�es de la case vis�e par l'adversaire (return de adv.tirer)
	 *  
	 *  @return entier informant, si touch�/coul�, de l'identifiant du bateau vis� 
	 */
	public int refresh(Etat result, Coord coordTir)
	{	
		plateau[coordTir.getX()][coordTir.getY()].setDeja(true);
		if(result==Etat.COULE)
		{
			for(int i=0 ; i<X ; i++)
			{
				for(int j=0 ; j<X ; j++)	
				{
					if (plateau[i][j].getTypeBat()==plateau[coordTir.getX()][coordTir.getY()].getTypeBat())
						plateau[i][j].setHow(result);
				}
			}
		}  
		else
			plateau[coordTir.getX()][coordTir.getY()].setHow(result);
		
		return plateau[coordTir.getX()][coordTir.getY()].getTypeBat();
	}

	
	/**
	 * Test si le joueur a gagn� ou non (nombreBatAdv=0), l'informe dans ce cas, et permet de stopper la partie
	 * 
	 * @return bool�en true si le joueur a gagn� : permet de sortir de la boucle de jeu
	 */
	public boolean victoire()
	{
		if (nombreBatAdv==0)
		{
			System.out.println("C'est gagn� ! C'est gagn� ! \n�*o Bravo " + name + "!!! o*� \n\n Vous avez gagn� en " + Jeu.tour + " tours.");
			return true;
		}
		
		if (nombreBatAdv>0)
			return false;
		
		else
		{
			System.out.println("ERROR : nombreBatAdv<0 !");
			return true;
		}		
	}
	
	
	/**
	 * toString renvoyant le plateau inconnu de l'adversaire et les coups jou�s
	 * 			ET le plateau du joueur avec les coups de l'adversaire, c�te � c�te
	 *			avec affichage dynamique en fonction de la taille du plateau
	 */
	public String toString()
	{
		String aff="";
		
		aff = "\nTour n�" + Jeu.tour + " : \n";
		aff += "Adversaire:  ";
		for (int i=4 ; i<X ; i++)			//les boucles ici permettent l'affichage dynamique,
		{									//le bon nombre d'espaces, etc en fonction de X
			aff += "   ";
		}

		aff+="           ";
		aff += "Vous:\n ";
		
		for (int i=0 ; i<Math.min(X,10) ; i++)
		{
			aff += "  " + i;
		}
		for (int i=10 ; i<X ; i++)
		{
			aff += " " + i;
		}
		aff+="            ";
		for (int i=0 ; i<Math.min(X,10) ; i++)
		{
			aff += "  " + i;
		}
		for (int i=10 ; i<X ; i++)
		{
			aff += " " + i;
		}
		for (int i=0 ; i<X ; i++)						//plateauAdv
		{
			aff += "\n" + Jeu.convertNL(i) +" ";
			for (int j=0 ; j<X ; j++)
			{
				switch (plateauAdv[i][j].getHow())
				{
					case INC : aff += " . "; break;
					case MANQUE : aff += " o "; break;
					case BATEAU : aff += " x "; break;
					case TOUCHE : aff += " T "; break;
					case COULE : aff += " C "; break;
					default : aff +=" ? "; break;
				}
			}
			aff+="          ";
			aff += Jeu.convertNL(i) +" ";
			for (int j=0 ; j<X ; j++)
			{
				switch (plateau[i][j].getHow())			//plateau
				{
					case EAU : aff += " . "; break;
					case MANQUE : aff += " o "; break;
					case BATEAU : aff += " x "; break;
					case TOUCHE : aff += " T "; break;
					case COULE : aff += " C "; break;
					default : aff +=" ? "; break;
				}
			}
		}
		
		aff += "\n\n";
		
		return aff;
	}

	
	/**
	 * second "toString" n'affichant que le plateau du joueur, utilis� pendant le placement des bateaux
	 * 
	 * @return String pour etre affich� � l'�cran
	 */
	public String affPlateau()
	{
		String aff = " ";
		for (int i=0 ; i<Math.min(X,10) ; i++)
		{
			aff += "  " + i;
		}
		for (int i=10 ; i<X ; i++)
		{
			aff += " " + i;
		}
		for (int i=0 ; i<plateau.length ; i++)
		{
			aff += "\n" + Jeu.convertNL(i) + " ";
			for (int j=0 ; j<plateau[0].length ; j++)
			{
				switch (plateau[i][j].getHow())
				{
					case EAU : aff += " . "; break;
					case MANQUE : aff += " o "; break;
					case BATEAU : aff += " x "; break;
					case TOUCHE : aff += " T "; break;
					case COULE : aff += " C "; break;
					default : aff +=" ? "; break;
				}
			}
		}
		
		return aff;
	}


	/// Get - Set /// 
	
	public String getName()			//permet d'acc�der au nom du joueur dans Jeu, pour demander clairement si le joueur nomm� est pr�t									
	{								//ceci �vite les confusions et le d�voilement non volontaire du plateau adverse
		return name;
	}

}
