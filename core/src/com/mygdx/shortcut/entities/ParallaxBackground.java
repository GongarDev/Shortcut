package com.mygdx.shortcut.entities;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.Viewport;

public class ParallaxBackground extends Actor {

    private int scroll;
    private Array<Texture> layers;
    private final int LAYER_SPEED_DIFFERENCE = 1;

    float x,y,width,heigth,scaleX,scaleY;
    int originX, originY,rotation,srcX,srcY;
    boolean flipX,flipY;

    private int speed;

    private Viewport viewport;

    public ParallaxBackground(Array<Texture> textures, Viewport viewport){
        this.viewport=viewport;
        layers = textures;
        for(int i = 0; i <textures.size;i++){
            layers.get(i).setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);
        }
        scroll = 0;
        speed = 0;

        x = y = originX = originY = rotation = srcY = 0;
        width =  Gdx.graphics.getWidth();
        if(onMobile()){
            width =  Gdx.graphics.getWidth()*100;

        }
        heigth = Gdx.graphics.getHeight();
        scaleX = scaleY = 1.5f;
        flipX = flipY = false;
    }

    public void setSpeed(int newSpeed){
        this.speed = newSpeed;
    }

    public void render(SpriteBatch batch) {
        scroll+=speed;
        if(onMobile()) {
            for (int i = 0; i < layers.size; i++) {
                srcX = scroll + i * this.LAYER_SPEED_DIFFERENCE * scroll;
                batch.draw(layers.get(i), 0, viewport.getCamera().position.y - (viewport.getScreenHeight() / 2) - 150, originX, originY, width, heigth, scaleX, scaleY, rotation, srcX, srcY, Gdx.graphics.getWidth()*100, Gdx.graphics.getHeight(), flipX, flipY);
            }
        }else{
            for (int i = 0; i < layers.size; i++) {
                srcX = scroll + i * this.LAYER_SPEED_DIFFERENCE * scroll;
                batch.draw(layers.get(i), 0, viewport.getCamera().position.y-viewport.getScreenHeight()/2, originX, originY, width*100, heigth*100, scaleX, scaleY, rotation, srcX, srcY, Gdx.graphics.getWidth()*100, Gdx.graphics.getHeight()*100, flipX, flipY);
            }
        }
    }

    private boolean onMobile() {
        return Gdx.app.getType() == Application.ApplicationType.Android || Gdx.app.getType() == Application.ApplicationType.iOS;
    }
}