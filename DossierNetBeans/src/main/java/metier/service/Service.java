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

	/*PersonneDaoJpa pdj;
	InterventionDaoJpa idj;

        //pas besoin de constructeur normalement
	//Constructeur
	Service(){
		pdj = new PersonneDaoJpa();
		idj = new InterventionDaoJpa();
	}*/

	public static Personne SeConnecter(String mail, String motdepasse){
		Personne a=PersonneDaoJpa.recupererPersonne(mail);
		if (a!=null){
			if (a.getMotDePasse().equals(motdepasse))//a.getMotDePasse()== motdepasse){ 
				return a;
		}
                return null;
	}

	public static boolean SeInscrire(boolean civilite, String nom, String prenom, String motDePasse, String adressePostale, String tel, String mail, Date dateDeNaissance){
		Personne x = PersonneDaoJpa.recupererPersonne(mail);
		if (x==null){
			if(8 < motDePasse.length() && motDePasse.length()< 16){
				Client a = new Client (civilite, nom, prenom, motDePasse, adressePostale, tel, mail, dateDeNaissance);
				PersonneDaoJpa.creerPersonne(a);
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
	public static Intervention DemanderIntervention(int type, String description){
			InterventionIncident a = new InterventionIncident(description);
			InterventionDaoJpa.creerIntervention(a);
			return a;
	}

	public static Intervention DemanderIntervention(int type, String description, String objet, String entreprise){
			InterventionLivraison a = new InterventionLivraison(objet, entreprise, description);
			InterventionDaoJpa.creerIntervention(a);
			return a;
	}

	public static Intervention DemanderIntervention(int type, String description, String animal){
			InterventionAnimal a = new InterventionAnimal(animal, description);
			InterventionDaoJpa.creerIntervention(a);
			return a;
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
		i.setStatus(status);
		i.setHeureDeFin(heureDefin);
		i.setCommentaire(commentaire);
		InterventionDaoJpa.modifierIntervention(i);
                return true;
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
		System.out.println("desc: " + description + "/n" + "status: " + status + "/n");
	}


	//main to check things
	/*public static void main(String[] args){
		
	}*/

}

