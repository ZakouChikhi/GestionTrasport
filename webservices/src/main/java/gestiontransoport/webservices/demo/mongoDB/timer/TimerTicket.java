package gestiontransoport.webservices.demo.mongoDB.timer;

import com.mongodb.client.MongoCollection;
import org.bson.Document;

import java.util.Collection;
import java.util.TimerTask;


/**
 * permet d'attendre 1H avant que le ticket ne soit plus valide
 */
public class TimerTicket extends TimerTask {

    private String mail;
    private Collection<Document> documents;
    private MongoCollection<Document> transport;
    private  int time=3650;

    public void setTime(int time) {
        this.time = time;
    }

    public TimerTicket(String mail, Collection<Document> documents, MongoCollection<Document> transport) {
        this.mail = mail;
        this.documents = documents;
        this.transport = transport;

    }

    @Override
    public void run() {

        Document abb = new Document("mailUtilisateur",mail);
        Document ubdate = new Document("$set",new Document("tickets",documents));

        transport.updateOne(abb,ubdate);
       /*
        if (time==0){
            cancel();
        }



        setTime(time--);


        */
        cancel();
    }
}
