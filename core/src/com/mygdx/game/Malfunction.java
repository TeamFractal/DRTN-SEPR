package com.mygdx.game;

/**
 * Created by jack on 18/02/2017.
 */
public class Malfunction extends RandomEvent {

    private GameEngine gameEngine;
    private int playerAffected = 1;
    private int duration = 2;
    private int roboticonTile = 5;


    public Malfunction(GameEngine gameEngine, GameScreen gameScreen) {
        super(gameScreen);
        this.gameEngine = gameEngine;
    }

    public void eventEffect(boolean doOrUndo) {}

    public int getDuration() {
        return this.duration;
    }

    public void decDuration() {this.duration -= 1;}

    public String eventMessage(boolean doOrUndo) {
        String messageToReturn;
        if (doOrUndo) {
            messageToReturn = "Player " + this.playerAffected + "'s roboticon on tile " +
            this.roboticonTile + " has malfunctioned and is now out of use for " + this.duration +
            " turns.";
        }
        else {
            messageToReturn = "Player " + this.playerAffected + "'s roboticon on tile " +
            this.roboticonTile + " has now been fixed and can produce resources as normal.";
        }
        return messageToReturn;
    }

    public void eventAnimation() {}

}
