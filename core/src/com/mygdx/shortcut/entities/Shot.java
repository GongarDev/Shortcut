package com.mygdx.shortcut.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.TimeUtils;
import com.mygdx.shortcut.Level;
import com.mygdx.shortcut.util.Assets;
import com.mygdx.shortcut.util.Constants;
import com.mygdx.shortcut.util.Enums.*;
import com.mygdx.shortcut.util.Utils;

public class Shot {

    private final Direction direction;
    private final Level level;
    public boolean active;
    private Vector2 position;
    final long startTime;
    Rectangle shotBounds;

    public static final String TAG = Shot.class.getName();

    public Shot(Level level, Vector2 position, Direction direction) {
        this.level = level;
        this.position = position;
        this.direction = direction;
        active = true;
        startTime = TimeUtils.nanoTime();
    }

    public void update(float delta) {
        switch (direction) {
            case LEFT:
                position.x -= delta * Constants.SHOT_MOVE_SPEED;
                break;
            case RIGHT:
                position.x += delta * Constants.SHOT_MOVE_SPEED;
                break;
        }
        for (Platform platform : level.getPlatforms()) {

            shotBounds = new Rectangle(
                    position.x,
                    position.y,
                    Constants.STANCE_WIDTH,
                    Constants.STANCE_WIDTH);

            Rectangle platformBounds = new Rectangle(
                    platform.left,
                    platform.bottom,
                    platform.width,
                    platform.height);

                if (shotBounds.overlaps(platformBounds)) {
                Direction direction = null;
                if(position.x-3<platform.left+1) {
                    direction = Direction.RIGHT;
                    Gdx.app.log(TAG, "right");
                }else if(position.x+3>platform.right-1) {
                    direction = Direction.LEFT;
                    Gdx.app.log(TAG, "left");
                }

                level.spawnPortal(position, level.getPortalColor(), direction);
                active = false;
                platform.collision = true;
                level.changePortal();
            }
        }

        final float worldWidth = level.getViewport().getWorldWidth();
        final float cameraX = level.getViewport().getCamera().position.x;

        if (position.x < cameraX - worldWidth / 2 || position.x > cameraX + worldWidth / 2) {
            active = false;
        }
    }

    public void render(SpriteBatch batch) {

        boolean flipX = false;
        TextureRegion region = null;
        if(direction == Direction.RIGHT) {
            float jumpTimeSeconds = MathUtils.nanoToSec * (TimeUtils.nanoTime() - startTime);
            region = Assets.instance.shotAssets.shotAnimation.getKeyFrame(jumpTimeSeconds);
            flipX = false;
        }else if(direction == Direction.LEFT) {
            float jumpTimeSeconds = MathUtils.nanoToSec * (TimeUtils.nanoTime() - startTime);
            region = Assets.instance.shotAssets.shotAnimation.getKeyFrame(jumpTimeSeconds);
            flipX = true;
        }
        Utils.drawTextureRegion(batch, region, position, Constants.ENEMY_CENTER, flipX);
    }
}
