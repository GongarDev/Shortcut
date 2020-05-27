package com.mygdx.shortcut.util;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;

public class Constants {

    //WORLD/CAMERA
    public static final String NAME_PLAY = "Shortcut";
    public static final Color BACKGROUND_COLOR = Color.PURPLE;
    public static final float WORLD_SIZE = 400;
    public static final float MENU_WORLD_SIZE = 480.0f;
    public static final float GRAVITY = WORLD_SIZE/0.3f;
    public static final int PLATFORM_EDGE = 7;
    public static final float KILL_PLANE_HEIGHT = -100.0f;
    public static final float CHASECAM_SPEED = WORLD_SIZE;
    public static final float EXIT_FINAL_RADIUS = 40;

    //ATLAS
    public static final String ATLAS_PLAYER = "images/atlas/player.pack.atlas";
    public static final String ATLAS_ENEMY = "images/atlas/enemy.pack.atlas";
    public static final String ATLAS_LEVEL1 = "images/atlas/level1.pack.atlas";
    public static final String ATLAS_SHOT_EXPLOSION = "images/atlas/shot-explosion.pack.atlas";
    public static final String ATLAS_PORTAL = "images/atlas/portal.pack.atlas";
    public static final String ATLAS_BUTTON = "images/atlas/button.pack.atlas";

    //PLAYER
    public static final Vector2 NOVA_EYE_POSITION = new Vector2(37.5f, 16.5f);
    public static final float NOVA_EYE_HEIGHT = 14.0f;
    public static final float NOVA_HEIGHT = 16.5f;
    public static final float STANCE_WIDTH = 3.0f;
    public static final float NOVA_MOVE_SPEED = WORLD_SIZE/3;
    public static final float NOVA_JUMP_SPEED = 0.8f * WORLD_SIZE;
    public static final float MAX_JUMP_DURATION = 0.15f;
    public static final float IDLE_LOOP_DURATION = 0.10f;
    public static final float RUN_LOOP_DURATION = 0.10f;
    public static final float JUMP_LOOP_DURATION = 0.10f;
    public static final float PORTAL_LOOP_DURATION = 0.10f;
    public static final Vector2 KNOCKBACK_VELOCITY = new Vector2(300, 300);
    public static final Vector2 NOVA_CANNON_OFFSET = new Vector2(20f, 35f);
    public static final int INITIAL_LIVES = 3;

    public static final String PLAYER_RUN1 = "run-1";
    public static final String PLAYER_RUN2 = "run-2";
    public static final String PLAYER_RUN3 = "run-3";
    public static final String PLAYER_RUN4 = "run-4";
    public static final String PLAYER_RUN5 = "run-5";
    public static final String PLAYER_RUN6 = "run-6";
    public static final String PLAYER_RUN7 = "run-7";
    public static final String PLAYER_RUN8 = "run-8";

    public static final String PLAYER_RUN_SHOOT1 = "run-shoot-1";
    public static final String PLAYER_RUN_SHOOT2 = "run-shoot-2";
    public static final String PLAYER_RUN_SHOOT3 = "run-shoot-3";
    public static final String PLAYER_RUN_SHOOT4 = "run-shoot-4";
    public static final String PLAYER_RUN_SHOOT5 = "run-shoot-5";
    public static final String PLAYER_RUN_SHOOT6 = "run-shoot-6";
    public static final String PLAYER_RUN_SHOOT7 = "run-shoot-7";
    public static final String PLAYER_RUN_SHOOT8 = "run-shoot-8";

    public static final String PLAYER_JUMP1 = "jump-1";
    public static final String PLAYER_JUMP2 = "jump-2";
    public static final String PLAYER_JUMP3 = "jump-3";
    public static final String PLAYER_JUMP4 = "jump-4";

    public static final String PLAYER_IDLE1 = "idle-1";
    public static final String PLAYER_IDLE2 = "idle-2";
    public static final String PLAYER_IDLE3 = "idle-3";
    public static final String PLAYER_IDLE4 = "idle-4";

    public static final String PLAYER_SHOOT = "shoot";
    public static final String PLAYER_CROUCH = "crouch";
    public static final String PLAYER_HURT = "hurt";
    public static final String PLAYER_PORTRAIT = "portraitNova";

    //ENEMY
    public static final Vector2 ENEMY_CENTER = new Vector2(36.5f, 16.5f);
    public static final float ENEMY_MOVEMENT_SPEED = WORLD_SIZE/15;
    public static final float ENEMY_COLLISION_RADIUS = 15.0f;

