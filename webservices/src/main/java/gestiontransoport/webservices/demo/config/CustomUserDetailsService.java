package gestiontransoport.webservices.demo.config;




import gestiontransoport.webservices.demo.Authentification.modele.Utilisateur;
import gestiontransoport.webservices.demo.Authentification.repository.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;


public class CustomUserDetailsService implements UserDetailsService {
    private static final String[] ROLES_ADMIN = {"USER", "ADMIN"};
    private static final String[] ROLES_USER = {"USER"};

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UtilisateurRepository utilisateurRepo;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Optional<Utilisateur> utilisateur = utilisateurRepo.findByUsername(s);
        if (utilisateur.isEmpty()){
            throw new UsernameNotFoundException("l'utilisateur ayant l'email:  " +s+ " n'existe pas");
        }
        String[] roles = utilisateur.get().isAdmin() ? ROLES_ADMIN : ROLES_USER;
        UserDetails userDetails = User.builder()
                .username(utilisateur.get().getUsername())
                .password(passwordEncoder.encode(utilisateur.get().getPassword()))
                .roles(roles)
                .build();
        return userDetails ;
    }
}
