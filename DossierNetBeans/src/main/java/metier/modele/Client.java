/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metier.modele;

/**
 *
 * @author Claharotte
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
    private List<Intervention> interventions;
    
    @Temporal(TemporalType.DATE)
    private Date dateDeNaissance;

    public Client() {
    }
 
    public Client(boolean civilite, String nom, String prenom, String motDePasse, String adressePostale, String tel, String mail, Date dateDeNaissance) {
        super(civilite, nom, prenom, motDePasse, adressePostale, tel, mail);
        this.dateDeNaissance = dateDeNaissance;
        this.interventions=new ArrayList<Intervention>();
    }

    public Date getDateDeNaissance() {
        return dateDeNaissance;
    }

    public void setDateDeNaissance(Date dateDeNaissance) {
        this.dateDeNaissance = dateDeNaissance;
    }

    
}
