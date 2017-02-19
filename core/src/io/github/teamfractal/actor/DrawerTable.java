package io.github.teamfractal.actor;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

public class DrawerTable extends Table {
    private Drawer drawer;

    public DrawerTable() {
        super();
        init();
    }

    private void init() {
        drawer = Drawer.getInstance();
    }

    @SuppressWarnings("unused")
    public DrawerTable(Skin skin) {
        super(skin);
        init();
    }

    /**
     * Add an element to current table.
     *
     * @param actor    The element to be add to the ui.
     * @param padding  Padding of top, right, bottom, left. Ignore extra arguments.
     */
    public void addRow(Actor actor, int... padding) {
        int[] paddingList = {0, 0, 0, 0};
        System.arraycopy(padding, 0, paddingList, 0, Math.min(padding.length, paddingList.length));

        drawer.addTableRow(this, actor, paddingList[0], paddingList[3], paddingList[2], paddingList[1]);
    }
}
