package gestiontransoport.webservices.demo.Authentification.facade;



import gestiontransoport.webservices.demo.Authentification.modele.Utilisateur;
import gestiontransoport.webservices.demo.Authentification.repository.UtilisateurRepository;
import gestiontransoport.webservices.demo.mongoDB.exception.PseudoDejaDansLaCollectionException;
import gestiontransoport.webservices.demo.mongoDB.exception.UtilisateurInexistantException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
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



    //Inscription d'un utilisateur
    @Override
    public Utilisateur singIn(Utilisateur user) throws PseudoDejaDansLaCollectionException {
            Optional<Utilisateur> utilisateur = utilisateurRepository.findByUsername(user.getUsername());
            boolean emailValid = emailValidator.test(user.getEmail());
            if (utilisateur.isPresent()){
                throw new PseudoDejaDansLaCollectionException("le pseudo est deja pris");
            }

            String encodedPassword = passwordEncoder.encode(user.getPassword());
            return utilisateurRepository.save(user);
    }

    //Procedure de desinscription
    @Override
    public void signOut(String username) throws UtilisateurInexistantException {
        Optional<Utilisateur> user = utilisateurRepository.findByUsername(username);
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
