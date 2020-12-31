package test;

import mongoDB.exception.MailDejaDansLaCollectionException;
import mongoDB.exception.MailNonTrouverException;
import mongoDB.fabrique.FabriqueAbonnementMensual;
import mongoDB.fabrique.FabriqueGestionAbonnement;
import mongoDB.fabrique.FabriqueTicket;
import mongoDB.facade.FacadeTransport;
import mongoDB.facade.FacadeTransportImpl;
import mongoDB.modele.Abonnement;
import mongoDB.modele.GestionAbonnement;
import mongoDB.modele.Ticket;
import java.util.Collection;

public class Main {
    public static void main(String[] args) throws MailDejaDansLaCollectionException, MailNonTrouverException {

        String mail="zakaria@live.fr";
        Abonnement abonnement = FabriqueAbonnementMensual.createAbonnementMensual();
        Ticket ticket = FabriqueTicket.createTicket();
        Collection<Ticket> tickets = FabriqueTicket.createDixTicket();
        tickets.add(ticket);


        GestionAbonnement gestionAbonnement = FabriqueGestionAbonnement.createGestionAbonnement(mail);

        FacadeTransport facadeTransport = new FacadeTransportImpl();


       // facadeTransport.creerGestionAbonnement(gestionAbonnement);
      //  facadeTransport.uptadeTicket(mail,ticket);
       // facadeTransport.uptadeTicket(mail,ticket);
        ///facadeTransport.uptadeAbonnement(mail,abonnement);
      ///  facadeTransport.uptadeAbonnement(mail,abonnement);

      //  facadeTransport.getAllMails().stream().forEach(e -> System.out.println(e));


   //     System.out.println(facadeTransport.getDateFin(mail).get().getString("ticket.etat"));




    }
}
