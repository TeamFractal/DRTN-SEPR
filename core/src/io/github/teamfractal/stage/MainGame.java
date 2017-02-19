package io.github.teamfractal.stage;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import io.github.teamfractal.GameEngine;
import io.github.teamfractal.actor.DrawerTable;
import io.github.teamfractal.screens.GameScreen;
import io.github.teamfractal.util.TTFont;
import sun.font.TextLabel;

public class MainGame extends Table {
    private final GameScreen gameScreen;
    private final DrawerTable leftTable;
    private final DrawerTable midTable;
    private final DrawerTable rightTable;
    private final Stage stage;
    private final TTFont font;
    private final GameEngine engine;

    public MainGame(GameScreen gameScreen) {
        this.font = GameScreen.getFont();
        this.stage = gameScreen.getStage();
        this.engine = GameEngine.getInstance();
        this.gameScreen = gameScreen;

        leftTable = new DrawerTable();
        midTable = new DrawerTable();
        rightTable = new DrawerTable();

        add(leftTable);
        add(midTable);
        add(rightTable);

        buildAll();

        setFillParent(true);
        setHeight(Gdx.graphics.getHeight());
    }

    private void buildAll() {
        buildLeftTable();
        buildMidTable();
        buildRightTable();
    }

    private void buildLeftTable() {
        leftTable.setWidth(256);
        leftTable.left().top();

        // Timer
        leftTable.addRow(engine.timer());

        // Phase text + Next Phase button
        font.setSize(22);
        Label.LabelStyle style = new Label.LabelStyle();
        style.font = font.font();
        style.fontColor = Color.WHITE;

        leftTable.addRow(new Label("test", style));
        leftTable.add("");
    }

    private void buildMidTable() {
        midTable.setFillParent(true);
    }

    private void buildRightTable() {
        rightTable.setWidth(256);
    }
}
