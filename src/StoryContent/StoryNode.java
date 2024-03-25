package StoryContent;

import java.util.Map;

public class StoryNode{
    private int id;
    private String text;
    private StoryNode leftChild;
    private StoryNode rightChild;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    private String type;

    public StoryNode(int id, String text, String type){
        this.id = id;
        this.type = type;
        this.text = text;
        this.leftChild = null;
        this.rightChild = null;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public StoryNode getLeftChild() {
        return leftChild;
    }

    public void setLeftChild(StoryNode leftChild) {
        this.leftChild = leftChild;
    }

    public StoryNode getRightChild() {
        return rightChild;
    }

    public void setRightChild(StoryNode rightChild) {
        this.rightChild = rightChild;
    }
}