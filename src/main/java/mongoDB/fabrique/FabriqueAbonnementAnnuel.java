package mongoDB.fabrique;

import mongoDB.modele.Abonnement;
import org.bson.types.ObjectId;
import java.lang.String;
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
    public static Abonnement createAbonnementAnnuel(){

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate localDate = LocalDate.now();

        Abonnement abonnement= new Abonnement();
        abonnement.setId(new ObjectId());
        abonnement.setType("Annuel");
        abonnement.setDateDebut(dtf.format(localDate));
        abonnement.setDateFin(dtf.format(localDate.plusMonths(12)));
        abonnement.setEtat("valide");


        return abonnement;
    }
}
