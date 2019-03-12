/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metier.modele;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

/**
 *
 * @author Claharotte
 */
@Entity
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
public abstract class Personne implements Serializable {
    @OneToMany(mappedBy="unePersonne")
    private List<Intervention> interventions;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String nom;
    private String prenom;
    private boolean civilite;
    private String motDePasse;
    
    @Transient
    private String adressePostale;
    
    private String tel;
    private String mail;
    
    //A calculer à partir de l'adresse
    private Float latitude;
    private Float longitude;
    

    public Personne() {
    }

    public Personne(boolean civilite, String nom, String prenom, String motDePasse, String adressePostale, String tel, String mail) {
        this.civilite = civilite;
        this.nom = nom;
        this.prenom = prenom;
        this.motDePasse = motDePasse;
        this.adressePostale = adressePostale;
        this.tel = tel;
        this.mail = mail;
        this.interventions=new ArrayList<Intervention>();
    }
   


    public void setCivilite(boolean civilite) {
        this.civilite = civilite;
    }
    
    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }

    public void setAdressePostale(String adressePostale) {
        this.adressePostale = adressePostale;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }
    
    public void setInterventions(List<Intervention> interventions) {
        this.interventions = interventions;
    }

    public void setLatitude(Float latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(Float longitude) {
        this.longitude = longitude;
    }

    public Integer getId() {
        return id;
    }

    public boolean isCivilite() {
        return civilite;
    }

    public String getNom() {
        return this.nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public String getMotDePasse() {
        return motDePasse;
    }

    public String getAdressePostale() {
        return adressePostale;
    }

    public String getTel() {
        return tel;
    }

    public String getMail() {
        return mail;
    }

    public List<Intervention> getInterventions() {
        return interventions;
    }

    public Float getLatitude() {
        return latitude;
    }

    public Float getLongitude() {
        return longitude;
    }

}
