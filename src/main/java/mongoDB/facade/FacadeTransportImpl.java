package mongoDB.facade;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import mongoDB.exception.MailDejaDansLaCollectionException;
import mongoDB.exception.MailNonTrouverException;
import mongoDB.modele.Abonnement;
import mongoDB.modele.GestionAbonnement;
import mongoDB.modele.Ticket;
import org.bson.Document;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import java.util.function.Consumer;



public class FacadeTransportImpl implements FacadeTransport {


    MongoClient mongoClient ;
    MongoDatabase mongoDatabase;

    public FacadeTransportImpl() {
        mongoClient = new MongoClient("localhost", 27017);
        mongoDatabase = mongoClient.getDatabase("gestionTransport");
        //mongoDatabase.createCollection("transport");
        MongoCollection<Document> transport = mongoDatabase.getCollection("transport");

      //  transport.deleteMany(new Document());




    }



    @Override
    public Document acheterTicket(Ticket ticket) {
        MongoCollection<Document> transport = mongoDatabase.getCollection("transport");


        Document tick = new Document("_id",ticket.getId())
                .append("dateAchat",ticket.getDateAchat())
                .append("etat",ticket.getEtat());

        return tick;
    }

    @Override
    public Collection<Document> acheterDixTicket(Collection<Ticket> tickets) {

        Collection<Document> documentCollection = new ArrayList<>();

        for (Ticket ticket : tickets) {
            documentCollection.add(acheterTicket(ticket));
        }

        return documentCollection;
    }

    @Override
    public Document souscrireAbonnement(Abonnement abonnement) {



        MongoCollection<Document> transport = mongoDatabase.getCollection("transport");


        Document abbonementD =  new Document("_id", abonnement.getId())
                                .append("type", abonnement.getType())
                                .append("dateDebut",abonnement.getDateDebut())
                                .append("dateFin",abonnement.getDateFin())
                                .append("etat",abonnement.getEtat());


        return abbonementD;

    }



    @Override
    public void uptadeAbonnement(String mailUtilisateur, Abonnement abonnement) throws MailNonTrouverException {

      if (!getAllMails().contains(mailUtilisateur)){
          throw new MailNonTrouverException("mail non trouvé");
      }else
        {


            MongoCollection<Document> transport = mongoDatabase.getCollection("transport");
            Document document = souscrireAbonnement(abonnement);
            Document abb = new Document("mailUtilisateur", mailUtilisateur);

            Document ubdate = new Document("$set",new Document("abonnement",document));

            transport.updateOne(abb,ubdate);




        }

    }

    @Override
    public void uptadeTicket(String mailUtilisateur, Ticket ticket) throws MailNonTrouverException {

        //TODO:


        if (!getAllMails().contains(mailUtilisateur)) {
            throw new MailNonTrouverException("mail non trouvé");
        } else {


            MongoCollection<Document> transport = mongoDatabase.getCollection("transport");

            Document document = acheterTicket(ticket);

            Document abb = new Document("mailUtilisateur", mailUtilisateur);

            Document ubdate = new Document("$addToSet", new Document("tickets", document));

            transport.updateOne(abb, ubdate);


        }
    }








        @Override
        public void uptadeDixTicket(String mailUtilisateur,Collection<Ticket> tickets) throws MailNonTrouverException {

            //TODO:


            if (!getAllMails().contains(mailUtilisateur)){
                throw new MailNonTrouverException("mail non trouvé");
            }else

            {


                for (Ticket ticket : tickets){
                    uptadeTicket(mailUtilisateur,ticket);
                }



            }


    }

    @Override
    public void creerGestionAbonnement(GestionAbonnement gestionAbonnement) throws MailDejaDansLaCollectionException {

        //TODO:

        
       if (getAllMails().contains(gestionAbonnement.getMailUtilisateur())){

           throw new MailDejaDansLaCollectionException("Le mail utilisateur existe deja");

       }else {


           MongoCollection<Document> transport = mongoDatabase.getCollection("transport");




           Document gestionA = new Document("_id", gestionAbonnement.getId())
                   .append("mailUtilisateur",gestionAbonnement.getMailUtilisateur());

           if (gestionAbonnement.getAbonnement()!= null){
               Document abonnement = souscrireAbonnement(gestionAbonnement.getAbonnement());
               gestionA.append("abonnement",abonnement);
           }if (gestionAbonnement.getTickets() != null){
               Collection<Document> tickets = acheterDixTicket(gestionAbonnement.getTickets());
               gestionA.append("tickets",tickets);
           }


           transport.insertOne(gestionA);

       }


    }




    @Override
    public Collection<String> getAllMails() {
        MongoCollection<Document> transport = mongoDatabase.getCollection("transport");
        Collection<String> resultats = new ArrayList<>();
        Consumer<Document> consumer = d -> resultats.add(d.getString("mailUtilisateur"));


        transport.find().forEach(consumer);

        return resultats;

    }

    @Override
    public Optional<Document> getDateFin(String mail) throws MailNonTrouverException {

        if (!getAllMails().contains(mail)) {
            throw new MailNonTrouverException("mail non trouvé");

        } else {

            MongoCollection<Document> transport = mongoDatabase.getCollection("transport");



            Optional<Document> document = Optional.ofNullable(transport.find().filter(Filters.eq("mailUtilisateur", mail)).first());





            return document;


        }
    }


}
