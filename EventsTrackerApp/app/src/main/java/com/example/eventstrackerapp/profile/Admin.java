package com.example.eventstrackerapp.profile;
import com.example.eventstrackerapp.Event;
import com.google.firebase.auth.FirebaseAuth;

public class Admin {

    private String username, password, email;

    public Admin(String u, String p, String e)
    {
        username = u;
        password = p;
        email = e;
    }

    public String getUsername()
    {
        return this.username;
    }

    public String getPassword()
    {
        return this.password;
    }

    public String getEmail() {
        return this.email;
    }

    public void setUsername(String u)
    {
        this.username = u;
    }

    public void setPassword(String p)
    {
        this.password = p;
    }

    public void setEmail(String e)
    {
        this.email = e;
    }

    public void deleteUser(User u)
    {

    }

    public void deleteEvent(Event e)
    {

    }

    public void deleteClub(Club c)
    {

    }
}
