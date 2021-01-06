package test;

import Authentification.facade.FacadeUser;
import Authentification.facade.FacadeUserImpl;
import mongoDB.exception.MailDejaDansLaCollectionException;
import mongoDB.exception.MailNonTrouverException;
import mongoDB.exception.PasDabonnementValideException;
import mongoDB.exception.PasDeTitreValideException;
import java.text.ParseException;

public class Main {
    public static void main(String[] args)  {



        FacadeUser faceUser = new FacadeUserImpl();
        String email = faceUser.getEmailById(1);
        System.out.println(email);



    }
}
