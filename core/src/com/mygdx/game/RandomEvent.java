package com.mygdx.game;

import com.badlogic.gdx.utils.Array;

import java.util.Random;

/**
 * Created by jack_holt on 06/02/17.
 */
public abstract class RandomEvent {

    protected Random randomiser;

    public RandomEvent() {
        this.randomiser = new Random();
    }

    public abstract void eventEffect();

    public abstract String eventMessage();

    public abstract void eventAnimation();

    public void eventHappen() {
        eventAnimation();
        eventEffect();
        String message = eventMessage();
        /*
            Code to display message goes here...
         */
    }
}
