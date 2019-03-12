/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metier.modele;

import javax.persistence.Entity;

/**
 *
 * @author Claharotte
 */
@Entity
public class Employe extends Personne {
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

    public void setDispo(boolean dispo) {
        this.dispo = dispo;
    }

    public void setHoraireEntree(int horaireEntree) {
        this.horaireEntree = horaireEntree;
    }

    public void setHoraireSortie(int horaireSortie) {
        this.horaireSortie = horaireSortie;
    }
    
    
    
}
