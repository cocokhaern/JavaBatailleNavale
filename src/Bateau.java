/**
 * 
 * @author CorentinR
 *
 */
public class Bateau{

	
	 /// Champs /// 
	
    public String nom;  
    private char[][] forme;
	private int pV;
	
	  
	/// Méthodes ///   
	
	public Bateau(String name, int life, char[][] form)
	{
		nom = name;
		pV = life;
		forme=form.clone();
	}
		  
	public boolean encaisser()
	{
		boolean couler = false;
		if (pV>0)
			pV--;
		else
			System.out.println("ERROR : bateau deja coulé, probleme de test de validité de tir");
		
		if (pV==0)
			couler=true;
		else
			couler=false;
		
		return couler;
	}
	
	public void placerManuel (Case[][] plateau, int typeBat, int paramRegle)
    {
        boolean test = false;
        boolean testRot = false;
        boolean testX = false;
        boolean testY = false;
        char[][] formeSave = forme.clone();
        int rot;
        char lX;
        int pX=-1;
        int pY=-1;
       
        do{
        	forme = formeSave.clone();
            System.out.println("\n\n=> Placement du " + nom.toUpperCase() + ", occupant " + pV + " cases : \n" + this.formeAff());
       
            do{
                System.out.println("\nOrientation (rotation vers la gauche de 0°(0), 90°(1), 180°(2), 270°(3)) ? :");
                rot = Jeu.sc.nextInt();
                switch (rot)
                {
                case 0 : testRot=true; break;
                case 1 : this.pivot(); testRot=true; break;
                case 2 : this.pivot();this.pivot(); testRot=true; break;
                case 3 : this.pivot();this.pivot();this.pivot(); testRot=true; break;
                default : testRot=false; System.out.println("\nEntrée incorrecte, reccomencez :"); break;
                }
            }while (testRot==false);
            System.out.println("Le bateau sera placé avec cette orientation : \n" + this.formeAff());
           
            if (paramRegle==2)
            {
	            do{
		            System.out.println("\nPosition X ?  (A-" + Jeu.convertNL((plateau.length-forme.length)) + ")");
		            lX = Jeu.sc.next().charAt(0);
		            pX=Jeu.convertLN(lX);
		            if(pX<0 || pX>plateau.length-forme.length)
		            {
		            	System.out.println("Entrée incorrecte, recommencez :");
		            	testX=false;
		            }
		            
		            else
		            	testX=true;
	            }while(testX == false);
	            
	            do{
		            System.out.println("Position Y ?  (0-" + (plateau.length-forme[0].length) + ")");
		            pY = Jeu.sc.nextInt();
		            if(pY<0 || pY>plateau.length-forme[0].length)
		            {
		            	System.out.println("Entrée incorrecte, recommencez :");
		            	testY=false;
		            }
		            else
		            	testY=true;
	            }while(testY == false);
            }
            
            if (paramRegle==1)
            {
	            do{
		            System.out.println("\nPosition X ?  (A-" + Jeu.convertNL((plateau.length-forme.length+2)) + ")");
		            lX = Jeu.sc.next().charAt(0);
		            pX=Jeu.convertLN(lX);
		            pX--;
		            if(pX<-1 || pX>plateau.length-forme.length+1)
		            {
		            	System.out.println("Entrée incorrecte, recommencez :");
		            	testX=false;
		            }
		            
		            else
		            	testX=true;
	            }while(testX == false);
	            
	            do{
		            System.out.println("Position Y ?  (0-" + (plateau.length-forme[0].length+2) + ")");
		            pY = Jeu.sc.nextInt();
		            pY--;		
		            if(pY<-1 || pY>plateau.length-forme[0].length+1)
		            {
		            	System.out.println("Entrée incorrecte, recommencez :");
		            	testY=false;
		            }
		            else
		            	testY=true;
	            }while(testY == false);
            }
            test = testCol(plateau, pX, pY, 1);
        }while (test==false);

        for(int i=0 ; i<forme.length ; i++)
        {
        	for(int j=0 ; j<forme[0].length ; j++)
            {
            	if (this.forme[i][j]=='X')
            	{
            		plateau[i+pX][j+pY].setHow(Etat.BATEAU);
            		plateau[i+pX][j+pY].setBat(this);
            		plateau[i+pX][j+pY].setTypeBat(typeBat);           		
            	}
            }
        }
    }
	
