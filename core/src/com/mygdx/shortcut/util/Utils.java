package com.mygdx.shortcut.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.TimeUtils;

public class Utils {

    public static final String TAG = Utils.class.toString();

    public static void drawTextureRegion(SpriteBatch batch, TextureRegion region, Vector2 position, Vector2 offset, boolean flipX) {
        drawTextureRegion(batch, region, position.x - offset.x, position.y - offset.y, flipX);
    }

    public static void drawTextureRegion(SpriteBatch batch, TextureRegion region, Vector2 position, boolean flipX) {
        drawTextureRegion(batch, region, position.x, position.y, flipX);
    }

    public static void drawTextureRegion(SpriteBatch batch, TextureRegion region, Vector2 position) {
        drawTextureRegion(batch, region, position.x, position.y, false);
    }

    public static void drawTextureRegion(SpriteBatch batch, TextureRegion region, float x, float y, boolean flipX) {

        batch.draw(
                region.getTexture(),
                x,
                y,
                0,
                0,
                region.getRegionWidth(),
                region.getRegionHeight(),
                1,
                1,
                0,
                region.getRegionX(),
                region.getRegionY(),
                region.getRegionWidth(),
                region.getRegionHeight(),
                flipX,
                false);
    }

    public static void drawTextureRegion(SpriteBatch batch, Texture texture, float x, float y) {

        batch.draw(
                texture,
                x,
                y,
                0,
                0,
                texture.getWidth(),
                texture.getHeight(),
                1,
                1,
                0,
                (int)x,
                (int)y,
                texture.getWidth(),
                texture.getHeight(),
                false,
                false);
    }

    public static void drawTextureBackground(SpriteBatch batch, Texture texture,
                                             float x, float y, float width, float height,
                                             float scaleX, float scaleY,
                                             int sourceX, int sourceY,
                                             int srcWidth, int srcHeight) {

        //Gdx.app.log(TAG, "width " + width);
        batch.draw(
                texture,
                x,
                y,
                0,
                0,
                width,
                height,
                scaleX,
                scaleY,
                0,
                sourceX,
                sourceY,
                srcWidth,
                srcHeight,
                false,
                false);
    }

    public static float secondsSince(long timeNanos) {
        return MathUtils.nanoToSec * (TimeUtils.nanoTime() - timeNanos);
    }
}