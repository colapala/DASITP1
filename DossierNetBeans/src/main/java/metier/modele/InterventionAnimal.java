/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metier.modele;

import javax.persistence.Entity;

/**
 *
 * @author colap
 */
@Entity
public class InterventionAnimal extends Intervention {
    private String animal;

    public InterventionAnimal() {
    }
    
    public InterventionAnimal(String animal) {
        this.animal = animal;
    }

    public InterventionAnimal(String animal, String description) {
        super(1, description);//initialisation du type à 1
        this.animal = animal;
    }

    public String getAnimal() {
        return animal;
    }

    public void setAnimal(String animal) {
        this.animal = animal;
    }
}