	public void placerAuto (Case[][] plateau, int typeBat, int paramRegle)
	{
		boolean test = false;
        boolean testRot = false;
        boolean testX = false;
        boolean testY = false;
        char[][] formeSave = forme.clone();
        int rot;
        int pX=-1;
        int pY=-1;
       
        do{
        	forme = formeSave.clone();     
            do{          
                rot = ((int)(Math.random()*4));
                switch (rot)
                {
                case 0 : testRot=true; break;
                case 1 : this.pivot(); testRot=true; break;
                case 2 : this.pivot();this.pivot(); testRot=true; break;
                case 3 : this.pivot();this.pivot();this.pivot(); testRot=true; break;
                default : testRot=false; break;
                }
            }while (testRot==false);
            
            do{	          
	            pX=((int)(Math.random()*(plateau.length-forme.length+1)));
	            if (paramRegle==1)
	            	pX--;
	            if(pX<0 || pX>plateau.length-forme.length)
	            	testX=false;
	            else
	            	testX=true;
            }while(testX == false);
       	
            do{
	            pY = ((int)(Math.random()*(plateau.length-forme[0].length+1)));
	            if (paramRegle==1)
	            	pX--;
	            if(pY<0 || pY>plateau.length-forme[0].length)
	            	testY=false;
	            else
	            	testY=true;
            }while(testY == false);
            
            test = testCol(plateau, pX, pY, 2);
        }while (test==false);

        for(int i=0 ; i<forme.length ; i++)
        {
        	for(int j=0 ; j<forme[0].length ; j++)
            {
            	if (this.forme[i][j]=='X')
            	{
            		plateau[i+pX][j+pY].setHow(Etat.BATEAU);
            		plateau[i+pX][j+pY].setBat(this);
            		plateau[i+pX][j+pY].setTypeBat(typeBat);
            		
            	}
            }
        }
	}
	
	public void pivot()
	{
		char[][] temp = new char[forme[0].length][forme.length];
		for(int i=0 ; i<forme.length ; i++)
		{
			for(int j=0 ; j<forme[0].length ; j++)
			{
				temp[j][forme.length-1-i]=forme[i][j];
			}
		}
		forme = temp.clone();	
	}
	
	public String formeAff()
	{
		String aff = "";
		
		for(int i=0 ; i<forme.length ; i++)
		{
			for(int j=0 ; j<forme[0].length ; j++)
			{
				if (forme[i][j]=='.')
					aff += "  ";
				else
					aff += forme[i][j] + " ";
			}
			aff += "\n";
		}
		
		return aff;
	}

	public boolean testCol(Case[][] plateau, int pX, int pY, int paramAuto)
	{		
		 for(int i=0 ; i<forme.length ; i++)
         {
         	for(int j=0 ; j<forme[0].length ; j++)
             {
         		if ((i+pX)>-1 && (j+pY)>-1 && (i+pX)<plateau.length && (j+pY)<plateau.length)
         		{												
	             	if (plateau[i+pX][j+pY].getHow()!=Etat.EAU && this.forme[i][j]=='X')
	             	{
	             		if(paramAuto==1)
	             			System.out.println("\nDes bateaux se chevauchent! Recommencez :");
	             		return false;
	             	}
	             	if (plateau[i+pX][j+pY].getHow()!=Etat.EAU && this.forme[i][j]=='.')
	             	{
	             		if(paramAuto==1)
	             			System.out.println("\nDes bateaux se touchent! (rappel : contre l'ordinateur, les bateaux ne doivent pas se toucher) Recommencez :");
	             		return false;
	             	}
         		}
             }
         }
		 return true;
	}
	
	
	/// Get - Set /// 
	
	public String getNom()    // permet d'annoncer au joueur lequel de ses bateaux a été touché par l'adversaire
	{
		return nom;
	}
	
}
