/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metier.service;

import com.google.maps.model.LatLng;
import dao.InterventionDaoJpa;
import dao.PersonneDaoJpa;
import java.text.SimpleDateFormat;
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
import util.GeoTest;


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
			if(8 < motDePasse.length() && motDePasse.length()< 16 && nom.length()>0 && prenom.length()>0 && mail.length()>2 && mail.contains("@") &&
			adressePostale.length()>0 && tel.length()>0 && ((ahorita.getTime() - dateDeNaissance.getTime()) > 16*365*24*60*60*1000)) {
				Client a = new Client (civilite, nom, prenom, motDePasse, adressePostale, tel, mail, dateDeNaissance);
				PersonneDaoJpa.creerPersonne(a);
                                calculerLatLng(a);
				return a;
			}
		}
		return null;
	} 
        
        public static void calculerLatLng(Personne p){
            LatLng GPS=GeoTest.getLatLng(p.getAdressePostale());
            p.setLongitude(GPS.lng);
            p.setLatitude(GPS.lat);
        }
        
	// I have supposed you cannot have a intervetionanimal without an animal, because then it would be no diference to interventionIncident (sauf le type ;)
	//the same with interventionlivraison
	//actually, we dont even need the type but I'll leave it
	public static Intervention DemanderIntervention(Client c,int type, String description){
                Employe employeTrouve=trouverEmploye(c);
		if (employeTrouve!=null && type>= 0 && type <=3 && description.length()>0){
			InterventionIncident a = new InterventionIncident(description);
                        c.ajouterIntervention(a);
                        employeTrouve.ajouterIntervention(a);
                        employeTrouve.setDispo(false);
			InterventionDaoJpa.creerIntervention(a);
			return a;	
		}
		return null;
	}

	public static Intervention DemanderIntervention(Client c,int type, String description, String objet, String entreprise){
                Employe employeTrouve=trouverEmploye(c);
		if (employeTrouve!=null && type>= 0 && type <=3 && description.length()>0 && objet.length()>0 && entreprise.length()>0){ 
			InterventionLivraison a = new InterventionLivraison(objet, entreprise, description);
                        c.ajouterIntervention(a);
                        employeTrouve.ajouterIntervention(a);
                        employeTrouve.setDispo(false);
			InterventionDaoJpa.creerIntervention(a);
			return a;
		}
		return null;
	}

	public static Intervention DemanderIntervention(Client c,int type, String description, String animal){
                Employe employeTrouve=trouverEmploye(c);
		if (employeTrouve!=null && type>= 0 && type <=3 && description.length()>0 && animal.length()>0 ){
			InterventionAnimal a = new InterventionAnimal(animal, description);
                        c.ajouterIntervention(a);
                        employeTrouve.ajouterIntervention(a);
                        employeTrouve.setDispo(false);
			InterventionDaoJpa.creerIntervention(a);
			return a;
		}
		return null;
	}
	//Im guessing recuperListeIntervention could get the intervention of clients and employes
	/*public static List<Intervention> ConsulterHistorique(Personne p){
		return p.get();
	}*/

        public static int heureActuelleToInt(){
            SimpleDateFormat format = new SimpleDateFormat ("hh:mm");
            Date heureActuelle = new Date();
            String heureString = format.format(heureActuelle);
            String nbHeureString = heureString.split(":")[0];
            int heure=Integer.parseInt(nbHeureString);
            return heure;
        }
        
        //trouve l'employé le plus près à vélo du client
        //il faut aussi que l'heure à laquell est faite la demande soit dans les horaires de l'employé
        public static Employe trouverEmploye(Client c){
            int heure=heureActuelleToInt();
            List <Employe> list=PersonneDaoJpa.trouverListeEmployeDispo(heure);
            Employe employeSelect=null;
            if(list!=null){
                double min=1000000000;
                LatLng clientGPS=new LatLng(c.getLatitude(),c.getLongitude());
                for (Employe e : list){
                            LatLng employeGPS=new LatLng(e.getLatitude(),e.getLongitude());
                            double tempsTrajet=GeoTest.getTripDurationByBicycleInMinute(clientGPS,employeGPS);
                            if(tempsTrajet<min){
                                min=tempsTrajet;
                                employeSelect=e;
                            }
                    }
            }
            return employeSelect;
        }

        public static List<Intervention> recupererHistorique(Client c){
			return c.getListInterventions();
        }
        
	public static boolean CloturerIntervention(Employe emp, int status, int heureDefin, String commentaire){
		Intervention i = RechercherIntervetnionEnCours(emp);
		if (status >= 0 && status <=3 && commentaire.length()>0 && heureDefin >= 0 && heureDefin <= 24 && i != null){
			i.setStatus(status);
			i.setHeureDeFin(heureDefin);
			i.setCommentaire(commentaire);
			InterventionDaoJpa.modifierIntervention(i);
                        //libération de l'employe
                        emp.setDispo(true);
			return true;
		}
		return false;
	}

	public static Intervention RechercherIntervetnionEnCours(Employe emp){
                List<Intervention> list=emp.getListInterventions();
                for (Intervention i : list){
                    if(i.getStatus()==0)
                        return i;
                }
		return null;
	}
}
