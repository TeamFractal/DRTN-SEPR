package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import java.util.*;


/**
 * @author Duck Related Team Name in Big Massive Letters
 * @since Assessment 2
 * @version Assessment 2
 *
 * An executable version of the game can be found at: https://jm179796.github.io/SEPR/DRTN-Assessment2.jar
 * Our website is: https://jm179796.github.io/SEPR/
 */
public class GameEngine {
    private static GameEngine _instance;
    public static GameEngine getInstance() {
        return _instance;
    }

    /**
     * Stores current game-state, enabling transitions between screens and external QOL drawing functions
     */
    private Game game;

    /**
     * The game's engine only ever runs while the main in-game interface is showing, so it was designed to manipulate
     * elements (both visual and logical in nature) on that screen
     * It therefore requires access to the public methods in the GameScreen class, so instantiation in this class
     * is a necessity
     */
    private GameScreen gameScreen;

    /**
     * Stores data pertaining to the game's active players
     * For more information, check the "Player" class
     */
    private static Player[] players;

    /**
     * Holds the numeric getID of the player who's currently active in the game
     */
    private int currentPlayerID;

    /**
     * Holds the number of the phase that the game is currently in
     * Varies between 1 and 5
     */
    private int phase;

    /**
     * Defines whether or not a tile has been acquired in the current phase of the game
     */
    private boolean tileAcquired;

    /**
     * Timer used to dictate the pace and flow of the game
     * This has a visual interface which will be displayed in the top-left corner of the game-screen
     */
    private GameTimer timer;

    /**
     * Object providing QOL drawing functions to simplify visual construction and rendering tasks
     */
    private Drawer drawer;

    /**
     * Holds all of the data and the functions of the game's market
     * Also comes bundled with a visual interface which can be rendered on to the game's screen
     */
    private Market market;

    /**
     * Array holding the tiles to be laid over the map
     * Note that the tiles' visuals are encoded by the image declared and stored in the GameScreen class (and not here)
     */
    private Tile[] tiles;

    /**
     * Holds the data pertaining to the currently-selected tile
     */
    private Tile selectedTile;

    /**
     * Variable dictating whether the game is running or paused at any given moment
     */
    private State state;

    /**
     * An integer signifying the ID of the next roboticon to be created
     *
     */
    private Integer roboticonIDCounter = 0;

    private ArrayList<RandomEvent> randomEvents = new ArrayList<RandomEvent>();

    /**
     * Constructs the game's engine. Imports the game's state (for direct renderer access) and the data held by the
     * GameScreen which this engine directly controls; then goes on to set up player-data for the game's players,
     * tile-data for the on-screen map and the in-game market for in-play manipulation. In the cases of the latter
     * two tasks, the engine directly interacts with the GameScreen object imported to it (as a parameter of this
     * constructor) so that it can draw the visual interfaces for the game's market and tiles directly to the game's
     * primary interface.
     *
     * @param game Variable storing the game's state
     * @param gameScreen The object encoding the in-game interface which is to be controlled by this engine
     */
    public GameEngine(Game game, GameScreen gameScreen) {
        _instance = this;

        this.game = game;
        //Import current game-state to access the game's renderer

        this.gameScreen = gameScreen;
        //Bind the engine to the main in-game interface
        //Required to alter the visuals and logic of the interface directly through this engine

        drawer = new Drawer(this.game);
        //Import QOL drawing function

        players = new Player[2];
        currentPlayerID = 0;
        //Set up objects to hold player-data
        //Start the game such that player 1 makes the first move

        phase = 1;
        //Start the game in the first phase (of 5, which recur until all tiles are claimed)

        timer = new GameTimer(0, new TTFont(Gdx.files.internal("font/testfontbignoodle.ttf"), 120), Color.WHITE, new Runnable() {
            @Override
            public void run() {
                nextPhase(); // Timeout event
            }
        });
        //Set up game timer
        //Game timer automatically ends the current turn when it reaches 0

        tiles = new Tile[16];
        //Initialise data for all 16 tiles on the screen
        //This instantiation does NOT automatically place the tiles on the game's main interface

        for (int i = 0; i < 16; i++) {
            final int fi = i;
            final GameScreen gs = gameScreen;

            tiles[i] = new Tile(this.game, i + 1, 5, 5, 5, false, new Runnable() {
                @Override
                public void run() {
                    gs.selectTile(tiles[fi], true);
                    selectedTile = tiles[fi];
                }
            });
        }
        //Configure all 16 tiles with independent yields and landmark data
        //Also assign listeners to them so that they can detect mouse clicks

        market = new Market(game, this);
        //Instantiates the game's market and hands it direct renderer access

        state = State.RUN;
        //Mark the game's current play-state as "running" (IE: not paused)

        Player goodrickePlayer = new Player(1);
        Player derwentPlayer = new AiPlayer(2);
        players[0] = goodrickePlayer;
        players[1] = derwentPlayer;
        College Goodricke = new College(1, "Goodricke");
        College Derwent = new College(2, "Derwent");
        goodrickePlayer.assignCollege(Goodricke);
        derwentPlayer.assignCollege(Derwent);
        Goodricke.assignPlayer(goodrickePlayer);
        Derwent.assignPlayer(derwentPlayer);
        //Temporary assignment of player-data for testing purposes
    }

