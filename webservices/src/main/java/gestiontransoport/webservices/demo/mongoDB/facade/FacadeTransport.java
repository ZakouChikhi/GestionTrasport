package gestiontransoport.webservices.demo.mongoDB.facade;

import gestiontransoport.webservices.demo.mongoDB.exception.PseudoDejaDansLaCollectionException;
import gestiontransoport.webservices.demo.mongoDB.exception.PseudoNonTrouverException;
import gestiontransoport.webservices.demo.mongoDB.exception.PasDabonnementValideException;
import gestiontransoport.webservices.demo.mongoDB.exception.PasDeTitreValideException;
import gestiontransoport.webservices.demo.mongoDB.modele.Abonnement;
import gestiontransoport.webservices.demo.mongoDB.modele.GestionAbonnement;
import gestiontransoport.webservices.demo.mongoDB.modele.Ticket;
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
    * @param pseudo
    * @param abonnement
    * @throws PseudoNonTrouverException : le pseudo n'existe pas dans la collection transport
    */
   void uptadeAbonnement(String pseudo, Abonnement abonnement) throws PseudoNonTrouverException;


   /**
    * permet d'ajouter un ticket  acheté par  l'utilisateur dans la base
    * @param pseudo
    * @throws PseudoNonTrouverException : le pseudo n'existe pas dans la collection transport
    */
   void uptadeTicket(String pseudo)throws PseudoNonTrouverException;


   /**
    * permet d'ajouter dix tickets  acheté par  l'utilisateur dans la base
    * @param pseudo
    * @throws PseudoNonTrouverException : le pseudo n'existe pas dans la collection transport
    */
   void uptadeDixTicket(String pseudo) throws PseudoNonTrouverException;


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
   Collection<String> getAllPseudo();

   /**
    *
    * @return tout les gestions abonnements dans la base
    */
   Collection<GestionAbonnement> getAllGestions();

   /**
    *
    * @param pseudo
    * @return la gestion d'abonnement de d'un utilisateur à partir de son pseudo
    * @throws PseudoNonTrouverException : le pseudo n'existe pas dans la collection transport
    */
   GestionAbonnement getGestion(String pseudo)throws PseudoNonTrouverException;

   /**
    * permet de valider le ticket d'un utilisateur à partir de son pseudo
    * @param pseudo
    * @throws PseudoNonTrouverException : le pseudo n'existe pas dans la collection transport
    * @throws PasDeTitreValideException : y'a pas de ticket valide dans la collection du pseudo
    */
   void validerTicket(String pseudo) throws PseudoNonTrouverException, PasDeTitreValideException;

   /**
    * permet de valider l'abonnement' d'un utilisateur à partir de son pseudo
    * @param pseudo
    * @throws PseudoNonTrouverException
    * @throws PasDabonnementValideException : y'a pas de ticket valide dans la collection du pseudo
    * @throws ParseException
    */
   void validerAbonnement(String pseudo) throws PseudoNonTrouverException, PasDabonnementValideException, ParseException;



   void removeAll();


   void removeGestion(String pseudo);
}
