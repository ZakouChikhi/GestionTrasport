package gestiontransoport.webservices.demo.mongoDB.fabrique;

import gestiontransoport.webservices.demo.mongoDB.modele.Abonnement;
import gestiontransoport.webservices.demo.mongoDB.modele.GestionAbonnement;
import gestiontransoport.webservices.demo.mongoDB.modele.Ticket;
import org.bson.types.ObjectId;

import java.util.Collection;

public class FabriqueGestionAbonnement {

    /**
     * permet de creer une gestion d'abonnement à partir des entrées
     * @param pseudo
     * @param abonnement
     * @param tickets
     * @return
     */
    public static GestionAbonnement createGestionAbonnement(String pseudo,Abonnement abonnement, Collection<Ticket> tickets){
        GestionAbonnement gestionAbonnement = new GestionAbonnement();

        gestionAbonnement.setPseudo(pseudo);
        gestionAbonnement.setId(new ObjectId());
        gestionAbonnement.setAbonnement(abonnement);
        gestionAbonnement.setTickets(tickets);


        return gestionAbonnement;
    }

    /**
     * permet de creer une gestion d'abonnement à partir des entrées
     * @param pseudo
     * @param abonnement
     * @return
     */
    public static GestionAbonnement createGestionAbonnement(String pseudo,Abonnement abonnement){
        GestionAbonnement gestionAbonnement = new GestionAbonnement();

        gestionAbonnement.setPseudo(pseudo);
        gestionAbonnement.setId(new ObjectId());
        gestionAbonnement.setAbonnement(abonnement);



        return gestionAbonnement;
    }


    /**
     * permet de creer une gestion d'abonnement à partir des entrées
     * @param pseudo
     * @param tickets
     * @return
     */
    public static GestionAbonnement createGestionAbonnement(String pseudo,Collection<Ticket> tickets){
        GestionAbonnement gestionAbonnement = new GestionAbonnement();

        gestionAbonnement.setPseudo(pseudo);
        gestionAbonnement.setId(new ObjectId());

        gestionAbonnement.setTickets(tickets);


        return gestionAbonnement;
    }

    /**
     * permet de creer une gestion d'abonnement à partir des entrées
     * @param pseudo
     * @return
     */
    public static GestionAbonnement createGestionAbonnement(String pseudo){
        GestionAbonnement gestionAbonnement = new GestionAbonnement();

        gestionAbonnement.setPseudo(pseudo);
        gestionAbonnement.setId(new ObjectId());



        return gestionAbonnement;
    }

    public static GestionAbonnement createGestionAbonnement(ObjectId objectId,String pseudo,Abonnement abonnement, Collection<Ticket> tickets){
        GestionAbonnement gestionAbonnement = new GestionAbonnement();

        gestionAbonnement.setPseudo(pseudo);
        gestionAbonnement.setId(objectId);
        gestionAbonnement.setAbonnement(abonnement);
        gestionAbonnement.setTickets(tickets);


        return gestionAbonnement;
    }


    /**
     * permet de creer une gestion d'abonnement à partir de la collection
     * @param objectId
     * @param pseudo
     * @param tickets
     * @return
     */
    public static GestionAbonnement createGestionAbonnement(ObjectId objectId,String pseudo,Collection<Ticket> tickets){
        GestionAbonnement gestionAbonnement = new GestionAbonnement();

        gestionAbonnement.setPseudo(pseudo);
        gestionAbonnement.setId(objectId);

        gestionAbonnement.setTickets(tickets);


        return gestionAbonnement;
    }

    /**
     * permet de creer une gestion d'abonnement à partir de la collection
     * @param objectId
     * @param pseudo
     * @param abonnement
     * @return
     */
    public static GestionAbonnement createGestionAbonnement(ObjectId objectId,String pseudo,Abonnement abonnement){
        GestionAbonnement gestionAbonnement = new GestionAbonnement();

        gestionAbonnement.setPseudo(pseudo);
        gestionAbonnement.setId(objectId);
        gestionAbonnement.setAbonnement(abonnement);



        return gestionAbonnement;
    }


    /**
     * permet de creer une gestion d'abonnement à partir de la collection
     * @param objectId
     * @param pseudo
     * @return
     */
    public static GestionAbonnement createGestionAbonnement(ObjectId objectId,String pseudo){
        GestionAbonnement gestionAbonnement = new GestionAbonnement();

        gestionAbonnement.setPseudo(pseudo);
        gestionAbonnement.setId(objectId);
        

        return gestionAbonnement;
    }





}