    public void selectTile(Tile tile) {
        selectedTile = tile;
    }

    /**
     * Advances the game's progress upon call
     * Acts as a state machine of sorts, moving the game from one phase to another depending on what phase it is
     * currently at when this method if called. If player 1 is the current player in any particular phase, then the
     * phase number remains and control is handed off to the other player: otherwise, control returns to player 1 and
     * the game advances to the next state, implementing any state-specific features as it goes.
     *
     * PHASE 1: Acquisition of Tiles
     * PHASE 2: Acquisition of Roboticons
     * PHASE 3: Placement of Roboticons
     * PHASE 4: Production of Resources by Roboticons
     * PHASE 5: Market Trading
     */
    public void nextPhase() {
        timer.stop();
        nextPlayer();
        System.out.println("Player " + currentPlayerID + " | Phase " + phase);

        market.refreshButtonAvailability();
        switch (phase) {
            case 1:
                tileAcquired = false;
                drawer.toggleButton(gameScreen.endTurnButton(), false, Color.GRAY);
                break;

            case 2:
                timer.setTime(0, 30);
                timer.start();

                drawer.toggleButton(gameScreen.endTurnButton(), true, Color.WHITE);
                break;

            case 3:
                break;

            case 4:
                timer.setTime(0, 5);
                timer.start();
                produceResource();
                break;

            case 5:
                break;
        }

        if(checkGameEnd()){
            gameScreen.showPlayerWin(getWinner());
        }
        //Temporary code for determining the game's winner once all tiles have been acquired
        //Each player should own 8 tiles when this block is executed

        gameScreen.updatePhaseLabel();

        gameScreen.closeUpgradeOverlay();
        //If the upgrade overlay is open, close it when the next phase begins

        if (isCurrentlyAiPlayer()) {
            AiPlayer aiPlayer = (AiPlayer)currentPlayer();
            aiPlayer.performPhase(this, gameScreen);
        }
    }

    public void checkEventDurations() {
        for (int event = 0; event < this.randomEvents.size(); event++) {
            if (randomEvents.get(event).getDuration() == 0) {
                this.randomEvents.get(event).eventHappen(false);
                this.randomEvents.remove(event);
            }
        }
    }

    public void selectRandomEvent() {
        Random random = new Random();
        int eventValue = random.nextInt(2);
        switch(eventValue) {
            case 0:
                randomEvents.add(new Earthquake(this));
                randomEvents.get(randomEvents.size() - 1).eventHappen(true);
                break;
        }
    }

    private void produceResource() {
        Player player = currentPlayer();
        for (Tile tile : player.getTileList()) {
            tile.produce();
        }
    }

    /**
     * Sets the current player to be that which isn't active whenever this is called
     * Updates the in-game interface to reflect the statistics and the identity of the player now controlling it
     */
    private void nextPlayer() {
        currentPlayerID ++;
        if (currentPlayerID >= players.length) {
            currentPlayerID = 0;

            if (phase == 4) {
                checkEventDurations();
                selectRandomEvent();
            }

            phase ++;
            if (phase == 6) {
                phase = 1;
            }
            System.out.print("Move to phase " + phase + ", ");
        }
        System.out.println("Change to player " + currentPlayerID);

        // Find and draw the icon representing the "new" player's associated college
        gameScreen.currentPlayerIcon().setDrawable(new TextureRegionDrawable(new TextureRegion(players[currentPlayerID].getCollege().getLogoTexture())));
        gameScreen.currentPlayerIcon().setSize(64, 64);

        // Display the "new" player's inventory on-screen
        gameScreen.updateInventoryLabels();

        gameScreen.updatePlayerName();
    }

