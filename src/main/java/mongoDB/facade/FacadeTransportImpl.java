package mongoDB.facade;


import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import mongoDB.exception.MailDejaDansLaCollectionException;
import mongoDB.exception.MailNonTrouverException;
import mongoDB.exception.PasDeTitreValideException;
import mongoDB.fabrique.FabriqueAbonnementAnnuel;
import mongoDB.fabrique.FabriqueAbonnementMensual;
import mongoDB.fabrique.FabriqueGestionAbonnement;
import mongoDB.fabrique.FabriqueTicket;
import mongoDB.modele.Abonnement;
import mongoDB.modele.GestionAbonnement;
import mongoDB.modele.Ticket;
import org.bson.Document;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;


public class FacadeTransportImpl implements FacadeTransport {


    MongoClient mongoClient ;
    MongoDatabase mongoDatabase;

    public FacadeTransportImpl() {
        this.mongoClient = MongoClients.create();

        this.mongoDatabase = mongoClient.getDatabase("gestionTransport");

        if (mongoDatabase.getCollection("transport")==null){
            mongoDatabase.createCollection("transport");
        }

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


            }else {
                throw new MailNonTrouverException("le mail n'existe pas dans la base");
            }
        }

        return gestionAbonnement1;
    }

    @Override
    public void validerTicket(String mail) throws MailNonTrouverException, PasDeTitreValideException {

       //TODO: changer la valeur du ticket de valide a expirer


        Collection<Ticket> tickets=getGestion(mail).getTickets();
        Ticket ticketUpdate=null;

        for (Ticket ticket : tickets){
            if (ticket.getEtat().equals("valide")){
                ticketUpdate = ticket;

                ticketUpdate.setEtat("expire");



                MongoCollection<Document> transport = mongoDatabase.getCollection("transport");
                Document document = acheterTicket(ticketUpdate);

                Document abb = new Document("tickets", ticket.getId());

                Document ubdate = new Document("$set",new Document("tickets",document));

                transport.updateOne(abb,ubdate);

                break;


            }else {
                throw new PasDeTitreValideException("vous avez pas de titre valide ");
            }
        }
    }



}
