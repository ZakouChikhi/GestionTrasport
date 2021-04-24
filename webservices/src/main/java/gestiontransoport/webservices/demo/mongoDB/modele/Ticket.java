package gestiontransoport.webservices.demo.mongoDB.modele;

import org.bson.types.ObjectId;


/**
 * represent un ticket de transport
 */
public class Ticket {

    private ObjectId id;
    private String dateAchat;
    private String etat;

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getDateAchat() {
        return dateAchat;
    }

    public void setDateAchat(String dateAchat) {
        this.dateAchat = dateAchat;
    }

    public String getEtat() {
        return etat;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }


    @Override
    public String toString() {
        return "Ticket{" +
                "id=" + id +
                ", dateAchat='" + dateAchat + '\'' +
                ", etat='" + etat + '\'' +
                '}';
    }
}
