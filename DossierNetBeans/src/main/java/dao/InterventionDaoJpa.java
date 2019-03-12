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
public class InterventionDaoJpa{
    
    public void creeIntervention(Intervention i){
        EntityManager em=JpaUtil.obtenirEntityManager();
        em.persist(i);
    }
    
    public List<Intervention> recupererIntervention(int idPers){
        EntityManager em=JpaUtil.obtenirEntityManager();
        Query requete = "Select i from Intervention i where i.[...]=:id";
        requete.setParameter("id",idPers);
        List<Intervention> result=(List<Intervention>) query.getResultList();
        return result;
    }
    
}
