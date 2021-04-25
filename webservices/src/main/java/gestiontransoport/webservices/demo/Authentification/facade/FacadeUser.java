package gestiontransoport.webservices.demo.Authentification.facade;


import gestiontransoport.webservices.demo.Authentification.modele.Utilisateur;
import gestiontransoport.webservices.demo.mongoDB.exception.PseudoDejaDansLaCollectionException;
import gestiontransoport.webservices.demo.mongoDB.exception.UtilisateurInexistantException;

import java.util.Collection;

public interface FacadeUser {
    void logIn(String username, String password);
    void logOut(Utilisateur user);
    Utilisateur singIn(Utilisateur user) throws PseudoDejaDansLaCollectionException;
    void signOut(Utilisateur user);
    String getEmailById(int id) throws UtilisateurInexistantException;

    String getEmailByPseudo(String pseudo);

    Collection<Utilisateur> getAllUtilisateurs();
}
