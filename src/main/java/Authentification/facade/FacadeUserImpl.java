package Authentification.facade;

import Authentification.DatabaseParameters;
import Authentification.modele.Utilisateur;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class FacadeUserImpl implements FacadeUser {

    // Authentification ou Connexion d'un utilisateur
    public void logIn(String username, String password) {
        String databaseUsername = "";
        String databasePassword = "";

        DatabaseParameters data = new DatabaseParameters();

        try{
            Connection myConn = DriverManager.getConnection(data.url, data.username, data.password);
            Statement myStmt = myConn.createStatement();
            String SQL = "SELECT * FROM utilisateur WHERE username='" + username + "' && password='" + password+ "'";

            ResultSet rs = myStmt.executeQuery(SQL);

            // Check Username and Password
            while (rs.next()) {
                databaseUsername = rs.getString("username");
                databasePassword = rs.getString("password");
            }

        }catch (Exception exc){
            exc.printStackTrace();
        }

        if (databasePassword != ""){
            System.out.println("Vous etes connecté");
        }else{
            System.out.println("Echec d'authentification");
        }

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
    public void singIn(Utilisateur user) {
        DatabaseParameters dat = new DatabaseParameters();

        try{
            Connection myConn = DriverManager.getConnection(dat.url, dat.username, dat.password);
            Statement myStmt = myConn.createStatement();

            String sql = "INSERT INTO `utilisateur` "
                    + "(`id`, `nom`, `prenom`, `adresse`, `username`, `email`, `password`, `date_naissance`)"
                    + "VALUES ('" + user.getId() + "', '" + user.getNom() + "', '"+user.getPrenom()+"', '"+user.getAdresse()+"', '"+user.getUsername()+"', '"+user.getEmail()+"', '"+ user.getPassword()+"', '"+ user.getDate_naissance()+"')";

            myStmt.executeUpdate(sql);

            System.out.println("Utiliateur inscrit");

        }catch (Exception exc){
            exc.printStackTrace();
        }

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

        System.out.println("Utilisteur desinscrit");

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
}
