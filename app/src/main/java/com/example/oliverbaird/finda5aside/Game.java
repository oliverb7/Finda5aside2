package com.example.oliverbaird.finda5aside;

/**
 * Created by oliverbaird on 25/01/2018.
 */

public class Game {

    private String username;
    private String time;
    private int iconID;
    private String description;


    public Game(String username, String time, int iconID, String description)
    {
        super();
        this.username = username;
        this.time = time;
        this.iconID = iconID;
        this.description = description;
    }

    public String username() {
        return username;
    }
    public String time() {
        return time;
    }
    public int getIconID(){
        return iconID;
    }
    public String getDescription(){
        return description;
    }
}
