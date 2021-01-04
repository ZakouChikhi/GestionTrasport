package test;

import mongoDB.exception.MailNonTrouverException;
import mongoDB.exception.PasDabonnementValideException;
import mongoDB.facade.FacadeTransport;
import mongoDB.facade.FacadeTransportImpl;

import java.text.ParseException;

public class Main2 {
    public static void main(String[] args) {


        FacadeTransport facadeTransport = new FacadeTransportImpl();


        try {
            facadeTransport.validerAbonnement("zakaria@live.fr");
        } catch (MailNonTrouverException e) {
            e.printStackTrace();
        } catch (PasDabonnementValideException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
