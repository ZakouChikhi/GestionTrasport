package test;
import mongoDB.exception.MailDejaDansLaCollectionException;
import mongoDB.exception.MailNonTrouverException;
import mongoDB.exception.PasDabonnementValideException;
import mongoDB.exception.PasDeTitreValideException;
import mongoDB.fabrique.FabriqueAbonnementAnnuel;
import mongoDB.fabrique.FabriqueAbonnementMensual;
import mongoDB.fabrique.FabriqueGestionAbonnement;
import mongoDB.fabrique.FabriqueTicket;
import mongoDB.facade.FacadeTransport;
import mongoDB.facade.FacadeTransportImpl;
import mongoDB.modele.GestionAbonnement;
import mongoDB.modele.Ticket;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.lang.String;

public class TestMongoDB {
    public static void main(String[] args) throws MailNonTrouverException, ParseException, PasDabonnementValideException, MailDejaDansLaCollectionException {


        FacadeTransport facadeTransport = new FacadeTransportImpl();

        System.out.println("------------------------------------");
        // creer une gestion d'abonnement pour le mail zakaria sans ticket ni abonnement
        System.out.println("creer une gestion d'abonnement pour le mail Adrien sans ticket ni abonnement");
        String mail="adrien@live.fr";
        GestionAbonnement gestionAbonnement = FabriqueGestionAbonnement.createGestionAbonnement(mail);
        System.out.println(gestionAbonnement.toString());


        System.out.println("------------------------------------");
        // creer une gestion d'abonnement pour le mail1 avec un ticket et sans abonnement
        System.out.println("creer une gestion d'abonnement pour le mail1 avec un ticket et sans abonnement");
        String mail1="stephane@live.fr";
        Collection<Ticket> ticketsStephane = new ArrayList<>();
        ticketsStephane.add(FabriqueTicket.createTicket());
        GestionAbonnement gestionAbonnement1 = FabriqueGestionAbonnement.createGestionAbonnement(
                mail1,
                ticketsStephane
        );
        System.out.println(gestionAbonnement1.toString());

        System.out.println("------------------------------------");
        // creer une gestion d'abonnement pour le mail2 avec dix tickets et sans abonnement
        System.out.println("creer une gestion d'abonnement pour le mail2 avec dix tickets et sans abonnement");
        String mail2="Moktar@live.fr";

        GestionAbonnement gestionAbonnement2 = FabriqueGestionAbonnement.createGestionAbonnement(
                mail2,
                FabriqueTicket.createDixTicket()
        );
        System.out.println(gestionAbonnement2.toString());



        System.out.println("------------------------------------");
        // creer une gestion d'abonnement pour le mail3 avec un abonnement
        System.out.println("creer une gestion d'abonnement pour le mail3 avec un abonnement");
        String mail3="sabah@live.fr";
        GestionAbonnement gestionAbonnement3 = FabriqueGestionAbonnement.createGestionAbonnement(
                mail3,
                FabriqueAbonnementMensual.createAbonnementMensual()
        );
        System.out.println(gestionAbonnement3.toString());


        System.out.println("------------------------------------");
        // creer une gestion d'abonnement pour le mail4 avec un abonnement et dix tickets
        System.out.println("creer une gestion d'abonnement pour le mail4 avec un abonnement et dix tickets");
        String mail4="jul@live.fr";

        GestionAbonnement gestionAbonnement4 = FabriqueGestionAbonnement.createGestionAbonnement(
                mail4,
                FabriqueAbonnementMensual.createAbonnementMensual(),
                FabriqueTicket.createDixTicket()
        );
        System.out.println(gestionAbonnement4.toString());


        System.out.println("------------------------------------");
        // ajouter les gestions dans la collection transport
        facadeTransport.creerGestionAbonnement(gestionAbonnement);
        facadeTransport.creerGestionAbonnement(gestionAbonnement1);
        facadeTransport.creerGestionAbonnement(gestionAbonnement2);
        facadeTransport.creerGestionAbonnement(gestionAbonnement3);
        facadeTransport.creerGestionAbonnement(gestionAbonnement4);

        System.out.println("------------------------------------");
        // ajouter une gestion déja existante
        System.out.println("ajouter une gestion déja existante");
        try {
            facadeTransport.creerGestionAbonnement(gestionAbonnement);
        } catch (MailDejaDansLaCollectionException e) {
            e.printStackTrace();
        }


        System.out.println("------------------------------------");
        // souscrire Moktar à un abonnement mensuel
        System.out.println("souscrire Moktar à un abonnement mensuel");
        facadeTransport.uptadeAbonnement(mail2,FabriqueAbonnementMensual.createAbonnementMensual());



        System.out.println("------------------------------------");
        // souscrire un mail qui ne se trouve pas de la collection à un abonnement mensual
        System.out.println("souscrire un mail qui ne se trouve pas de la collection à un abonnement mensual");
        try {
            facadeTransport.uptadeAbonnement("mailErr@live.fr",FabriqueAbonnementMensual.createAbonnementMensual());
        }catch (MailNonTrouverException e ){
            e.printStackTrace();
        }


        System.out.println("------------------------------------");
        // Adrien à decider de souscrire à un abonnement annual
        System.out.println("zakaria à decider de souscrire à un abonnement annual");
        facadeTransport.uptadeAbonnement(mail, FabriqueAbonnementAnnuel.createAbonnementAnnuel());



        System.out.println("------------------------------------");
        // valider l'abonnement de Adrien
        System.out.println("valider l'abonnement de zakaria");
        facadeTransport.validerAbonnement(mail);



        System.out.println("------------------------------------");
        // valider l'abonnement de stephane (il à pas d'abonnement)
        System.out.println("valider l'abonnement de stephane (il à pas d'abonnement)");
        try {
            facadeTransport.validerAbonnement(mail1);

        }catch (PasDabonnementValideException e){
            e.printStackTrace();
        }


        System.out.println("------------------------------------");
        // valider un ticket de Adrien (n'a pas de ticket valide)
        System.out.println("valider le ticket de Adrien");
        try {
            facadeTransport.validerTicket(mail);
        } catch (PasDeTitreValideException e) {
            e.printStackTrace();
        }



        System.out.println("------------------------------------");
        // l'achat de d'un tickets par Adrien
        System.out.println("l'achat d'un ticket par Adrien");
        facadeTransport.uptadeTicket(mail,FabriqueTicket.createTicket());



        System.out.println("------------------------------------");
        // l'achat de dix tickets par sabah
        System.out.println("l'achat de dix tickets par cheba sabah");
        facadeTransport.uptadeDixTicket(mail3,FabriqueTicket.createDixTicket());



        System.out.println("------------------------------------");
        // l'achat de d'un ticket par Adrien
        System.out.println("l'achat d'un ticket par Adrien");
        facadeTransport.uptadeTicket(mail,FabriqueTicket.createTicket());




        System.out.println("------------------------------------");
        // valider un ticket de Adrien
        System.out.println("valider le ticket de Adrien");
        try {
            facadeTransport.validerTicket(mail);
        } catch (PasDeTitreValideException e) {
            e.printStackTrace();
        }




    }
}
