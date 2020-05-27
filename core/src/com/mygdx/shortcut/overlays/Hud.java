package com.mygdx.shortcut.overlays;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.shortcut.util.Assets;
import com.mygdx.shortcut.util.Constants;
import com.mygdx.shortcut.util.Enums.*;
import com.mygdx.shortcut.util.Utils;

public class Hud {

    public final Viewport viewport;
    final BitmapFont font;

    public Hud() {
        this.viewport = new ExtendViewport(Constants.HUD_VIEWPORT_SIZE, Constants.HUD_VIEWPORT_SIZE);
        font = new BitmapFont();
        font.getData().setScale(1);
    }

    public void render(SpriteBatch batch, int lives, String countdown, PortalColor portalColor) {
        viewport.apply();
        batch.setProjectionMatrix(viewport.getCamera().combined);
        batch.begin();
        final String hudString =
                //Constants.HUD_COUNTDOWN_LABEL + countdown + "\n" +
                        Constants.HUD_PORTAL_LABEL + portalColor;

        font.draw(batch, hudString, Constants.HUD_MARGIN, viewport.getWorldHeight() - Constants.HUD_MARGIN);
        final TextureRegion portraitNova = Assets.instance.novaAssets.portraitNova;
        for (int i = 1; i <= lives; i++) {
            final Vector2 drawPosition = new Vector2(
                    viewport.getWorldWidth() - i * (Constants.HUD_MARGIN / 2 + portraitNova.getRegionWidth()),
                    viewport.getWorldHeight() - Constants.HUD_MARGIN - portraitNova.getRegionHeight()
            );
            Utils.drawTextureRegion(
                    batch,
                    portraitNova,
                    drawPosition
            );
        }
        batch.end();
    }
}
