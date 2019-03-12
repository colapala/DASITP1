/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;
import javax.persistence.EntityManager;
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
    
    public void creerPersonne(Personne p){
        EntityManager em=JpaUtil.obtenirEntityManager();
        em.persist(p);
    }
    
    public Personne recupererPersonne(String mail){
        EntityManager em=JpaUtil.obtenirEntityManager();
        Query requete = "Select p from Personne p where p.mail=:email";
        requete.setParameter("email",mail);
        Personne result=(Personne) requete.getSingleResult();
        return result;
    }
    
}
