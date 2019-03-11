/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metier.modele;

/**
 *
 * @author colap
 */

import javax.persistence.Entity;

@Entity
public class InterventionLivraison extends Intervention {
    private String objet;
    private String entreprise;

    public InterventionLivraison() {
    }

    public InterventionLivraison(String type,String objet, String entreprise, String description) {
        super(type, description);
        this.objet = objet;
        this.entreprise = entreprise;
    }

    public String getObjet() {
        return objet;
    }

    public String getEntreprise() {
        return entreprise;
    }

    public void setObjet(String objet) {
        this.objet = objet;
    }

    public void setEntreprise(String entreprise) {
        this.entreprise = entreprise;
    }

}
