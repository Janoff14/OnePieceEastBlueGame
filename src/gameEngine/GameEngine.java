package gameEngine;

import StoryContent.*;
import characters.Character;
import characters.CharacterManager;
import locations.Location;
import locations.LocationManager;

import java.util.ArrayList;
import java.util.Map;

public class GameEngine {
    private StoryTree storyTree;
    private StoryNode currentStoryNode;
    private Map<String, Character> characterMap;
    private Map<String, Location> locationMap;
    private ArrayList<Event> events;
    private final String CHARACTERS_CSV_PATH = "one_piece_characters.csv";
    private final String LOCATIONS_CSV_PATH = "one_piece_locations.csv";
    private final String EVENTS_CSV_PATH = "one_piece_events_final.csv";
    private final String POSES_CSV_PATH = "characterPoses/one_piece_characters_poses.csv";
    private Event currentEvent;


    public GameEngine(){
        initializeGame();
        currentEvent = getCurrentEvent();
    }

    private void initializeGame(){
        CharacterManager characterManager = new CharacterManager(CHARACTERS_CSV_PATH, POSES_CSV_PATH);
        LocationManager locationManager = new LocationManager(LOCATIONS_CSV_PATH);
        EventManager eventManager = new EventManager(EVENTS_CSV_PATH);
        events = eventManager.getEvents();


        storyTree = new StoryTree();

        characterMap = characterManager.getCharacterMap();
        locationMap = locationManager.getLocationMap();
//        System.out.println("ssssssssssssssssssssssssssssssssssssssssssss");



        // Begin the game loop
        gameLoop();

    }
    public ArrayList<String> getEventChoices(Event event){
        ArrayList<String> choices = new ArrayList<>();
        if (event.getStoryNode().getLeftChild() != null){
            choices.add(event.getStoryNode().getLeftChild().getText());

        }
        if (event.getStoryNode().getRightChild() != null){

            choices.add(event.getStoryNode().getRightChild().getText());

        }
        return choices;

    }
    public Event getNextEvent(String choiceText){

        Event currEvent = findEventByStoryNodeText(choiceText);
        StoryNode currStoryNode = currEvent.getStoryNode();
        if (currEvent.getStoryNode().getLeftChild() != null){
            currEvent = findEventByStoryNodeText(currStoryNode.getLeftChild().getText());
            return currEvent;
        }
        if (currEvent.getStoryNode().getRightChild() != null) {
            currentEvent = findEventByStoryNodeText(currStoryNode.getRightChild().getText());

            return currEvent;

        }

        return currEvent;

    }

    public Event findEventByStoryNodeText(String storyNodeText) {


        for (Event event : events) {


            if (event.getStoryNode().getText().equals(storyNodeText)) {
                return event;
            }
        }
        return null; // Handle case where no matching Event is found
    }


    public Event getCurrentEvent(){
        currentStoryNode = storyTree.getNodes().get(1);
        currentEvent = findEventByStoryNodeText(currentStoryNode.getText());
        return currentEvent;
    }
    public StoryNode getCurrentStoryNode() {
        return currentStoryNode;
    }


    private void gameLoop(){

    }
}
