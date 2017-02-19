package com.mygdx.game;

import java.util.Random;

/**
 * Created by jack_holt on 06/02/17.
 */
public abstract class RandomEvent {

    // Fields shared by all random events
    Random randomiser;
    protected GameScreen gameScreen;
    protected int eventCooldown;

    RandomEvent(GameScreen gameScreen) {
        this.randomiser = new Random();
        this.gameScreen = gameScreen;
        this.eventCooldown = 0;
    }

    public abstract void eventEffect(boolean doOrUndo);

    public abstract int getDuration();

    public abstract void decDuration();

    public abstract String eventMessage(boolean doOrUndo);

    public abstract void eventAnimation();

    public int getEventCooldown() {
        return this.eventCooldown;
    }

    public void eventHappen(boolean doOrUndo) {
        this.eventEffect(doOrUndo);
        this.eventAnimation();
        String message = this.eventMessage(doOrUndo);
        gameScreen.showEventMessage(message);

        if (!doOrUndo) {this.eventCooldown = -2;}

        System.out.println(message);

    }

    public int getNumberGreaterThanX(int limit, int x) {
        int numberGenerated = randomiser.nextInt(limit);
        if (numberGenerated == 0 || numberGenerated == 1) {
            numberGenerated += x;
        }
        return numberGenerated;
    }
}
