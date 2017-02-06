package com.mygdx.game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

/**
 * Created by jack_holt on 06/02/17.
 */
public class Earthquake extends RandomEvent {

    private GameEngine engine;
    private ArrayList<Tile> tilesDamaged;
    private int tileDamageValue;

    public Earthquake() {
        super();
        this.tilesDamaged = chooseAffectedTiles(randomiser);
        this.tileDamageValue = randomiser.nextInt(4);
    }

    public void eventEffect() {
        
    }

    public String eventMessage() {
        return "An earthquake has damaged some of your tiles! Food production is now reduced by " +
                tileDamageValue + " on these tiles.";
    }

    public void eventAnimation() {}

    public ArrayList<Tile> chooseAffectedTiles(Random randomiser) {
        // Initialise tilesAffected ArrayList
        ArrayList<Tile> tilesAffected = new ArrayList<Tile>();
        // Shuffles list of tiles on the map
        Tile tiles[] = engine.tiles();
        // Shuffles the list of tiles so a random selection can be made by
        // simply choosing tiles starting from the front
        Collections.shuffle(Arrays.asList(tiles));
        // Randomly generates the number of tiles affected and chooses that
        // number of tiles
        int numberOfTilesAffected = randomiser.nextInt(16);

        for (int tile = 0; tile < numberOfTilesAffected; tile++) {
            tilesAffected.set(tile, tiles[tile]);
        }

        return tilesAffected;

    }


}
