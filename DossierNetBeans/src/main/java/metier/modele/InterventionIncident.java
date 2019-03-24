
package metier.modele;

import javax.persistence.Entity;

/**
 *
 *@author Chanèle Jourdan, Jorge Terreu, Corentin laharotte
 * Description d'une InterventionIncident
 */
@Entity
public class InterventionIncident extends Intervention{

    public InterventionIncident() {
    }

    public InterventionIncident(String description) {
        super(3, description);
    }
}
