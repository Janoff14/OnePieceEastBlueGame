package StoryContent;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StoryTree {
    private String filePath = "storyCSV.csv";
    private StoryNode root;


    private Map<Integer, StoryNode> nodes = new HashMap<>();
    private Map<Integer, Integer> parentMap = new HashMap<>();

    public StoryTree(){
        buildTree();
    }
    public void buildTree(){

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            br.readLine();
            while ((line = br.readLine()) != null) {
                String[] parts = line.trim().split("\\|");
                if (parts.length >= 4) {
                    int nodeId = Integer.parseInt(parts[0]);
                    int parentId = Integer.parseInt(parts[1]);
                    String text = parts[2];
                    String type = parts[3];

                    StoryNode node = new StoryNode(nodeId, text, type);
//                    System.out.println("STORYTREE" + node.getText());
                    nodes.put(nodeId, node);
                    parentMap.put(nodeId, parentId);

                    if (parentId == 0) { // assuming 0 is used for the root
                        root = node;
                    }
                }
            }

            for (Map.Entry<Integer, Integer> entry : parentMap.entrySet()) {
                int childId = entry.getKey();
                int parentId = entry.getValue();

                StoryNode parent = nodes.get(parentId);
                StoryNode child = nodes.get(childId);

                if (parent != null && child != null) {
                    if (parent.getLeftChild() == null) {
                        parent.setLeftChild(child);
                    } else {
                        parent.setRightChild(child);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public StoryNode getRoot() {
        return root;
    }

    public void setRoot(StoryNode root) {
        this.root = root;
    }

    public Map<Integer, StoryNode> getNodes() {
        return nodes;
    }

    public void setNodes(Map<Integer, StoryNode> nodes) {
        this.nodes = nodes;
    }

    public Map<Integer, Integer> getParentMap() {
        return parentMap;
    }

    public void setParentMap(Map<Integer, Integer> parentMap) {
        this.parentMap = parentMap;
    }
}
