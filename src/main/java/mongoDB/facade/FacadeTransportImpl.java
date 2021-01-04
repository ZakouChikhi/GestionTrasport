package mongoDB.facade;


import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import mongoDB.exception.MailDejaDansLaCollectionException;
import mongoDB.exception.MailNonTrouverException;
import mongoDB.exception.PasDabonnementValideException;
import mongoDB.exception.PasDeTitreValideException;
import mongoDB.fabrique.FabriqueAbonnementAnnuel;
import mongoDB.fabrique.FabriqueAbonnementMensual;
import mongoDB.fabrique.FabriqueGestionAbonnement;
import mongoDB.fabrique.FabriqueTicket;
import mongoDB.modele.Abonnement;
import mongoDB.modele.GestionAbonnement;
import mongoDB.modele.Ticket;
import mongoDB.mongo.Mongo;
import mongoDB.timer.TimerTicket;
import org.bson.Document;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.function.Consumer;


public class FacadeTransportImpl  implements FacadeTransport  {


    MongoClient mongoClient ;
    MongoDatabase mongoDatabase;


    public FacadeTransportImpl() {

        this.mongoClient = MongoClients.create();

        this.mongoDatabase = mongoClient.getDatabase("gestionTransport");

        if (mongoDatabase.getCollection("transport")==null){
            mongoDatabase.createCollection("transport");
        }

     // mongoDatabase.getCollection("transport").deleteMany(new Document());




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

            System.out.println("vous venez de souscrire à l'abonnement " + abonnement.getType() + " jusqu'a " + abonnement.getDateFin());




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

            System.out.println("vous venez d'acheter un ticket " );



        }
    }








        @Override
        public void uptadeDixTicket(String mailUtilisateur,Collection<Ticket> tickets) throws MailNonTrouverException {




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



        
       if (getAllMails().contains(gestionAbonnement.getMailUtilisateur())){

           throw new MailDejaDansLaCollectionException("L'utilisateur existe deja");

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
    public Collection<GestionAbonnement> getAllGestions() {


            MongoCollection<Document> transport = mongoDatabase.getCollection("transport");
            Collection<GestionAbonnement> resultat = new ArrayList<>();

              Consumer<Document> consumer = e ->

            {

                GestionAbonnement gestionAbonnement=null;
                Collection<Ticket> tickets= new ArrayList<>();
                List<Document> ticketsDocument= e.getList("tickets",Document.class);

             if (ticketsDocument==null){

                 Document abonnement = e.get("abonnement",Document.class);
                 Abonnement abonnement1;

                 if (abonnement!=null){

                     if (abonnement.getString("type").equals("mensual")){

                         abonnement1= FabriqueAbonnementMensual.createAbonnementMensual(abonnement.getObjectId("_id"),abonnement.getString("type"),abonnement.getString("dateDebut"),abonnement.getString("dateFin"),abonnement.getString("etat"));

                     }else{

                         abonnement1= FabriqueAbonnementAnnuel.createAbonnementAnnuel(abonnement.getObjectId("_id"),abonnement.getString("type"),abonnement.getString("dateDebut"),abonnement.getString("dateFin"),abonnement.getString("etat"));

                     }
                     gestionAbonnement = FabriqueGestionAbonnement.createGestionAbonnement(e.getObjectId("_id"),e.getString("mailUtilisateur"),abonnement1);

                 }else {
                     gestionAbonnement = FabriqueGestionAbonnement.createGestionAbonnement(e.getObjectId("_id"),e.getString("mailUtilisateur"));

                 }

             }else {
                 for (Document d: ticketsDocument
                 ) {

                     Ticket ticket  =  FabriqueTicket.createTicket(d.getObjectId("_id"), d.getString("dateAchat"),d.getString("etat"));
                     tickets.add(ticket);
                 }
                 Document abonnement = e.get("abonnement",Document.class);
                 Abonnement abonnement1;

                 if (abonnement!=null){

                     if (abonnement.getString("type").equals("mensual")){

                         abonnement1= FabriqueAbonnementMensual.createAbonnementMensual(abonnement.getObjectId("_id"),abonnement.getString("type"),abonnement.getString("dateDebut"),abonnement.getString("dateFin"),abonnement.getString("etat"));

                     }else{

                         abonnement1= FabriqueAbonnementAnnuel.createAbonnementAnnuel(abonnement.getObjectId("_id"),abonnement.getString("type"),abonnement.getString("dateDebut"),abonnement.getString("dateFin"),abonnement.getString("etat"));

                     }
                     gestionAbonnement = FabriqueGestionAbonnement.createGestionAbonnement(e.getObjectId("_id"),e.getString("mailUtilisateur"),abonnement1,tickets);

                 }else {
                     gestionAbonnement = FabriqueGestionAbonnement.createGestionAbonnement(e.getObjectId("_id"),e.getString("mailUtilisateur"),tickets);

                 }
             }




                resultat.add(gestionAbonnement);


            };


            transport.find().forEach(consumer);

              return resultat;



    }

    @Override
    public GestionAbonnement getGestion(String mail) throws MailNonTrouverException{
        Collection<GestionAbonnement> gestionAbonnements = getAllGestions();
        GestionAbonnement gestionAbonnement1 = null;

        for (GestionAbonnement gestionAbonnement : gestionAbonnements){
            if (gestionAbonnement.getMailUtilisateur().equals(mail)){
                gestionAbonnement1= gestionAbonnement ;


            }
        }
        if (gestionAbonnement1==null){
            throw new MailNonTrouverException("le mail n'existe pas dans la base");
        }

        return gestionAbonnement1;
    }

    @Override
    public void validerTicket(String mail) throws MailNonTrouverException, PasDeTitreValideException {

        Collection<Ticket> tickets=getGestion(mail).getTickets();
        int i = 0;
        Ticket ticketUpdate=null;

        for (Ticket ticket : tickets){


            if (ticket.getEtat().equals("valide")){

                System.out.println("votre ticket est valide pour une heure");
                i++;

                ticketUpdate = ticket;
                ticketUpdate.setEtat("expire");
                tickets.remove(ticket);
                tickets.add(ticketUpdate);
                MongoCollection<Document> transport = mongoDatabase.getCollection("transport");
                Collection<Document> documents = acheterDixTicket(tickets);


                Timer chrono = new Timer();
                chrono.schedule(new TimerTicket(mail,documents,transport),36000000);


                break;

            }
        }
        if (i==0){
            throw new PasDeTitreValideException("vous avez pas de titre valide");
        }
    }

    @Override
    public void validerAbonnement(String mail) throws MailNonTrouverException, PasDabonnementValideException, ParseException {
        Abonnement abonnement = getGestion(mail).getAbonnement();

        if (abonnement!=null){


        LocalDate localDate = LocalDate.now();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        Date present = sdf.parse(dtf.format(localDate));
        Date dateDebut = sdf.parse(abonnement.getDateDebut());
        Date dateFin = sdf.parse(abonnement.getDateFin());




        if ((present.compareTo(dateDebut)>=0 ) && (present.compareTo(dateFin)<=0)){

            System.out.println("votre abonnement est valide jusqu'a " + abonnement.getDateFin());


        }else {

            Abonnement abonnementUpdate = abonnement;
            abonnementUpdate.setEtat("expire");
            uptadeAbonnement(mail,abonnementUpdate);

            throw new PasDabonnementValideException("vous avez pas d'abonnement valide");
        }

    }else {
            throw new PasDabonnementValideException("vous avez pas d'abonnement valide");
        }




    }


}
