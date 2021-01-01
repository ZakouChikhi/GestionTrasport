package Authentification;

public class DatabaseParameters {
    public String url;
    public String username;
    public String password;

    public DatabaseParameters() {
        this.url = "jdbc:mysql://localhost:3306/gesttrans";
        this.username = "root";
        this.password = "";
    }
}
