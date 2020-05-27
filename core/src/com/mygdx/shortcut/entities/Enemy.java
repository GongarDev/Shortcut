package com.mygdx.shortcut.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.TimeUtils;
import com.mygdx.shortcut.util.Assets;
import com.mygdx.shortcut.util.Constants;
import com.mygdx.shortcut.util.Enums.*;
import com.mygdx.shortcut.util.Utils;

public class Enemy {

    String identifier;
    Platform platform;
    Vector2 position;
    Direction direction;
    final long startTime;

    public Enemy(Platform platform, String identifier) {
        this.identifier = identifier;
        this.platform = platform;
        this.direction = Direction.RIGHT;
        this.position = new Vector2(platform.left, platform.top + Constants.ENEMY_CENTER.y);
        startTime = TimeUtils.nanoTime();
    }

    public void update(float delta) {

        switch (direction) {
            case LEFT:
                position.x -= Constants.ENEMY_MOVEMENT_SPEED * delta;
                break;
            case RIGHT:
                position.x += Constants.ENEMY_MOVEMENT_SPEED * delta;
                break;
            case STATIC:
                position.x = platform.right*platform.width;
        }

        if (position.x < platform.left) {
            position.x = platform.left;
            direction = Direction.RIGHT;
        } else if (position.x > platform.right) {
            position.x = platform.right;
            direction = Direction.LEFT;
        }
    }

    public void render(SpriteBatch batch) {

        boolean flipX = false;
        TextureRegion region = null;

        if (this.identifier == Constants.LEVEL_ENEMY_ALIEN) {
            if (direction == Direction.RIGHT) {
                float jumpTimeSeconds = MathUtils.nanoToSec * (TimeUtils.nanoTime() - startTime);
                region = Assets.instance.enemyAssets.enemyAlienAnimation.getKeyFrame(jumpTimeSeconds);
                flipX = true;
            } else if (direction == Direction.LEFT) {
                float idleTimeSeconds = MathUtils.nanoToSec * (TimeUtils.nanoTime() - startTime);
                region = Assets.instance.enemyAssets.enemyAlienAnimation.getKeyFrame(idleTimeSeconds);
                flipX = false;
            }
            Utils.drawTextureRegion(batch, region, position, Constants.ENEMY_CENTER, flipX);
        } else if (this.identifier == Constants.LEVEL_ENEMY_BIPEDAL) {
            if (direction == Direction.RIGHT) {
                float jumpTimeSeconds = MathUtils.nanoToSec * (TimeUtils.nanoTime() - startTime);
                region = Assets.instance.enemyAssets.enemyBipedalAnimation.getKeyFrame(jumpTimeSeconds);
                flipX = false;
            } else if (direction == Direction.LEFT) {
                float idleTimeSeconds = MathUtils.nanoToSec * (TimeUtils.nanoTime() - startTime);
                region = Assets.instance.enemyAssets.enemyBipedalAnimation.getKeyFrame(idleTimeSeconds);
                flipX = true;
            }
            Utils.drawTextureRegion(batch, region, position, Constants.ENEMY_CENTER, flipX);
        } else if (this.identifier == Constants.LEVEL_ENEMY_TURRET) {
            this.direction=Direction.STATIC;
            float jumpTimeSeconds = MathUtils.nanoToSec * (TimeUtils.nanoTime() - startTime);
            region = Assets.instance.enemyAssets.enemyTurretAnimation.getKeyFrame(jumpTimeSeconds);
            Utils.drawTextureRegion(batch, region, position, Constants.ENEMY_CENTER, flipX);
        }else if (this.identifier == Constants.LEVEL_ENEMY_DRONE) {
            if (direction == Direction.RIGHT) {
                float jumpTimeSeconds = MathUtils.nanoToSec * (TimeUtils.nanoTime() - startTime);
                region = Assets.instance.enemyAssets.enemyDroneAnimation.getKeyFrame(jumpTimeSeconds);
                flipX = false;
            } else if (direction == Direction.LEFT) {
                float idleTimeSeconds = MathUtils.nanoToSec * (TimeUtils.nanoTime() - startTime);
                region = Assets.instance.enemyAssets.enemyDroneAnimation.getKeyFrame(idleTimeSeconds);
                flipX = true;
            }
            Utils.drawTextureRegion(batch, region, position, Constants.ENEMY_CENTER, flipX);
        }
    }
}
