package mongoDB.facade;

import mongoDB.exception.MailDejaDansLaCollectionException;
import mongoDB.exception.MailNonTrouverException;
import mongoDB.exception.PasDabonnementValideException;
import mongoDB.exception.PasDeTitreValideException;
import mongoDB.modele.Abonnement;
import mongoDB.modele.GestionAbonnement;
import mongoDB.modele.Ticket;
import org.bson.Document;

import java.text.ParseException;
import java.util.Collection;

public interface FacadeTransport {


   Document souscrireAbonnement(Abonnement abonnement);
   Document acheterTicket(Ticket ticket);
   Collection<Document> acheterDixTicket(Collection<Ticket> tickets);

   void uptadeAbonnement(String mailUtilisateur, Abonnement abonnement) throws MailNonTrouverException;
   void uptadeTicket(String mailUtilisateur, Ticket ticket)throws MailNonTrouverException;

   void uptadeDixTicket(String mailUtilisateur, Collection<Ticket> tickets) throws MailNonTrouverException;


   void creerGestionAbonnement(GestionAbonnement gestionAbonnement) throws MailDejaDansLaCollectionException;




   Collection<String> getAllMails();

   Collection<GestionAbonnement> getAllGestions() ;

   GestionAbonnement getGestion(String mail)throws MailNonTrouverException;

   void validerTicket(String mail) throws MailNonTrouverException, PasDeTitreValideException;

   void validerAbonnement(String mail) throws MailNonTrouverException, PasDabonnementValideException, ParseException;



}
