package com.mygdx.shortcut.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetErrorListener;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;

public class Assets implements Disposable, AssetErrorListener {

    public static final String TAG = Assets.class.getName();
    public static final Assets instance = new Assets();

    public NovaAssets novaAssets;
    public EnemyAssets enemyAssets;
    public ShotAssets shotAssets;
    public PortalAssets portalAssets;
    public OnscreenControlsAssets onscreenControlsAssets;
    public Level1Assets level1Assets;

    private Assets() {
    }

    public void init(AssetManager assetManagerButton) {
        assetManagerButton.setErrorListener(this);
        assetManagerButton.load(Constants.ATLAS_BUTTON, TextureAtlas.class);
        assetManagerButton.finishLoading();
        TextureAtlas atlasButton = assetManagerButton.get(Constants.ATLAS_BUTTON);
        onscreenControlsAssets = new OnscreenControlsAssets(atlasButton);
    }

    public void init(AssetManager assetManagerPlayer, AssetManager assetManagerEnemy,
                     AssetManager assetManagerShot, AssetManager assetManagerPortals,
                     AssetManager assetManagerButton, AssetManager assetManagerLevel1) {

        assetManagerPlayer.setErrorListener(this);
        assetManagerPlayer.load(Constants.ATLAS_PLAYER, TextureAtlas.class);
        assetManagerPlayer.finishLoading();
        TextureAtlas atlasPlayer = assetManagerPlayer.get(Constants.ATLAS_PLAYER);
        novaAssets = new NovaAssets(atlasPlayer);

        assetManagerEnemy.setErrorListener(this);
        assetManagerEnemy.load(Constants.ATLAS_ENEMY, TextureAtlas.class);
        assetManagerEnemy.finishLoading();
        TextureAtlas atlasEnemy = assetManagerEnemy.get(Constants.ATLAS_ENEMY);
        enemyAssets = new EnemyAssets(atlasEnemy);

        assetManagerShot.setErrorListener(this);
        assetManagerShot.load(Constants.ATLAS_SHOT_EXPLOSION, TextureAtlas.class);
        assetManagerShot.finishLoading();
        TextureAtlas atlasShot = assetManagerShot.get(Constants.ATLAS_SHOT_EXPLOSION);
        shotAssets = new ShotAssets(atlasShot);

        assetManagerPortals.setErrorListener(this);
        assetManagerPortals.load(Constants.ATLAS_PORTAL, TextureAtlas.class);
        assetManagerPortals.finishLoading();
        TextureAtlas atlasPortal = assetManagerPortals.get(Constants.ATLAS_PORTAL);
        portalAssets = new PortalAssets(atlasPortal);

        assetManagerButton.setErrorListener(this);
        assetManagerButton.load(Constants.ATLAS_BUTTON, TextureAtlas.class);
        assetManagerButton.finishLoading();
        TextureAtlas atlasButton = assetManagerButton.get(Constants.ATLAS_BUTTON);
        onscreenControlsAssets = new OnscreenControlsAssets(atlasButton);

        assetManagerLevel1.setErrorListener(this);
        assetManagerLevel1.load(Constants.ATLAS_LEVEL1, TextureAtlas.class);
        assetManagerLevel1.finishLoading();
        TextureAtlas atlasPlatform = assetManagerLevel1.get(Constants.ATLAS_LEVEL1);
        level1Assets = new Level1Assets(atlasPlatform);
    }

    @Override
    public void error(AssetDescriptor asset, Throwable throwable) {
        Gdx.app.error(TAG, "No se pudo cargar el atlas: " + asset.fileName, throwable);
    }

    @Override
    public void dispose() {
    }

    public class NovaAssets {



        public final Animation idleAnimation;
        public final Animation runningAnimation;
        public final Animation jumpingAnimation;
        public final Animation runShootAnimation;

        public final AtlasRegion shoot;
        public final AtlasRegion portraitNova;

