package gestiontransoport.webservices.demo.controleur;
import gestiontransoport.webservices.demo.Authentification.modele.Utilisateur;
import gestiontransoport.webservices.demo.Authentification.facade.FacadeUser;
import gestiontransoport.webservices.demo.mongoDB.exception.*;
import gestiontransoport.webservices.demo.mongoDB.fabrique.FabriqueAbonnementAnnuel;
import gestiontransoport.webservices.demo.mongoDB.fabrique.FabriqueAbonnementMensual;
import gestiontransoport.webservices.demo.mongoDB.facade.FacadeTransport;
import gestiontransoport.webservices.demo.mongoDB.modele.Abonnement;
import gestiontransoport.webservices.demo.mongoDB.modele.GestionAbonnement;
import gestiontransoport.webservices.demo.mongoDB.modele.Ticket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import java.net.URI;
import java.security.Principal;
import java.text.ParseException;
import java.util.Collection;

@RestController
@RequestMapping("/api/v1/transport")
public class TransportControleur {



    @Autowired
    private FacadeTransport facadeTransportImpl;

    @Autowired
    private FacadeUser facadeUserImpl;

    @GetMapping(value = "/utilisateurs", produces = MediaType.APPLICATION_JSON_VALUE)
    public Collection<Utilisateur> getAllUsers(){
        return facadeUserImpl.getAllUtilisateurs();
    }


