package com.mygdx.shortcut.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.TimeUtils;
import com.mygdx.shortcut.util.Assets;
import com.mygdx.shortcut.util.Constants;
import com.mygdx.shortcut.util.Enums.*;
import com.mygdx.shortcut.util.Utils;

public class Portal {

    final Direction direction;
    final Vector2 position;
    private final long startTime;
    PortalState portalState;
    PortalColor portalColor;

    public Portal(Vector2 position, PortalColor portalColor, Direction direction) {
        this.position = position;
        this.startTime = TimeUtils.nanoTime();
        this.direction = direction;
        this.portalState = PortalState.OPENING;
        this.portalColor = portalColor;
    }

    public void render(SpriteBatch batch) {

        boolean flipX = false;
        TextureRegion regionOpen;
        TextureRegion regionHold;
        TextureRegion regionClosed;

        if(direction == Direction.RIGHT){
            flipX = true;
        }else if(direction == Direction.LEFT){
            flipX = false;
        }

        if (portalColor == PortalColor.GREEN){
            if(portalState == PortalState.OPENING){

                float openTimeSeconds = MathUtils.nanoToSec * (TimeUtils.nanoTime() - startTime);
                regionOpen = Assets.instance.portalAssets.portalGreenOpenAnimation.getKeyFrame(openTimeSeconds);
                Utils.drawTextureRegion(batch, regionOpen, position, Constants.PORTAL_CENTER, flipX);
                if(isOpened()){
                    this.portalState = PortalState.HOLD;
                }
            }else if(portalState == PortalState.HOLD){
                float HoldTimeSeconds = MathUtils.nanoToSec * (TimeUtils.nanoTime() - startTime);
                regionHold = Assets.instance.portalAssets.portalGreenHoldAnimation.getKeyFrame(HoldTimeSeconds);
                Utils.drawTextureRegion(batch, regionHold, position, Constants.PORTAL_CENTER, flipX);
            }else if(portalState == PortalState.CLOSING){
                float ClosedTimeSeconds = MathUtils.nanoToSec * (TimeUtils.nanoTime() - startTime);
                regionClosed = Assets.instance.portalAssets.portalGreenClosedAnimation.getKeyFrame(ClosedTimeSeconds);
                Utils.drawTextureRegion(batch, regionClosed, position, Constants.PORTAL_CENTER, flipX);
            }
        }else if (portalColor == PortalColor.PURPLE){
            if(portalState == PortalState.OPENING){
                float OpenTimeSeconds = MathUtils.nanoToSec * (TimeUtils.nanoTime() - startTime);
                regionOpen = Assets.instance.portalAssets.portalPurpleOpenAnimation.getKeyFrame(OpenTimeSeconds);
                Utils.drawTextureRegion(batch, regionOpen, position, Constants.PORTAL_CENTER, flipX);
                if(isOpened()){
                    this.portalState = PortalState.HOLD;
                }
            }else if(portalState == PortalState.HOLD){
                float HoldTimeSeconds = MathUtils.nanoToSec * (TimeUtils.nanoTime() - startTime);
                regionHold = Assets.instance.portalAssets.portalPurpleHoldAnimation.getKeyFrame(HoldTimeSeconds);
                Utils.drawTextureRegion(batch, regionHold, position, Constants.PORTAL_CENTER, flipX);
            }else if(portalState == PortalState.CLOSING){
                float ClosedTimeSeconds = MathUtils.nanoToSec * (TimeUtils.nanoTime() - startTime);
                regionClosed = Assets.instance.portalAssets.portalPurpleClosedAnimation.getKeyFrame(ClosedTimeSeconds);
                Utils.drawTextureRegion(batch, regionClosed, position, Constants.PORTAL_CENTER, flipX);
            }
        }
    }

    public boolean isOpened() {
        final float elapsedTime = Utils.secondsSince(startTime);
        return Assets.instance.portalAssets.portalGreenOpenAnimation.isAnimationFinished(elapsedTime);
    }

    public boolean isFinished() {
        final float elapsedTime = Utils.secondsSince(startTime);
        if(portalColor==PortalColor.GREEN)
            return Assets.instance.portalAssets.portalGreenClosedAnimation.isAnimationFinished(elapsedTime);
        else
            return Assets.instance.portalAssets.portalPurpleClosedAnimation.isAnimationFinished(elapsedTime);
    }

    public void Closing() {
        this.portalState = PortalState.CLOSING;
    }
}
