package mongoDB.fabrique;

import mongoDB.modele.Ticket;
import org.bson.types.ObjectId;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;

public class FabriqueTicket {

    /**
     * permet de creer un ticket à partir de la date du jour
     * @return
     */
    public static Ticket createTicket(){

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate localDate = LocalDate.now();
        Ticket ticket= new Ticket();
        ticket.setId(new ObjectId());
        ticket.setDateAchat(dtf.format(localDate));
        ticket.setEtat("valide");



        return ticket;
    }


    /**
     * permet de recuperer un ticket de la collection
     * @param objectId
     * @param dateAchat
     * @param etat
     * @return
     */
    public static Ticket createTicket(ObjectId objectId,String dateAchat,String etat){


        Ticket ticket= new Ticket();
        ticket.setId(objectId);
        ticket.setDateAchat(dateAchat);
        ticket.setEtat(etat);



        return ticket;
    }


    /**
     * permet de creer de dix tickets
     * @return
     */
    public static Collection<Ticket> createDixTicket(){

        Collection<Ticket> tickets = new ArrayList<>();
        for(int i = 0;i<10; i++){
            tickets.add(createTicket());
        }



        return tickets;
    }

}
