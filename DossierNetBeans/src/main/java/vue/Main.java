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
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import metier.modele.Client;
import metier.modele.Employe;
import metier.modele.Intervention;
import metier.modele.InterventionAnimal;
import metier.modele.InterventionIncident;
import metier.modele.InterventionLivraison;
import metier.modele.Personne;
import metier.service.Service;
import util.Saisie;


public class Main {
    
   public final static String NOM_PERSISTENCE = "TPDASIPU";
    
   public static void main(String[] args) {
         Personne p=null;
        
        //Test sur remplissage
        //Remplir();
  
       
        
        JpaUtil.init();
        //insererTousLesEmployes();
        lancerMenuPrincipal(p);
        //services testés :
        //-SeConnecter
        //-SeInscrire
        //-getListInterventions
        //-Demander interventions
        //-recupérer les employés dispo
        //-trouver Employé
        
        
        
        /*Client c=(Client)PersonneDaoJpa.recupererPersonne("mail1@mail.com");
        System.out.println(c);
        Employe e=Service.demanderIntervention( c,2,"jhjhj");*/
       //List<Intervention> l=c.getListInterventions();
      /* List<Employe> l=PersonneDaoJpa.trouverListeEmployeDispo(10);
       l.size();
       System.out.println(l);*/
       /*int heure=Service.heureActuelleToInt();
       System.out.println(heure);
       List<Employe> l=PersonneDaoJpa.trouverListeEmployeDispo(6);
       l.size();
       System.out.println(l);*/
      // System.out.println(e);
       //Service.cloturerIntervention(e,2,12,"tout s'est bien passé");
       //
       //Employe e=Service.trouverEmploye(c);
        //System.out.println("debut : "+e.getHoraireEntree());
       //afficherHistorique(l);
        
        /*List <Employe> ll=PersonneDaoJpa.trouverListeEmployeDispo();
        System.out.println(ll);
        Employe e=Service.trouverEmploye(c);
        System.out.println(e.getNom());
        //System.out.println(l.get(0));
        //System.out.println(c.getListInterventions());*/
        
        /*Service.DemanderIntervention(c,2,"j'ai pas touché", "colis", "amazon");
        //Service.DemanderIntervention(c,2,"je ne crois pas qu'il y ait de bonne ou de mauvaise situation... si je devais résumer ma vie avec vous, je dirai que c'est d'abord des rencontres, des gens qui m'ont tendu la main à un moment où je ne pouvais pas, ou j'étais seul chez moi ...et c'est assez bizarre de se dire que les hasards, les rencontres forgent une destinée ...parce que quand on a le goût de la chose , ..le goût de la chose bien faite, le beau geste ...on ne trouve pas toujours l'interlocuteur en face, je dirai le miroir qui nous aide à avancer..");
        Service.DemanderIntervention(c,2,"mon chien s'est fait écraser", "compote");*/
        
        /*A Faire:
        -trouver l'employé le plus proche du client 
        
        Je pense qu'il faut que DemanderInterventiuon prenne un employé en entrée aussi
        */
        JpaUtil.destroy();
        
    }
    
	
    //Permet de créer et persister différents objets pour les intégrer à notre base de données
    public static void Remplir(){
        
        EntityManagerFactory emf = Persistence.createEntityManagerFactory(NOM_PERSISTENCE);
        EntityManager em = emf.createEntityManager();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        try{
        Date date = sdf.parse("12/11/1998");
        Date d=new Date();
        Client p2 = new Client(false, "DELAPIERRE", "ROGER", "motdepasse", "14 Cours Emile Zola, Villeurbanne", "063256585", "mail1@mail.com",date);
        Service.calculerLatLng(p2);
        Client p1 = new Client(false,"ANDRE","Anouchka", "mdp", "15 Rue de la Paix, Paris", "0695666406", "googlemail@mail.com",d);
        Service.calculerLatLng(p1);
        Employe E2 = new Employe(true, "DUCAILLOUX", "PIERRE", "present", "12 Place Bellecour, Lyon", "063256585", "mail2@mail.com",true,10,20);
        Service.calculerLatLng(E2);
        Employe E3 = new Employe(true, "Blanc", "gerard", "ghfru", "15 rue René, villeurbanne", "063256585", "mailmail@mail.com",true,10,20);
        Service.calculerLatLng(E3);
       
        InterventionAnimal A1=new InterventionAnimal("hot-dog","scoubidou");
        InterventionLivraison L1=new InterventionLivraison("colis","Entreprise","colis non livré");
        InterventionIncident I1=new InterventionIncident("j'ai un probleme");
        
        //test des relations
        p2.ajouterIntervention(I1);
        p2.ajouterIntervention(A1);
        E2.ajouterIntervention(L1);
        E2.ajouterIntervention(A1);
        E2.ajouterIntervention(I1);
         
        EntityTransaction tx = em.getTransaction();
            tx.begin();
            em.persist(p2);
            em.persist(p1);
            em.persist(E2);
            em.persist(A1);
            em.persist(L1);
            em.persist(I1);
            em.persist(E3);
            tx.commit();
        em.close();
         }catch(Exception e){
            
        }
      //  emf.close();
    }
    
    //Création de la liste des employés de l'entreprise
    public static List<Employe> listDesEmployes(){
        List<Employe>l=new ArrayList<Employe>();
        l.add(new Employe(true,"BORROTI MATIAS DANTAS","Raphaël","motdepassepouremploye","8 Rue Arago, Villeurbanne","328178508","rborrotimatiasdantas4171@free.fr",true,2,12));
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
         int choix = Saisie.lireInteger("Choix: ", Arrays.asList(1,2));
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
                 int madame = Saisie.lireInteger("Civilité (0=Mme, 1=Mr) : ",Arrays.asList(0,1));
                 boolean civilite=false;
                    if (madame==1){
                        civilite=true;
                    }   
                 String nom = Saisie.lireChaine("Nom : ");
                 String prenom = Saisie.lireChaine("Prenom : ");
                 String mdp = Saisie.lireChaine("Mot de passe : ");
                 String addpost = Saisie.lireChaine("Adresse postale : ");
                 String tel = Saisie.lireChaine("Tel : ");
                 String mail = Saisie.lireChaine("Mail : ");
		 SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                 try{
                 String dateNaiss = Saisie.lireChaine("Date de naissance (dd/MM/yyyy) : ");
		 Date date = sdf.parse(dateNaiss);
		 p=Service.seInscrire(civilite, nom, prenom, mdp, addpost, tel, mail, date);
                 }catch(Exception e){
                 }
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
                 break;
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
