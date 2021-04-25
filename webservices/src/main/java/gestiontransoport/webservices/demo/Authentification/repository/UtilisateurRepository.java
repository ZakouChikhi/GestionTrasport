package gestiontransoport.webservices.demo.Authentification.repository;

import gestiontransoport.webservices.demo.Authentification.modele.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UtilisateurRepository extends JpaRepository<Utilisateur, Integer> {
    Optional<Utilisateur> findByEmail(String username);

    Optional<Utilisateur> findByUsername(String s);

    Optional<Utilisateur> findByPassword(String password);
}