    public static final String ENEMY_ALIEN1 = "alien-enemy-flying1";
    public static final String ENEMY_ALIEN2 = "alien-enemy-flying2";
    public static final String ENEMY_ALIEN3 = "alien-enemy-flying3";
    public static final String ENEMY_ALIEN4 = "alien-enemy-flying4";
    public static final String ENEMY_ALIEN5 = "alien-enemy-flying5";
    public static final String ENEMY_ALIEN6 = "alien-enemy-flying6";
    public static final String ENEMY_ALIEN7 = "alien-enemy-flying7";
    public static final String ENEMY_ALIEN8 = "alien-enemy-flying8";

    public static final String ENEMY_BIPEDAL1 = "bipedal-unit1";
    public static final String ENEMY_BIPEDAL2 = "bipedal-unit2";
    public static final String ENEMY_BIPEDAL3 = "bipedal-unit3";
    public static final String ENEMY_BIPEDAL4 = "bipedal-unit4";
    public static final String ENEMY_BIPEDAL5 = "bipedal-unit5";
    public static final String ENEMY_BIPEDAL6 = "bipedal-unit6";
    public static final String ENEMY_BIPEDAL7 = "bipedal-unit7";

    public static final String ENEMY_DRONE1 = "drone-1";
    public static final String ENEMY_DRONE2 = "drone-2";
    public static final String ENEMY_DRONE3 = "drone-3";
    public static final String ENEMY_DRONE4 = "drone-4";

    public static final String ENEMY_TURRET1 = "turret-1";
    public static final String ENEMY_TURRET2 = "turret-2";
    public static final String ENEMY_TURRET3 = "turret-3";
    public static final String ENEMY_TURRET4 = "turret-4";
    public static final String ENEMY_TURRET5 = "turret-5";
    public static final String ENEMY_TURRET6 = "turret-6";

    //SHOT-EXPLOSION
    public static final float SHOT_MOVE_SPEED = 350;
    public static final Vector2 SHOT_CENTER = new Vector2(3, 2);

    public static final String SHOT1 = "shot-1";
    public static final String SHOT2 = "shot-2";
    public static final String SHOT3 = "shot-3";
    public static final String SHOT_HIT1 = "shot-hit-1";
    public static final String SHOT_HIT2 = "shot-hit-2";
    public static final String SHOT_HIT3 = "shot-hit-3";
    public static final String EXPLOSION1 = "enemy-explosion-1";
    public static final String EXPLOSION2 = "enemy-explosion-2";
    public static final String EXPLOSION3 = "enemy-explosion-3";
    public static final String EXPLOSION4 = "enemy-explosion-4";
    public static final String EXPLOSION5 = "enemy-explosion-5";
    public static final String EXPLOSION6 = "enemy-explosion-6";

    //PORTAL
    public static final Vector2 PORTAL_CENTER = new Vector2(4, 35);
    public static final float PORTAL_COLLISION_RADIUS = 5.0f;
    public static final Vector2 PORTAL_OUT_VELOCITY = new Vector2(75, 100);

    public static final String PORTAL_OPEN_GREEN1 = "portal-open-green1";
    public static final String PORTAL_OPEN_GREEN2 = "portal-open-green2";
    public static final String PORTAL_OPEN_GREEN3 = "portal-open-green3";
    public static final String PORTAL_OPEN_GREEN4 = "portal-open-green4";
    public static final String PORTAL_OPEN_GREEN5 = "portal-open-green5";
    public static final String PORTAL_OPEN_GREEN6 = "portal-open-green6";
    public static final String PORTAL_OPEN_GREEN7 = "portal-open-green7";
    public static final String PORTAL_OPEN_GREEN8 = "portal-open-green8";
    public static final String PORTAL_HOLD_GREEN1 = "portal-hold-green1";
    public static final String PORTAL_HOLD_GREEN2 = "portal-hold-green2";
    public static final String PORTAL_HOLD_GREEN3 = "portal-hold-green3";
    public static final String PORTAL_HOLD_GREEN4 = "portal-hold-green4";
    public static final String PORTAL_HOLD_GREEN5 = "portal-hold-green5";
    public static final String PORTAL_HOLD_GREEN6 = "portal-hold-green6";
    public static final String PORTAL_HOLD_GREEN7 = "portal-hold-green7";
    public static final String PORTAL_HOLD_GREEN8 = "portal-hold-green8";

