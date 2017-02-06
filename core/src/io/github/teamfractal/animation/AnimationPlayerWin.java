package io.github.teamfractal.animation;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.mygdx.game.GameEngine;
import io.github.teamfractal.screens.AbstractAnimationScreen;

public class AnimationPlayerWin implements IAnimation {
    private IAnimationFinish callback;
    private static BitmapFont font = new BitmapFont();

    public AnimationPlayerWin() {

    }

    /**
     *
     * @param delta     Time change since last call.
     * @param screen    The screen to draw on.
     * @param batch     The Batch for drawing stuff.
     * @return          return <code>true</code> if the animation has completed.
     */
    @Override
    public boolean tick(float delta, AbstractAnimationScreen screen, Batch batch) {
        AbstractAnimationScreen.Size size = screen.getScreenSize();
        batch.begin();
        font.setColor(1,1,1, 1);
        if (GameEngine.getWinner() == true){
            font.draw(batch,  "PLAYER 1 WIN", 100, 50);
        }
        else{
            font.draw(batch,  "PLAYER 1 WIN", 100, 50);
        }
        batch.end();
        return false;
    }

    @Override
    public void setAnimationFinish(IAnimationFinish callback) {
        this.callback = callback;
    }

    @Override
    public void callAnimationFinish() {
        if (callback != null)
            callback.OnAnimationFinish();
    }

    @Override
    public void cancelAnimation() {

    }

    @Override
    public AnimationType getType() {
        return AnimationType.Tile;
    }
}
