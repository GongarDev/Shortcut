package com.mygdx.shortcut.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.shortcut.util.Assets;

public class BackgroundProps {

    public float top;
    public float bottom;
    public float left;
    public float right;
    public float width;
    public float height;
    public String patch;
    public Vector2 position;

    String identifier;

    public BackgroundProps(float left, float top, float width, float height, String patch) {
        this.top = top;
        this.bottom = top - height;
        this.left = left;
        this.right = left + width;
        this.width = width;
        this.height = height;
        this.position = new Vector2(left, top);
        this.patch = patch;
    }

    public void render(SpriteBatch batch) {
        float width = this.right - this.left;
        float height = this.top - this.bottom;
        Assets.instance.level1Assets.takeNinePatch(this.patch).draw(batch, left - 1, bottom - 1, width + 2, height + 2);

    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }
}

