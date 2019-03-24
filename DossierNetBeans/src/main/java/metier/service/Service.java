/* @author jorge terreu, corentin laharotte, chanèle jourdan 
   Classe qui implémente l'ensemble des services de notre application
*/

package metier.service;

import com.google.maps.model.LatLng;
import dao.InterventionDaoJpa;
import dao.JpaUtil;
import dao.PersonneDaoJpa;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import util.Message;

public class Service{

	//Connection du client ou de l'employé à l'application
	public static Personne seConnecter(String mail, String motdepasse){
                JpaUtil.creerEntityManager();
                JpaUtil.ouvrirTransaction();
		Personne a=PersonneDaoJpa.recupererPersonne(mail);
		if (a!=null){
			if (!(a.getMotDePasse().equals(motdepasse)))
                            a=null;      
		}
                JpaUtil.validerTransaction();
                JpaUtil.fermerEntityManager();
                return a;
	}

	//Inscription d'un client. Vérifications effectuées : tous les champs ont été remplis, le mail contient un @, le client a plus de 16 ans, le tel contient 10 caractères
	public static Personne seInscrire(boolean civilite, String nom, String prenom, String motDePasse, String adressePostale, String tel, String mail, Date dateDeNaissance){
                JpaUtil.creerEntityManager();
                JpaUtil.ouvrirTransaction();
		Personne x = PersonneDaoJpa.recupererPersonne(mail);
		if (x==null){
			Date ahorita = new Date();
                        //seize ans en millisecondes
			double seizeAns= 16.0*365.0*24.0*3600.0*1000.0;
                        //différence de temps en millisecondes entre aujourd'hui et dateDeNaissance
                        double differenceTemps=ahorita.getTime() - dateDeNaissance.getTime();
                        
			if(8 < motDePasse.length() && motDePasse.length()< 16 && nom.length()>0 && prenom.length()>0 && mail.length()>2 && mail.contains("@") &&
			adressePostale.length()>0 && tel.length()==10 && (differenceTemps > seizeAns)) {

				//Création et persistence d'un nouveau client
				x = new Client (civilite, nom, prenom, motDePasse, adressePostale, tel, mail, dateDeNaissance);
				calculerLatLng(x);
                                PersonneDaoJpa.creerPersonne(x);
				
				//Envoi de mail de confirmation d'inscription
				String message="Bonjour "+prenom+", nous vous confirmons votre inscription au service PROACT'IF.";
				Message.envoyerMail("contact@proact.if", mail, "Bienvenue chez PROACT'IF", message);
			}
		}else{
		//Envoie de mail pour informer de l'échec de l'inscription
		String message="Bonjour "+prenom+",  votre inscription a échoué, veuillez recommencer ultérieurement.";
		Message.envoyerMail("contact@proact.if", mail, "Bienvenue chez PROACT'IF", message);
		x=null;
                }
                JpaUtil.validerTransaction();
                JpaUtil.fermerEntityManager();
		return x;
	} 
        
	//Demande d'une intervention de type Animal. Vérification : tous les champs sont remplis
	public static Employe demanderIntervention(Client c,int type, String description){
                JpaUtil.creerEntityManager();
                JpaUtil.ouvrirTransaction();
                Employe employeTrouve=trouverEmploye(c);
		if (employeTrouve!=null && type>= 0 && type <=3 && description.length()>0){
			
			//Création et persistence de l'intervention
			InterventionIncident a = new InterventionIncident(description);
                        a.setHorodate(new  Date());
                        c.ajouterIntervention(a);
                        employeTrouve.ajouterIntervention(a);
                        employeTrouve.setDispo(false);
			InterventionDaoJpa.creerIntervention(a);
			
			//Envoi d'une notification à l'employé trouvé
			String message = "Intervention Animal demandee pour " +c.getPrenom()+ " " +c.getNom()+ ", " +c.getAdressePostale()+ ". Commentaire : " +description;			
			Message.envoyerNotification(employeTrouve.getNom(), employeTrouve.getPrenom(), employeTrouve.getTel(), message);	
                }else{
                    employeTrouve=null;
                }
                JpaUtil.validerTransaction();
                JpaUtil.fermerEntityManager();
                return employeTrouve;

                
	}

	//Demande d'une intervention de type Livraison. Vérification : tous les champs sont remplis
	public static Employe demanderIntervention(Client c,int type, String description, String objet, String entreprise){
                JpaUtil.creerEntityManager();
                JpaUtil.ouvrirTransaction();
                Employe employeTrouve=trouverEmploye(c);
		if (employeTrouve!=null && type>= 0 && type <=3 && description.length()>0 && objet.length()>0 && entreprise.length()>0){ 
			
			//Creation et persistence de l'intervention
			InterventionLivraison a = new InterventionLivraison(objet, entreprise, description);
                        a.setHorodate(new  Date());
                        c.ajouterIntervention(a);
                        employeTrouve.ajouterIntervention(a);
                        employeTrouve.setDispo(false);
			InterventionDaoJpa.creerIntervention(a);
			
			//Envoi d'une notification à l'employé trouvé
			String message = "Intervention Livraison demandee pour " +c.getPrenom()+ " " +c.getNom()+ ", " +c.getAdressePostale()+ ". Commentaire : " +description;			
			Message.envoyerNotification(employeTrouve.getNom(), employeTrouve.getPrenom(), employeTrouve.getTel(), message);
				
		}else{
                    employeTrouve=null;
                }
                JpaUtil.validerTransaction();
                JpaUtil.fermerEntityManager();
		return employeTrouve;
	}

