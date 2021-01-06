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


   /**
    * permet de creer un document d'un abonnement
    * @param abonnement
    * @return Document d'un abonnement
    */
   Document souscrireAbonnement(Abonnement abonnement);

   /**
    * permet de creer un document d'un ticket
    * @param ticket
    * @return Document d'un ticket
    */
   Document acheterTicket(Ticket ticket);

   /**
    * permet de creer une collection document de 10 ticket
    * @param tickets
    * @return une collection de documents
    */
   Collection<Document> acheterDixTicket(Collection<Ticket> tickets);

   /**
    * permet de mettre a jour l'abonnement d'un utilisateur dans la base
    * @param mailUtilisateur
    * @param abonnement
    * @throws MailNonTrouverException : le mail n'existe pas dans la collection transport
    */
   void uptadeAbonnement(String mailUtilisateur, Abonnement abonnement) throws MailNonTrouverException;


   /**
    * permet d'ajouter un ticket  acheté par  l'utilisateur dans la base
    * @param mailUtilisateur
    * @param ticket
    * @throws MailNonTrouverException : le mail n'existe pas dans la collection transport
    */
   void uptadeTicket(String mailUtilisateur, Ticket ticket)throws MailNonTrouverException;


   /**
    * permet d'ajouter dix tickets  acheté par  l'utilisateur dans la base
    * @param mailUtilisateur
    * @param tickets
    * @throws MailNonTrouverException : le mail n'existe pas dans la collection transport
    */
   void uptadeDixTicket(String mailUtilisateur, Collection<Ticket> tickets) throws MailNonTrouverException;


   /**
    * permet de cree une gestion d'abonnement d'un utilisateur dans la base
    * @param gestionAbonnement
    * @throws MailDejaDansLaCollectionException : y'a déja une collection de gestion d'abonnement pour le mail
    */
   void creerGestionAbonnement(GestionAbonnement gestionAbonnement) throws MailDejaDansLaCollectionException;


   /**
    *
    * @return tout les mails des utilisateurs dans la base
    */
   Collection<String> getAllMails();

   /**
    *
    * @return tout les gestions abonnements dans la base
    */
   Collection<GestionAbonnement> getAllGestions();

   /**
    *
    * @param mail
    * @return la gestion d'abonnement de d'un utilisateur à partir de son mail
    * @throws MailNonTrouverException : le mail n'existe pas dans la collection transport
    */
   GestionAbonnement getGestion(String mail)throws MailNonTrouverException;

   /**
    * permet de valider le ticket d'un utilisateur à partir de son mail
    * @param mail
    * @throws MailNonTrouverException : le mail n'existe pas dans la collection transport
    * @throws PasDeTitreValideException : y'a pas de ticket valide dans la collection du mail
    */
   void validerTicket(String mail) throws MailNonTrouverException, PasDeTitreValideException;

   /**
    * permet de valider l'abonnement' d'un utilisateur à partir de son mail
    * @param mail
    * @throws MailNonTrouverException
    * @throws PasDabonnementValideException : y'a pas de ticket valide dans la collection du mail
    * @throws ParseException
    */
   void validerAbonnement(String mail) throws MailNonTrouverException, PasDabonnementValideException, ParseException;



}
