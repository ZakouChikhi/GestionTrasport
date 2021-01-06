package mongoDB.mongo;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

public class Mongo {


        private static MongoClient mClient;

    public Mongo() {

    }

    private MongoClient getMongoClient() {

            if (mClient == null) {
                mClient = MongoClients.create();
            }
            return mClient;
        }

        // Utility method to get database instance
        private MongoDatabase getDB() {

            return getMongoClient().getDatabase("gestionTransport");
        }


        // Utility method to get user collection

        private MongoCollection<Document> getUserCollection() {
            if (getDB().getCollection("transport")==null){
                getDB().createCollection("transport");
            }
            return getDB().getCollection("transport");

        }




    }

