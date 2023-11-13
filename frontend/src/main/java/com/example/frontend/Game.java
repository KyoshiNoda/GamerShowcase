package com.example.frontend;

public class Game {
    private String name;
    private String platforms;
    private String released;
    private String rating;
    private int id;
    private String esrb;
    private String background_image;


    public Game(String name, String platforms, String released, String rating, int id, String esrb, String background_image) {
        this.name = name;
        this.platforms = platforms;
        this.released = released;
        this.rating = rating;
        this.id = id;
        this.esrb = esrb;
        this.background_image = background_image;
    }

    public Game() {
        this.name = null;
        this.platforms = null;
        this.released = null;
        this.rating = null;
        this.id = 0;
        this.esrb = null;
        this.background_image = null;
    }
    public String getName() {
        return name;
    }

    public String getPlatforms() {
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

    public void setPlatforms(String platforms) {
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


}
