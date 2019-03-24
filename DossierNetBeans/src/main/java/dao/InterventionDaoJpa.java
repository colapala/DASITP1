
package dao;
import javax.persistence.EntityManager;
import metier.modele.Intervention;
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

}
