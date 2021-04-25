package gestiontransoport.webservices.demo.Authentification.facade;


import gestiontransoport.webservices.demo.Authentification.DatabaseParameters;
import gestiontransoport.webservices.demo.Authentification.modele.Utilisateur;
import gestiontransoport.webservices.demo.Authentification.repository.UtilisateurRepository;
import gestiontransoport.webservices.demo.mongoDB.exception.PseudoDejaDansLaCollectionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Collection;
import java.util.Optional;


@Component("facadeUserImpl")
public class FacadeUserImpl implements FacadeUser {

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @Autowired
    private EmailValidator emailValidator;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Collection<Utilisateur> getAllUtilisateurs() {
        return utilisateurRepository.findAll();
    }

    // Authentification ou Connexion d'un utilisateur
    public void logIn(String username, String password) {


    }

    //Deconnexion d'un utilisateur
    public void logOut(Utilisateur user) {
        String databaseUsername = "";

        DatabaseParameters data = new DatabaseParameters();

        try{
            Connection myConn = DriverManager.getConnection(data.url, data.username, data.password);
            Statement myStmt = myConn.createStatement();
            String SQL = "SELECT * FROM utilisateur WHERE username='" + user.getUsername() + "'";

            ResultSet rs = myStmt.executeQuery(SQL);

            // Check Username and Password
            while (rs.next()) {
                databaseUsername = rs.getString("username");
            }

        }catch (Exception exc){
            exc.printStackTrace();
        }

        if (databaseUsername != ""){
            System.out.println("Vous etes deconnecté");
        }else{
            System.out.println("Echec");
        }


    }

    //Inscription d'un utilisateur


    @Override
    public Utilisateur singIn(Utilisateur user) throws PseudoDejaDansLaCollectionException {
            Optional<Utilisateur> utilisateur = utilisateurRepository.findByEmail(user.getUsername());
            boolean emailValid = emailValidator.test(user.getEmail());
            if ((utilisateur.isPresent())&&(!emailValid)){
                throw new PseudoDejaDansLaCollectionException("le pseudo est deja pris");
            }

            String encodedPassword = passwordEncoder.encode(user.getPassword());
            return utilisateurRepository.save(user);
    }

    //Procedure de desinscription
    public void signOut(Utilisateur user) {
        DatabaseParameters data = new DatabaseParameters();

        try{
            Connection myConn = DriverManager.getConnection(data.url, data.username, data.password);
            Statement myStmt = myConn.createStatement();
            String SQL = "DELETE FROM `utilisateur` WHERE username='" + user.getUsername() +"'";

            ResultSet rs = myStmt.executeQuery(SQL);

        }catch (Exception exc){
            exc.printStackTrace();
        }

        System.out.println("Compte utilisateur supprimer");

    }

    @Override
    public String getEmailById(int id) {
        String databaseEmail = "";

        DatabaseParameters data = new DatabaseParameters();

        try{
            Connection myConn = DriverManager.getConnection(data.url, data.username, data.password);
            Statement myStmt = myConn.createStatement();
            String SQL = "SELECT * FROM utilisateur WHERE id='" + id + "'";

            ResultSet rs = myStmt.executeQuery(SQL);

            // Check Username and Password
            while (rs.next()) {
                databaseEmail = rs.getString("email");
            }

        }catch (Exception exc){
            exc.printStackTrace();
        }

        if (databaseEmail != ""){
            return databaseEmail;
        }else{
            System.out.println("Echec");
        }
        return null;
    }


    @Override
    public String getEmailByPseudo(String pseudo){
        String databaseEmail = "";

        DatabaseParameters data = new DatabaseParameters();

        try{
            Connection myConn = DriverManager.getConnection(data.url, data.username, data.password);
            Statement myStmt = myConn.createStatement();
            String SQL = "SELECT * FROM utilisateur WHERE username='" + pseudo + "'";

            ResultSet rs = myStmt.executeQuery(SQL);

            // Check Username and Password
            while (rs.next()) {
                databaseEmail = rs.getString("email");
            }

        }catch (Exception exc){
            exc.printStackTrace();
        }

        if (databaseEmail != ""){
            return databaseEmail;
        }else{
            System.out.println("Echec ");;
        }
        return null;
    }
}
