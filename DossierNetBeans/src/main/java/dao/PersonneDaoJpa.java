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
    
    public void creerPersonne(Personne p){
        //JpaUtil.creerEntityManager();
        EntityManager em=JpaUtil.obtenirEntityManager();
        //JpaUtil.ouvrirTransaction();
        em.persist(p);
        /*JpaUtil.validerTransaction();
        JpaUtil.fermerEntityManager();*/
    }
    
}
