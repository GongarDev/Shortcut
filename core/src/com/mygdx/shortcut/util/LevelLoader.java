package com.mygdx.shortcut.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.shortcut.Level;
import com.mygdx.shortcut.entities.Background;
import com.mygdx.shortcut.entities.BackgroundProps;
import com.mygdx.shortcut.entities.Enemy;
import com.mygdx.shortcut.entities.FinalLevel;
import com.mygdx.shortcut.entities.Nova;
import com.mygdx.shortcut.entities.Platform;
import com.mygdx.shortcut.entities.Props;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.File;
import java.util.Comparator;

public class LevelLoader {

    public static final String TAG = LevelLoader.class.toString();

    public static Level load(String levelName, Viewport viewport) {

        String path = Constants.LEVEL_DIR + File.separator + levelName + "." + Constants.LEVEL_FILE_EXTENSION;
        Level level = new Level(viewport);

        FileHandle file = Gdx.files.internal(path);
        JSONParser parser = new JSONParser();

        try {
            JSONObject rootJsonObject = (JSONObject) parser.parse(file.reader());
            JSONObject composite = (JSONObject) rootJsonObject.get(Constants.LEVEL_COMPOSITE);
            JSONArray platforms = (JSONArray) composite.get(Constants.LEVEL_9PATCHES);
            loadPlatforms(platforms, level);

        } catch (Exception ex) {
            Gdx.app.error(TAG, ex.getMessage());
            Gdx.app.error(TAG, Constants.LEVEL_ERROR_MESSAGE);
        }

        return level;
    }

    private static float safeGetFloat(JSONObject object, String key) {
        Number number = (Number) object.get(key);
        return (number == null) ? 0 : number.floatValue();
    }

    private static void loadPlatforms(JSONArray array, Level level) {

        Array<BackgroundProps> backgroundPropsArray = new Array<BackgroundProps>();
        Array<Platform> platformArray = new Array<Platform>();
        Array<Props> propsArray = new Array<Props>();
        Vector2 spawnLocation = new Vector2();

        for (Object object : array) {
            final JSONObject platformObject = (JSONObject) object;
            final float x = safeGetFloat(platformObject, Constants.LEVEL_X_KEY);
            final float y = safeGetFloat(platformObject, Constants.LEVEL_Y_KEY);
            final float width = ((Number) platformObject.get(Constants.LEVEL_WIDTH_KEY)).floatValue();
            final float height = ((Number) platformObject.get(Constants.LEVEL_HEIGHT_KEY)).floatValue();
            final String path = (String) platformObject.get(Constants.LEVEL_IMAGENAME_KEY);
            final String identifier = (String) platformObject.get(Constants.LEVEL_IDENTIFIER_KEY);
            if (identifier != null && identifier.equals(Constants.LEVEL_BACKGROUND)) {
                final BackgroundProps backgroundProps = new BackgroundProps(x, y + height, width, height, path);
                backgroundPropsArray.add(backgroundProps);
            } else if (identifier != null && identifier.equals(Constants.LEVEL_PROPS)) {
                final Props props = new Props(x, y + height, width, height, path);
                propsArray.add(props);
            } else {
                final Platform platform = new Platform(x, y + height, width, height, path);
                platformArray.add(platform);
                if (identifier != null && identifier.equals(Constants.LEVEL_ENEMY_ALIEN)) {
                    Enemy enemy = new Enemy(platform, Constants.LEVEL_ENEMY_ALIEN);
                    level.getEnemies().add(enemy);
                }
                if (identifier != null && identifier.equals(Constants.LEVEL_ENEMY_BIPEDAL)) {
                    Enemy enemy = new Enemy(platform, Constants.LEVEL_ENEMY_BIPEDAL);
                    level.getEnemies().add(enemy);
                }
                if (identifier != null && identifier.equals(Constants.LEVEL_ENEMY_TURRET)) {
                    Enemy enemy = new Enemy(platform, Constants.LEVEL_ENEMY_TURRET);
                    level.getEnemies().add(enemy);
                }
                if (identifier != null && identifier.equals(Constants.LEVEL_ENEMY_DRONE)) {
                    Enemy enemy = new Enemy(platform, Constants.LEVEL_ENEMY_DRONE);
                    level.getEnemies().add(enemy);
                }
                if (identifier != null && identifier.equals(Constants.LEVEL_RESPAWN)) {
                    spawnLocation = new Vector2(x+30,y+45);
                }
                if (identifier != null && identifier.equals(Constants.LEVEL_FINAL)) {
                    level.setFinalLevel(new FinalLevel(x, y + height, width, height, path));
                }
            }
        }

        backgroundPropsArray.sort(new Comparator<BackgroundProps>() {
            @Override
            public int compare(BackgroundProps o1, BackgroundProps o2) {
                if (o1.top < o2.top) {
                    return 1;
                } else if (o1.top > o2.top) {
                    return -1;
                }
                return 0;
            }
        });
        level.getBackgroundProps().addAll(backgroundPropsArray);

        platformArray.sort(new Comparator<Platform>() {
            @Override
            public int compare(Platform o1, Platform o2) {
                if (o1.top < o2.top) {
                    return 1;
                } else if (o1.top > o2.top) {
                    return -1;
                }
                return 0;
            }
        });
        level.getPlatforms().addAll(platformArray);


        propsArray.sort(new Comparator<Props>() {
            @Override
            public int compare(Props o1, Props o2) {
                if (o1.top < o2.top) {
                    return 1;
                } else if (o1.top > o2.top) {
                    return -1;
                }
                return 0;
            }
        });

        level.getProps().addAll(propsArray);
        level.nova = new Nova(spawnLocation, level);
    }
}
