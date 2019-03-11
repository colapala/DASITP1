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
import javax.persistence.Entity;

@Entity
public class Client extends Personne {

    private int dateDeNaissance;

    public Client() {
    }

    public Client(boolean civilite, String nom, String prenom, String motDePasse, String adressePostale, String tel, String mail) {
        super(civilite, nom, prenom, motDePasse, adressePostale, tel, mail);
    }

   
    public Client(boolean civilite, String nom, String prenom, String motDePasse, String adressePostale, String tel, String mail, int dateDeNaissance) {
        super(civilite, nom, prenom, motDePasse, adressePostale, tel, mail);
        this.dateDeNaissance = dateDeNaissance;
    }

    public int getDateDeNaissance() {
        return dateDeNaissance;
    }

    public void setDateDeNaissance(int dateDeNaissance) {
        this.dateDeNaissance = dateDeNaissance;
    }

    
}