    public static final String PORTAL_OPEN_PURPLE1 = "portal-open-purple1";
    public static final String PORTAL_OPEN_PURPLE2 = "portal-open-purple2";
    public static final String PORTAL_OPEN_PURPLE3 = "portal-open-purple3";
    public static final String PORTAL_OPEN_PURPLE4 = "portal-open-purple4";
    public static final String PORTAL_OPEN_PURPLE5 = "portal-open-purple5";
    public static final String PORTAL_OPEN_PURPLE6 = "portal-open-purple6";
    public static final String PORTAL_OPEN_PURPLE7 = "portal-open-purple7";
    public static final String PORTAL_OPEN_PURPLE8 = "portal-open-purple8";
    public static final String PORTAL_HOLD_PURPLE1 = "portal-hold-purple1";
    public static final String PORTAL_HOLD_PURPLE2 = "portal-hold-purple2";
    public static final String PORTAL_HOLD_PURPLE3 = "portal-hold-purple3";
    public static final String PORTAL_HOLD_PURPLE4 = "portal-hold-purple4";
    public static final String PORTAL_HOLD_PURPLE5 = "portal-hold-purple5";
    public static final String PORTAL_HOLD_PURPLE6 = "portal-hold-purple6";
    public static final String PORTAL_HOLD_PURPLE7 = "portal-hold-purple7";
    public static final String PORTAL_HOLD_PURPLE8 = "portal-hold-purple8";

    //LEVEL LOADING
    public static final float HIT_DETECTION_RADIUS = 17;

    public static final String LEVEL_DIR = "levels";
    public static final String LEVEL_FILE_EXTENSION = "dt";
    public static final String LEVEL_COMPOSITE = "composite";
    public static final String LEVEL_9PATCHES = "sImage9patchs";
    public static final String LEVEL_IMAGES = "sImages";
    public static final String LEVEL_ERROR_MESSAGE = "There was a problem loading the level.";
    public static final String LEVEL_IMAGENAME_KEY = "imageName";
    public static final String LEVEL_X_KEY = "x";
    public static final String LEVEL_Y_KEY = "y";
    public static final String LEVEL_WIDTH_KEY = "width";
    public static final String LEVEL_HEIGHT_KEY = "height";
    public static final String LEVEL_IDENTIFIER_KEY = "itemIdentifier";
    public static final String LEVEL_RESPAWN = "nova";
    public static final String LEVEL_ENEMY_ALIEN = "enemy_alien";
    public static final String LEVEL_ENEMY_BIPEDAL = "enemy_bipedal";
    public static final String LEVEL_ENEMY_TURRET = "enemy_turret";
    public static final String LEVEL_ENEMY_DRONE = "enemy_drone";
    public static final String LEVEL_BACKGROUND = "background";
    public static final String LEVEL_PROPS = "props";
    public static final String LEVEL_FINAL = "final";
    public static final String[] LEVELS = {"MainScene", "secondScene", "lastScene"};

    //VICTORY/END OVER SCREENS
    public static final float LEVEL_END_DURATION = 4;
    public static final String VICTORY_MESSAGE = "Level Finished!";
    public static final String GAME_OVER_MESSAGE = "Game Over";
    public static final String FONT_FILE = "font/Cybrpnuk2.ttf";

    //HUD
    public static final float HUD_VIEWPORT_SIZE = 480;
    public static final float HUD_MARGIN = 20;
    public static final String HUD_PORTAL_LABEL = "Next Portal: ";
    public static final String HUD_COUNTDOWN_LABEL = "Countdown: ";

    public static final int ENEMY_KILL_SCORE = 100;
    public static final int ENEMY_HIT_SCORE = 25;
    public static final int POWERUP_SCORE = 50;

    //ONSCREEN CONTROLS
    public static final float ONSCREEN_CONTROLS_VIEWPORT_SIZE = 200;
    public static final String MOVE_LEFT_BUTTON = "button-move-left";
    public static final String MOVE_RIGHT_BUTTON = "button-move-right";
    public static final String JUMP_BUTTON = "button-jump";
    public static final String SHOOT_BUTTON = "button-shoot";
    public static final String PLAY_BUTTON = "button-play";
    public static final String EXIT_BUTTON = "button-exit";
    public static final String INFO_BUTTON = "button-info";

    public static final Vector2 BUTTON_CENTER = new Vector2(15, 15);
    public static final Vector2 BUTTON_CENTER_MENU = new Vector2(100, 30);
    public static final float BUTTON_RADIUS = 32;
    public static final float BUTTON_RADIUS_MENU = 64;

    public static final Vector2 START_CENTER = new Vector2(MENU_WORLD_SIZE *3 / 15, MENU_WORLD_SIZE / 9);
    public static final Vector2 EXIT_CENTER = new Vector2(MENU_WORLD_SIZE *3 / 15, MENU_WORLD_SIZE / 8);
    public static final Vector2 START_CENTER_DESKTOP = new Vector2(MENU_WORLD_SIZE / 2.2f , MENU_WORLD_SIZE / 9);
    public static final Vector2 EXIT_CENTER_DESKTOP = new Vector2(MENU_WORLD_SIZE / 2.2f , MENU_WORLD_SIZE / 8);
}
