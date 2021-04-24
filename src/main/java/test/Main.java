package test;

import Authentification.facade.FacadeUser;
import Authentification.facade.FacadeUserImpl;

public class Main {
    public static void main(String[] args)  {



        FacadeUser faceUser = new FacadeUserImpl();
        String email = faceUser.getEmailById(1);
        System.out.println(email);



    }
}
