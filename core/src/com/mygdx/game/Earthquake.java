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
        this.tileDamageValue = randomiser.nextInt(5);
    }

    public void eventEffect() {
        // Divides production on each tile by damage value
        for (int tile = 0; tile < this.tilesDamaged.size(); tile++) {
            int tileOreCount = this.tilesDamaged.get(tile).getOreCount();
            int tileEnergyCount = this.tilesDamaged.get(tile).getEnergyCount();

            this.tilesDamaged.get(tile).changeOreCount(tileOreCount / this.tileDamageValue);
            this.tilesDamaged.get(tile).changeEnergyCount(tileEnergyCount / this.tileDamageValue);
        }
    }

    // Message that appears when
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
