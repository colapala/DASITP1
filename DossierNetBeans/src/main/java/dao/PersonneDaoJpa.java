
package dao;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import metier.modele.Employe;
import metier.modele.Personne;

/**
 *
 * @author Chanèle Jourdan, Jorge Terreu, Corentin laharotte
 * Classe qui gère la persistance des Personnes
 */
public class PersonneDaoJpa{
    
    public static void creerPersonne(Personne p){
        EntityManager em=JpaUtil.obtenirEntityManager();
        try {
            em.persist(p);
        } catch(Exception e) {
        }
    }
    
    //mise à jour de la Personne
     //Pour le cas où on change sa disponibilité
    public static void modifierPersonne(Personne p){
        EntityManager em=JpaUtil.obtenirEntityManager();
        try {
            em.merge(p);
        } catch(Exception e) {
        }
    }
    
    //Trouve la peronne qui possède le mail indiqué
    public static Personne recupererPersonne(String mail){
        String jpql= "Select p from Personne p where p.mail=:email";
        Query requete=JpaUtil.obtenirEntityManager().createQuery(jpql);
        requete.setParameter("email",mail);
        Personne result=null;
        try{
            result=(Personne) requete.getSingleResult();
        } catch (Exception e){
        }
        return result;
    }
    
      //Trouve la peronne qui possède l'id indiqué
    public static Personne recupererPersonne(int id){
        String jpql= "Select p from Personne p where p.id=:id";
        Query requete=JpaUtil.obtenirEntityManager().createQuery(jpql);
        requete.setParameter("id",id);
        Personne result=null;
        try{
            result=(Personne) requete.getSingleResult();
        } catch (Exception e){
        }
        return result;
    }
    
    //Trouve tous les employés disponibles et qui travaillent à l'heure indiquée
    public static List<Employe> trouverListeEmployeDispo(int heure){ 
        EntityManager em=JpaUtil.obtenirEntityManager();
        String jpql= "Select e from Employe e where e.dispo=1 and e.horaireEntree<=:heure and e.horaireSortie>:heure";
        Query requete=em.createQuery(jpql);
        requete.setParameter("heure",heure);
        List<Employe> result=null;
        try{
            result=(List<Employe>) requete.getResultList();
        } catch (Exception e){
        }
        return result;
    }

}
