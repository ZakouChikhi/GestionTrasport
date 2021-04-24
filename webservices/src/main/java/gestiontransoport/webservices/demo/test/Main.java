package gestiontransoport.webservices.demo.test;


import gestiontransoport.webservices.demo.Authentification.facade.FacadeUser;
import gestiontransoport.webservices.demo.Authentification.facade.FacadeUserImpl;
import mongoDB.exception.UtilisateurInexistantException;

public class Main {
    public static void main(String[] args) throws UtilisateurInexistantException {



        FacadeUser faceUser = new FacadeUserImpl();
   //     Utilisateur utilisateur = new Utilisateur(1,"zak","aal","ak","o","ao","ao",null);
       // faceUser.singIn(utilisateur);
        String email = faceUser.getEmailById(2);
        System.out.println(email);



    }
}
