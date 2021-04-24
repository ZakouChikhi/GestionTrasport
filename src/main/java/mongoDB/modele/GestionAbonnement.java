package mongoDB.modele;

import org.bson.types.ObjectId;

import java.util.Collection;


/**
 * represent une gestion d'abonnement d'un utilisateur
 */
public class GestionAbonnement {

    private ObjectId id;
    String mailUtilisateur;
    private Abonnement abonnement;
    private Collection<Ticket> tickets;



      public String getMailUtilisateur() {
        return mailUtilisateur;
    }

    public void setMailUtilisateur(String mailUtilisateur) {
        this.mailUtilisateur = mailUtilisateur;
    }


    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public Abonnement getAbonnement() {
        return abonnement;
    }

    public void setAbonnement(Abonnement abonnement) {
        this.abonnement = abonnement;
    }

    public Collection<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(Collection<Ticket> tickets) {
        this.tickets = tickets;
    }


    @Override
    public String toString() {
        return "GestionAbonnement{" +
                "id=" + id +
                ", mailUtilisateur='" + mailUtilisateur + '\'' +
                ", abonnement=" + abonnement +
                ", tickets=" + tickets +
                '}';
    }
}