        public NovaAssets(TextureAtlas atlas) {

            Array<AtlasRegion> idleFrames = new Array<AtlasRegion>();
            idleFrames.add(atlas.findRegion(Constants.PLAYER_IDLE1));
            idleFrames.add(atlas.findRegion(Constants.PLAYER_IDLE2));
            idleFrames.add(atlas.findRegion(Constants.PLAYER_IDLE3));
            idleFrames.add(atlas.findRegion(Constants.PLAYER_IDLE4));
            idleAnimation = new Animation(Constants.IDLE_LOOP_DURATION, idleFrames, Animation.PlayMode.LOOP);


            Array<AtlasRegion> runningFrames = new Array<AtlasRegion>();
            runningFrames.add(atlas.findRegion(Constants.PLAYER_RUN1));
            runningFrames.add(atlas.findRegion(Constants.PLAYER_RUN2));
            runningFrames.add(atlas.findRegion(Constants.PLAYER_RUN3));
            runningFrames.add(atlas.findRegion(Constants.PLAYER_RUN4));
            runningFrames.add(atlas.findRegion(Constants.PLAYER_RUN5));
            runningFrames.add(atlas.findRegion(Constants.PLAYER_RUN6));
            runningFrames.add(atlas.findRegion(Constants.PLAYER_RUN7));
            runningFrames.add(atlas.findRegion(Constants.PLAYER_RUN8));
            runningAnimation = new Animation(Constants.RUN_LOOP_DURATION, runningFrames, Animation.PlayMode.LOOP);

            Array<AtlasRegion> jumpingFrames = new Array<AtlasRegion>();
            jumpingFrames.add(atlas.findRegion(Constants.PLAYER_JUMP1));
            jumpingFrames.add(atlas.findRegion(Constants.PLAYER_JUMP2));
            jumpingFrames.add(atlas.findRegion(Constants.PLAYER_JUMP3));
            jumpingFrames.add(atlas.findRegion(Constants.PLAYER_JUMP4));
            jumpingAnimation = new Animation(Constants.JUMP_LOOP_DURATION, jumpingFrames, Animation.PlayMode.NORMAL);

            Array<AtlasRegion> runShootFrames = new Array<AtlasRegion>();
            runShootFrames.add(atlas.findRegion(Constants.PLAYER_RUN_SHOOT1));
            runShootFrames.add(atlas.findRegion(Constants.PLAYER_RUN_SHOOT2));
            runShootFrames.add(atlas.findRegion(Constants.PLAYER_RUN_SHOOT3));
            runShootFrames.add(atlas.findRegion(Constants.PLAYER_RUN_SHOOT4));
            runShootFrames.add(atlas.findRegion(Constants.PLAYER_RUN_SHOOT5));
            runShootFrames.add(atlas.findRegion(Constants.PLAYER_RUN_SHOOT6));
            runShootFrames.add(atlas.findRegion(Constants.PLAYER_RUN_SHOOT7));
            runShootFrames.add(atlas.findRegion(Constants.PLAYER_RUN_SHOOT8));
            runShootAnimation = new Animation(Constants.RUN_LOOP_DURATION, runShootFrames, Animation.PlayMode.LOOP);

            shoot = atlas.findRegion(Constants.PLAYER_SHOOT);
            portraitNova = atlas.findRegion(Constants.PLAYER_PORTRAIT);
        }
    }

    public class Level1Assets {

        TextureAtlas atlas;
        public AtlasRegion region;
        public NinePatch platformNinePatch;

        public Level1Assets(TextureAtlas atlas) {
            this.atlas = atlas;
        }

        public NinePatch takeNinePatch(String path){
            region = atlas.findRegion(path);
            int edge = Constants.PLATFORM_EDGE;
            platformNinePatch = new NinePatch(region, edge, edge, edge, edge);
            return platformNinePatch;
        }

        public AtlasRegion takeRegion(String path){
            region = atlas.findRegion(path);
            return region;
        }

    }

    public class EnemyAssets {

        public final Animation enemyAlienAnimation;
        public final Animation enemyBipedalAnimation;
        public final Animation enemyTurretAnimation;
        public final Animation enemyDroneAnimation;

