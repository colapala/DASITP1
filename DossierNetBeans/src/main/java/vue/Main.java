/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vue;

import dao.JpaUtil;
import java.util.Date;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import metier.modele.Client;
import metier.modele.Employe;
import metier.modele.InterventionAnimal;
import metier.modele.InterventionIncident;
import metier.modele.InterventionLivraison;
import static metier.modele.test.NOM_PERSISTENCE;
import metier.service.Service;

/**
 *
 * @author colap
 * classe qui permet de faire l'interface utilisateur
 */
public class Main {
    
    public static void Remplir(){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory(NOM_PERSISTENCE);
        EntityManager em = emf.createEntityManager();
        Date d=new Date();
        Client p2 = new Client(false, "DELAPIERRE", "ROGER", "motdepasse", "15 Rue Ampère, Paris", "063256585", "mail1@mail.com",d);
        Client p1 = new Client(false,"ANDRE","Anouchka", "mdp", "15 Rue de la Paix, Paris", "0695666406", "googlemail@mail.com",d);
        Employe E2 = new Employe(true, "DUCAILLOUX", "PIERRE", "present", "15 rue René, villeurbanne", "063256585", "mail2@mail.com",true,100,10);
        InterventionAnimal A1=new InterventionAnimal("hot-dog","scoubidou");
        InterventionLivraison L1=new InterventionLivraison("colis","Entreprise","colis non livré");
        InterventionIncident I1=new InterventionIncident("j'ai un probleme");
        //test des relations
        I1.ajouterPersonne(p2,E2);
        L1.ajouterPersonne(p1,E2);
        
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
    }
     
    public static void main(String[] args) {

        //Test sur remplissage
        Remplir();
  
        JpaUtil.init();
        JpaUtil.creerEntityManager();
        JpaUtil.ouvrirTransaction();
        boolean trouve=Service.SeConnecter("mail1@mail.com","motdepasse");
        System.out.println("trouve= "+trouve);
        
        JpaUtil.validerTransaction();
        JpaUtil.fermerEntityManager();
        JpaUtil.destroy();
        
    }
}
