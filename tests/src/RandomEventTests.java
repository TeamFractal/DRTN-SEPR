import com.badlogic.gdx.Game;
import com.mygdx.game.*;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Created by jack_holt on 06/02/17.
 */

public class RandomEventTests extends TesterFile {

    private Game game;
    private GameScreen gameScreen;
    private GameEngine gameEngine;


    @Before
    public void setup() {
        game = new Main();
        gameEngine = new GameEngine(game, gameScreen);
        gameEngine.initialisePlayers(1, 1);
    }

    @Test
    public void testEarthquakeCutsProduction() throws Exception {
        Earthquake testEarthquake = new Earthquake(gameEngine, gameScreen);
        ArrayList<Tile> tilesDamagedBeforeQuake = testEarthquake.getTilesDamaged();
        int tileDamageValue = testEarthquake.getTileDamageValue();
        try {
            testEarthquake.eventHappen(true);
        }
        catch (Exception e) {

        }
        ArrayList<Tile> tileDamagedAfterQuake = testEarthquake.getTilesDamaged();


        for (int tile = 0; tile < tilesDamagedBeforeQuake.size(); tile++) {
            assertEquals(tilesDamagedBeforeQuake.get(tile).getOreCount() / tileDamageValue, tileDamagedAfterQuake.get(tile).getOreCount());
            assertEquals(tilesDamagedBeforeQuake.get(tile).getEnergyCount() / tileDamageValue, tileDamagedAfterQuake.get(tile).getEnergyCount());
            assertEquals(tilesDamagedBeforeQuake.get(tile).getFoodCount() / tileDamageValue, tileDamagedAfterQuake.get(tile).getFoodCount());
        }
    }
}