	//Demande d'une intervention de type Incident. Vérification : tous les champs sont remplis
	public static Employe demanderIntervention(Client c,int type, String description, String animal){
                JpaUtil.creerEntityManager();
                JpaUtil.ouvrirTransaction();
                Employe employeTrouve=trouverEmploye(c);
		if (employeTrouve!=null && type>= 0 && type <=3 && description.length()>0 && animal.length()>0 ){
			
			//Creation et persistence de l'intervention
			InterventionAnimal a = new InterventionAnimal(animal, description);
                        a.setHorodate(new  Date());
                        c.ajouterIntervention(a);
                        employeTrouve.ajouterIntervention(a);
                        employeTrouve.setDispo(false);
			InterventionDaoJpa.creerIntervention(a);
			
			//Envoi d'une notification à l'employé trouvé
			String message = "Intervention Incident demandee pour " +c.getPrenom()+ " " +c.getNom()+ ", " +c.getAdressePostale()+ ". Commentaire : " +description;			
			Message.envoyerNotification(employeTrouve.getNom(), employeTrouve.getPrenom(), employeTrouve.getTel(), message);			
		}else{
                    employeTrouve=null;
                }
                JpaUtil.validerTransaction();
                JpaUtil.fermerEntityManager();
		return employeTrouve;
	}
        
        //Recherche de l'employé le plus proche du client pour faire une intervention.
	//Vérification : il est disponible, l'heure de la demande d'intervention est dans ses horaires de travail
        public static Employe trouverEmploye(Client c){
            int heure=heureActuelleToInt();
            
            //Récupération de la liste des employés disponibles
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

	//Récupération de l'historique d'un client pour le consulter
        public static List<Intervention> recupererHistorique(Client c){
            JpaUtil.creerEntityManager();
            JpaUtil.ouvrirTransaction();
            PersonneDaoJpa.modifierPersonne(c);
            JpaUtil.validerTransaction();
            JpaUtil.fermerEntityManager();
            return c.getListInterventions();
        }
	
	//Récupération des interventions d'un employé pour les consulter (tableau de bord)
        public static List<Intervention> recupererTableauDeBord(Employe e){
            List <Intervention>l=e.getListInterventions();
            List <Intervention> list=new ArrayList<Intervention>();
            Date d=new Date();
            for (Intervention i: l){
			if(d.compareTo(i.getHorodate())!=0)
                            list.add(i);
            }
            return list;
        }
        
	//Clotûre d'une intervention par un employé. Vérification : tous les champs sont remplis
	public static boolean cloturerIntervention(Employe emp, int status, int heureDefin, String commentaire){
                JpaUtil.creerEntityManager();
                JpaUtil.ouvrirTransaction();
		Intervention i = rechercherInterventionEnCours(emp);
                boolean cloture=false;
		if (status >= 0 && status <=3 && commentaire.length()>0 && heureDefin >= 0 && heureDefin <= 24 && i != null){
			
			//Modification de l'intervention
			i.setStatus(status);
			i.setHeureDeFin(heureDefin);
			i.setCommentaire(commentaire);
			InterventionDaoJpa.modifierIntervention(i);
			
                        //libération de l'employe
                        emp.setDispo(true);
                        PersonneDaoJpa.modifierPersonne(emp);
			
			//Envoi d'une notification au client
			String message = "Votre demande d'intervention a ete cloturee. Commentaire de l'employe : " +commentaire;			
			Message.envoyerNotification(i.getUnClient().getNom(), i.getUnClient().getPrenom(), i.getUnClient().getTel(), message);
			
			 cloture=true;
		}
                JpaUtil.validerTransaction();
                JpaUtil.fermerEntityManager();
		return cloture;
	}

	//Recherche de l'intervention en cours pour un employé donnée (s'il en a une)
	public static Intervention rechercherInterventionEnCours(Employe emp){
                List<Intervention> list=emp.getListInterventions();
                for (Intervention i : list){
                    if(i.getStatus()==0)
                        return i;
                }
		return null;
	}
	
	//Calcul des coordonnées GPS d'un employé ou d'un client
        public static void calculerLatLng(Personne p){
            LatLng GPS=GeoTest.getLatLng(p.getAdressePostale());
            p.setLongitude(GPS.lng);
            p.setLatitude(GPS.lat);
        }
	
	//SERVICE EN PLUS DE CEUX SPECIFIES DANS LE COMPTE RENDU 
	//Récupération de l'heure actuelle (pour savoir à quel moment est faite une demande d'intervention)
	 public static int heureActuelleToInt(){
            SimpleDateFormat format = new SimpleDateFormat ("HH:mm");
            Date heureActuelle = new Date();
            String heureString = format.format(heureActuelle);
            String nbHeureString = heureString.split(":")[0];
            int heure=Integer.parseInt(nbHeureString);
            return heure;
        }
}
