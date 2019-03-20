/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import metier.modele.Client;
import metier.modele.Employe;
import metier.modele.Intervention;
import metier.modele.Personne;

/**
 *
 * @author Claharotte
 */
public class PersonneDaoJpa{
    
    /*A mettre dans service : 
        JpaUtil.creerEntityManager();
        JpaUtil.ouvrirTransaction();
       [...]
        JpaUtil.validerTransaction();
        JpaUtil.fermerEntityManager();*/
    
    /*A mettre dans le main une unique fois :
        JpaUtil.init();
        [...]
        JpaUtil.destroy();
    */
    
    public static void creerPersonne(Personne p){
        EntityManager em=JpaUtil.obtenirEntityManager();
        try {
            em.persist(p);
        } catch(Exception e) {
        }
    }
    
    public static Personne recupererPersonne(String mail){
//        EntityManager em=JpaUtil.obtenirEntityManager();
        String jpql= "Select p from Personne p where p.mail=:email";
        Query requete=JpaUtil.obtenirEntityManager().createQuery(jpql);
        requete.setParameter("email",mail);
        Personne result=null;
        try{
            result=(Personne) requete.getSingleResult();
          //  ((Client)result).getListInterventions().size();
        } catch (Exception e){
        }
        return result;
    }
    
    //Pas sure de la structure : requete à compléter quand on aura vu comment on fonctionne ac le truc de google maps
    public static List<Employe> trouverListeEmployeDispo(){ 
        EntityManager em=JpaUtil.obtenirEntityManager();
        String jpql= "Select e from Employe e where e.dispo=1";
        Query requete=em.createQuery(jpql);
        List<Employe> result=null;
        try{
            result=(List<Employe>) requete.getResultList();
        } catch (Exception e){
        }
        return result;
    }
    
    
    //Besoin de pouvoir trouver une Personne à partir d'une intervention ? oui pour trouver les coordonnées du client quand l'employé clôture une intervention
    
}
