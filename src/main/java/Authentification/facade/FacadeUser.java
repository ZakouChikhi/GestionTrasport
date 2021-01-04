package Authentification.facade;

import Authentification.modele.Utilisateur;

public interface FacadeUser {
    void logIn(String username, String password);
    void logOut(Utilisateur user);
    void singIn(Utilisateur user);
    void signOut(Utilisateur user);
    String getEmailById(int id);
}
