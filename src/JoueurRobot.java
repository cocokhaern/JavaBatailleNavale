import java.util.Stack;

/**
 * @author CorentinR
 *
 *  Repr�sente le joueur robot (intelligence artificielle).
 *  M�thodes permettant de tirer, actualiser, et gestion de toute l'IA
 *  
 */
public class JoueurRobot implements Joueur{
	
	/// Champs ///
	
	private Case[][] plateau;    			//plateau du joueur, mis � jour avec les coups jou�s par l'adversaire humain
	private Case[][] plateauAdv;			//plateau de l'adversaire, inconnu, mis � jour avec les coups jou�s par le robot
	int X;									//taille des plateau X*X
	int nombreBatAdv;						//nombre de bateau restant � l'adversaire (permet de d�tecter la victoire)
	boolean piste;		//indique si le robot a localis� un bateau
	boolean pisteDir;	//indique si le robot a localis� un bateau et sait dans quel direction tirer pour le couler
	Coord init;			//Coord de la premi�re Case touch� pour le Bateau actuellement pist�
	Coord lastCoord;	//Coord de la derni�re Case touch� pour le Bateau actuellement pist�
	int direction;		//direction dans laquelle tirer par rapport � lastCoord (0:haut, 1:gauche, 2:bas, 3:droit).
	
	
	/// M�thodes ///
	
