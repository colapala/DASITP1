/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metier.service;

import dao.InterventionDaoJpa;
import dao.PersonneDaoJpa;
import java.util.Date;
import java.util.List;
import metier.modele.Client;
import metier.modele.Employe;
import metier.modele.Intervention;
import metier.modele.InterventionAnimal;
import metier.modele.InterventionIncident;
import metier.modele.InterventionLivraison;
import metier.modele.Personne;


/**
 *
 * @author terreu
 *
 */
public class Service{

	PersonneDaoJpa pdj;
	InterventionDaoJpa idj;

        //pas besoin de constructeur normalement
	//Constructeur
	Service(){
		pdj = new PersonneDaoJpa();
		idj = new InterventionDaoJpa();
	}

	public boolean SeConnecter(String mail, String motdepasse){
		Personne a = pdj.recupererPersonne(mail);
		if (a!=null){
			if (a.getMotDePasse()== motdepasse){ // a.getMotDePasse().equals(motdepasse)?
				return true;
			}
		}
		return false;
	}

	public boolean SeInscrire(boolean civilite, String nom, String prenom, String motDePasse, String adressePostale, String tel, String mail, Date dateDeNaissance){
		Personne x = pdj.recupererPersonne(mail);
		if (x==null){
			if(8 < motDePasse.length() && motDePasse.length()< 16){
				Client a = new Client (civilite, nom, prenom, motDePasse, adressePostale, tel, mail, dateDeNaissance);
				pdj.creerPersonne(a);
				return true;
			}
			else{
				System.out.println ("Password must be between 8 and 16 characters");
			}

		}
		else{
			System.out.println ("That mail is already used by someone.");
		}
		return false;
	} 	

	// I have supposed you cannot have a intervetionanimal without an animal, because then it would be no diference to interventionIncident (sauf le type ;)
	//the same with interventionlivraison
	//actually, we dont even need the type but I'll leave it
	public Intervention DemanderIntervention(int type, String description){
			InterventionIncident a = new InterventionIncident(description);
			idj.creerIntervention(a);
			return a;
	}

	public Intervention DemanderIntervention(int type, String description, String objet, String entreprise){
			InterventionLivraison a = new InterventionLivraison(objet, entreprise, description);
			idj.creerIntervention(a);
			return a;
	}

	public Intervention DemanderIntervention(int type, String description, String animal){
			InterventionAnimal a = new InterventionAnimal(animal, description);
			idj.creerIntervention(a);
			return a;
	}
	//Im guessing recuperListeIntervention could get the intervention of clients and employes
	public List<Intervention> ConsulterHistorique(Personne p){
		return idj.recupererListeIntervention(p);
	}
	
	public void AfficherHistorique (List<Intervention> listint){
		for (Intervention i : listint){
			AfficherIntervention(i);
		}
	}

	public boolean CloturerIntervention(Employe emp, int status, int heureDefin, String commentaire){
		Intervention i = RechercherIntervetnionEnCours(emp);
		i.setStatus(status);
		i.setHeureDeFin(heureDefin);
		i.setCommentaire(commentaire);
		idj.modifierIntervention(i);
                return true;
	}

	public Intervention RechercherIntervetnionEnCours(Employe emp){
		return idj.recupererInterventionEnCours(emp);
	}

	public void AfficherIntervention( Intervention i){
		int type = i.getType();
		String description = i.getDescription();
		int status = i.getStatus(); //int ?
                //on pourrait faire un switch, ce serait un peu mieux
		if (type == 1){
			System.out.println ("Intervention Animal");
		}
		else if(type == 2){
			System.out.println ("Intervention Livraison");
		}
                else if (type==3){
			System.out.println ("Intervention Incident");
                }else{
                    System.out.println ("Erreur, nombre invalide");
		}
		System.out.println("desc: " + description + "/n" + "status: " + status + "/n");
	}


	//main to check things
	public static void main(String[] args){
		System.out.println ("Pruebas");
	}

}

