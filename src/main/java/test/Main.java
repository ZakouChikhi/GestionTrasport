package test;

import Authentification.facade.FacadeUserImpl;
import Authentification.modele.Utilisateur;
import mongoDB.exception.MailDejaDansLaCollectionException;
import mongoDB.exception.MailNonTrouverException;

public class Main {
    public static void main(String[] args) throws MailDejaDansLaCollectionException, MailNonTrouverException {

        //String mail="zakaria@live.fr";
        /*Abonnement abonnement = FabriqueAbonnementMensual.createAbonnementMensual();
        Ticket ticket = FabriqueTicket.createTicket();
        Collection<Ticket> tickets = FabriqueTicket.createDixTicket();
        tickets.add(ticket);


        GestionAbonnement gestionAbonnement = FabriqueGestionAbonnement.createGestionAbonnement(mail);

        FacadeTransport facadeTransport = new FacadeTransportImpl();*/


       // facadeTransport.creerGestionAbonnement(gestionAbonnement);
      //  facadeTransport.uptadeTicket(mail,ticket);
       // facadeTransport.uptadeTicket(mail,ticket);
        ///facadeTransport.uptadeAbonnement(mail,abonnement);
      ///  facadeTransport.uptadeAbonnement(mail,abonnement);

      //  facadeTransport.getAllMails().stream().forEach(e -> System.out.println(e));


   //     System.out.println(facadeTransport.getDateFin(mail).get().getString("ticket.etat"));

        FacadeUserImpl fac = new FacadeUserImpl();
        Utilisateur user = new Utilisateur(1,"Ratu","Fabien","10 rue de la croix", "fabRatu", "fabien.rat@live.fr","fab123", "10-10-2000");
        fac.logIn("fabRatu", "fab123");
        //fac.singIn(user);




    }
}
