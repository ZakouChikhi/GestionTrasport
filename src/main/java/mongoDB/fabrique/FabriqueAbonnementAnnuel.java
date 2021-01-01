package mongoDB.fabrique;

import mongoDB.modele.Abonnement;
import org.bson.types.ObjectId;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class FabriqueAbonnementAnnuel {

    public static Abonnement createAbonnementMensual(){

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate localDate = LocalDate.now();

        Abonnement abonnement= new Abonnement();
        abonnement.setId(new ObjectId());
        abonnement.setType("mensual");
        abonnement.setDateDebut(dtf.format(localDate));
        abonnement.setDateFin(dtf.format(localDate.plusYears(1)));
        abonnement.setEtat("valide");


        return abonnement;
    }
    public static Abonnement createAbonnementAnnuel(ObjectId objectId,String type, String dateDebut,String dateFin,String etat){


        Abonnement abonnement= new Abonnement();
        abonnement.setId(objectId);
        abonnement.setType(type);
        abonnement.setDateDebut(dateDebut);
        abonnement.setDateFin(dateFin);
        abonnement.setEtat(etat);


        return abonnement;
    }
}
