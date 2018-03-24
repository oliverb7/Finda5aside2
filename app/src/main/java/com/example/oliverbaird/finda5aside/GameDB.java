package com.example.oliverbaird.finda5aside;

/**
 * Created by oliverbaird on 12/02/2018.
 */

public class GameDB {

    String gameID, gameCost, gameLocation, gameTime, gameSpaces,
            gameDate, gameNumber, skill, name;


    public GameDB(String gameID,String gameCost, String gameLocation, String gameTime, String gameSpaces, String gameDate, String gameNumber, String skill, String name) {
        this.gameID = gameID;
        this.gameCost = gameCost;
        this.gameLocation = gameLocation;
        this.gameTime = gameTime;
        this.gameSpaces = gameSpaces;
        this.gameDate = gameDate;
        this.gameNumber = gameNumber;
        this.skill = skill;
        this.name = name;
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

    public String getGameDate() {return gameDate;}

    public String getGameNumber() {return gameNumber;}

    public String getSkill() {return skill;}

    public String getName() {return name;}
}
