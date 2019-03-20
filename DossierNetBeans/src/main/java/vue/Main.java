/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vue;

import dao.InterventionDaoJpa;
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

/**
 *
 * @author colap
 * classe qui permet de faire l'interface utilisateur
 */
public class Main {
    
    public final static String NOM_PERSISTENCE = "TPDASIPU";
    
    public static void main(String[] args) {
        //LancerMenuPrincipal();
        Personne p;
        
        //Test sur remplissage
        Remplir();
  
        JpaUtil.init();
        JpaUtil.creerEntityManager();
        JpaUtil.ouvrirTransaction();
        
        //services testés :
        //-SeConnecter
        //-SeInscrire
        //-getListInterventions
        //-Demander interventions
        //-recupérer les employés dispo
 
        Client c=(Client)PersonneDaoJpa.recupererPersonne("mail1@mail.com");
        System.out.println(c);
        Service.DemanderIntervention(c,2,"j'ai pas touché", "colis", "amazon");
        //Service.DemanderIntervention(c,2,"je ne crois pas qu'il y ai de bonne ou de mauvaise situation... si je devais résumer ma vie avec vous, je dirai que c'est d'abord des rencontres, des gens qui m'ont tendu la main à un moment où je ne pouvais pas, ou j'étais seul chez moi ...et c'est assez bizarre de se dire que les hasards, les rencontres forgent une destinée ...parce que quand on a le goût de la chose , ..le goût de la chose bien faite, le beau geste ...on ne trouve pas toujours l'interlocuteur en face, je dirai le miroir qui nous aide à avancer..");
        Service.DemanderIntervention(c,2,"mon chien s'est fait écraser", "compote");
        
        /*A Faire:
        -trouver l'employé le plus proche du client 
        -service tableau de bord
        -service consulter historique
        
        Je pense qu'il faut que DemanderInterventiuon prenne un employé en entrée aussi
        */
        JpaUtil.validerTransaction();
        JpaUtil.fermerEntityManager();
        JpaUtil.destroy();
        
    }
    
    public static void Remplir(){
        
        EntityManagerFactory emf = Persistence.createEntityManagerFactory(NOM_PERSISTENCE);
        EntityManager em = emf.createEntityManager();
        Date d=new Date();
        Client p2 = new Client(false, "DELAPIERRE", "ROGER", "motdepasse", "15 Rue Ampère, Paris", "063256585", "mail1@mail.com",d);
        Client p1 = new Client(false,"ANDRE","Anouchka", "mdp", "15 Rue de la Paix, Paris", "0695666406", "googlemail@mail.com",d);
        Employe E2 = new Employe(true, "DUCAILLOUX", "PIERRE", "present", "15 rue René, villeurbanne", "063256585", "mail2@mail.com",false,100,10);
        InterventionAnimal A1=new InterventionAnimal("hot-dog","scoubidou");
        InterventionLivraison L1=new InterventionLivraison("colis","Entreprise","colis non livré");
        InterventionIncident I1=new InterventionIncident("j'ai un probleme");
        
        //test des relations
        p2.ajouterIntervention(I1);
        p2.ajouterIntervention(A1);
        E2.ajouterIntervention(L1);
         
        EntityTransaction tx = em.getTransaction();
            tx.begin();
            em.persist(p2);
            em.persist(p1);
            em.persist(E2);
            em.persist(A1);
            em.persist(L1);
            em.persist(I1);
            tx.commit();
        em.close();
          //  emf.close();
    }
    
   public static void AffichageMainMenu(){
        System.out.println("--------------------");
        System.out.println("Bienvenue sur ProAct'IF");
        System.out.println("--------------------");
        System.out.println("Choisir une option:");
        System.out.println("1.Se connecter");
        System.out.println("2.S'inscrire");
        System.out.println();
    }
    
    public static void AffichageMenuClient(){
        System.out.println("Choisir une option:");
        System.out.println("1.Demander une intervention");
        System.out.println("2.Consulter Historique");
        System.out.println();
    }
    
