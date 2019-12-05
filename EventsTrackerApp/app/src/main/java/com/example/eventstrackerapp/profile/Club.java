package com.example.eventstrackerapp.profile;
import java.util.ArrayList;

public class Club {
    private String name;
    private String description;
    private int numMembers, numEvents;
    private ArrayList<User> members;
    //private ArrayList<Event> events;
    private ArrayList<ClubExec> executives;

    public Club(String name)
    {
        this.name = name;
    }

    public String getDescription()
    {
        return this.description;
    }

    public String getName()
    {
        return this.name;
    }

    public int getNumMembers()
    {
        return this.numMembers;
    }

    public int getNumEvents()
    {
        return this.numEvents;
    }

    public void addMember(User user){ members.add(user); }

    public void deleteMember(User user){
        for(int i = 0; i < members.size(); i++){
            if(members.get(i).getEmail().equals(user.getEmail())){ members.remove(user); }
        }
    }
}
