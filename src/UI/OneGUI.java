package UI;

import StoryContent.Event;
import StoryContent.EventManager;
import StoryContent.StoryNode;
import characters.Character;
import gameEngine.GameEngine;
import locations.Location;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.ArrayList;
import java.util.Objects;

public class OneGUI {
    public JLabel backgroundLabel = new JLabel(); // Assume this is initialized somewhere in your GUI setup
    public JPanel characterPanel; // A panel to hold character images, ensuring they're layered on the background

    public JPanel bgPanel = new JPanel();
    public JFrame frame;
    public JTextArea storyTextArea;
    public GameEngine gameEngine;
    public JPanel mainPanel;

    public Event currentEvent;

    private Location currentLocation;
    private ArrayList<Character> currentCharacters;
    private String currentStory;

    private JPanel bottomPanel;
    public  JButton choice_btn_1;
    public JButton choice_btn_2;
    private ArrayList<String> choices;
    public OneGUI(){


        gameEngine = new GameEngine();
        currentEvent = gameEngine.getCurrentEvent();
        currentLocation = currentEvent.getLocation();
        currentCharacters = currentEvent.getCharacters();
        currentStory = currentEvent.getStoryNode().getText();

        currentEvent = gameEngine.getNextEvent(currentStory);
        updateCurrentEvent(currentEvent);
        choices = gameEngine.getEventChoices(currentEvent);

        initializeGUI();


    }