    /**
     * Pauses the game and opens the pause-menu (which is just a sub-stage in the GameScreen class)
     * Specifically pauses the game's timer and marks the engine's internal play-state to [State.PAUSE]
     */
    public void pauseGame() {
        timer.stop();
        //Stop the game's timer

        gameScreen.openPauseStage();
        //Prepare the pause menu to accept user inputs

        state = State.PAUSE;
        //Mark that the game has been paused
    }

    /**
     * Resumes the game and re-opens the primary in-game inteface
     * Specifically increments the in-game timer by 1 second, restarts it and marks the engine's internal play-state
     * to [State.PAUSE]
     * Note that the timer is incremented by 1 second to circumvent a bug that causes it to lose 1 second whenever
     * it's restarted
     */
    public void resumeGame() {
        state = State.RUN;
        //Mark that the game is now running again

        gameScreen.openGameStage();
        //Show the main in-game interface again and prepare it to accept inputs

        if (timer.minutes() > 0 || timer.seconds() > 0) {
            timer.increment();
            timer.start();
        }
        //Restart the game's timer from where it left off
        //The timer needs to be incremented by 1 second before being restarted because, for a reason that I can't
        //quite identify, restarting the timer automatically takes a second off of it straight away
    }

    /**
     * Claims the last tile to have been selected on the main GameScreen for the active player
     * This grants them the ability to plant a Roboticon on it and yield resources from it for themselves
     * Specifically registers the selected tile under the object holding the active player's data, re-colours its
     * border for owner identification purposes and moves the game on to the next player/phase
     */
    public void claimTile() {
        if (phase == 1 && selectedTile.isOwned() == false) {
            players[currentPlayerID].assignTile(selectedTile);
            //Assign selected tile to current player

            selectedTile.setOwner(players[currentPlayerID]);
            //Set the owner of the currently selected tile to be the current player

            tileAcquired = true;
            //Mark that a tile has been acquired on this turn

            switch (players[currentPlayerID].getCollege().getID()) {
                case (1):
                    //DERWENT
                    selectedTile.setTileBorderColor(Color.BLUE);
                    break;
                case (2):
                    //LANGWITH
                    selectedTile.setTileBorderColor(Color.CHARTREUSE);
                    break;
                case (3):
                    //VANBURGH
                    selectedTile.setTileBorderColor(Color.TEAL);
                    break;
                case (4):
                    //JAMES
                    selectedTile.setTileBorderColor(Color.CYAN);
                    break;
                case (5):
                    //WENTWORTH
                    selectedTile.setTileBorderColor(Color.MAROON);
                    break;
                case (6):
                    //HALIFAX
                    selectedTile.setTileBorderColor(Color.YELLOW);
                    break;
                case (7):
                    //ALCUIN
                    selectedTile.setTileBorderColor(Color.RED);
                    break;
                case (8):
                    //GOODRICKE
                    selectedTile.setTileBorderColor(Color.GREEN);
                    break;
                case (9):
                    //CONSTANTINE
                    selectedTile.setTileBorderColor(Color.PINK);
                    break;
            }
            //Set the colour of the tile's new border based on the college of the player who claimed it

            nextPhase(); // at ClaimTile
            //Advance the game
        }
    }

    /**
     * Deploys a Roboticon on the last tile to have been selected
     * Draws a Roboticon from the active player's Roboticon count and assigns it to the tile in question
     */
    public void deployRoboticon() {
        if (phase == 3) {
            if (!selectedTile.hasRoboticon()) {
                if (players[currentPlayerID].getRoboticonInventory() > 0) {
                    Roboticon Roboticon = new Roboticon(roboticonIDCounter, players[currentPlayerID], selectedTile);
                    selectedTile.assignRoboticon(Roboticon);
                    roboticonIDCounter += 1;
                    players[currentPlayerID].decreaseRoboticonInventory();
                    gameScreen.updateInventoryLabels();
                }
            }
        }
    }

