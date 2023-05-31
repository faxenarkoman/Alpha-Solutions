package kea.dk.alpha_solutions.model;

public class User
{
    private int userId;
    private int hourlyWage;
    private String password;
    private String mail;
    private String name;
    private boolean admin;


    //Constructor for User
    public User(int userId, String mail, String password, int hourlyWage, String name, boolean admin){
        this.userId = userId;
        this.mail = mail;
        this.password = password;
        this.name = name;
        this.hourlyWage = hourlyWage;
        this.admin = admin;
    }
    public User(){
        this.mail = mail;
        this.password = password;
        this.name = name;
        this.hourlyWage = hourlyWage;
    }
    public User(int userId, String mail, String password, String name, boolean admin) {
        this.mail = mail;
        this.name = name;
        this.password = password;
    }
    public User(String mail, String password, int hourlyWage, String name, boolean admin) {
        this.mail = mail;
        this.password = password;
        this.hourlyWage = hourlyWage;
        this.name = name;
        this.admin = admin;
    }


    public User(int userId, String email, String passwordHash, String name) {
        this.userId = userId;
        this.mail = email;
        this.password = passwordHash;
        this.name = name;
    }

    //Getters and setters
    public int getHourlyWage() {
        return hourlyWage;
    }

    public void setHourlyWage(int hourlyWage) {
        this.hourlyWage = hourlyWage;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public String getMail() {
    return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }
    public String getName() {
    return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean isAdmin) {
        this.admin = isAdmin;
    }

}
