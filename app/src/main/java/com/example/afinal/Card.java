package com.example.afinal;

public class Card
{
    private int number; // The number on the card
    private String shape; // The shape of the card (e.g., heart, spade, etc.)
    private String id; // Unique identifier for the card
    private int imageResource; // Resource ID for the card's image
    private boolean isVisible; // Whether the card is visible on the board
    private int index;
    private boolean turn_a;

    public Card() {}


    // Constructor
    public Card(int number, String shape, String id, int imageResource) {
        this.number = number;
        this.shape = shape;
        this.id = id;
        this.imageResource = imageResource;
        this.isVisible = true; // ברירת מחדל: הקלף מוצג
        this.index =-1;
        this.turn_a = true;
    }

    // Getters and Setters

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getShape() {
        return shape;
    }

    public void setShape(String shape) {
        this.shape = shape;
    }

    public String getId() {
        return id;
    }

    public boolean isVisible() {
        return isVisible;
    }

    public void setVisible(boolean visible) {
        isVisible = visible;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getImageResource() {
        return imageResource;
    }

    public void setImageResource(int imageResource) {
        this.imageResource = imageResource;
    }

    public int getIndex() {
        return index;
    }

    public void SetIndex(int index) {
        this.index =index;
    }

    public boolean turn_a() {
        return turn_a;
    }

    public void SetTurn_a(boolean turn_a) {
        this.turn_a = turn_a;
    }


}
