/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metier.modele;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;

/**
 *
 * @author Claharotte
 */
public class test {

    public final static String NOM_PERSISTENCE = "TPDASIPU";

    public static void afficher(Personne p) {
        System.out.println(p.getNom() + " " + p.getPrenom());
    }
    
    public static void afficher(Intervention i) {
        System.out.println("Type :"+i.getType() + " " + "Desc :"+i.getDescription());
    }

    public static Personne rechercherPersonne(EntityManager em, Integer id) {
        return em.find(Personne.class, id);
    }

    public static void savePersonne(EntityManager em, Personne p) {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.persist(p);
        tx.commit();
    }

    public static void displayAllPersonnes(EntityManager em) {
        String requete = "select p from Personne p";
        Query query = em.createQuery(requete);
        List<Personne> result = (List<Personne>) query.getResultList();
        for (Personne p : result) {
            afficher(p);
        }
    }
    
     public static void displayAllInterventions(EntityManager em) {
        String requete = "select i from Intervention i";
        Query query = em.createQuery(requete);
        List<Intervention> result = (List<Intervention>) query.getResultList();
        for (Intervention i : result) {
            afficher(i);
        }
    }

    public static void initPersonnes(EntityManager em) {
        Date d=new Date();
        Client p2 = new Client(true, "DELAPIERRE", "ROGER", "motdepasse", "adresse", "063256585", "mail@mail.com",d);
        Employe E2 = new Employe(true, "DUCAILLOUX", "PIERRE", "motdepasse", "adresse", "063256585", "mail@mail.com",true,100,10);
        savePersonne(em, p2);
        savePersonne(em, E2);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory(NOM_PERSISTENCE);
        EntityManager em = emf.createEntityManager();

        //initPersonnes(em);
        Date d=new Date();
        Client p2 = new Client(false, "DELAPIERRE", "ROGER", "motdepasse", "adresse", "063256585", "mail@mail.com",d);
        Client p1 = new Client(false, "Anouchka", "ANDRE", "mdp", "rue de la Paix", "0695666406", "googlemail@mail.com",d);
        Employe E2 = new Employe(true, "DUCAILLOUX", "PIERRE", "present", "adresse", "063256585", "mail@mail.com",true,100,10);
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
            
        //initPersonnes(em);
        displayAllPersonnes(em);
        displayAllInterventions(em);
        em.close();
    }

}
