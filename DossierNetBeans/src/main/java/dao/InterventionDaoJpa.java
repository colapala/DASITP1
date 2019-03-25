
package dao;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import metier.modele.Intervention;
import metier.modele.Personne;
/**
 *
 * @author Chanèle Jourdan, Jorge Terreu, Corentin laharotte
 * Classe qui gère la persistance des interventions
 * 
 */
public class InterventionDaoJpa{
    
    //persite l'intervention placée en paramètre
    public static void creerIntervention(Intervention i){
        EntityManager em=JpaUtil.obtenirEntityManager();
        try {
            em.persist(i);
        } catch(Exception e) {
        }
    }
    
    //mise à jour de l'intervention
     //Pour le cas où on change son statut
    public static void modifierIntervention(Intervention i){
        EntityManager em=JpaUtil.obtenirEntityManager();
        try {
            em.merge(i);
        } catch(Exception e) {
        }
    }
    
          //Trouve la peronne qui possède l'id indiqué
    public static Intervention recupererIntervention(int id){
        String jpql= "Select i from Intervention i where i.id=:id";
        Query requete=JpaUtil.obtenirEntityManager().createQuery(jpql);
        requete.setParameter("id",id);
        Intervention result=null;
        try{
            result=(Intervention)requete.getSingleResult();
        } catch (Exception e){
        }
        return result;
    }

}
