/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;
import javax.persistence.EntityManager;
import javax.persistence.Query;
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
        EntityManager em=JpaUtil.obtenirEntityManager();
        String jpql= "Select p from Personne p where p.mail=:email";
        Query requete=em.createQuery(jpql);
        requete.setParameter("email",mail);
        Personne result=null;
        try{
            result=(Personne) requete.getSingleResult();
        } catch (Exception e){
        }
        return result;
    }
    
    //Pas sure de la structure : requete à compléter quand on aura vu comment on fonctionne ac le truc de google maps
    public static Employe trouverEmployeDispo(Intervention i){ 
        EntityManager em=JpaUtil.obtenirEntityManager();
        String jpql= "Select p1 from Personne p1, Personne p2, Intervention i where i.[...]=:id and i.[...]=p2.[...]and [p1.lat p2.lat p1.long p2.long etc] ";
        Query requete=em.createQuery(jpql);
        requete.setParameter("id", i.getId());
        Employe result=null;
        try{
            result=(Employe) requete.getSingleResult();
        } catch (Exception e){
        }
        return result;
    }
    
    
    //Besoin de pouvoir trouver une Personne à partir d'une intervention ? oui pour trouver les coordonnées du client quand l'employé clôture une intervention
    
}