    /**
     * inscription d'un utilisateur a la base
     * @param utilisateur un utilisateur en body de la requete
     * @return la location de l'URI
     */
    @PostMapping(value = "/utilisateur", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> enregistrement(@RequestBody Utilisateur utilisateur){


        try {

            this.facadeUserImpl.singIn(utilisateur);

            return ResponseEntity.created(URI.create("transport/utilisateur/"+utilisateur.getUsername())).build();

        } catch (PseudoDejaDansLaCollectionException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(
                    "le pseudo est deja dans la base"
            );

        }


    }





    /**
     * Creation d'une collection d'une gestion Abonnement d'un utilisateur dans la base
     * @param pseudo le pseudo de l'utilisateur en variable uri
     * @param gestionAbonnement gestion d'abonnement en body de la requete
     * @param base
     * @return location uri creer de la gestion d'abonnement d'un utilisateur
     */
    @PostMapping( value = "/utilisateur/{pseudo}/gestionAbonnement", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> creerGestionAbonnement(@PathVariable String pseudo,
                                                         @RequestBody GestionAbonnement gestionAbonnement, UriComponentsBuilder base,Principal principal){


        if (principal.getName().equals(pseudo)){
            try {

                facadeUserImpl.getEmailByPseudo(pseudo);


                facadeTransportImpl.creerGestionAbonnement(gestionAbonnement);

                URI location = base.path("transport/utilisateur/{pseudo}/gestionAbonnement/{pseudo}").buildAndExpand(pseudo).toUri();

                return ResponseEntity.created(location).body("gestion abonnement creer");


            } catch (UtilisateurInexistantException e) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Pseudo non inscrit");
            } catch (PseudoDejaDansLaCollectionException e) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Pseudo déja dans la base");
            }
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Vous n'etes pas autorisez");
    }


    /**
     * recuperation d'une gestion d'abonnement d'un utlisateur
     * @param pseudo  le pseudo de l'utilisateur en variable uri
     * @return la gestion d'abonnement de l'utilisateur
     */
    @GetMapping(value = "/utilisateur/{pseudo}/gestionAbonnement", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GestionAbonnement>  getGestionUtilisateur(@PathVariable("pseudo") String pseudo,Principal principal) {
        if (principal.getName().equals(pseudo)){
            try {
                GestionAbonnement gestionAbonnement = facadeTransportImpl.getGestion(pseudo);

                return ResponseEntity.ok().body(gestionAbonnement);
            } catch (PseudoNonTrouverException e) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

    }


    /**
     * recuperation de l'abonnement d'un utilisateur
     * @param pseudo  le pseudo de l'utilisateur en variable uri
     * @param type le type de l'abonnement passer en variable uri
     * @return
     */
    @GetMapping(value = "/utilisateur/{pseudo}/gestionAbonnement/{pseudo}/abonnement/{type}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Abonnement>  getAbonnementUtilisateur(@PathVariable("pseudo") String pseudo,@PathVariable("type") String type,Principal principal) {


        if (principal.getName().equals(pseudo)){
            try {
                Abonnement abonnement = facadeTransportImpl.getGestion(pseudo).getAbonnement();

                if (abonnement.getType().equals(type)){

                    return ResponseEntity.ok().body(abonnement);

                }else {
                    if (abonnement.getType().equals(type)){
                        return ResponseEntity.ok().body(abonnement);
                    }

                    return ResponseEntity.status(HttpStatus.NOT_FOUND).build();


                }



            } catch (PseudoNonTrouverException e) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }


        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }


    /**
     * souscription a un abonnement d'un utilisateur
     * @param pseudo le pseudo de l'utilisateur passer en variable uri
     * @param abonnement type d'abonnement choisi passer dans le body de la requete
     * @param base
     * @return la location uri creer pour l'abonnement de l'utilisateur
     */
    @PostMapping( value = "/utilisateur/{pseudo}/gestionAbonnement/{pseudo}/abonnement", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> souscrireAbonnement(@PathVariable String pseudo,
                                                      @RequestBody Abonnement abonnement, UriComponentsBuilder base, Principal principal){


        if (principal.getName().equals(pseudo)){


            if (abonnement.getType().equals("mensual")){
                try {
                    facadeTransportImpl.uptadeAbonnement(pseudo, FabriqueAbonnementMensual.createAbonnementMensual());

                    URI location = base.path("transport/utilisateur/{pseudo}/gestionAbonnement/abonnement/{type}").buildAndExpand(pseudo,abonnement.getType()).toUri();
                    return ResponseEntity.created(location).body("Abonnement  acheté");

                } catch (PseudoNonTrouverException e) {
                    return ResponseEntity.
                            status(HttpStatus.NOT_FOUND).build();
                }
            }
            else if (abonnement.getType().equals("annuel")){
                try {
                    facadeTransportImpl.uptadeAbonnement(pseudo, FabriqueAbonnementAnnuel.createAbonnementAnnuel());
                    URI location = base.path("transport/utilisateur/{pseudo}/gestionAbonnement/abonnement/{type}").buildAndExpand(pseudo,abonnement.getType()).toUri();
                    return ResponseEntity.created(location).body("Abonnement  acheté");

                } catch (PseudoNonTrouverException e) {
                    return ResponseEntity.
                            status(HttpStatus.NOT_FOUND).build();
                }

            }


            return ResponseEntity.status(HttpStatus.CONFLICT).build();

        }


        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Vous n'etes pas autoriser");


    }

    /**
     * permet l'achat d'un ou de dix tickets par un utilisateur
     * @param pseudo  le pseudo de l'utilisateur en variable uri
     * @param nbrTickets  le nombre de tickets souhaiter par l'utilisateur en parametre requete
     * @param base
     * @return la location uri creer du ticket
     */
    @PostMapping( value = "/utilisateur/{pseudo}/gestionAbonnement/{pseudo}/tickets")
    public ResponseEntity<String> achatTickets(@PathVariable String pseudo, @RequestParam int nbrTickets,UriComponentsBuilder base,Principal principal){

if (principal.getName().equals(pseudo)){

    try {
        facadeUserImpl.getEmailByPseudo(pseudo);

        switch (nbrTickets) {

            case 1:
                try {
                    facadeTransportImpl.uptadeTicket(pseudo);
                    URI location = base.path("transport/utilisateur/{pseudo}/gestionAbonnement/tickets/{a}").buildAndExpand(pseudo, 1).toUri();
                    return ResponseEntity.created(location).body("Vous avez acheté un ticket");

                } catch (PseudoNonTrouverException e) {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("utilisateur pas trouve");
                }

            case 10:
                try {
                    facadeTransportImpl.uptadeDixTicket(pseudo);
                    URI location = base.path("transport/utilisateur/{pseudo}/gestionAbonnement/tickets/{a}").buildAndExpand(pseudo, 1).toUri();
                    return ResponseEntity.created(location).body("Vous avez acheté dix tickets");

                } catch (PseudoNonTrouverException e) {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("utilisateur pas trouve");

                }

            default:
                return ResponseEntity.status(HttpStatus.CONFLICT).body("nombre tickets non autorise");


        }



    } catch (UtilisateurInexistantException e) {
        return ResponseEntity.notFound().build();
    }



}
return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Vous n'etes pas autoriser");





    }

    /**
     * recuperation de la collection de tickets d'un utilisateur
     * @param pseudo  le pseudo de l'utilisateur en variable uri
     * @return la collection de tickets d'un utilisateur
     */
    @GetMapping(value = "/utilisateur/{pseudo}/gestionAbonnement/{pseudo}/tickets/{a}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<Ticket>>  getTicketsUtilisateur(@PathVariable("pseudo") String pseudo,Principal principal) {


        if (principal.getName().equals(pseudo)){


            Collection<Ticket> tickets = null;
            try {
                tickets = facadeTransportImpl.getGestion(pseudo).getTickets();

                if (tickets.isEmpty()){

                    return ResponseEntity.noContent().build();

                }else {
                    return ResponseEntity.ok().body(tickets);
                }
            } catch (PseudoNonTrouverException pseudoNonTrouverException) {

                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

            }


        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();










    }

    /**
     * validation d'un ticket de l'utilisateur
     * @param pseudo  le pseudo de l'utilisateur en variable uri
     * @return
     */
    @PostMapping(value = "/utilisateur/{pseudo}/gestionAbonnement/{pseudo}/tickets/{a}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String>  validerTicketsUtilisateur(@PathVariable("pseudo") String pseudo,Principal principal) {


        if (principal.getName().equals(pseudo)){
            try {
                facadeTransportImpl.validerTicket(pseudo);

                return ResponseEntity.ok("Ticket valider");
            } catch (PseudoNonTrouverException e) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(" Pseudo Non Trouver");
            } catch (PasDeTitreValideException e) {

                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Vous n'avez pas de ticket valide");

            }
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Vous n'etes pas autoriser ");


    }

    /**
     * validation d'un abonnement de l'utilisateur
     * @param pseudo pseudo le pseudo de l'utilisateur en variable uri
     * @return
     */
    @PostMapping(value = "/utilisateur/{pseudo}/gestionAbonnement/{pseudo}/abonnement/{type}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String>  validerAbonnementUtilisateur(@PathVariable("pseudo") String pseudo,Principal principal) {

    if (principal.getName().equals(pseudo)){

        try {
            facadeTransportImpl.validerAbonnement(pseudo);
            return ResponseEntity.ok("Abonnement valider");
        } catch (PseudoNonTrouverException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(" Pseudo Non Trouver");
        } catch (PasDabonnementValideException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Vous n'avez pas d'abonnement valide");

        } catch (ParseException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Vous n'etes pas autoriser ");


    }


    /**
     * désinscription d'un utilisateur de la base
     * @param pseudo
     *
     * @return
     */
    @DeleteMapping("/utilisateur/{pseudo}")
    public ResponseEntity  deleteUtilisateurById(@PathVariable String pseudo,Principal principal) {


        if (principal.getName().equals(pseudo)){

            try {
                facadeUserImpl.signOut(pseudo);

                return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Utilisateur supprimer");
            } catch (UtilisateurInexistantException e) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("L'utilisateur n'est pas inscrit");
            }


        }

       return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Vous n'etes pas autoriser");
    }

    /**
     * suppression d'une collection de gestion d'abonnement d'un utilisateur
     * @param pseudo  le pseudo de l'utilisateur en variable uri
     * @return
     */
    @DeleteMapping("/utilisateur/{pseudo}/gestionAbonnement/{pseudo}")
    public ResponseEntity  deleteGestionAbonnementById(@PathVariable String pseudo, Principal principal) {


        if (principal.getName().equals(pseudo)) {

            try {
                facadeUserImpl.getEmailByPseudo(pseudo);
                facadeTransportImpl.removeGestion(pseudo);

                return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
            } catch (UtilisateurInexistantException e) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }


        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Vous n'etes pas autoriser à supprimer");
    }

















}
