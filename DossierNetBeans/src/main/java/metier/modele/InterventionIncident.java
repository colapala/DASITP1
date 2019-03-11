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
public class InterventionIncident extends Intervention{

    public InterventionIncident() {
    }

    public InterventionIncident(String type, String description) {
        super(type, description);
    }
}
