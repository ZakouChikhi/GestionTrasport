package mongoDB.fabrique;


import mongoDB.modele.Abonnement;
import org.bson.types.ObjectId;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class FabriqueAbonnementMensual {


    /**
     * permet de creer un abonnement annuel à partir de la date du jour
     * @return
     */
    public static Abonnement createAbonnementMensual(){

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate localDate = LocalDate.now();


        Abonnement abonnement= new Abonnement();
        abonnement.setId(new ObjectId());
        abonnement.setType("mensual");
        abonnement.setDateDebut(dtf.format(localDate));
        abonnement.setDateFin(dtf.format(localDate.plusMonths(1)));
        abonnement.setEtat("valide");


        return abonnement;
    }


    /***
     * permet de creer un Abonnement mensuel à partir de la base
     * @param objectId
     * @param type
     * @param dateDebut
     * @param dateFin
     * @param etat
     * @return
     */
    public static Abonnement createAbonnementMensual(ObjectId objectId,String type, String dateDebut,String dateFin,String etat){

        Abonnement abonnement= new Abonnement();
        abonnement.setId(objectId);
        abonnement.setType(type);
        abonnement.setDateDebut(dateDebut);
        abonnement.setDateFin(dateFin);
        abonnement.setEtat(etat);


        return abonnement;
    }


    }
