package com.example.eventstrackerapp.profile;
import java.util.ArrayList;
import com.example.eventstrackerapp.Event;

/**
 * Holds information for a specific club, including their name,
 */
public class Club {
    private String name;
    private String description;
    private String email;
    private int numMembers, numEvents;
    private ArrayList<User> members;
    private ArrayList<Event> events;
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

    public ArrayList<User> getMembers()
    {
        return members;
    }

    public ArrayList<Event> getEvents()
    {
        return events;
    }

    public ArrayList<ClubExec> getExecutives()
    {
        return executives;
    }

    public void addEvent(Event e)
    {
        events.add(e);
    }

    public void removeEvent(Event e)
    {
        events.remove(e);
    }
}
