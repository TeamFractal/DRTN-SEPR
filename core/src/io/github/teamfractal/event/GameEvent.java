package io.github.teamfractal.event;

import io.github.teamfractal.screens.GameScreen;

import java.util.Random;

public abstract class GameEvent {

    // Fields shared by all random events
    Random randomiser;
    protected GameScreen gameScreen;
    private int eventCooldown;

    GameEvent(GameScreen gameScreen) {
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
        if (numberGenerated == 0) {
            numberGenerated += 1;
        }
        return numberGenerated;
    }
}
