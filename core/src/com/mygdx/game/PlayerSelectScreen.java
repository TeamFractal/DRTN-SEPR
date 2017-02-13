package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

public class PlayerSelectScreen implements Screen {
	
	/**
     * Stores current game-state, enabling transitions between screens and external QOL drawing functions
     */
    private Game game;

    /**
     * On-screen stage which can be populated with actors
     */
    private Stage stage;

    /**
     * Provides the spatial framework for menu buttons and labels to be organised over
     */
    private Table table;

    /**
     * Array of all menu buttons
     */

    /**
     * Establishes the font which is used to encode the menu's options
     */
    private TTFont menuFont;

    /**
     * Establishes the font which is used to encode the game's title
     */
    private TTFont titleFont;

    /**
     * Establishes the font which, at the moment, encodes a "Title TBC" message
     */
    private TTFont tempFont;

    /**
     * Object defining QOL drawing functions for rectangles and on-screen tables
     * Used in this class accelerate table row creation
     */
    private Drawer drawer;

    /**
     * Batch that manages the rendering pipeline for all of the images to be displayed on the screen
     */
    private SpriteBatch batch;

    /**
     * The object which will encode the menu's background
     */
    private Sprite background;

	private TextButton addPlayerButton;

	private TextButton addAIPlayerButton;

	private TextButton removePlayerButton;

	private TextButton removeAIPlayerButton;

	private Label playerLabel;

	private Label AIPlayerLabel;

    /**
     * The menu-screen's initial constructor
     *
     * @param game Variable storing the game's state for rendering purposes
     */
    public PlayerSelectScreen(Game game) {
        this.game = game;
    }
    //Import current game-state


	@Override
	public void show() {
		drawer = new Drawer(game);
		
        batch = new SpriteBatch();
        //Initialise sprite-batch

        stage = new Stage();
        table = new Table();
        //Initialise stage and button-table
        
        

        titleFont = new TTFont(Gdx.files.internal("font/earthorbiterxtrabold.ttf"), 120, 2, Color.BLACK, false);
        menuFont = new TTFont(Gdx.files.internal("font/enterthegrid.ttf"), 36, 2, Color.BLACK, false);
        tempFont = new TTFont(Gdx.files.internal("font/earthorbiter.ttf"), 24, 2, Color.BLACK, false);
        //Initialise menu font

        Gdx.input.setInputProcessor(stage);
        //Set the stage up to accept user inputs

        background = new Sprite(new Texture("image/MenuBG.png"));
        background.setSize(background.getWidth(), background.getHeight());
        background.setCenter(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2);
        //Create logo sprite and re-size/re-position it to fit into game window

        table.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        //Fill the screen with the table
        //This is bound to change in the future for obvious reasons

        TextButton.TextButtonStyle menuButtonStyle = new TextButton.TextButtonStyle();
        menuButtonStyle.font = menuFont.font();
        menuButtonStyle.fontColor = Color.WHITE;
        menuButtonStyle.pressedOffsetX = 1;
        menuButtonStyle.pressedOffsetY = -1;
        //Set up the format for the buttons on the menu
        //STILL NEED TO SORT OUT BUTTON ANIMATIONS
        
        playerLabel = new Label("0", new Label.LabelStyle(menuFont.font(), Color.WHITE));
        AIPlayerLabel = new Label("0", new Label.LabelStyle(menuFont.font(), Color.WHITE));
        addPlayerButton = new TextButton(" more", menuButtonStyle);
        addPlayerButton.setVisible(true);
        addAIPlayerButton = new TextButton(" more", menuButtonStyle);
        removePlayerButton = new TextButton("less ", menuButtonStyle);
        removeAIPlayerButton = new TextButton("less ", menuButtonStyle);
        
        drawer.addTableRow(table, removePlayerButton);
        table.add(playerLabel);
        table.add(addPlayerButton);
        drawer.addTableRow(table, removeAIPlayerButton);
        table.add(AIPlayerLabel);
        table.add(addAIPlayerButton);
        stage.addActor(table);

	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        //OpenGL nonsense
        //First instruction sets background colour

        batch.begin();
        background.draw(batch);
        batch.end();
        //Run through the rendering pipeline to draw the menu's background image to the screen

        stage.act(delta);
        stage.draw();
        //Draw the stage onto the screen

	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub

	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		menuFont.dispose();

        stage.dispose();

	}

}
