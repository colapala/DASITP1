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
    
    public static void affichageMainMenu(){
        System.out.println("--------------------");
        System.out.println("Bienvenue sur ProAct'IF");
        System.out.println("--------------------");
        System.out.println("Choisir une option:");
        System.out.println("1.Se connecter");
        System.out.println("2.S'inscrire");
        System.out.println();
    }
    
    public static void affichageMenuClient(){
        System.out.println("Choisir une option:");
        System.out.println("3.Demander une intervention");
        System.out.println("4.Consulter Historique");
        System.out.println();
    }
    
     public static void affichageMenuEmploye(){
        System.out.println("Choisir une option:");
        System.out.println("5.Cloturer l'intervention en cours");
        System.out.println("6.Consulter Tableau de bord");
        System.out.println();
    }
    
    public static void affichageOption(int option){
    
    }
     
    public static void lancerMenu(){
         affichageMainMenu();
         int choix = Saisie.lireInteger("Choix: ", Arrays.asList(1,2));
         switch(choix){
             case 1:
                 String mail = Saisie.lireChaine("Mail : ");
                 String motDePasse = Saisie.lireChaine("Mot de passe : ");
                 break;
             case 2: 
                 System.out.println("afficher menu inscription");
                 break;
         }
     }
    
    
    public static void main(String[] args) {
       //LancerMenu();
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
        Service.DemanderIntervention(c,2,"je ne crois pas qu'il y ai de bonne ou de mauvaise situation... si je devais résumer ma vie avec vous, je dirai que c'est d'abord des rencontres, des gens qui m'ont tendu la main à un moment où je ne pouvais pas, ou j'étais seul chez moi ...et c'est assez bizarre de se dire que les hasards, les rencontres forgent une destinée ...parce que quand on a le goût de la chose , ..le goût de la chose bien faite, le beau geste ...on ne trouve pas toujours l'interlocuteur en face, je dirai le miroir qui nous aide à avancer..");
        Service.DemanderIntervention(c,2,"mon chien s'est fait écraser", "compote");
        
        JpaUtil.validerTransaction();
        JpaUtil.fermerEntityManager();
        JpaUtil.destroy();
        
    }

}
