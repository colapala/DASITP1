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
import java.util.Date;


/**
 *
 * @author terreu
 *
 */
public class Service{


	public static Personne SeConnecter(String mail, String motdepasse){

		Personne a=PersonneDaoJpa.recupererPersonne(mail);
		if (a!=null){
			if (a.getMotDePasse().equals(motdepasse)){//a.getMotDePasse()== motdepasse){ 
				return a;
			}
		}
                return null;
	}

	// On verifie ici que:
	// nothing is null,
	// mail contains @
	// person is over 16 years old
	public static Personne SeInscrire(boolean civilite, String nom, String prenom, String motDePasse, String adressePostale, String tel, String mail, Date dateDeNaissance){
		Personne x = PersonneDaoJpa.recupererPersonne(mail);
		if (x==null){
			Date ahorita = new Date();
			if(8 < motDePasse.length() && motDePasse.length()< 16 && nom.lenght()>0 && prenom.lenght()>0 && mail.lenght()>2 && mail.contains("@") &&
			adressePostale.lenght()>0 && tel.lenght()>0 && ((ahorita.getTime() - dateDeNaissance.getTime()) > 16*365*24*60*60*1000)) {
				Client a = new Client (civilite, nom, prenom, motDePasse, adressePostale, tel, mail, dateDeNaissance);
				PersonneDaoJpa.creerPersonne(a);
				return a;
			}
		}
		return null;
	} 	

	// I have supposed you cannot have a intervetionanimal without an animal, because then it would be no diference to interventionIncident (sauf le type ;)
	//the same with interventionlivraison
	//actually, we dont even need the type but I'll leave it
	public static Intervention DemanderIntervention(int type, String description){
		if (type>= 0 && type <=3 && description.lenght()>0){
			InterventionIncident a = new InterventionIncident(description);
			InterventionDaoJpa.creerIntervention(a);
			return a;	
		}
		return null;
	}

	public static Intervention DemanderIntervention(int type, String description, String objet, String entreprise){
		if (type>= 0 && type <=3 && description.lenght()>0 && objet.length()>0 && entreprise.length()>0){ 
			InterventionLivraison a = new InterventionLivraison(objet, entreprise, description);
			InterventionDaoJpa.creerIntervention(a);
			return a;
		}
		return null;
	}

	public static Intervention DemanderIntervention(int type, String description, String animal){
		if (type>= 0 && type <=3 && description.lenght()>0 && objet.length()>0 && entreprise.length()>0){
			InterventionAnimal a = new InterventionAnimal(animal, description);
			InterventionDaoJpa.creerIntervention(a);
			return a;
		}
		return null;
	}
	//Im guessing recuperListeIntervention could get the intervention of clients and employes
	public static List<Intervention> ConsulterHistorique(Personne p){
		return InterventionDaoJpa.recupererListeIntervention(p);
	}
	
	public static void AfficherHistorique (List<Intervention> listint){
		for (Intervention i : listint){
			AfficherIntervention(i);
		}
	}

	public static boolean CloturerIntervention(Employe emp, int status, int heureDefin, String commentaire){
		Intervention i = RechercherIntervetnionEnCours(emp);
		if (status >= 0 && status <=3 && commentaire.lenght()>0 && heureDefin >= 0 && heureDefin < 24 && i != null){
			i.setStatus(status);
			i.setHeureDeFin(heureDefin);
			i.setCommentaire(commentaire);
			InterventionDaoJpa.modifierIntervention(i);
			return true;
		}
		return false;
	}

	public static Intervention RechercherIntervetnionEnCours(Employe emp){
		return InterventionDaoJpa.recupererInterventionEnCours(emp);
	}

	public static void AfficherIntervention( Intervention i){
		int type = i.getType();
		String description = i.getDescription();
		int status = i.getStatus();
            //on pourrait faire un switch, ce serait un peu mieux
            switch (type) {
                case 1:
                    System.out.println ("Intervention Animal");
                    break;
                case 2:
                    System.out.println ("Intervention Livraison");
                    break;
                case 3:
                    System.out.println ("Intervention Incident");
                    break;
                default:
                    System.out.println ("Erreur, nombre invalide");
                    break;
            }
		System.out.println("status: " + status + "/n" + "desc: " + description + "/n" );
	}


	//main to check things
	public static void main(String[] args){
		//System.out.println ("Pruebas");
	}
}