        public EnemyAssets(TextureAtlas atlas) {

            Array<AtlasRegion> alienFrames = new Array<AtlasRegion>();
            alienFrames.add(atlas.findRegion(Constants.ENEMY_ALIEN1));
            alienFrames.add(atlas.findRegion(Constants.ENEMY_ALIEN2));
            alienFrames.add(atlas.findRegion(Constants.ENEMY_ALIEN3));
            alienFrames.add(atlas.findRegion(Constants.ENEMY_ALIEN4));
            alienFrames.add(atlas.findRegion(Constants.ENEMY_ALIEN5));
            alienFrames.add(atlas.findRegion(Constants.ENEMY_ALIEN6));
            alienFrames.add(atlas.findRegion(Constants.ENEMY_ALIEN7));
            alienFrames.add(atlas.findRegion(Constants.ENEMY_ALIEN8));
            enemyAlienAnimation = new Animation(Constants.IDLE_LOOP_DURATION, alienFrames, Animation.PlayMode.LOOP);

            Array<AtlasRegion> bipedalFrames = new Array<AtlasRegion>();
            bipedalFrames.add(atlas.findRegion(Constants.ENEMY_BIPEDAL1));
            bipedalFrames.add(atlas.findRegion(Constants.ENEMY_BIPEDAL2));
            bipedalFrames.add(atlas.findRegion(Constants.ENEMY_BIPEDAL3));
            bipedalFrames.add(atlas.findRegion(Constants.ENEMY_BIPEDAL4));
            bipedalFrames.add(atlas.findRegion(Constants.ENEMY_BIPEDAL5));
            bipedalFrames.add(atlas.findRegion(Constants.ENEMY_BIPEDAL6));
            bipedalFrames.add(atlas.findRegion(Constants.ENEMY_BIPEDAL7));

            enemyBipedalAnimation = new Animation(Constants.IDLE_LOOP_DURATION, bipedalFrames, Animation.PlayMode.LOOP);

            Array<AtlasRegion> turretFrames = new Array<AtlasRegion>();
            turretFrames.add(atlas.findRegion(Constants.ENEMY_TURRET1));
            turretFrames.add(atlas.findRegion(Constants.ENEMY_TURRET2));
            turretFrames.add(atlas.findRegion(Constants.ENEMY_TURRET3));
            turretFrames.add(atlas.findRegion(Constants.ENEMY_TURRET4));
            turretFrames.add(atlas.findRegion(Constants.ENEMY_TURRET5));
            turretFrames.add(atlas.findRegion(Constants.ENEMY_TURRET6));
            enemyTurretAnimation = new Animation(Constants.IDLE_LOOP_DURATION, turretFrames, Animation.PlayMode.LOOP);

            Array<AtlasRegion> droneFrames = new Array<AtlasRegion>();
            droneFrames.add(atlas.findRegion(Constants.ENEMY_DRONE1));
            droneFrames.add(atlas.findRegion(Constants.ENEMY_DRONE2));
            droneFrames.add(atlas.findRegion(Constants.ENEMY_DRONE3));
            droneFrames.add(atlas.findRegion(Constants.ENEMY_DRONE4));
            enemyDroneAnimation = new Animation(Constants.IDLE_LOOP_DURATION, droneFrames, Animation.PlayMode.LOOP);
        }
    }

    public class ShotAssets {

        public final Animation shotAnimation;

        public ShotAssets(TextureAtlas atlas) {
            Array<AtlasRegion> shotFrames = new Array<AtlasRegion>();
            shotFrames.add(atlas.findRegion(Constants.SHOT1));
            shotFrames.add(atlas.findRegion(Constants.SHOT2));
            shotFrames.add(atlas.findRegion(Constants.SHOT3));
            shotAnimation = new Animation(Constants.IDLE_LOOP_DURATION, shotFrames, Animation.PlayMode.LOOP);
        }
    }

    public class PortalAssets {

        public final Animation portalGreenOpenAnimation;
        public final Animation portalGreenHoldAnimation;
        public final Animation portalGreenClosedAnimation;
        public final Animation portalPurpleOpenAnimation;
        public final Animation portalPurpleHoldAnimation;
        public final Animation portalPurpleClosedAnimation;

