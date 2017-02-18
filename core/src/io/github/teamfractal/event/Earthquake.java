package com.mygdx.game;

import java.util.*;

/**
 * Created by jack_holt on 06/02/17.
 */
public class Earthquake extends RandomEvent {

    private int playerAffected;
    private GameEngine gameEngine;
    private ArrayList<Tile> tilesDamaged;
    private int tileDamageValue;
    private int duration;

    public Earthquake(GameEngine engine, GameScreen gameScreen) {
        super(gameScreen);
        this.gameEngine = engine;
        this.tilesDamaged = chooseAffectedTiles();
        this.tileDamageValue = getNumberGreaterThanX(5, 3);
        this.duration = 2;
    }

    public int getDuration() {
        return this.duration;
    }

    public void decDuration () {
        this.duration -= 1;
    }

    public void eventEffect(boolean doOrUndo) {
        // Divides production on each tile by damage value
        for (Tile tile : this.tilesDamaged) {
            if (doOrUndo) {
                tile.changeOreCount(tile.getOreCount() / this.tileDamageValue);
                tile.changeEnergyCount(tile.getEnergyCount() / this.tileDamageValue);
                tile.changeFoodCount(tile.getFoodCount() / this.tileDamageValue);
            } else {
                tile.changeOreCount(tile.getOreCount() * this.tileDamageValue);
                tile.changeEnergyCount(tile.getEnergyCount() * this.tileDamageValue);
                tile.changeFoodCount(tile.getFoodCount() * this.tileDamageValue);
            }
        }
    }

    // Message that appears when
    public String eventMessage(boolean doOrUndo) {
        String messageToReturn;
        if (doOrUndo) {
            messageToReturn = "An earthquake has damaged Player " + this.playerAffected +  "'s tiles! " +
                    "Production is now divided by " + tileDamageValue + " on their tiles for "
                    + this.duration + " turns.";
        }
        else {
            messageToReturn = "The damage to Player " + this.playerAffected +" from the earthquake 2 turns ago " +
                    "has been repaired! The effects of this have been reversed.";
        }
        
        return messageToReturn;
    }

    public void eventAnimation() {}

    private ArrayList<Tile> chooseAffectedTiles() {

        ArrayList<Tile> tilesAffected = new ArrayList<Tile>();

        Player players[] = this.gameEngine.players();

        this.playerAffected = randomiser.nextInt(players.length);
        System.out.println("Earthquake affecting Player " + this.playerAffected);

        List<Tile> playerTiles = players[this.playerAffected].getTileList();

        Collections.shuffle(playerTiles);

        for (Tile playerTile : playerTiles) {
            tilesAffected.add(playerTile);
        }

        return tilesAffected;
    }

    public String toString() {
        return "<Earthquake: Duration = " + this.duration + ">";
    }


}