	public JoueurRobot(int nX)
	{
		X=nX;
		plateau = new Case[X][X];
		nombreBatAdv = -1;
		for(int i=0 ; i<X ; i++)
		{
			for(int j=0 ; j<X ; j++)
			{
				plateau[i][j] = new Case(Etat.EAU, -1);
			} 
		}
		
		plateauAdv = new Case[X][X];
		for(int i=0 ; i<X ; i++)
		{
			for(int j=0 ; j<X ; j++)
			{
				plateauAdv[i][j] = new Case(Etat.INC, -1);
			}
		}
		piste = false;
		pisteDir=false;
		direction = -1;
	}

	
	public void initialiser (Bateau[] flotte, int paramAuto, int paramCpu)
	{
		for(int i=0 ; i<flotte.length ; i++)
		{
			flotte[i].placerAuto(plateau, i, 2);
		}
		nombreBatAdv = flotte.length;
	}

	
	public Coord tirer()
	{
		boolean testInutile;
		boolean testDeja;
		Coord coordTir;
		int x=-1; int y=-1;
		
		do
		{
			if(piste==false)
			{
				do
				{
					testDeja=false;
					do
					{
						x = ((int)(Math.random()*X));
						if (x%2==0)
							y =  ((int)(Math.random()*(X/2)))*2+1;
						if (x%2!=0)
							y =  ((int)(Math.random()*(X/2)+1))*2;
					}while(y>=X || y<0 || x>=X || x<0);
					
					if (plateauAdv[x][y].getDeja()==true)
					{
						testDeja=true;
					}
				}while(testDeja==true);
			}
			
			if(piste==true)
			{
				do
				{
					testDeja=false;
					do
					{
						if (pisteDir==false)
							direction = ((int)(Math.random()*4));
	
						switch(direction)
						{
							case 0 : x=lastCoord.getX()-1; y=lastCoord.getY();   break;
							case 1 : x=lastCoord.getX();   y=lastCoord.getY()+1; break;
							case 2 : x=lastCoord.getX()+1; y=lastCoord.getY();   break;
							case 3 : x=lastCoord.getX();   y=lastCoord.getY()-1; break;
							default : System.out.println("direction != 0..3");
						}
					}while(x>=X || x<0 || y>=X || y<0);
					if (plateauAdv[x][y].getDeja()==true)
					{
						testDeja=true;
						if (pisteDir==true)	
							this.dirInverse();
					}
				}while(testDeja==true);
			}
		coordTir = new Coord(x,y);
		testInutile = tirInutile(coordTir);
		
		if(testInutile==true && pisteDir==true)
			this.dirInverse();
		
		}while(testInutile==true);

		return coordTir;
	}

	
	public boolean tirInutile(Coord coordTir)
	{
		if((coordTir.getX()+1)<X)
		{
			if(plateauAdv[coordTir.getX()+1][coordTir.getY()].getHow() == Etat.COULE)
				return true;
		}		
		if((coordTir.getX()-1)>0)
		{
			if(plateauAdv[coordTir.getX()-1][coordTir.getY()].getHow() == Etat.COULE)
				return true;
		}
		if((coordTir.getY()+1)<X)
		{
			if(plateauAdv[coordTir.getX()][coordTir.getY()+1].getHow() == Etat.COULE)
				return true;
		}
		if((coordTir.getY()-1)>0)
		{
			if(plateauAdv[coordTir.getX()][coordTir.getY()-1].getHow() == Etat.COULE)
				return true;
		}
		
		return false;
	}
	
	
	public Etat recevoir(Coord coordTir)
	{
		return plateau[coordTir.getX()][coordTir.getY()].getHow();
	}

	
	public Etat resultatTir(Joueur adv, Etat result, Coord coordTir)
	{
		Etat etatRefresh = Etat.INVALIDE;
		if (result == Etat.EAU)  		 // si Etat.EAU : une seule possibilit�, pas de test couler, etc
		{
			etatRefresh = Etat.MANQUE;
			plateauAdv[coordTir.getX()][coordTir.getY()].setHow(etatRefresh);	//met � jour le joueur
			adv.refresh(etatRefresh, coordTir);									//met � jour l'adversaire
			if(piste==true)
			{
				if (pisteDir==true)	
					this.dirInverse();
				if (pisteDir==false)
					lastCoord = new Coord(init.getX(), init.getY());
			}
				
		}
		else if (result == Etat.BATEAU)  // si Etat.BATEAU : test couler -> 2 possibilit�s
		{
			boolean couler = adv.testCouler(coordTir);  //test si la bateau touch� est coul� (pV=0)
			if (couler == false)   //si NON -> TOUCHE
			{
				etatRefresh = Etat.TOUCHE;		
				plateauAdv[coordTir.getX()][coordTir.getY()].setHow(etatRefresh); //met � jour le joueur
				int type = adv.refresh(etatRefresh, coordTir); 					  //met � jour l'adversaire et renvoit l'identifiant du bateau touch�
				plateauAdv[coordTir.getX()][coordTir.getY()].setTypeBat(type);	  //met � jour le joueur : type de bateau touch�
				
				if(piste==false)
				{
					piste=true;
					init = new Coord(coordTir.getX(), coordTir.getY());
					lastCoord = new Coord(coordTir.getX(), coordTir.getY());
					pisteDir=false;
				}
				else if(piste==true)
				{
					lastCoord = new Coord(coordTir.getX(), coordTir.getY());
					pisteDir=true;
				}			
			}
			if (couler == true)   //si OUI -> COULE
			{
				etatRefresh = Etat.COULE;
				int type = adv.refresh(etatRefresh, coordTir);					  //idem
				plateauAdv[coordTir.getX()][coordTir.getY()].setTypeBat(type);    //idem
				piste=false;
				pisteDir=false;
				direction=-1;
				
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

	
	public boolean testCouler(Coord coordTir)
	{
		return plateau[coordTir.getX()][coordTir.getY()].getBat().encaisser();
	}

	
	public void dirInverse()
	{
		{
			switch (direction)
			{
			case 0 : direction=2; break; 
			case 1 : direction=3; break; 
			case 2 : direction=0; break; 
			case 3 : direction=1; break; 
			}
			lastCoord = new Coord(init.getX(), init.getY());	
		}
	}
	
	
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

	
	public boolean victoire()
	{
		if (nombreBatAdv==0)
		{
			System.out.println("D�sol�, l'ordinateur vous a battu et a gagn� en " + Jeu.tour + " tours.");
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

	
	public String toString()
	{
		return "\n.\n.\n.\nL'ordinateur a jou� !\n.\n.\n.\n\n ";
	}
	
	
	public void recap(Etat etatTir, Coord coordTir)
	{
		// inutile dans JoueurRobot
	}
	
	
}
    
     