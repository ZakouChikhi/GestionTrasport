package mongoDB.facade;

import mongoDB.exception.PseudoDejaDansLaCollectionException;
import mongoDB.exception.PseudoNonTrouverException;
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
    * @throws PseudoNonTrouverException : le mail n'existe pas dans la collection transport
    */
   void uptadeAbonnement(String mailUtilisateur, Abonnement abonnement) throws PseudoNonTrouverException;


   /**
    * permet d'ajouter un ticket  acheté par  l'utilisateur dans la base
    * @param mailUtilisateur
    * @param ticket
    * @throws PseudoNonTrouverException : le mail n'existe pas dans la collection transport
    */
   void uptadeTicket(String mailUtilisateur, Ticket ticket)throws PseudoNonTrouverException;


   /**
    * permet d'ajouter dix tickets  acheté par  l'utilisateur dans la base
    * @param mailUtilisateur
    * @param tickets
    * @throws PseudoNonTrouverException : le mail n'existe pas dans la collection transport
    */
   void uptadeDixTicket(String mailUtilisateur, Collection<Ticket> tickets) throws PseudoNonTrouverException;


   /**
    * permet de cree une gestion d'abonnement d'un utilisateur dans la base
    * @param gestionAbonnement
    * @throws PseudoDejaDansLaCollectionException : y'a déja une collection de gestion d'abonnement pour le mail
    */
   void creerGestionAbonnement(GestionAbonnement gestionAbonnement) throws PseudoDejaDansLaCollectionException;


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
    * @throws PseudoNonTrouverException : le mail n'existe pas dans la collection transport
    */
   GestionAbonnement getGestion(String mail)throws PseudoNonTrouverException;

   /**
    * permet de valider le ticket d'un utilisateur à partir de son mail
    * @param mail
    * @throws PseudoNonTrouverException : le mail n'existe pas dans la collection transport
    * @throws PasDeTitreValideException : y'a pas de ticket valide dans la collection du mail
    */
   void validerTicket(String mail) throws PseudoNonTrouverException, PasDeTitreValideException;

   /**
    * permet de valider l'abonnement' d'un utilisateur à partir de son mail
    * @param mail
    * @throws PseudoNonTrouverException
    * @throws PasDabonnementValideException : y'a pas de ticket valide dans la collection du mail
    * @throws ParseException
    */
   void validerAbonnement(String mail) throws PseudoNonTrouverException, PasDabonnementValideException, ParseException;



}
