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
    private ClubType type;
    private String clubID;

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

    public void deleteMember(User user) {
        for (int i = 0; i < members.size(); i++) {
            if (members.get(i).getEmail().equals(user.getEmail())) {
                members.remove(user);
            }
        }
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

    public ClubType getType()
    {
        return type;
    }

    public void addEvent(Event e)
    {
        events.add(e);
        numEvents++;
    }

    public void removeEvent(Event e)
    {
        events.remove(e);
        numEvents--;
    }

    public String getClubID() {
        return clubID;
    }
}
