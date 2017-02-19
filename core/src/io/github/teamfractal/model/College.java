package io.github.teamfractal.model;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class College {

    /**
     * The numeric representation of the college.
     *
     * DERWENT: 0
     * LANGWITH: 1
     * VANBURGH: 2
     * JAMES: 3
     * WENTWORTH: 4
     * HALIFAX: 5
     * ALCUIN: 6
     * GOODRICKE: 7
     * CONSTANTINE: 8
     */
    private int ID;

    /**
     * The custom name that a player can choose for the college.
     */
    private String customName;

    /**
     * The player is playing as the college.
     */
    private Player owner;

    private final static Texture[] textures = new Texture[9];
    private final static Image[] logos = new Image[9];
    private final static String[] names = {
            "Derwent", "Langwith", "Vanburgh", "James", "Wentworth",
            "Halifax", "Alcuin", "Goodricke", "Constantine"};
    private final static Color[] colours = {
            Color.BLUE, Color.CHARTREUSE, Color.TEAL, Color.CYAN, Color.MAROON,
            Color.YELLOW, Color.RED, Color.GREEN, Color.PINK
    };

    /**
     * The constructor for the College class
     * This will assign a name and a logo to the College based on the ID provided
     *
     * @param ID          The ID of the college.
     * @param Description The description of the college.
     */
    public College(int ID, String Description) {
        this.ID = ID;
    }

    /**
     * Setter for the custom name.
     *
     * @param Name The name that the custom name is to be changed to.
     */
    public void changeCustomName(String Name) {
        this.customName = Name;
    }

    /**
     * Assigns a player to the college.
     *
     * @param Player The player that has chosen the college.
     */
    public void setPlayer(Player Player) {
        this.owner = Player;
    }

    /**
     * Get the player for this college.
     * @return The assigned player.
     */
    public Player getPlayer() {
        return this.owner;
    }

    /**
     * Returns the college's assigned name
     *
     * @return String The college's name
     */
    public String getName() {
        return names[ID];
    }

    /**
     * Returns the college's associated ID
     *
     * @return int The college's associated ID
     */
    public int getID() {
        return this.ID;
    }

    /**
     * Returns an Image object with the texture of the college's logo mapped to it
     *
     * @return Image Icon representing the college
     */
    public Image getLogo() {
        if (logos[ID] == null) {
            logos[ID] = new Image(getLogoTexture());
        }

        return logos[ID];
    }

    /**
     * Returns the texture file encoding the college's logo
     *
     * @return Texture The texture encoding the college's logo
     */
    public Texture getLogoTexture() {
        if (textures[ID] == null) {
            textures[ID] = new Texture("image/" + getName() + ".png");
        }

        return textures[ID];
    }

    /**
     * Get tile border colour for current college.
     * @return  College colour.
     */
    public Color getColour () {
        return colours[ID];
    }
}