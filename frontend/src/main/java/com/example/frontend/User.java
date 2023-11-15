package com.example.frontend;

import java.util.List;

public class User {
    private String firstName;
    private String lastName;
    private String email;
    private List<String> favGames;

    public User() {
        this.firstName = null;
        this.lastName = null;
        this.email = null;
        this.favGames = null;
    }

    public User(String firstName, String lastName, String email, List<String> favGames) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.favGames = favGames;
    }
}
