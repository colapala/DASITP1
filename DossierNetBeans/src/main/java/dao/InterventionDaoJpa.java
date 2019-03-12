/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;
import javax.persistence.EntityManager;
import metier.modele.Intervention;
import metier.modele.Personne;

/**
 *
 * @author Claharotte
 */
public class InterventionDaoJpa{
    
    public void creeIntervention(Intervention i){
        EntityManager em=JpaUtil.obtenirEntityManager();
        try {
            em.persist(i);
        } catch(Exception e) {
        }
    }
    
    public List<Intervention> recupererIntervention(int idPers){
        EntityManager em=JpaUtil.obtenirEntityManager();
        String jpql = "Select i from Intervention i where i.[...]=:id";
        Query requete=em.createQuery(jpql);
        requete.setParameter("id",idPers);
        List<Intervention> result=null; //peut faire ça pr une liste ?
        try{
            result=(List<Intervention>) query.getResultList();
        } catch (Exception e) {
        }
        return result;
    }
    
    public void modifierIntervention(Intervention i) //Pour le cas où on change son statut
        EntityManager em=JpaUtil.obtenirEntityManager();
        try {
            em.merge(i);
        } catch(Exception e) {
        }
    }
    
    
}
