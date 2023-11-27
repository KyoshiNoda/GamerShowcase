package com.example.frontend;

import org.mindrot.jbcrypt.BCrypt;

import java.util.ArrayList;

public class User {
    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private ArrayList<Game> favGames;

    private String password;

    public User() {
        this.firstName = null;
        this.lastName = null;
        this.email = null;
        this.password = null;
        this.favGames = null;
    }

    public User(String id, String firstName, String lastName, String email, String password, ArrayList<Game> favGames) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.favGames = favGames;
    }

    public void setId(String id) { this.id = id;}
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public void setEmail(String email) { this.email = email; }
    public void setPassword(String password) { this.password = password; }
    public void setFavGames(ArrayList<Game> favGames) { this.favGames = favGames; }

    public String getId() { return id; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }
    public ArrayList<Game> getFavGames() { return favGames; }

    public boolean gameExists(Game potentialGame) {
        for (Game game : favGames) {
            if (game.getId() == potentialGame.getId()) {
                return true;
            }
        }
        return false;
    }
}
