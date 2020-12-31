package mongoDB.facade;

import mongoDB.exception.MailDejaDansLaCollectionException;
import mongoDB.exception.MailNonTrouverException;
import mongoDB.modele.Abonnement;
import mongoDB.modele.GestionAbonnement;
import mongoDB.modele.Ticket;
import org.bson.Document;

import java.util.Collection;
import java.util.Optional;

public interface FacadeTransport {


   Document souscrireAbonnement(Abonnement abonnement);
   Document acheterTicket(Ticket ticket);
   Collection<Document> acheterDixTicket(Collection<Ticket> tickets);

   void uptadeAbonnement(String mailUtilisateur, Abonnement abonnement) throws MailNonTrouverException;
   void uptadeTicket(String mailUtilisateur, Ticket ticket)throws MailNonTrouverException;

   void uptadeDixTicket(String mailUtilisateur, Collection<Ticket> tickets) throws MailNonTrouverException;


   void creerGestionAbonnement(GestionAbonnement gestionAbonnement) throws MailDejaDansLaCollectionException;




   Collection<String> getAllMails();

   Optional<Document> getDateFin(String mail) throws MailNonTrouverException;


}
