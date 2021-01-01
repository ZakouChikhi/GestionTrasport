package Authentification.facade;

import Authentification.modele.Utilisateur;

public interface FacadeUser {
    void logIn(String username, String password);
    void logOut();
    void singIn(Utilisateur user);
    void signOut();
}
