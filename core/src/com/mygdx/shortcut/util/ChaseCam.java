package com.mygdx.shortcut.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.shortcut.entities.Nova;

public class ChaseCam {

    public Camera camera;
    public Nova nova;
    Viewport viewport;
    boolean following;
    boolean inicio;

    public ChaseCam(Camera camera, Nova nova, Viewport viewport) {
        this.camera = camera;
        this.nova = nova;
        following = false;
        inicio = true;
        this.viewport = viewport;
    }

    public void update(float delta) {

        if(inicio){
            camera.position.x = nova.getPosition().x;
            camera.position.y = nova.getPosition().y+Constants.WORLD_SIZE/4f;
            inicio = false;
        }

        if(Gdx.input.isKeyJustPressed(Input.Keys.SHIFT_LEFT)){
            if(!following) {
                following = true;
            }else
                following = false;
        }

        if(following) {
            camera.position.x = nova.getPosition().x;
            camera.position.y = nova.getPosition().y+Constants.WORLD_SIZE/4f;
        }

        if(!following) {
            boolean left = Gdx.input.isKeyPressed(Input.Keys.A) || nova.leftButtonPressed;
            boolean right = Gdx.input.isKeyPressed(Input.Keys.D) || nova.rightButtonPressed;
            if(left && !right){
                if(nova.getPosition().x <= camera.position.x+100)
                    camera.position.x -= delta * Constants.CHASECAM_SPEED;
                else{
                    camera.position.x = nova.getPosition().x-110;
                }

            }
            if(right && !left){
                if(nova.getPosition().x >= camera.position.x-100)
                    camera.position.x += delta * Constants.CHASECAM_SPEED;
                else{
                    camera.position.x = nova.getPosition().x+110;
                }
            }
            camera.position.y = nova.getPosition().y+Constants.WORLD_SIZE/4f;
        }
    }

    public float getX(){
        return this.camera.position.x;
    }

    public float getY(){
        return this.camera.position.y;
    }

    public float getWidth(){
        return this.camera.viewportWidth;
    }

    public float getHeight(){
        return this.camera.viewportHeight;
    }
}
