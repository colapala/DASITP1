
package metier.modele;

import javax.persistence.Entity;

/**
 *
 * @author Chanèle Jourdan, Jorge Terreu, Corentin laharotte
 * Description d'une InterventionAnimal
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
