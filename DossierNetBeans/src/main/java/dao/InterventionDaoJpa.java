/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import metier.modele.Employe;
import metier.modele.Intervention;
import metier.modele.Personne;

/**
 *
 * @author Claharotte
 */
public class InterventionDaoJpa{
    
    public static void creerIntervention(Intervention i){
        EntityManager em=JpaUtil.obtenirEntityManager();
        try {
            em.persist(i);
        } catch(Exception e) {
        }
    }
    
    /*public static List<Intervention> recupererListeIntervention(Personne p){
        EntityManager em=JpaUtil.obtenirEntityManager();
        String jpql = "Select i from Intervention i where i.unclient_id=:id";//or i.UNEMPLOYE_ID=:id";
        Query requete=em.createQuery(jpql);
        requete.setParameter("id",p.getId());
        List<Intervention> result=null; //peut faire ça pr une liste ? 
        try{
            result=(List<Intervention>) requete.getResultList();
        } catch (Exception e) {
        }
        return result;
    }*/
    
    public static Intervention recupererInterventionEnCours(Employe emp){ //Requete à compléter selon les attributs de Intervention dans la bdd
        EntityManager em=JpaUtil.obtenirEntityManager();
        String jpql = "Select i from Intervention i where i.[...]=:id and i.statut=0"; 
        Query requete=em.createQuery(jpql);
        requete.setParameter("id",emp.getId());
        Intervention result=null;
        try{
            result=(Intervention) requete.getSingleResult();
        } catch (Exception e) {
        }
        return result;
    } 
    
    public static void modifierIntervention(Intervention i){ //Pour le cas où on change son statut
        EntityManager em=JpaUtil.obtenirEntityManager();
        try {
            em.merge(i);
        } catch(Exception e) {
        }
    }
    
    
}
