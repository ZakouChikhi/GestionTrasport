package mongoDB.fabrique;

import mongoDB.modele.Abonnement;
import mongoDB.modele.GestionAbonnement;
import mongoDB.modele.Ticket;
import org.bson.types.ObjectId;

import java.util.Collection;

public class FabriqueGestionAbonnement {

    /**
     * permet de creer une gestion d'abonnement à partir des entrées
     * @param mail
     * @param abonnement
     * @param tickets
     * @return
     */
    public static GestionAbonnement createGestionAbonnement(String mail,Abonnement abonnement, Collection<Ticket> tickets){
        GestionAbonnement gestionAbonnement = new GestionAbonnement();

        gestionAbonnement.setMailUtilisateur(mail);
        gestionAbonnement.setId(new ObjectId());
        gestionAbonnement.setAbonnement(abonnement);
        gestionAbonnement.setTickets(tickets);


        return gestionAbonnement;
    }

    /**
     * permet de creer une gestion d'abonnement à partir des entrées
     * @param mail
     * @param abonnement
     * @return
     */
    public static GestionAbonnement createGestionAbonnement(String mail,Abonnement abonnement){
        GestionAbonnement gestionAbonnement = new GestionAbonnement();

        gestionAbonnement.setMailUtilisateur(mail);
        gestionAbonnement.setId(new ObjectId());
        gestionAbonnement.setAbonnement(abonnement);



        return gestionAbonnement;
    }


    /**
     * permet de creer une gestion d'abonnement à partir des entrées
     * @param mail
     * @param tickets
     * @return
     */
    public static GestionAbonnement createGestionAbonnement(String mail,Collection<Ticket> tickets){
        GestionAbonnement gestionAbonnement = new GestionAbonnement();

        gestionAbonnement.setMailUtilisateur(mail);
        gestionAbonnement.setId(new ObjectId());

        gestionAbonnement.setTickets(tickets);


        return gestionAbonnement;
    }

    /**
     * permet de creer une gestion d'abonnement à partir des entrées
     * @param mail
     * @return
     */
    public static GestionAbonnement createGestionAbonnement(String mail){
        GestionAbonnement gestionAbonnement = new GestionAbonnement();

        gestionAbonnement.setMailUtilisateur(mail);
        gestionAbonnement.setId(new ObjectId());



        return gestionAbonnement;
    }

    public static GestionAbonnement createGestionAbonnement(ObjectId objectId,String mail,Abonnement abonnement, Collection<Ticket> tickets){
        GestionAbonnement gestionAbonnement = new GestionAbonnement();

        gestionAbonnement.setMailUtilisateur(mail);
        gestionAbonnement.setId(objectId);
        gestionAbonnement.setAbonnement(abonnement);
        gestionAbonnement.setTickets(tickets);


        return gestionAbonnement;
    }


    /**
     * permet de creer une gestion d'abonnement à partir de la collection
     * @param objectId
     * @param mail
     * @param tickets
     * @return
     */
    public static GestionAbonnement createGestionAbonnement(ObjectId objectId,String mail,Collection<Ticket> tickets){
        GestionAbonnement gestionAbonnement = new GestionAbonnement();

        gestionAbonnement.setMailUtilisateur(mail);
        gestionAbonnement.setId(objectId);

        gestionAbonnement.setTickets(tickets);


        return gestionAbonnement;
    }

    /**
     * permet de creer une gestion d'abonnement à partir de la collection
     * @param objectId
     * @param mail
     * @param abonnement
     * @return
     */
    public static GestionAbonnement createGestionAbonnement(ObjectId objectId,String mail,Abonnement abonnement){
        GestionAbonnement gestionAbonnement = new GestionAbonnement();

        gestionAbonnement.setMailUtilisateur(mail);
        gestionAbonnement.setId(objectId);
        gestionAbonnement.setAbonnement(abonnement);



        return gestionAbonnement;
    }


    /**
     * permet de creer une gestion d'abonnement à partir de la collection
     * @param objectId
     * @param mail
     * @return
     */
    public static GestionAbonnement createGestionAbonnement(ObjectId objectId,String mail){
        GestionAbonnement gestionAbonnement = new GestionAbonnement();

        gestionAbonnement.setMailUtilisateur(mail);
        gestionAbonnement.setId(objectId);
        

        return gestionAbonnement;
    }





}
