package com.mygdx.game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by jack on 18/02/2017.
 */
public class Malfunction extends RandomEvent {

    private GameEngine gameEngine;
    private int playerAffected;
    private List<Tile> playerTiles;
    private ArrayList<Tile> tilesWithRoboticons = new ArrayList<Tile>();
    private Tile selectedRoboticonTile;
    private int roboticonTileNo;
    private Roboticon roboticonToMalfunction;
    private int[] startingRoboticonLevels;
    private int duration;


    public Malfunction(GameEngine gameEngine, GameScreen gameScreen, int playerToAffect) {
        super(gameScreen);
        this.gameEngine = gameEngine;
        this.playerAffected = playerToAffect;
        this.playerTiles = gameEngine.players()[this.playerAffected].getTileList();
        this.gatherRoboticonTiles();
        this.selectedRoboticonTile = this.tilesWithRoboticons.get(randomiser.nextInt(this.tilesWithRoboticons.size()));
        this.roboticonTileNo = this.selectedRoboticonTile.getID();
        this.roboticonToMalfunction = this.selectedRoboticonTile.getRoboticonStored();
        this.startingRoboticonLevels = this.roboticonToMalfunction.getLevel();
        System.out.println(Arrays.toString(this.startingRoboticonLevels));
        this.duration = 2;
    }

    public void eventEffect(boolean doOrUndo) {
        if (doOrUndo) {
            this.roboticonToMalfunction.setOreLevel(0);
            this.roboticonToMalfunction.setEnergyLevel(0);
            this.roboticonToMalfunction.setFoodLevel(0);
        }
        else {
            this.roboticonToMalfunction.setOreLevel(this.startingRoboticonLevels[0]);
            this.roboticonToMalfunction.setEnergyLevel(this.startingRoboticonLevels[1]);
            this.roboticonToMalfunction.setFoodLevel(this.startingRoboticonLevels[2]);
        }
    }

    public int getDuration() {
        return this.duration;
    }

    public void decDuration() {this.duration -= 1;}

    public String eventMessage(boolean doOrUndo) {
        String messageToReturn;
        if (doOrUndo) {
            messageToReturn = "Player " + (this.playerAffected + 1) + "'s roboticon on tile " +
            this.roboticonTileNo + " has malfunctioned and is now out of use for " + this.duration +
            " turns.";
        }
        else {
            messageToReturn = "Player " + (this.playerAffected + 1) + "'s roboticon on tile " +
            this.roboticonTileNo + " has now been fixed and can produce resources as normal.";
        }
        return messageToReturn;
    }

    public void eventAnimation() {}

    public void gatherRoboticonTiles() {
        for (Tile tile: this.playerTiles) {
            if (tile.getRoboticonStored() != null) {
                this.tilesWithRoboticons.add(tile);
            }
        }
    }

    public String toString() {
        return "<Malfunction: Duration = " + this.duration + ">";
    }
}
