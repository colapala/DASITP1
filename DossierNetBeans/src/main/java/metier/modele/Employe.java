
package metier.modele;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

/**
 *
 * @author Chanèle Jourdan, Jorge Terreu, Corentin laharotte
 * Description d'un Employe
 */
@Entity
public class Employe extends Personne {
    @OneToMany(mappedBy="unEmploye")
    private List<Intervention> listInterventions;
    
    boolean dispo;
    int horaireEntree;
    int horaireSortie;
    
    public Employe() {
    }

    public Employe(boolean civilite, String nom, String prenom, String motDePasse, String adressePostale, String tel, String mail,boolean dispo,int horaireEntree,int horaireSortie) {
        super(civilite, nom, prenom, motDePasse, adressePostale, tel, mail);
        this.dispo=dispo;
        this.horaireEntree=horaireEntree;
        this.horaireSortie=horaireSortie;
        this.listInterventions=new ArrayList<Intervention>();
    }

    public void ajouterIntervention(Intervention i){
       listInterventions.add(i);
       i.setUnEmploye(this);
    }
    
    public boolean isDispo() {
        return dispo;
    }

    public int getHoraireEntree() {
        return horaireEntree;
    }

    public int getHoraireSortie() {
        return horaireSortie;
    }
    
    public List<Intervention> getListInterventions() {
        return listInterventions;
    }
    public void setDispo(boolean dispo) {
        this.dispo = dispo;
    }

    public void setHoraireEntree(int horaireEntree) {
        this.horaireEntree = horaireEntree;
    }

    public void setHoraireSortie(int horaireSortie) {
        this.horaireSortie = horaireSortie;
    }

    public void setListInterventions(List<Intervention> interventions) {
        this.listInterventions = interventions;
    }
    
}
