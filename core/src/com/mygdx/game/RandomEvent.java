package com.mygdx.game;

import com.badlogic.gdx.utils.Array;

import java.util.Random;

/**
 * Created by jack_holt on 06/02/17.
 */
public abstract class RandomEvent {

    // Fields shared by all random events
    protected Random randomiser;
    protected int duration;

    public RandomEvent() {
        this.randomiser = new Random();
    }

    public abstract void eventEffect();

    public abstract void reverseEffect();

    public abstract String eventMessage();

    public abstract void eventAnimation();

    public void eventHappen() {
        eventEffect();
        eventAnimation();
        String message = eventMessage();
        /*
            Code to display message goes here...
         */
    }
}
