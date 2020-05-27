package com.mygdx.shortcut.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.shortcut.util.Constants;
import com.mygdx.shortcut.util.Utils;

public class Background extends Actor {

    Texture background1;
    Texture background2;
    Texture background3;
    Viewport viewport;
    int sourceX1;
    int sourceY1;
    int sourceX2;
    int sourceY2;
    int sourceX3;
    int sourceY3;
    Nova nova;

    public Background(String path1, Viewport viewport, Nova nova) {
        this.background1 = new Texture(Gdx.files.internal(path1));
        this.background1.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);
        this.viewport = viewport;
        this.nova = nova;
    }

    public Background(String path1,String path2, Viewport viewport, Nova nova) {
        this.background1 = new Texture(Gdx.files.internal(path1));
        this.background1.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);
        this.background2 = new Texture(Gdx.files.internal(path2));
        this.background2.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);
        this.viewport = viewport;
        this.nova = nova;
    }

    public Background(String path1,String path2,String path3, Viewport viewport, Nova nova) {
        this.background1 = new Texture(Gdx.files.internal(path1));
        this.background1.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);
        this.background2 = new Texture(Gdx.files.internal(path2));
        this.background2.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);
        this.background3 = new Texture(Gdx.files.internal(path3));
        this.background3.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);
        this.viewport = viewport;
        this.nova = nova;
    }

    public void render(SpriteBatch batch) {

             if(this.background2==null) {
            this.sourceX1 = (int) (viewport.getScreenX() * 1.2f);
            this.sourceY1 = (int) (viewport.getScreenY() * 10 / 100);
            this.sourceX1 = (int) ((sourceX1 + Gdx.graphics.getDeltaTime() * nova.velocity.x) % viewport.getScreenWidth());
            this.sourceY1 = (int) ((sourceY1 + Gdx.graphics.getDeltaTime() * nova.velocity.y) % viewport.getScreenHeight());
            Utils.drawTextureBackground(batch, this.background1, viewport.getScreenX()-Constants.WORLD_SIZE*2, viewport.getScreenY()-Constants.WORLD_SIZE,
                    viewport.getScreenWidth()*1000, viewport.getScreenHeight()*1000,2.5f,2.5f,
                    this.sourceX1, this.sourceY1, viewport.getScreenWidth()*1000, viewport.getScreenHeight()*1000);
        }else if(this.background2!=null) {
            this.sourceX1 = (int) (viewport.getScreenX());
            this.sourceY1 = (int) (viewport.getScreenY() * 1 / 100);
            this.sourceX1 = (int) ((sourceX1 + Gdx.graphics.getDeltaTime() * nova.velocity.x) % viewport.getScreenWidth());
            this.sourceY1 = (int) 0;
            Utils.drawTextureBackground(batch, this.background1, 0, 0,
                    Gdx.graphics.getHeight()*1000, Gdx.graphics.getHeight()*1000,1,1,
                    this.sourceX1, this.sourceY1, Gdx.graphics.getHeight()*1000, Gdx.graphics.getHeight()*1000);

            this.sourceX2 = (int) (viewport.getScreenX() * 1.2f);
            this.sourceY2 = (int) (viewport.getScreenY() * 10 / 100);
            this.sourceX2 = (int) ((sourceX2 + Gdx.graphics.getDeltaTime() * nova.velocity.x) % viewport.getScreenWidth());
            this.sourceY2 = (int) ((sourceY2 + Gdx.graphics.getDeltaTime() * nova.velocity.y) % viewport.getScreenHeight());
            Utils.drawTextureBackground(batch, this.background2, 0, 0,
                    Gdx.graphics.getWidth()*1000, Gdx.graphics.getHeight()*1000,1,1,
                    this.sourceX2, this.sourceY2, Gdx.graphics.getHeight()*1000, Gdx.graphics.getHeight()*1000);
        }

        /*if(this.background3!=null) {
            this.sourceX3 = (int) (chaseCam.getX() * 1.2f);

            this.sourceY3 = (int) (chaseCam.getY() * 10 / 100);
            this.sourceX3 = (int) ((sourceX3 + Gdx.graphics.getDeltaTime() * nova.velocity.x) % chaseCam.getWidth());
            this.sourceY3 = (int) ((sourceY3 + Gdx.graphics.getDeltaTime() * nova.velocity.y) % chaseCam.getHeight());
            Utils.drawTextureBackground(batch, this.background3, chaseCam.getX(), chaseCam.getY(),
                    chaseCam.getWidth(), chaseCam.getHeight(),2,2, this.sourceX2, this.sourceY2, (int)chaseCam.getWidth(),(int) chaseCam.getHeight());
        }*/
    }
}
