package com.mygdx.shortcut;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.DelayedRemovalArray;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.shortcut.entities.Background;
import com.mygdx.shortcut.entities.BackgroundProps;
import com.mygdx.shortcut.entities.Enemy;
import com.mygdx.shortcut.entities.FinalLevel;
import com.mygdx.shortcut.entities.Nova;
import com.mygdx.shortcut.entities.ParallaxBackground;
import com.mygdx.shortcut.entities.Platform;
import com.mygdx.shortcut.entities.Portal;
import com.mygdx.shortcut.entities.Props;
import com.mygdx.shortcut.entities.Shot;
import com.mygdx.shortcut.util.Constants;
import com.mygdx.shortcut.util.Enums.*;

import java.sql.Time;

public class Level {

    public static final String TAG = Level.class.getName();

    public boolean gameOver;
    public boolean victory;
    public Viewport viewport;
    public Nova nova;
    FinalLevel finalLevel;
    Background background;
    ParallaxBackground parallaxBackground;

    private Array<BackgroundProps> backgroundProps;
    private Array<Props> props;
    private Array<Platform> platforms;
    private DelayedRemovalArray<Enemy> enemies;
    private DelayedRemovalArray<Shot> shots;
    private DelayedRemovalArray<Portal> portals;
    PortalColor portalColor;
    int numPortals;
    Time countdown;
    Sound sound;
    AssetManager amSound;



    public Level(Viewport viewport) {
        this.viewport = viewport;
        backgroundProps = new Array<BackgroundProps>();
        props = new Array<Props>();
        platforms = new Array<Platform>();
        enemies = new DelayedRemovalArray<Enemy>();
        shots = new DelayedRemovalArray<Shot>();
        portals = new DelayedRemovalArray<Portal>();
        finalLevel = null;
        gameOver = false;
        victory = false;
        this.portalColor = PortalColor.GREEN;
        this.numPortals = 0;
        amSound = new AssetManager();
        chargeSounds();
    }

    public void update(float delta) {


        if (nova.getLives() <= 0) {
            gameOver = true;
        }
        else if (nova.getPosition().dst(finalLevel.position) < Constants.EXIT_FINAL_RADIUS) {
            victory = true;
        }

        if(!gameOver && !victory) {
            nova.update(delta, platforms);

            shots.begin();
            for (Shot shot : shots) {
                shot.update(delta);
                if (!shot.active) {
                    //nova.clicked=false;
                    shots.removeValue(shot, false);
                }
            }
            shots.end();

            enemies.begin();
            for (int i = 0; i < enemies.size; i++) {
                enemies.get(i).update(delta);
            }
            enemies.end();

            portals.begin();
            for (int i = 0; i < portals.size; i++) {
                if (numPortals == 3) {
                    portals.get(0).Closing();
                    numPortals = 2;
                    if (portals.get(0).isFinished()) {
                        portals.removeIndex(0);
                    }
                }
            }
            portals.end();
        }

    }

    public void render(SpriteBatch batch) {

        if(this.background!=null) {
            this.background.render(batch);
        }else if(this.parallaxBackground!=null){
            this.parallaxBackground.render(batch);
        }

        for (BackgroundProps backgroundProps : backgroundProps){
            backgroundProps.render(batch);
        }

        for (Platform platform : platforms){
            platform.render(batch);
        }

        for (Props props : props){
            props.render(batch);
        }

        nova.render(batch);

        for (Enemy enemy : enemies) {
            enemy.render(batch);
        }

        for (Shot bullet : shots) {
            bullet.render(batch);
        }

        for(Portal portal : portals){
            portal.render(batch);
        }

        finalLevel.render(batch);
    }

    public Array<BackgroundProps> getBackgroundProps() {
        return backgroundProps;
    }

    public Array<Shot> getShots() {
        return shots;
    }

    public Array<Platform> getPlatforms() {
        return platforms;
    }

    public Array<Props> getProps() {
        return props;
    }

    public DelayedRemovalArray<Enemy> getEnemies() {
        return enemies;
    }

    public Viewport getViewport() {
        return viewport;
    }

    public void setViewport(Viewport viewport) {
        this.viewport = viewport;
    }

    public Nova getNova() {
        return nova;
    }

    public void setNova(Nova nova) {
        this.nova = nova;
    }

    public int getNumPortals(){
        return numPortals;
    }

    public DelayedRemovalArray<Portal> getPortals() {
        return portals;
    }

    public PortalColor getPortalColor() {
        return portalColor;
    }

    public String getTime(){
        String time = "";
        return time;
    }

    public void chooseBackground(int nextLevel){

        switch(nextLevel){
            case 0:
                this.background = new Background("background/bg1.png", this.viewport, this.nova);
                break;
            case 1:
                this.background = new Background("background/bg2-1.png","background/bg2-2.png", this.viewport, this.nova);
                break;
            case 2:
                parallaxBackground();
                break;
        }
    }

    public void setFinalLevel(FinalLevel finalLevel){
        this.finalLevel = finalLevel;
    }
    public void changePortal(){
        if(portalColor == PortalColor.GREEN){
            portalColor = PortalColor.PURPLE;
        }else{
            portalColor = PortalColor.GREEN;
        }
        numPortals++;
    }

    public void spawnShot(Vector2 position, Direction direction) {
        if (amSound.isLoaded("sounds/beam.ogg")) {
            sound = amSound.get("sounds/beam.ogg", Sound.class);
            long idSound = sound.play();
            sound.setVolume(idSound,0.3f);
        }
        shots.add(new Shot(this, position, direction));
    }

    public void spawnPortal(Vector2 position, PortalColor portalColor, Direction direction) {
        if (amSound.isLoaded("sounds/open-portal.ogg")) {
            sound = amSound.get("sounds/open-portal.ogg", Sound.class);
            long idSound = sound.play();
            sound.setVolume(idSound,0.3f);
        }
        portals.add(new Portal(position, portalColor, direction));
    }

    public void chargeSounds(){
        amSound.load("sounds/beam.ogg", Sound.class);
        amSound.finishLoading();
        amSound.load("sounds/open-portal.ogg", Sound.class);
        amSound.finishLoading();
    }

    public void parallaxBackground() {
        Array<Texture> textures = new Array<Texture>();
        for(int i = 1; i <=3;i++){
            textures.add(new Texture(Gdx.files.internal("background/parallax/bg3-"+i+".png")));
            textures.get(textures.size-1).setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);
        }
        parallaxBackground = new ParallaxBackground(textures, this.viewport);
        parallaxBackground.setSize(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        parallaxBackground.setSpeed(0);
    }
}
