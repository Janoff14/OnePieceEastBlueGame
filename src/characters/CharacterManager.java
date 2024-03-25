package characters;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class CharacterManager {

    private Map<String, Character> characterMap = new HashMap<>();

    public CharacterManager(String charactersCsvPath, String posesCsvPath) {
        loadCharacters(charactersCsvPath);
        loadPoses(posesCsvPath);
    }



    private void loadCharacters(String filePath) {
        // Read the character CSV file
        // For each line, create a new Character object with the name, health, attack, and description.
        // Add the Character object to the characterMap with the character's name as the key.
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            br.readLine(); // Skip the header
            while ((line = br.readLine()) != null) {
                String[] characterDetails = line.split(",");
                if (characterDetails.length == 4) {
                    String name = characterDetails[0];
                    int health = Integer.parseInt(characterDetails[1].trim());
                    int attack = Integer.parseInt(characterDetails[2].trim());
                    String description = characterDetails[3];
                    // Initialize an empty poses map, to be filled in later by loadPoses
                    Character character = new Character(name, health, attack, description);
                    characterMap.put(name, character);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadPoses(String csvFilePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(csvFilePath))) {
            String line;
            br.readLine(); // Skip the header
            while ((line = br.readLine()) != null) {
                String[] poseDetails = line.split(",");
                if (poseDetails.length == 3) {
                    String characterName = poseDetails[0].trim();
                    String poseName = poseDetails[1].trim();
                    String filePath = poseDetails[2].trim();

                    // Find the corresponding Character object and update its poses map
                    Character character = characterMap.get(characterName);
                    if (character != null) {
                        character.getCharacterPoses().put(poseName, filePath);
                    } else {
                        // Optionally, handle the case where a character from the poses file doesn't exist in the characterMap
//                        System.out.println("Warning: No character found for pose entry: " + characterName);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Map<String, Character> getCharacterMap() {
        return characterMap;
    }

    // Utility methods can go here
}