        public PortalAssets(TextureAtlas atlas) {
            Array<AtlasRegion> portalGreenOpenFrames = new Array<AtlasRegion>();
            portalGreenOpenFrames.add(atlas.findRegion(Constants.PORTAL_OPEN_GREEN1));
            portalGreenOpenFrames.add(atlas.findRegion(Constants.PORTAL_OPEN_GREEN2));
            portalGreenOpenFrames.add(atlas.findRegion(Constants.PORTAL_OPEN_GREEN3));
            portalGreenOpenFrames.add(atlas.findRegion(Constants.PORTAL_OPEN_GREEN4));
            portalGreenOpenFrames.add(atlas.findRegion(Constants.PORTAL_OPEN_GREEN5));
            portalGreenOpenFrames.add(atlas.findRegion(Constants.PORTAL_OPEN_GREEN6));
            portalGreenOpenFrames.add(atlas.findRegion(Constants.PORTAL_OPEN_GREEN7));
            portalGreenOpenFrames.add(atlas.findRegion(Constants.PORTAL_OPEN_GREEN8));
            portalGreenOpenAnimation = new Animation(Constants.PORTAL_LOOP_DURATION, portalGreenOpenFrames, Animation.PlayMode.LOOP);

            Array<AtlasRegion> portalGreenHoldFrames = new Array<AtlasRegion>();
            portalGreenHoldFrames.add(atlas.findRegion(Constants.PORTAL_HOLD_GREEN1));
            portalGreenHoldFrames.add(atlas.findRegion(Constants.PORTAL_HOLD_GREEN2));
            portalGreenHoldFrames.add(atlas.findRegion(Constants.PORTAL_HOLD_GREEN3));
            portalGreenHoldFrames.add(atlas.findRegion(Constants.PORTAL_HOLD_GREEN4));
            portalGreenHoldFrames.add(atlas.findRegion(Constants.PORTAL_HOLD_GREEN5));
            portalGreenHoldFrames.add(atlas.findRegion(Constants.PORTAL_HOLD_GREEN6));
            portalGreenHoldFrames.add(atlas.findRegion(Constants.PORTAL_HOLD_GREEN7));
            portalGreenHoldFrames.add(atlas.findRegion(Constants.PORTAL_HOLD_GREEN8));
            portalGreenHoldAnimation = new Animation(Constants.PORTAL_LOOP_DURATION, portalGreenHoldFrames, Animation.PlayMode.LOOP);

            portalGreenClosedAnimation = new Animation(Constants.PORTAL_LOOP_DURATION, portalGreenOpenFrames, Animation.PlayMode.REVERSED);

            Array<AtlasRegion> portalPurpleOpenFrames = new Array<AtlasRegion>();
            portalPurpleOpenFrames.add(atlas.findRegion(Constants.PORTAL_OPEN_PURPLE1));
            portalPurpleOpenFrames.add(atlas.findRegion(Constants.PORTAL_OPEN_PURPLE2));
            portalPurpleOpenFrames.add(atlas.findRegion(Constants.PORTAL_OPEN_PURPLE3));
            portalPurpleOpenFrames.add(atlas.findRegion(Constants.PORTAL_OPEN_PURPLE4));
            portalPurpleOpenFrames.add(atlas.findRegion(Constants.PORTAL_OPEN_PURPLE5));
            portalPurpleOpenFrames.add(atlas.findRegion(Constants.PORTAL_OPEN_PURPLE6));
            portalPurpleOpenFrames.add(atlas.findRegion(Constants.PORTAL_OPEN_PURPLE7));
            portalPurpleOpenFrames.add(atlas.findRegion(Constants.PORTAL_OPEN_PURPLE8));
            portalPurpleOpenAnimation = new Animation(Constants.PORTAL_LOOP_DURATION, portalPurpleOpenFrames, Animation.PlayMode.NORMAL);

            Array<AtlasRegion> portalPurpleHoldFrames = new Array<AtlasRegion>();
            portalPurpleHoldFrames.add(atlas.findRegion(Constants.PORTAL_HOLD_PURPLE1));
            portalPurpleHoldFrames.add(atlas.findRegion(Constants.PORTAL_HOLD_PURPLE2));
            portalPurpleHoldFrames.add(atlas.findRegion(Constants.PORTAL_HOLD_PURPLE3));
            portalPurpleHoldFrames.add(atlas.findRegion(Constants.PORTAL_HOLD_PURPLE4));
            portalPurpleHoldFrames.add(atlas.findRegion(Constants.PORTAL_HOLD_PURPLE5));
            portalPurpleHoldFrames.add(atlas.findRegion(Constants.PORTAL_HOLD_PURPLE6));
            portalPurpleHoldFrames.add(atlas.findRegion(Constants.PORTAL_HOLD_PURPLE7));
            portalPurpleHoldFrames.add(atlas.findRegion(Constants.PORTAL_HOLD_PURPLE8));
            portalPurpleHoldAnimation = new Animation(Constants.PORTAL_LOOP_DURATION, portalPurpleHoldFrames, Animation.PlayMode.LOOP);

            portalPurpleClosedAnimation = new Animation(Constants.PORTAL_LOOP_DURATION, portalPurpleOpenFrames, Animation.PlayMode.REVERSED);
        }
    }

    public class OnscreenControlsAssets {

        public final AtlasRegion moveRight;
        public final AtlasRegion moveLeft;
        public final AtlasRegion shoot;
        public final AtlasRegion jump;
        public final AtlasRegion play;
        public final AtlasRegion exit;
        public final AtlasRegion info;

        public OnscreenControlsAssets(TextureAtlas atlas) {
            moveRight = atlas.findRegion(Constants.MOVE_RIGHT_BUTTON);
            moveLeft = atlas.findRegion(Constants.MOVE_LEFT_BUTTON);
            shoot = atlas.findRegion(Constants.SHOOT_BUTTON);
            jump = atlas.findRegion(Constants.JUMP_BUTTON);
            play = atlas.findRegion(Constants.PLAY_BUTTON);
            exit = atlas.findRegion(Constants.EXIT_BUTTON);
            info = atlas.findRegion(Constants.INFO_BUTTON);
        }
    }
}