    private void setupBackground() {
        backgroundLabel.setLayout(new BorderLayout()); // Use BorderLayout for layout

        // Load the original icon
        ImageIcon originalIcon = new ImageIcon(currentLocation.getImagePath());

        // Set initial icon
        updateBackgroundImage(originalIcon);

        // Add a resize listener to the frame to update the background image when the frame size changes
        frame.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                updateBackgroundImage(originalIcon);
            }
        });

        // Add the background label to the main panel
        mainPanel.add(backgroundLabel, BorderLayout.CENTER);
    }

    // Method to update the background image based on the current frame size
    private void updateBackgroundImage(ImageIcon originalIcon) {
        Image image = originalIcon.getImage(); // Get the original image
        // Scale the image to fit the current frame size
        Image scaledImage = image.getScaledInstance(frame.getWidth(), frame.getHeight(), Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage); // Convert back to an ImageIcon

        backgroundLabel.setIcon(scaledIcon); // Update the background label's icon
        backgroundLabel.revalidate(); // Revalidate to ensure the label updates
        backgroundLabel.repaint(); // Repaint to apply the changes visually
    }

    private void setupCharacterPanel() {
        characterPanel = new JPanel();
        characterPanel.setOpaque(false); // Make the panel transparen
        updateCharacterPanel(currentCharacters);
        Border border = BorderFactory.createLineBorder(Color.RED, 2); // Temporary to see each label's bounds
        characterPanel.setBorder(border);
        backgroundLabel.add(characterPanel, BorderLayout.CENTER);

    }
    public void updateCharacterPanel(ArrayList<Character> characters) {
        characterPanel.removeAll(); // Clear the panel for new character icons
        int iconWidth = frame.getWidth() *3/10; // 10% of screen width
        int iconHeight = frame.getHeight() * 3 / 10; // 30% of screen height
        System.out.println("CHARACTERS     " + characters.get(1).getName());

        // Iterate over the characters and add their icons to the panel
        for (Character character : characters) {

            ImageIcon originalIcon = new ImageIcon(character.getCharacterPoses().get("happy"));
            Image image = originalIcon.getImage(); // Transform it
            Image newimg = image.getScaledInstance(iconWidth, iconHeight, java.awt.Image.SCALE_SMOOTH); // Scale it smoothly
            ImageIcon characterIcon = new ImageIcon(newimg);  // Transform it back
            JLabel characterLabel = new JLabel(characterIcon);
            characterPanel.add(characterLabel);
        }

        characterPanel.revalidate();
        characterPanel.repaint(); // Repaint the panel to display the new icons
    }
    private void setupStoryTextArea(JPanel bottomPanel) {
        storyTextArea = new JTextArea();
        storyTextArea.setEditable(false); // Make the text area non-editable
        storyTextArea.setLineWrap(true);
        storyTextArea.setWrapStyleWord(true);
        storyTextArea.setText(currentStory);

        JScrollPane scrollPane = new JScrollPane(storyTextArea);
        scrollPane.setPreferredSize(new Dimension(frame.getWidth(), frame.getHeight() / 10)); // 10% of the window size
// Add the text area to a scroll pane for scrolling
        bottomPanel.add(scrollPane, BorderLayout.SOUTH); // Add the scroll pane to the bottom of the main panel
    }
    private void updateScreen(Event currentEvent1) {
        // Update the current event, location, characters, and story from the game engine
        updateCurrentEvent(currentEvent1);

        // Update the background
        updateBackground();

        // Update the characters displayed
        updateCharacterPanel(currentEvent1.getCharacters());

        // Update the story text
        storyTextArea.setText(currentEvent1.getStoryNode().getText());


        // Update the choice buttons
        updateButtons(currentEvent1.getChoices());

        // Revalidate and repaint the main panel to reflect changes
        mainPanel.revalidate();
        mainPanel.repaint();
    }

    private void updateCurrentEvent(Event nextEvent){
        currentStory = nextEvent.getStoryNode().getText();
        currentLocation = nextEvent.getLocation();
        currentCharacters = nextEvent.getCharacters();
        choices = gameEngine.getEventChoices(nextEvent);

    }
    private void setupButtons(JPanel bottomPanel) {
        JPanel buttonsPanel = new JPanel(); // Panel to hold the buttons
        buttonsPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10)); // Use FlowLayout for side-by-side buttons

        String button_1_text = "b1";
        String button_2_text = "b2";
        // Create and add the first button
        if (!choices.isEmpty()) {

            button_1_text = choices.getFirst();
            button_2_text = choices.getLast();
        }
        choice_btn_1 = new JButton(button_1_text);
        choice_btn_1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                Event thisEvent = gameEngine.getNextEvent(choice_btn_1.getText());
                updateScreen(thisEvent);
            }
        });
        buttonsPanel.add(choice_btn_1);

        // Create and add the second button
        choice_btn_2 = new JButton(button_2_text);
        choice_btn_2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Event thisEvent = gameEngine.getNextEvent(choice_btn_2.getText());
                updateScreen(thisEvent);
            }
        });
            buttonsPanel.add(choice_btn_2);

        // Add the buttons panel below the story text area
        bottomPanel.add(buttonsPanel, BorderLayout.PAGE_END); // Ensures it's at the very bottom
    }

    private void updateBackground() {
        ImageIcon imageIcon = new ImageIcon(currentLocation.getImagePath());
        backgroundLabel.setIcon(imageIcon);
    }

    private void updateButtons(ArrayList<String> choices1) {

        // Clear existing buttons
        choice_btn_1.setVisible(false);
        choice_btn_2.setVisible(false);

        // Assuming Event.getChoices() returns a list of choices and each choice has a text
        if (!choices1.isEmpty()) {
            String button_1_text = choices1.getFirst(); // Get text for the first choice
            String button_2_text = choices1.getLast() ; // Get text for the second choice, if exists

            choice_btn_1.setText(button_1_text);
            choice_btn_1.setVisible(true); // Make the first button visible again with updated text

            if (!button_2_text.isEmpty()) {
                choice_btn_2.setText(button_2_text);
                choice_btn_2.setVisible(true); // Make the second button visible again with updated text if it exists
            }
        } else {
            JOptionPane.showMessageDialog(frame, "The game is over. Thank you for playing!", "Game Over", JOptionPane.INFORMATION_MESSAGE);

        }
    }

    private void initializeGUI() {
        // JFrame setup
        frame = new JFrame("One Piece Adventure");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);

        mainPanel = new JPanel(new BorderLayout());
        frame.add(mainPanel);


        setupBottomPanel();
        setupBackground(); // Set up the background
        setupCharacterPanel(); // Set up the character panel


        frame.setVisible(true); // Make the frame visible
    }
    private void setupBottomPanel() {
        bottomPanel = new JPanel();
        bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.PAGE_AXIS)); // Stack components vertically

        setupStoryTextArea(bottomPanel); // Pass bottomPanel to add the story text area to it
        setupButtons(bottomPanel); // Pass bottomPanel to add the buttons to it

        if (!currentEvent.getChoices().isEmpty()){
        }
        mainPanel.add(bottomPanel, BorderLayout.SOUTH); // Add the bottom panel to the main panel
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(OneGUI::new);
    }

}
