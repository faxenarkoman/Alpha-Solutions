package kea.dk.alpha_solutions.model;

public class User
{
    private int userId;
    private int hourlyWage;
    private String password;
    private String mail;
    private String name;


    //Constructor for User
    public User(int userId, String mail, String password, int hourlyWage, String name){
        this.userId = userId;
        this.mail = mail;
        this.password = password;
        this.name = name;
        this.hourlyWage = hourlyWage;
    }

    public User(String name, String mail, String password) {
        this.mail = mail;
        this.name = name;
        this.password = password;
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
}
