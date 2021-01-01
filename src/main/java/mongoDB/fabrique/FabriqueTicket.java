package mongoDB.fabrique;

import mongoDB.modele.Ticket;
import org.bson.types.ObjectId;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;

public class FabriqueTicket {

    public static Ticket createTicket(){

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate localDate = LocalDate.now();
        Ticket ticket= new Ticket();
        ticket.setId(new ObjectId());
        ticket.setDateAchat(dtf.format(localDate));
        ticket.setEtat("valide");



        return ticket;
    }


    public static Ticket createTicket(ObjectId objectId,String dateAchat,String etat){


        Ticket ticket= new Ticket();
        ticket.setId(objectId);
        ticket.setDateAchat(dateAchat);
        ticket.setEtat(etat);



        return ticket;
    }

    public static Collection<Ticket> createDixTicket(){
        Collection<Ticket> tickets = new ArrayList<>();

        for(int i = 0;i<10; i++){
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate localDate = LocalDate.now();
            Ticket ticket= new Ticket();
            ticket.setId(new ObjectId());
            ticket.setDateAchat(dtf.format(localDate));
            ticket.setEtat("valide");
            tickets.add(ticket);
        }



        return tickets;
    }

}
