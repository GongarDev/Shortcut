package com.mygdx.shortcut;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.shortcut.entities.ParallaxBackground;
import com.mygdx.shortcut.util.Assets;
import com.mygdx.shortcut.util.Constants;
import com.mygdx.shortcut.util.Utils;

public class MainMenuScreen extends InputAdapter implements Screen {

    ShortcutGame game;

    SpriteBatch batch;
    Viewport viewport;
    ParallaxBackground background;
    private Vector2 playButtonCenter;
    private Vector2 exitButtonCenter;
    TextureRegion playButton;
    TextureRegion exitButton;

    AssetManager amButton;
    AssetManager amSound;
    BitmapFont font;
    FreeTypeFontGenerator generator;
    FreeTypeFontGenerator.FreeTypeFontParameter parameter;

    Music music;

    public MainMenuScreen(ShortcutGame game) {
        this.game = game;
        batch = new SpriteBatch();
        viewport = new ExtendViewport(Constants.MENU_WORLD_SIZE, Constants.MENU_WORLD_SIZE);
        this.viewport.setScreenY(0);
        this.viewport.getCamera().position.y=0;
        amButton = new AssetManager();
        amSound = new AssetManager();
        Assets.instance.init(amButton);
        playButton = Assets.instance.onscreenControlsAssets.play;
        exitButton = Assets.instance.onscreenControlsAssets.exit;
        playButtonCenter = new Vector2();
        exitButtonCenter = new Vector2();
        parallaxBackground();
        recalculateButtonPositions();
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(this);
        generator = new FreeTypeFontGenerator(Gdx.files.internal(Constants.FONT_FILE));
        parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 96;
        font = generator.generateFont(parameter);
        generator.dispose();
        chargeMusic();
    }

    @Override
    public void render(float delta) {
        viewport.apply();
        Gdx.gl.glClearColor(Constants.BACKGROUND_COLOR.r, Constants.BACKGROUND_COLOR.g, Constants.BACKGROUND_COLOR.b, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.setProjectionMatrix(viewport.getCamera().combined);
        batch.begin();

        this.background.render(batch);

        if(onMobile()) {
            this.font.draw(batch, Constants.NAME_PLAY, viewport.getCamera().viewportWidth/2.5f, viewport.getCamera().viewportHeight/1.2f, 200, Align.center, false);

            Utils.drawTextureRegion(
                    batch,
                    playButton,
                    playButtonCenter,
                    Constants.START_CENTER,
                    false
            );

            Utils.drawTextureRegion(
                    batch,
                    exitButton,
                    exitButtonCenter,
                    Constants.EXIT_CENTER,
                    false
            );
        }else{
            this.font.draw(batch, Constants.NAME_PLAY, viewport.getCamera().viewportWidth/3, viewport.getCamera().viewportHeight/1.2f, 200, Align.center, false);

            Utils.drawTextureRegion(
                    batch,
                    playButton,
                    playButtonCenter,
                    Constants.START_CENTER_DESKTOP,
                    false
            );

            Utils.drawTextureRegion(
                    batch,
                    exitButton,
                    exitButtonCenter,
                    Constants.EXIT_CENTER_DESKTOP,
                    false
            );
        }
        batch.end();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    @Override
    public void pause() { }

    @Override
    public void resume() { }

    @Override
    public void hide() {
        batch.dispose();
        font.dispose();
    }

    @Override
    public void dispose() {
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {

        Vector2 viewportPosition = viewport.unproject(new Vector2(screenX, screenY));

        if (viewportPosition.dst(playButtonCenter) < Constants.BUTTON_RADIUS_MENU) {
            this.music.stop();
            Gdx.input.setInputProcessor(null);
            game.showGamePlayScreen();

        } else if (viewportPosition.dst(exitButtonCenter) < Constants.BUTTON_RADIUS_MENU) {
            this.music.stop();
            System.exit(0);
        }
        return true;
    }

    public void recalculateButtonPositions() {
        playButtonCenter.set(Constants.MENU_WORLD_SIZE-(playButton.getRegionWidth()/4f), Constants.BUTTON_RADIUS * 7.5f);
        exitButtonCenter.set(Constants.MENU_WORLD_SIZE-(playButton.getRegionWidth()/4f), Constants.BUTTON_RADIUS * 3.5f);
    }

    public void chargeMusic(){
        amSound.load("sounds/Melkart-OstMain.ogg", Music.class);
        amSound.finishLoading();
        if (amSound.isLoaded("sounds/Melkart-OstMain.ogg")) {
            music = amSound.get("sounds/Melkart-OstMain.ogg", Music.class);
            music.play();
            music.setLooping(true);
            music.setVolume(0.40f);
        }
    }

    public void parallaxBackground() {
        Array<Texture> textures = new Array<Texture>();
        for(int i = 1; i <=3;i++){
            textures.add(new Texture(Gdx.files.internal("background/parallax/bg3-"+i+".png")));
            textures.get(textures.size-1).setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);
        }
        background = new ParallaxBackground(textures, this.viewport);
        background.setSize(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        background.setSpeed(1);
    }

    private boolean onMobile() {
        return Gdx.app.getType() == Application.ApplicationType.Android || Gdx.app.getType() == Application.ApplicationType.iOS;
    }
}
