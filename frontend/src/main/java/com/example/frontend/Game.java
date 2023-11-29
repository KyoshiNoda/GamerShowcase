package com.example.frontend;

import java.util.ArrayList;

public class Game {
    private String name;
    private ArrayList<String> platforms;
    private String released;
    private String rating;
    private int id;
    private String esrb;
    private String background_image;
    private String description;


    public Game(String name, ArrayList<String> platforms, String released, String rating, int id, String esrb, String background_image) {
        this.name = name;
        this.platforms = platforms;
        this.released = released;
        this.rating = rating;
        this.id = id;
        this.esrb = esrb;
        this.background_image = background_image;
        this.description = null;
    }

    public Game() {
        this.name = null;
        this.platforms = null;
        this.released = null;
        this.rating = null;
        this.id = 0;
        this.esrb = null;
        this.background_image = null;
        this.description = null;
    }

    public Game(String name, ArrayList<String> platforms, String released, double rating, int id, String esrb, String backgroundImage) {
    }

    public String getName() {
        return name;
    }

    public ArrayList<String> getPlatforms() {
        return platforms;
    }

    public String getReleased() {
        return released;
    }

    public String getRating() {
        return rating;
    }

    public int getId() {
        return id;
    }

    public String getEsrb() {
        return esrb;
    }

    public String getBackground_image() {
        return background_image;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPlatforms(ArrayList<String> platforms) {
        this.platforms = platforms;
    }

    public void setReleased(String released) {
        this.released = released;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setEsrb(String esrb) {
        this.esrb = esrb;
    }

    public void setBackground_image(String background_image) {
        this.background_image = background_image;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void print() {
        System.out.println("Game ID: " + id);
        System.out.println("Game Name: " + name);
        System.out.println("Game Image: " + background_image);
        System.out.println("Game ESRB: " +  esrb);
        System.out.println("Game Rating: " +  rating);
        System.out.println("Game Released: " +  released);
        System.out.println("Game Platforms:");
        for (String platform : platforms) {
            System.out.println("\t" + platform);
        }
    }

}
