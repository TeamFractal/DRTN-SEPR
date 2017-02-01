package io.github.teamfractal.animation;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import io.github.teamfractal.screens.AbstractAnimationScreen;

public class AnimationTileFlash implements IAnimation {
    private static ShapeRenderer rect = new ShapeRenderer();
    private final float height;
    private final float x;
    private final float y;
    private final float width;
    private float time;
    private final static float timeout = 0.5f;
    private IAnimationFinish callback;

    public AnimationTileFlash(float x, float y, float width, float height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
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
        time += delta;

        synchronized (rect) {
            rect.begin(ShapeRenderer.ShapeType.Filled);
            updateRectOpacity();
            rect.rect(x, y, width, height);
            rect.end();
        }

        return time >= timeout;
    }

    /**
     * Updates rectangle opacity.
     */
    private void updateRectOpacity() {
        rect.setColor(1, 1, 1,  calculateOpacity());
    }

    /**
     * Calculate opacity for rectangle.
     * @return   Calculated opacity for current time.
     */
    private float calculateOpacity() {
        float v = time/timeout;
        v = 1 - v * v;
        v *= 0.8;
        return v;
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
        time = timeout;
    }
}
