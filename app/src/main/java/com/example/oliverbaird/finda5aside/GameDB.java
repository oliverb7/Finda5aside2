package com.example.oliverbaird.finda5aside;

/**
 * Created by oliverbaird on 12/02/2018.
 */

public class GameDB {

    String gameID;
    String gameCost;
    String gameLocation;
    String gameTime;
    String gameSpaces;

    public GameDB(){

    }

    public GameDB(String gameID,String gameCost, String gameLocation, String gameTime, String gameSpaces) {
        this.gameID = gameID;
        this.gameCost = gameCost;
        this.gameLocation = gameLocation;
        this.gameTime = gameTime;
        this.gameSpaces = gameSpaces;
    }

    public String getGameID() {
        return gameID;
    }

    public String getGameCost() {
        return gameCost;
    }

    public String getGameLocation() {
        return gameLocation;
    }

    public String getGameTime() {
        return gameTime;
    }

    public String getGameSpaces() {
        return gameSpaces;
    }
}
