/* @author jorge terreu, corentin laharotte, chanèle jourdan 
   Classe qui représente l'interface avec l'utilisateur
*/

package vue;

import dao.JpaUtil;
import dao.PersonneDaoJpa;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.List;
import metier.modele.Client;
import metier.modele.Employe;
import metier.modele.Intervention;
import metier.modele.Personne;
import metier.service.Service;
import util.Saisie;


public class Main {
    
   public final static String NOM_PERSISTENCE = "TPDASIPU";
    
   public static void main(String[] args) {
        Personne p=null; 
        
        JpaUtil.init();
        
        //méthode à appeler une seule fois (pour remplir la base avec les employés)
       //insererTousLesEmployes();
       
        lancerMenuPrincipal(p);
        JpaUtil.destroy();       
    }
    
    //Création de la liste des employés de l'entreprise
    public static List<Employe> listDesEmployes(){
        List<Employe>l=new ArrayList<Employe>();
        l.add(new Employe(true,"BORROTI MATIAS DANTAS","Raphaël","motdepassepouremploye","8 Rue Arago, Villeurbanne","328178508","rborrotimatiasdantas4171@free.fr",true,10,20));
        l.add(new Employe(false,"OLMEADA MARAIS","Nor","motdepassepouremploye","5 Rue Léon Fabre, Villeurbanne","0418932546","nolmeadamarais1551@gmail.com",true,8,18));
        l.add(new Employe(false,"RAYES GEMEZ","Olena","motdepassepouremploye","12 Rue de la Prevoyance, Villeurbanne","0532731620","orayesgemez5313@outlook.com",true,6,16));
        l.add(new Employe(false,"SING","Ainhoa","motdepassepouremploye","4 Rue Phelypeaux, Villeurbanne","0705224200","asing8183@free.fr",true,5,15));
        l.add( new Employe(true,"ABDIULLINA","David Alexander","motdepassepouremploye","8 Rue Wilhelmine, Villeurbanne","0590232772","david-alexander.abdiullina@laposte.net",true,12,22));
        l.add(new Employe(true,"WOAGNER","Moez","motdepassepouremploye","6 Rue Camille Koechlin, Villeurbanne","0832205629","moez.woagner@laposte.net",true,7,17));
        l.add(new Employe(true,"HONRY","Matteo","motdepassepouremploye","9 Impasse Guillet, Villeurbanne","0482381862","matteo.honry@yahoo.com",true,5,15));
        l.add( new Employe(true,"CECCANI","Kevin","motdepassepouremploye","20 Rue Decomberousse, Villeurbanne","0664426037","kevin.ceccani@hotmail.com",true,8,18));
        l.add(new Employe(false,"VOYRET","Alice","motdepassepouremploye","1 Rue d'Alsace, Villeurbanne","0486856520","alice.voyret@hotmail.com",true,6,16));
        l.add(new Employe(true,"RINERD","Julien","motdepassepouremploye","4 Rue de la Jeunesse, Villeurbanne","0727252485","jrinerd5241@yahoo.com",true,9,19));
        return l;
    }
    
    //Permet d'insérer l'ensemble des employés dans la base de données
    public static void insererTousLesEmployes(){
        JpaUtil.creerEntityManager();
        JpaUtil.ouvrirTransaction();
        List<Employe>list=listDesEmployes();
        for (Employe e : list){
            Service.calculerLatLng(e);
            PersonneDaoJpa.creerPersonne(e);
        }
        JpaUtil.validerTransaction();
        JpaUtil.fermerEntityManager();
    }
    
    //Permet d'afficher une liste d'intervention 
    //Sert pour l'affichage de l'historique d'un client et du tableau de bord d'un employé
    public static void afficherListeInterventions (List<Intervention> listint){
        if (!listint.isEmpty()){
        System.out.println("|  Type  |    Description    |  Statut  |");
		for (Intervention i : listint){
                     int type=i.getType();
                     int status=i.getStatus();
                     String typeString="";
                     String statusString="";
                    switch (type) {
                    case 1:
                        typeString="Animal";
                        break;
                    case 2:
                        typeString="Livraison";
                        break;
                    case 3:
                        typeString="Incident";
                        break;
                    default:
                        break;
                    }
                    switch (status) {
                    case 0:
                        statusString="En Cours";
                        break;
                    case 1:
                        statusString="Succès";
                        break;
                    case 2:
                        statusString="Echec";
                        break;
                    default:
                        break;
                    }
                    System.out.println ("| "+typeString+" | "+i.getDescription()+" | "+statusString+" | ");
                    }
            }else{
                System.out.println ("Pas d'intervention");
            }
	}
    
	
   //Affichage des différents Menu (principal, client, employé)
	
   public static void affichageMenuPrincipal(){
        System.out.println("--------------------");
        System.out.println("Bienvenue sur ProAct'IF");
        System.out.println("--------------------");
        System.out.println("Choisir une option:");
        System.out.println("1.Se connecter");
        System.out.println("2.S'inscrire");
        System.out.println("3.Quitter");
        System.out.println();
    }
    
    public static void affichageMenuClient(){
        System.out.println();
        System.out.println("Choisir une option:");
        System.out.println("1.Demander une intervention");
        System.out.println("2.Consulter Historique");
        System.out.println("3.Retour");
        System.out.println();
    }
    
     public static void affichageMenuEmploye(){
        System.out.println();
        System.out.println("Choisir une option:");
        System.out.println("1.Cloturer l'intervention en cours");
        System.out.println("2.Consulter Tableau de bord");
        System.out.println("3.Retour");
        System.out.println();
    }
      
	
    //Lancement des différents menus (principal, client, employé)
    //Ils font automatiquement appel aux différents affichage et service selon la demande de l'utilisateur
	
