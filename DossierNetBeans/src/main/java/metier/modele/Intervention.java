/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metier.modele;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.ManyToOne;

/**
 *
 * @author colap
 */
@Entity
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
public abstract class Intervention implements Serializable {
    @ManyToOne
    private Personne unePersonne;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    
    private int type;
    private String description;
    
    private int status;
    private int heureDeFin;
    private String commentaire;
    
    @Temporal(TemporalType.DATE)
     private Date horodate;

    public Intervention() {
    }
    
    public Intervention(int type, String description) {
        this.type = type;
        this.description = description;
    }

     //*****************************************************
    //A enlever 
     //***************************************************
    public void ajouterPersonne(Personne p){
        unePersonne=p;
    }
    
    public Integer getId() {
        return id;
    }

    public int getStatus() {
        return status;
    }

    public int getType() {
        return type;
    }

    public String getDescription() {
        return description;
    }

    public int getHeureDeFin() {
        return heureDeFin;
    }

    public String getCommentaire() {
        return commentaire;
    }

    public Date getHorodate() {
        return horodate;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setHeureDeFin(int heureDeFin) {
        this.heureDeFin = heureDeFin;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }

    public void setHorodate(Date horodate) {
        this.horodate = horodate;
    } 
    
}
