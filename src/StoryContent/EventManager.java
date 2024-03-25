package StoryContent;

import characters.Character;
import characters.CharacterManager;
import locations.Location;
import locations.LocationManager;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class EventManager {

    private ArrayList<Event> events = new ArrayList<>();

    private final String CHARACTERS_CSV_PATH = "one_piece_characters.csv";
    private final String LOCATIONS_CSV_PATH = "one_piece_locations.csv";
    private final String POSES_CSV_PATH = "characterPoses/one_piece_characters_poses.csv";
    private final String STORY_DATA_JSON = "C:\\Users\\sanja\\IdeaProjects\\OnePiece-EastBlueSaga\\story_tree.json";


    public ArrayList<Event> getEvents() {
        return events;
    }

    public EventManager(String eventFilePath){
        loadEvents(eventFilePath);
    }

    private void loadEvents(String eventFilePath){
        LocationManager locationManager = new LocationManager(LOCATIONS_CSV_PATH);
        CharacterManager characterManager = new CharacterManager(CHARACTERS_CSV_PATH, POSES_CSV_PATH);
        try (BufferedReader br = new BufferedReader(new FileReader(eventFilePath))) {
            String line;
            br.readLine(); // Skip the header
            while ((line = br.readLine()) != null) {
                String[] eventDetails = line.split("\\|");
                if (eventDetails.length == 4) {
                    int eventID = Integer.parseInt(eventDetails[0].trim());
                    String description = (eventDetails[1].trim());
                    String[] charactersName = eventDetails[2].trim().split("-");
                    ArrayList<Character> characterArrayList = loadCharacters(charactersName, characterManager);
                    String locationName = eventDetails[3].trim();
                    Location location = loadLocation(locationName, locationManager);
                    StoryNode storyNode = loadStoryNode(description);

                    ArrayList<String> choices = new ArrayList<>();

//                    System.out.println("cho" + choices.getLast());
                    if (Objects.equals(storyNode.getType(), "narrative")){
                        choices = getChoices(storyNode);
                    }
                    Event event = new Event(eventID, storyNode, characterArrayList, location, choices);
//                    System.out.println(event.toString());
                    events.add(event);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private ArrayList<Character> loadCharacters(String[] characters, CharacterManager characterManager){
        ArrayList<Character> characterArrayList = new ArrayList<>();
        Map<String, Character> characterMap = characterManager.getCharacterMap();
        for (Character character:characterMap.values()){
            System.out.println("Map " + character.getName());
        }
        for (String charName: characters){
            System.out.println(charName);
            Character character = characterMap.get(charName);
            System.out.println(character.getName());
            characterArrayList.add(character);
        }
        return characterArrayList;
    }



    private Location loadLocation(String locationName, LocationManager locationManager){
        Map<String, Location> locationMap = locationManager.getLocationMap();

        return locationMap.get(locationName);
    }

    private StoryNode loadStoryNode(String text) {
        StoryTree storyTree = new StoryTree();
        Map<Integer, StoryNode> storyNodeHashMap = storyTree.getNodes();
        StoryNode storyNode1 = new StoryNode(0, "", " ");

        // Iterate over the values of the map, which are StoryNode objects
        for (StoryNode storyNode : storyNodeHashMap.values()) {
            // Compare the normalized texts (without any spaces)


            // Debugging output, can be removed or adjusted as needed

            // If a match is found, return the corresponding StoryNode
            if (storyNode.getText().equals(text)) {

                return storyNode;
            }
        }

        // If no match is found, return null
        return null;
    }

    private ArrayList<String> getChoices(StoryNode currentStoryNode){
        ArrayList<String> choices;
        choices = new ArrayList<>();
        if (currentStoryNode.getLeftChild() != null && currentStoryNode.getRightChild() != null){
            choices.add(currentStoryNode.getLeftChild().getText());
            choices.add(currentStoryNode.getRightChild().getText());

        }
        return choices;
    }

}
