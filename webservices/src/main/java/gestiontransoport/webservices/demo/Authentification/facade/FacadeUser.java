package gestiontransoport.webservices.demo.Authentification.facade;


import gestiontransoport.webservices.demo.Authentification.exceptions.UtilisateurDejaConnecte;
import gestiontransoport.webservices.demo.Authentification.exceptions.UtilisateurDejaDeconnecte;
import gestiontransoport.webservices.demo.Authentification.modele.Utilisateur;
import gestiontransoport.webservices.demo.mongoDB.exception.PseudoDejaDansLaCollectionException;
import gestiontransoport.webservices.demo.mongoDB.exception.UtilisateurInexistantException;

import java.util.Collection;

public interface FacadeUser {
    void logIn(String username) throws UtilisateurInexistantException, UtilisateurDejaConnecte;
    void logOut(String username) throws UtilisateurInexistantException, UtilisateurDejaDeconnecte;
    Utilisateur singIn(Utilisateur user) throws PseudoDejaDansLaCollectionException;
    void signOut(Utilisateur utilisateur) throws UtilisateurInexistantException;
    String getEmailById(int id) throws UtilisateurInexistantException;

    String getEmailByPseudo(String pseudo) throws UtilisateurInexistantException;

    Collection<Utilisateur> getAllUtilisateurs();
}