    public static void lancerMenuPrincipal(Personne p){
        affichageMenuPrincipal();
         int choix = Saisie.lireInteger("Choix: ", Arrays.asList(1,2,3));
         switch(choix){
             case 1:
             {
                 String mail = Saisie.lireChaine("Mail : ");
                 String motDePasse = Saisie.lireChaine("Mot de passe : ");
		 p=Service.seConnecter(mail, motDePasse);
                 if(p==null){
			System.out.println("Echec de la connexion \n\n");
			lancerMenuPrincipal(p);
		}
		 if (p instanceof Employe){
			lancerMenuEmploye((Employe)p);
		 } else if ( p instanceof Client){
			lancerMenuClient((Client)p);
		 }
                 break;
             }
             case 2: 
                 try{
                 int madame = Saisie.lireInteger("Civilité (0=Mme, 1=Mr) : ",Arrays.asList(0,1));
                 boolean civilite=false;
                    if (madame==1){
                        civilite=true;
                    }
                     String nom = Saisie.lireChaine("Nom : ");
                     String prenom = Saisie.lireChaine("Prenom : ");
                     String mdp = Saisie.lireChaine("Mot de passe : ");
                     String addpost = Saisie.lireChaine("Adresse postale : ");
                     String tel =Saisie.lireChaine("Tel :");
                     String mail = Saisie.lireChaine("Mail : ");

                     SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                     String dateNaiss = Saisie.lireChaine("Date de naissance (dd/MM/yyyy) : ");
                     Date date = sdf.parse(dateNaiss);
                     p=Service.seInscrire(civilite, nom, prenom, mdp, addpost, tel, mail, date);

                     if(p==null){
                            System.out.println("Erreur : L'inscription n'a pas pu être réalisée (champ vide ou mail déjà utilisé) \n\n");
                            lancerMenuPrincipal(p);
                     } else {
                             System.out.println("Inscription réussie \n\n");
                     }

                     if (p instanceof Employe){
                            lancerMenuEmploye((Employe)p);
                     } else if ( p instanceof Client){
                            lancerMenuClient((Client)p);
                     }

                  }catch(Exception e){
                     System.out.println("Erreur : L'inscription n'a pas pu être réalisée (erreur sur la date) \n\n");
		     lancerMenuPrincipal(p);
                 }
                 
                 break;
             case 3:
                    System.out.println("A bientôt");
                    System.exit(0);
           }
     }
	 
    public static void lancerMenuEmploye(Employe e){ 
            affichageMenuEmploye();
            int choix = Saisie.lireInteger("Choix: ", Arrays.asList(1,2,3));
            switch(choix){
                case 1:
		    if(Service.rechercherInterventionEnCours(e)!=null){
                  	  int status = Saisie.lireInteger("Statut (1=Succès, 2=Echec) : ",Arrays.asList(1,2));
                   	  int heureDeFin = Saisie.lireInteger("Heure de fin : ");
		  	  String commentaire = Saisie.lireChaine("Commentaire : ");
		    	  boolean cloture=Service.cloturerIntervention(e,status,heureDeFin,commentaire);
                   	  if(!cloture){
				   System.out.println("Erreur : Cloture de l'intervention échouée \n\n");
		    	  } else {
			  	   System.out.println("Cloture de l'intervention réussie \n\n");
			  }
		    } else {
		    	  System.out.println("Vous n’avez pas d’intervention à clôturer \n\n");
		    }
		    lancerMenuEmploye(e);
                    break;
                case 2: 
		    afficherListeInterventions(Service.recupererTableauDeBord(e));
		    lancerMenuEmploye(e);
                    break;
                case 3:
                    lancerMenuPrincipal(e);
                    break;
	    }
	}
	
    public static void lancerMenuClient(Client c){ 
            affichageMenuClient();
            int choix = Saisie.lireInteger("Choix: ", Arrays.asList(1,2,3));
            switch(choix){
                case 1:
		    Employe tmp=null;
                    int type = Saisie.lireInteger("Type (1=Animal, 2=Livraison, 3=Incident) : ",Arrays.asList(1,2,3));
           	    switch (type) {
             	   case 1:
                           String animal = Saisie.lireChaine("Animal : ");
                           String description = Saisie.lireChaine("Description: ");
                           tmp=Service.demanderIntervention(c,type,description,animal);
                           break;
                   case 2:
                           String objet = Saisie.lireChaine("Objet : ");
                           String entreprise = Saisie.lireChaine("Entreprise : ");
                           String description1 = Saisie.lireChaine("Description: ");
                           tmp=Service.demanderIntervention(c,type,description1,objet, entreprise);
                           break;
                   case 3:
                           String description2= Saisie.lireChaine("Description: ");
                           tmp=Service.demanderIntervention(c,type,description2);
                           break;
                   default:
                       break;
                    }
				    
                    if(tmp==null){
			System.out.println("Erreur : la demande d'intervention n'est pas possible (champ vide ou pas d'employe disponible)");
                    } else {
		    	System.out.println("Demande d’intervention validée");
		    }
   		    lancerMenuClient(c);
                    break;
                    
                case 2: 
		     afficherListeInterventions(Service.recupererHistorique(c));
		     lancerMenuClient(c);
                     break;
                     
                case 3:
                    lancerMenuPrincipal(c);
                    break;
                    
                default:
                     break;
	   }
      }
}
