package com.mygdx.game;

import java.util.Random;

/**
 * Created by jack_holt on 06/02/17.
 */
public abstract class RandomEvent {

    // Fields shared by all random events
    Random randomiser;

    RandomEvent() {
        this.randomiser = new Random();
    }

    public abstract void eventEffect(boolean doOrUndo);

    public abstract int getDuration();

    public abstract String eventMessage(boolean doOrUndo);

    public abstract void eventAnimation();

    public void eventHappen(boolean doOrUndo) {
        this.eventEffect(doOrUndo);
        this.eventAnimation();
        String message = this.eventMessage(doOrUndo);
        System.out.println(message);
        /*
            Code to display message goes here...
         */
    }

    public int getNumberGreaterThanX(int limit, int x) {
        int numberGenerated = randomiser.nextInt(limit);
        if (numberGenerated == 0) {
            numberGenerated += 1;
        }
        return numberGenerated;
    }
}