     public static void AffichageMenuEmploye(){
        System.out.println("Choisir une option:");
        System.out.println("1.Cloturer l'intervention en cours");
        System.out.println("2.Consulter Tableau de bord");
        System.out.println();
    }
 
     public static void AffichageHistorique(){
         
     }
     
     public static void AffichageTableaudeBord(){
         
     }
     
  public static void LancerMenuPrincipal(Personne p){
         AffichageMainMenu();
         int choix = Saisie.lireInteger("Choix: ", Arrays.asList(1,2));
         switch(choix){
             case 1:
             {
                 String mail = Saisie.lireChaine("Mail : ");
                 String motDePasse = Saisie.lireChaine("Mot de passe : ");
		 p=Service.SeConnecter(mail, motDePasse);
                 while(p==null){
			System.out.println("Erreur lors de la connexion \n\n");
			LancerMenuPrincipal(p);
		}
		 if (p instanceof Employe){
			LancerMenuEmploye((Employe)p);
		 } else if ( p instanceof Client){
			LancerMenuClient((Client)p);
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
		 p=Service.SeInscrire(civilite, nom, prenom, mdp, addpost, tel, mail, date);
                 }catch(Exception e){
                 }
		 while(p==null){
			System.out.println("Erreur : L'inscription n'a pas pu être réalisée (champ vide ou mail déjà utilisé \n\n");
			LancerMenuPrincipal(p);
		 } 
		 if (p instanceof Employe){
			LancerMenuEmploye((Employe)p);
		 } else if ( p instanceof Client){
			LancerMenuClient((Client)p);
		 }
                 break;
           }
     }
	 
	public static void LancerMenuEmploye(Employe e){ 
            AffichageMenuEmploye();
            int choix = Saisie.lireInteger("Choix: ", Arrays.asList(1,2));
            switch(choix){
                case 1:
                    int status = Saisie.lireInteger("Statut (1=Succès, 2=Echec) : ",Arrays.asList(1,2));
                    int heureDeFin = Saisie.lireInteger("Heure de fin : ");
		    String commentaire = Saisie.lireChaine("Commentaire : ");
                    while((Service.CloturerIntervention(e,status,heureDeFin,commentaire))==false){
			System.out.println("Erreur : La cloture n'a pas pu être réalisée \n\n");
		    }
		    LancerMenuEmploye(e);
                    break;
                case 2: 
		    //ConsulterTableauDeBord(e);
		    LancerMenuEmploye(e);
	    }
	}
	
	public static void LancerMenuClient(Client c){ 
            AffichageMenuClient();
            int choix = Saisie.lireInteger("Choix: ", Arrays.asList(1,2));
            switch(choix){
                case 1:
		    Intervention tmp=null;
                    int type = Saisie.lireInteger("Statut (1=Animal, 2=Livraison, 3=Incident) : ",Arrays.asList(1,2,3));
            switch (type) {
                case 1:
                    {
                        String animal = Saisie.lireChaine("Animal : ");
                        String description = Saisie.lireChaine("Description: ");
                        tmp=Service.DemanderIntervention(c,type,description,animal);
                        break;
                    }
                case 2:
                    {
                        String objet = Saisie.lireChaine("Objet : ");
                        String entreprise = Saisie.lireChaine("Entreprise : ");
                        String description = Saisie.lireChaine("Description: ");
                        tmp=Service.DemanderIntervention(c,type,description,objet, entreprise);
                        break;
                    }
                case 3:
                    {
                        String description = Saisie.lireChaine("Description: ");
                        tmp=Service.DemanderIntervention(c,type,description);
                        break;
                    }
                default:
                    break;
            }
				 
                    while(tmp==null){ //if plutot ? 
			System.out.println("Erreur : la demande d'intervention n'est pas possible (champ vide ou pas d'employe disponible)");
                    }
   		    LancerMenuClient(c);
                    break;
                case 2: 
		     //ConsulterHistorique(c);
		     LancerMenuClient(c);
	   }
      }
}
