package characters;

import org.w3c.dom.CharacterData;

import java.util.HashMap;

public class Character {
    private String name;
    private HashMap<String, String> characterPoses = new HashMap<>();
    private int health;
    private int attack;
    private int maxHealth;
    private String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public  Character(String name, int health, int attack, String description){
        this.name = name;
        this.maxHealth = health;
        this.attack = attack;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public HashMap<String, String> getCharacterPoses() {
        return characterPoses;
    }

    public void setCharacterPoses(HashMap<String, String> characterPoses) {
        this.characterPoses = characterPoses;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getAttack() {
        return attack;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public void setMaxHealth(int maxHealth) {
        this.maxHealth = maxHealth;
    }



    public void addPoses(HashMap<String, String> characterPoses){
        this.characterPoses = characterPoses;
    }
}
