package mongoDB.fabrique;

import mongoDB.modele.Abonnement;
import mongoDB.modele.GestionAbonnement;
import mongoDB.modele.Ticket;
import org.bson.types.ObjectId;

import java.util.Collection;

public class FabriqueGestionAbonnement {

    public static GestionAbonnement createGestionAbonnement(String mail,Abonnement abonnement, Collection<Ticket> tickets){
        GestionAbonnement gestionAbonnement = new GestionAbonnement();

        gestionAbonnement.setMailUtilisateur(mail);
        gestionAbonnement.setId(new ObjectId());
        gestionAbonnement.setAbonnement(abonnement);
        gestionAbonnement.setTickets(tickets);


        return gestionAbonnement;
    }
    public static GestionAbonnement createGestionAbonnement(String mail,Abonnement abonnement){
        GestionAbonnement gestionAbonnement = new GestionAbonnement();

        gestionAbonnement.setMailUtilisateur(mail);
        gestionAbonnement.setId(new ObjectId());
        gestionAbonnement.setAbonnement(abonnement);



        return gestionAbonnement;
    }
    public static GestionAbonnement createGestionAbonnement(String mail,Collection<Ticket> tickets){
        GestionAbonnement gestionAbonnement = new GestionAbonnement();

        gestionAbonnement.setMailUtilisateur(mail);
        gestionAbonnement.setId(new ObjectId());

        gestionAbonnement.setTickets(tickets);


        return gestionAbonnement;
    }
    public static GestionAbonnement createGestionAbonnement(String mail){
        GestionAbonnement gestionAbonnement = new GestionAbonnement();

        gestionAbonnement.setMailUtilisateur(mail);
        gestionAbonnement.setId(new ObjectId());



        return gestionAbonnement;
    }






}
