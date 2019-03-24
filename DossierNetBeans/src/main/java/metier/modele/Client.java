
package metier.modele;

/**
 *
 * @author Chanèle Jourdan, Jorge Terreu, Corentin laharotte
 * Description d'un Client
 */
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Client extends Personne {
    @OneToMany(mappedBy="unClient")
    private List<Intervention> listInterventions;
    
    @Temporal(TemporalType.DATE)
    private Date dateDeNaissance;

    public Client() {
        this.listInterventions=new ArrayList<Intervention>();
    }
 
    public Client(boolean civilite, String nom, String prenom, String motDePasse, String adressePostale, String tel, String mail, Date dateDeNaissance) {
        super(civilite, nom, prenom, motDePasse, adressePostale, tel, mail);
        this.dateDeNaissance = dateDeNaissance;
        this.listInterventions=new ArrayList<Intervention>();
    }

    public void ajouterIntervention(Intervention i){
       listInterventions.add(i);
       i.setUnClient(this);
    }
    
    public Date getDateDeNaissance() {
        return dateDeNaissance;
    }

    public void setDateDeNaissance(Date dateDeNaissance) {
        this.dateDeNaissance = dateDeNaissance;
    }

    public List<Intervention> getListInterventions() {
        return listInterventions;
    }

    public void setListInterventions(List<Intervention> listInterventions) {
        this.listInterventions = listInterventions;
    }
   
}