    /**
     * Return's the game's current play-state, which can either be [State.RUN] or [State.PAUSE]
     * This is not to be confused with the game-state (which is directly linked to the renderer)
     *
     * @return State The game's current play-state
     */
    public State state() {
        return state;
    }

    /**
     * Return's the game's phase as a number between (or possibly one of) 1 and 5
     *
     * @return Integer The game's current phase
     */
    public int phase() {
        return phase;
    }

    /**
     * Returns all of the data pertaining to the array of players managed by the game's engine
     * Unless the game's architecture changes radically, this should only ever return two Player objects
     *
     * @return Player[] An array of all Player objects (encapsulating player-data) managed by the engine
     */
    public Player[] players() { return players; }

    /**
     * Returns the data pertaining to the player who is active at the time when this is called
     *
     * @return Player The current user's Player object, encoding all of their data
     */
    public Player currentPlayer() { return players[currentPlayerID]; }

    /**
     * Returns the ID of the player who is active at the time when this is called
     *
     * @return Integer The current player's ID
     */
    public int currentPlayerID() {
        return currentPlayerID;
    }

    /**
     * Returns the GameTimer declared and managed by the engine
     *
     * @return GameTimer The game's internal timer
     */
    public GameTimer timer() {
        return timer;
    }

    /**
     * Collectively returns every Tile managed by the engine in array
     *
     * @return Tile[] An array of all Tile objects (encapsulating tile-data) managed by the engine
     */
    public Tile[] tiles() {
        return tiles;
    }

    /**
     * Returns the data pertaining to the last Tile that was selected by a player
     *
     * @return Tile The last Tile to have been selected
     */
    public Tile selectedTile() {
        return selectedTile;
    }

    /**
     * Returns all of the data pertaining to the game's market, which is declared and managed by the engine
     *
     * @return Market The game's market
     */
    public Market market() {
        return market;
    }

    /**
     * Returns a value that's true if all tiles have been claimed, and false otherwise
     *
     * @return Boolean Determines if the game has ended or not
     */
    private boolean checkGameEnd(){
        for(Tile tile : tiles){
            if (tile.getOwner() != null){
                return false;
            }
        }

        return true;
    }

    /**
     * Updates the data pertaining to the game's current player
     * This is used by the Market class to process item transactions
     *
     * @param currentPlayer The new Player object to represent the active player with
     */
    public void updateCurrentPlayer(Player currentPlayer) {
        players[currentPlayerID] = currentPlayer;

        gameScreen.updateInventoryLabels();
        //Refresh the on-screen inventory labels to reflect the new object's possessions
    }

    /**
     * Function for upgrading a particular level of the roboticon stored on the last tile to have been selected
     * @param resource The type of resource which the roboticon will gather more of {0: ore | 1: energy | 2: food}
     */
    public void upgradeRoboticon(int resource) {
        if (selectedTile().getRoboticonStored().getLevel()[resource] < selectedTile().getRoboticonStored().getMaxLevel()) {
            switch (resource) {
                case (0):
                    currentPlayer().setMoney(currentPlayer().getMoney() - selectedTile.getRoboticonStored().getOreUpgradeCost());
                    break;
                case (1):
                    currentPlayer().setMoney(currentPlayer().getMoney() - selectedTile.getRoboticonStored().getEnergyUpgradeCost());
                    break;
                case (2):
                    currentPlayer().setMoney(currentPlayer().getMoney() - selectedTile.getRoboticonStored().getFoodUpgradeCost());
                    break;
            }

            selectedTile().getRoboticonStored().upgrade(resource);
        }
        //Upgrade the specified resource
        //0: ORE
        //1: ENERGY
        //2: FOOD
    }

    public int getPhase() {
        return phase;
    }

    public int getWinner(){
        List<Player> playersList = Arrays.asList(players);
        Collections.sort(playersList, new Comparator<Player>() {
            @Override
            public int compare(Player a, Player b) {
                return b.calculateScore() - a.calculateScore();
            }
        });
        return playersList.get(0).getPlayerID();
    }
    public boolean isCurrentlyAiPlayer() {
        return currentPlayer().isAi();
    }

    /**
     * Encodes possible play-states
     * These are not to be confused with the game-state (which is directly linked to the renderer)
     */
    public enum State {
        RUN,
        PAUSE
    }
}
