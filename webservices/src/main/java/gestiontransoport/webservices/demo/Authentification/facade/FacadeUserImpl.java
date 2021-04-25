package gestiontransoport.webservices.demo.Authentification.facade;


import gestiontransoport.webservices.demo.Authentification.DatabaseParameters;
import gestiontransoport.webservices.demo.Authentification.exceptions.UtilisateurDejaConnecte;
import gestiontransoport.webservices.demo.Authentification.exceptions.UtilisateurDejaDeconnecte;
import gestiontransoport.webservices.demo.Authentification.modele.Utilisateur;
import gestiontransoport.webservices.demo.Authentification.repository.UtilisateurRepository;
import gestiontransoport.webservices.demo.mongoDB.exception.PseudoDejaDansLaCollectionException;
import gestiontransoport.webservices.demo.mongoDB.exception.UtilisateurInexistantException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
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
    @Override
    @Transactional
    public void logIn(String username) throws UtilisateurInexistantException, UtilisateurDejaConnecte {
        Optional<Utilisateur> utilisateur = utilisateurRepository.findByUsername(username);
        if (utilisateur.isEmpty()){
            throw new UtilisateurInexistantException();
        }
        if (utilisateur.get().isConnected()){
            throw new UtilisateurDejaConnecte();
        }
        utilisateur.get().setConnected(true);
    }

    //Deconnexion d'un utilisateur
    @Override
    @Transactional
    public void logOut(String username) throws UtilisateurInexistantException, UtilisateurDejaDeconnecte {
        Optional<Utilisateur> utilisateur = utilisateurRepository.findByUsername(username);
        if (utilisateur.isEmpty()){
            throw new UtilisateurInexistantException();
        }
        if (!utilisateur.get().isConnected()){
            throw new UtilisateurDejaDeconnecte();
        }
        utilisateur.get().setConnected(false);
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
    @Override
    public void signOut(Utilisateur utilisateur) throws UtilisateurInexistantException {
        Optional<Utilisateur> user = utilisateurRepository.findByUsername(utilisateur.getUsername());
        if (user.isEmpty()){
            throw new UtilisateurInexistantException();
        }
        utilisateurRepository.deleteById(user.get().getId());
    }

    @Override
    public String getEmailById(int id) throws UtilisateurInexistantException {
        Optional<Utilisateur> user = utilisateurRepository.findById(id);
        if (user.isEmpty()){
            throw new UtilisateurInexistantException();
        }
        return user.get().getEmail();
    }


    @Override
    public String getEmailByPseudo(String pseudo) throws UtilisateurInexistantException {
        Optional<Utilisateur> user = utilisateurRepository.findByUsername(pseudo);
        if (user.isEmpty()){
            throw new UtilisateurInexistantException();
        }
        return user.get().getEmail();
    }
}
