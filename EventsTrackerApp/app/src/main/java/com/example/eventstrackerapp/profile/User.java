package com.example.eventstrackerapp.profile;
import java.util.ArrayList;
import com.google.firebase.auth.FirebaseUser;

public class User {
    private String userID;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private int gradYear;
    private String userType;
    private ArrayList<Club> subscriptions;
    //private FirebaseUser useMe;

    public User() {
    }

    public User(String userID, String userType) {
        this.userID = userID;
        this.userType = userType;
    }

    public User(String userID, String firstName, String lastName){
        this.userID = userID;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public User(String email, String password, String firstName, String lastName, int gradYear, String userType, String userID) {
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gradYear = gradYear;
        this.userType = userType;
        this.userID = userID;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
        //useMe.updateEmail(password);
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getGradYear() {
        return gradYear;
    }

    public void setGradYear(int gradYear) {
        this.gradYear = gradYear;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public ArrayList<Club> getSubscriptions()
    {
        return subscriptions;
    }

    public void addClubToSubscriptionList(Club club)
    {
        subscriptions.add(club);
    }

    public void removeClubFromSubscriptionList(Club club)
    {
        subscriptions.remove(club);
    }
}
