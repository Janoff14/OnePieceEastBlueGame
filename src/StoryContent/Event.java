package StoryContent;

import characters.Character;
import locations.Location;
import locations.LocationManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Event {
    private int eventID;
    private Location location;
    private ArrayList<Character> characters; // Assuming character names for simplicity
    private StoryNode storyNode;

    private ArrayList<String> choices;

    public ArrayList<String> getChoices() {
        return choices;
    }

    public void setChoices(ArrayList<String> choices) {
        this.choices = choices;
    }

    // Constructor
    public Event(int id, StoryNode storyNode, ArrayList<Character> characters, Location location, ArrayList<String> choices) {
        this.choices = choices;
        this.eventID = id;
        this.storyNode = storyNode;
        this.characters = characters; // Splitting characters by comma and optional whitespace
        this.location = location;
    }


    // Getters and Setters
    public int getEventID() {
        return eventID;
    }

    public void setEventID(int eventID) {
        this.eventID = eventID;
    }


    public Location getLocation() {
        return location;
    }

    public ArrayList<Character> getCharacters() {
        return characters;
    }

    public StoryNode getStoryNode() {
        return storyNode;
    }

    public void setStoryNode(StoryNode storyNode) {
        this.storyNode = storyNode;
    }

}

