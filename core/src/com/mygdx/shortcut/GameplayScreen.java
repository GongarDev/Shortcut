package com.mygdx.shortcut;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.mygdx.shortcut.overlays.GameOverOverlay;
import com.mygdx.shortcut.overlays.Hud;
import com.mygdx.shortcut.overlays.OnscreenControls;
import com.mygdx.shortcut.overlays.VictoryOverlay;
import com.mygdx.shortcut.util.Assets;
import com.mygdx.shortcut.util.ChaseCam;
import com.mygdx.shortcut.util.Constants;
import com.mygdx.shortcut.util.LevelLoader;
import com.mygdx.shortcut.util.Utils;

public class GameplayScreen extends ScreenAdapter {

    ShortcutGame game;
    ExtendViewport viewport;
    private Level level;
    private SpriteBatch batch;
    private ChaseCam chaseCam;
    private Hud hud;
    OnscreenControls onscreenControls;
    private VictoryOverlay victoryOverlay;
    private GameOverOverlay gameOverOverlay;
    Music music;
    AssetManager amSound;
    long levelEndOverlayStartTime;
    int nextLevel;

    public GameplayScreen(ShortcutGame game) {
        this.game=game;
    }
        @Override
    public void show() {
        AssetManager amPlayer = new AssetManager();
        AssetManager amButton = new AssetManager();
        AssetManager amEnemy = new AssetManager();
        AssetManager amShot = new AssetManager();
        AssetManager amPortal = new AssetManager();
        AssetManager amLevel1 = new AssetManager();
        amSound = new AssetManager();
        Assets.instance.init(amPlayer, amEnemy, amShot, amPortal, amButton, amLevel1);
        batch = new SpriteBatch();
        viewport = new ExtendViewport(Constants.WORLD_SIZE, Constants.WORLD_SIZE);
        onscreenControls = new OnscreenControls();
        if (onMobile()) {
            Gdx.input.setInputProcessor(onscreenControls);
        }
            victoryOverlay = new VictoryOverlay();
        gameOverOverlay = new GameOverOverlay();
        hud = new Hud();
        nextLevel = 0;
        startNewLevel();
    }

    @Override
    public void resize(int width, int height) {
        victoryOverlay.viewport.update(width, height, true);
        gameOverOverlay.viewport.update(width, height, true);
        hud.viewport.update(width, height, true);
        level.viewport.update(width, height, true);
        chaseCam.camera = level.viewport.getCamera();
        onscreenControls.viewport.update(width, height, true);
        onscreenControls.recalculateButtonPositions();
    }

    @Override
    public void dispose() {
        Assets.instance.dispose();
        amSound.dispose();
    }

    @Override
    public void render(float delta) {

        level.update(delta);
        chaseCam.update(delta);

        viewport.apply();
        batch.setProjectionMatrix(viewport.getCamera().combined);
        batch.begin();
        level.render(batch);
        batch.end();
        hud.render(batch, level.getNova().getLives(), level.getTime(), level.getPortalColor());
        if (onMobile()) {
            onscreenControls.render(batch);
        }
        renderLevelEndOverlays(batch);
    }

    private void renderLevelEndOverlays(SpriteBatch batch) {
        if (level.victory) {
            if (levelEndOverlayStartTime == 0) {
                levelEndOverlayStartTime = TimeUtils.nanoTime();
                victoryOverlay.init();
                this.music.stop();

                if (amSound.isLoaded("sounds/win.ogg")) {
                    music = amSound.get("sounds/win.ogg", Music.class);
                    music.play();
                    music.setVolume(0.30f);
                }
            }


            victoryOverlay.render(batch);
            if (Utils.secondsSince(levelEndOverlayStartTime) > Constants.LEVEL_END_DURATION) {
                levelEndOverlayStartTime = 0;
                levelComplete();
            }
        }


        if (level.gameOver) {

            if (levelEndOverlayStartTime == 0) {
                levelEndOverlayStartTime = TimeUtils.nanoTime();
                gameOverOverlay.init();
                music.stop();

                if (amSound.isLoaded("sounds/game-over.ogg")) {
                    music = amSound.get("sounds/game-over.ogg", Music.class);
                    music.play();
                    music.setVolume(0.30f);
                }
            }


            gameOverOverlay.render(batch);
            if (Utils.secondsSince(levelEndOverlayStartTime) > Constants.LEVEL_END_DURATION) {
                levelEndOverlayStartTime = 0;
                levelFailed();
            }
        }
    }

    private void startNewLevel() {

        String levelName = Constants.LEVELS[nextLevel];
        level = LevelLoader.load(levelName, viewport);
        chaseCam = new ChaseCam(viewport.getCamera(), level.nova, viewport);
        chaseCam.camera = level.viewport.getCamera();
        chaseCam.nova = level.getNova();
        level.chooseBackground(nextLevel);
        onscreenControls.nova = level.getNova();
        resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        level.nova.setOnMobile(onMobile());
        chargeMusic();
        chooseMusic(nextLevel);
    }

    public void levelComplete() {
        nextLevel++;
        if(Constants.LEVELS.length>nextLevel) {
            startNewLevel();
        }else {
            this.game.showMainMenuScreen();
        }
    }

    public void levelFailed() {
        startNewLevel();
    }

    private boolean onMobile() {
        return Gdx.app.getType() == Application.ApplicationType.Android || Gdx.app.getType() == Application.ApplicationType.iOS;
    }

    public void chooseMusic(int nextLevel){
        switch(nextLevel){
            case 0:
                if (amSound.isLoaded("sounds/Melkart_Space_Dementia.ogg")) {
                    music = amSound.get("sounds/Melkart_Space_Dementia.ogg", Music.class);
                    music.play();
                    music.setLooping(true);
                    music.setVolume(0.35f);
                }
                break;
            case 1:
                if (amSound.isLoaded("sounds/Melkart_Ost1.ogg")) {
                    music = amSound.get("sounds/Melkart_Ost1.ogg", Music.class);
                    music.play();
                    music.setLooping(true);
                    music.setVolume(0.35f);
                }
                break;
            case 2:
                if (amSound.isLoaded("sounds/Melkart_LegendaryCity.ogg")) {
                    music = amSound.get("sounds/Melkart_LegendaryCity.ogg", Music.class);
                    music.play();
                    music.setLooping(true);
                    music.setVolume(0.35f);
                }
                break;
        }
    }

    public void chargeMusic(){
        amSound.load("sounds/Melkart_Space_Dementia.ogg", Music.class);
        amSound.finishLoading();
        amSound.load("sounds/Melkart_Ost1.ogg", Music.class);
        amSound.finishLoading();
        amSound.load("sounds/Melkart_LegendaryCity.ogg", Music.class);
        amSound.finishLoading();
        amSound.load("sounds/game-over.ogg", Music.class);
        amSound.finishLoading();
        amSound.load("sounds/win.ogg", Music.class);
        amSound.finishLoading();
    }
}
