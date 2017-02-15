package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import io.github.teamfractal.screens.AbstractAnimationScreen;

import java.util.Random;

import static com.badlogic.gdx.math.MathUtils.random;

/**
 * Created by chankaichi on 13/02/2017.
 */

public class MiniGameScreen extends AbstractAnimationScreen implements Screen {
    enum GameActions {money, robotcoin, lose_money }

    private ImageButton button1;
    private ImageButton button2;
    private ImageButton button3;
    private Table table;
    private int width;
    private int high;
    private Stage stage;
    Random rnd = new Random();


    @Override
    public void show() {

        stage = new Stage();
        this.table = new Table();
        table.setFillParent(true);

        this.width = width;
        this.high = high;

        button1 = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture("minigame/card.png"))));
        button2 = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture("minigame/card.png"))));
        button3 = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture("minigame/card.png"))));

        table.add(button1).padRight(10);

        table.add(button2).padRight(10);
        table.add(button3);
        stage.addActor(table);

        GameActions[] allActions = GameActions.values();
        int index = rnd.nextInt(allActions.length);
        final GameActions choose_gift = allActions[index];

        ChangeListener event = new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Player player = GameEngine.getInstance().currentPlayer();
                switch (choose_gift) {

                    case money:
                        player.setMoney(player.getMoney() + 100);
                        break;
                    case robotcoin:
                        player.increaseRoboticonInventory();
                        break;
                    case lose_money:
                        break;
                }

            }
        };
        button1.addListener(event);

        button2.addListener(event);

        button3.addListener(event);

        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }

    @Override
    protected Batch getBatch() {
        return null;
    }

    @Override
    public Size getScreenSize() {
        return null;
    }
}