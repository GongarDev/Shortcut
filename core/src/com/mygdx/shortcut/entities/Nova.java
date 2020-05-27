package com.mygdx.shortcut.entities;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.DelayedRemovalArray;
import com.badlogic.gdx.utils.TimeUtils;
import com.mygdx.shortcut.Level;
import com.mygdx.shortcut.util.Assets;
import com.mygdx.shortcut.util.Constants;
import com.mygdx.shortcut.util.Utils;
import com.mygdx.shortcut.util.Enums.*;

public class Nova {

    Vector2 spawnLocation;
    Vector2 position;
    Vector2 lastFramePosition;
    Vector2 velocity;

    Direction direction;
    JumpState jumpState;
    RunState runState;

    long idleStartTime;
    long runStartTime;
    long jumpStartTime;
    long runShotStartTime;

    Level level;

    public boolean clicked;
    boolean onMobile;
    int numShots;
    public int lives;
    public Sound sound;
    AssetManager amSound;

    public boolean jumpButtonPressed;
    public boolean leftButtonPressed;
    public boolean rightButtonPressed;

    public static final String TAG = Nova.class.getName();


    public Nova(Vector2 spawnLocation, Level level) {
        this.spawnLocation = spawnLocation;
        this.level = level;
        position = new Vector2();
        lastFramePosition = new Vector2();
        velocity = new Vector2();
        numShots = 0;
        amSound = new AssetManager();
        init();
    }

    public void init() {
        chargeSounds();
        lives = Constants.INITIAL_LIVES;
        respawn();
    }

    private void respawn() {
        position.set(spawnLocation);
        lastFramePosition.set(spawnLocation);
        this.velocity.setZero();
        this.jumpState = JumpState.FALLING;
        this.direction = Direction.RIGHT;
        this.runState = RunState.STANDING;
        level.getViewport().getCamera().position.set(this.position.x, this.position.y, 0);
        this.clicked = false;
    }

    public int getLives() {
        return lives;
    }

    public Vector2 getPosition() {
        return position;
    }

    public void update(float delta, Array<Platform> platforms) {
        lastFramePosition.set(position);
        velocity.y -= delta * Constants.GRAVITY;
        position.mulAdd(velocity, delta);

        //HitBox Player
        Rectangle novaBounds = new Rectangle(
                position.x - Constants.STANCE_WIDTH / 2,
                position.y + 16.0f,
                Constants.STANCE_WIDTH,
                Constants.NOVA_HEIGHT);

        //Respawn by fall
        if (position.y < Constants.KILL_PLANE_HEIGHT) {
            lives--;
            if (lives > -1) {
                respawn();
            }
        }

        //Check HitBox player with other hitBoxes platforms
        if (jumpState != JumpState.JUMPING) {
            if (jumpState != JumpState.RECOILING) {
                jumpState = JumpState.FALLING;
            }

            for (Platform platform : platforms) {
                if (landedOnPlatform(platform)) {

                    jumpState = JumpState.GROUNDED;
                    velocity.y = 0;
                    velocity.x = 0;
                    position.y = platform.top + Constants.NOVA_EYE_HEIGHT;
                }
                if (landedDownPlatform(platform)) {

                    jumpState = JumpState.FALLING;
                    velocity.y = 0;
                    velocity.x = 0;
                    position.y = platform.bottom - Constants.NOVA_EYE_HEIGHT;
                }
            }

        }else{
            for (Platform platform : platforms) {
                if (landedLeftPlatform(platform)) {

                    jumpState = JumpState.FALLING;
                    position.x = platform.left - Constants.STANCE_WIDTH;
                }
                if (landedRightPlatform(platform)) {

                    jumpState = JumpState.FALLING;
                    position.x = platform.right + Constants.STANCE_WIDTH;
                }
                if (landedDownPlatform(platform)) {
                    jumpState = JumpState.FALLING;
                    position.y = platform.bottom - Constants.NOVA_EYE_HEIGHT;
                }
            }
        }

        for (Platform platform : platforms) {
            if (landedLeftPlatform(platform))  {
                position.x = platform.left - Constants.STANCE_WIDTH;
            }
            if (landedRightPlatform(platform)) {
                position.x = platform.right + Constants.STANCE_WIDTH;
            }
            if (landedDownPlatform(platform)) {
                jumpState = JumpState.FALLING;
                velocity.y = 0;
                velocity.x = 0;
                position.y = platform.bottom - Constants.NOVA_EYE_HEIGHT;
            }
        }

        //Check hitbox player with hitbox enemies
        for (Enemy enemy : level.getEnemies()) {

            Rectangle enemyBounds = new Rectangle(
                    enemy.position.x - Constants.ENEMY_COLLISION_RADIUS,
                    enemy.position.y - Constants.ENEMY_COLLISION_RADIUS,
                    2 * Constants.ENEMY_COLLISION_RADIUS,
                    2 * Constants.ENEMY_COLLISION_RADIUS);

            if (novaBounds.overlaps(enemyBounds)) {
                if (position.x < enemy.position.x) {
                    recoilFromEnemy(Direction.LEFT);
                } else {
                    recoilFromEnemy(Direction.RIGHT);
                }
            }
        }

        //Check hitbox player with hitbox portals
        if (level.getNumPortals() > 1) {
            for (Portal portal : level.getPortals()) {
                Rectangle portalBounds = new Rectangle(
                        portal.position.x - Constants.PORTAL_COLLISION_RADIUS,
                        portal.position.y - Constants.PORTAL_COLLISION_RADIUS,
                        2 * Constants.PORTAL_COLLISION_RADIUS,
                        2 * Constants.PORTAL_COLLISION_RADIUS);

                if (novaBounds.overlaps(portalBounds)) {
                    teleport(portal);
                }

            }
        }

        //Jump action
        if (Gdx.input.isKeyPressed(Input.Keys.SPACE) || jumpButtonPressed) {

            switch (jumpState) {
                case GROUNDED:
                    startJump();
                    break;
                case JUMPING:
                    continueJump();
                    break;
                case FALLING:
                    break;
            }
        } else {
            endJump();
        }

        //Moving left-right or standing action
        if (jumpState != JumpState.RECOILING) {

            boolean left = Gdx.input.isKeyPressed(Input.Keys.A) || leftButtonPressed;
            boolean right = Gdx.input.isKeyPressed(Input.Keys.D) || rightButtonPressed;

            if (left && !right) {
                moveLeft(delta);
            } else if (right && !left) {
                moveRight(delta);
            } else {
                if (!this.clicked)
                    runState = RunState.STANDING;
                else
                    runState = RunState.STANDING_SHOT;
            }
        }


        if (jumpState == JumpState.FALLING && (runState == RunState.STANDING || runState == RunState.STANDING_SHOT)) {
            idleStartTime = TimeUtils.nanoTime();
        }

        //Shot action
        if(!this.onMobile) {
            if (Gdx.input.isButtonPressed(Input.Buttons.LEFT) && this.clicked == false) {
                shootAction();
            } else if (!Gdx.input.isButtonPressed(Input.Buttons.LEFT) && this.clicked == true) {
                this.clicked = false;
            }
        }
    }

    boolean landedOnPlatform(Platform platform) {
        boolean leftFootIn = false;
        boolean rightFootIn = false;
        boolean straddle = false;
        if (lastFramePosition.y - Constants.NOVA_EYE_HEIGHT >= platform.top &&
                position.y - Constants.NOVA_EYE_HEIGHT < platform.top) {
            float leftFoot = position.x - Constants.STANCE_WIDTH / 2;
            float rightFoot = position.x + Constants.STANCE_WIDTH / 2;

            leftFootIn = (platform.left < leftFoot && platform.right > leftFoot);
            rightFootIn = (platform.left < rightFoot && platform.right > rightFoot);
            straddle = (platform.left > leftFoot && platform.right < rightFoot);
        }
        return leftFootIn || rightFootIn || straddle;
    }

    boolean landedDownPlatform(Platform platform) {
        boolean leftHeadIn = false;
        boolean rightHeadIn = false;
        boolean straddle = false;
        if (lastFramePosition.y <= platform.bottom &&
                position.y > platform.bottom) {
            float leftHead = position.x - Constants.STANCE_WIDTH / 2;
            float rightHead = position.x + Constants.STANCE_WIDTH / 2;

            leftHeadIn = (platform.left < leftHead && platform.right > leftHead);
            rightHeadIn = (platform.left < rightHead && platform.right > rightHead);
            straddle = (platform.left > leftHead && platform.right < rightHead);

        }
        return leftHeadIn || rightHeadIn || straddle;
    }

    boolean landedLeftPlatform(Platform platform) {
        boolean headIn = false;
        boolean footIn = false;
        boolean straddle = false;
        if (lastFramePosition.x - Constants.STANCE_WIDTH <= platform.left &&
                position.x >= platform.left - Constants.STANCE_WIDTH) {

            float head = position.y - Constants.NOVA_HEIGHT / 2;
            float foot = position.y + Constants.NOVA_HEIGHT / 2;

            headIn = (platform.top < head && platform.bottom > head);
            footIn = (platform.top < foot && platform.bottom > foot);
            straddle = (platform.top > head && platform.bottom < foot);

        }
        return headIn || footIn || straddle;
    }

    boolean landedRightPlatform(Platform platform) {
        boolean headIn = false;
        boolean footIn = false;
        boolean straddle = false;
        if (lastFramePosition.x - Constants.STANCE_WIDTH <= platform.right &&
                position.x >= platform.right - Constants.STANCE_WIDTH) {

            float head = position.y - Constants.NOVA_HEIGHT / 2;
            float foot = position.y + Constants.NOVA_HEIGHT / 2;

            headIn = (platform.top < head && platform.bottom > head);
            footIn = (platform.top < foot && platform.bottom > foot);
            straddle = (platform.top > head && platform.bottom < foot);

        }
        return headIn || footIn || straddle;
    }

    private void moveLeft(float delta) {
        if (jumpState == JumpState.GROUNDED && (runState != RunState.RUNNING && runState != RunState.RUNNING_SHOT)) {
            runStartTime = TimeUtils.nanoTime();
        }
        if (!this.clicked) {
            runState = RunState.RUNNING;
        } else {
            runState = RunState.RUNNING_SHOT;
        }
        direction = Direction.LEFT;
        position.x -= delta * Constants.NOVA_MOVE_SPEED;
    }

    private void moveRight(float delta) {
        if (jumpState == JumpState.GROUNDED && (runState != RunState.RUNNING && runState != RunState.RUNNING_SHOT)) {
            runStartTime = TimeUtils.nanoTime();
        }
        if (!this.clicked) {
            runState = RunState.RUNNING;
        } else {
            runState = RunState.RUNNING_SHOT;
        }
        direction = Direction.RIGHT;
        position.x += delta * Constants.NOVA_MOVE_SPEED;
    }

    private void startJump() {
        jumpState = JumpState.JUMPING;
        jumpStartTime = TimeUtils.nanoTime();
        if (amSound.isLoaded("sounds/jump.ogg")) {
            sound = amSound.get("sounds/jump.ogg", Sound.class);
            long idSound = sound.play();
            sound.setVolume(idSound,0.3f);
        }
        continueJump();
    }

    private void continueJump() {
        if (jumpState == JumpState.JUMPING) {
            float jumpDuration = MathUtils.nanoToSec * (TimeUtils.nanoTime() - jumpStartTime);
            if (jumpDuration < Constants.MAX_JUMP_DURATION) {
                velocity.y = Constants.NOVA_JUMP_SPEED;
            } else {
                endJump();
            }
        }
    }

    private void endJump() {
        if (jumpState == JumpState.JUMPING) {
            jumpState = JumpState.FALLING;
            this.clicked=false;
        }
    }

    private void recoilFromEnemy(Direction direction) {
        lives--;
        if (amSound.isLoaded("sounds/hit.ogg")) {
            sound = amSound.get("sounds/hit.ogg", Sound.class);
            long idSound = sound.play();
            sound.setVolume(idSound,0.3f);
        }
        jumpState = JumpState.RECOILING;
        velocity.y = Constants.KNOCKBACK_VELOCITY.y;

        if (direction == Direction.LEFT) {
            velocity.x = -Constants.KNOCKBACK_VELOCITY.x;
        } else {
            velocity.x = Constants.KNOCKBACK_VELOCITY.x;
        }
    }

    public void shootAction(){

        Vector2 shotPosition;
        Direction direction;

        if(level.getShots().size<2 && this.clicked==false) {
            if (this.direction == Direction.RIGHT) {
                shotPosition = new Vector2(position.x + Constants.NOVA_CANNON_OFFSET.x,
                        position.y + Constants.NOVA_CANNON_OFFSET.y);
                direction = Direction.RIGHT;
                runShotStartTime = TimeUtils.nanoTime();
                numShots++;
            } else {
                shotPosition = new Vector2(position.x + Constants.NOVA_CANNON_OFFSET.x,
                        position.y + Constants.NOVA_CANNON_OFFSET.y);
                direction = Direction.LEFT;
                runShotStartTime = TimeUtils.nanoTime();
                numShots++;
            }
            level.spawnShot(shotPosition, direction);
        }
        if(level.getNumPortals() == 2) {
            numShots = level.getShots().size;
        }else if(level.getNumPortals() ==0 && level.getShots().size < 2){

        }
        this.clicked = true;
    }
    private void teleport(Portal portal) {
        DelayedRemovalArray<Portal> portals = level.getPortals();
        if (portal.portalColor == PortalColor.GREEN) {
            if (portals.get(1).portalColor == PortalColor.PURPLE) {
                if (portals.get(1).direction == Direction.RIGHT) {
                    this.position.x = level.getPortals().get(1).position.x - 5;
                    this.position.y = level.getPortals().get(1).position.y + 5;
                    velocity.x = -Constants.PORTAL_OUT_VELOCITY.x;
                } else {
                    this.position.x = level.getPortals().get(1).position.x + 5;
                    this.position.y = level.getPortals().get(1).position.y + 5;
                    velocity.x = Constants.PORTAL_OUT_VELOCITY.x;
                }
            } else {
                if (portals.get(0).direction == Direction.RIGHT) {
                    this.position.x = level.getPortals().get(0).position.x - 5;
                    this.position.y = level.getPortals().get(0).position.y + 5;
                    velocity.x = -Constants.PORTAL_OUT_VELOCITY.x;
                } else {
                    this.position.x = level.getPortals().get(0).position.x + 5;
                    this.position.y = level.getPortals().get(0).position.y + 5;
                    velocity.x = Constants.PORTAL_OUT_VELOCITY.x;
                }
            }
        } else if (portal.portalColor == PortalColor.PURPLE) {
            if (portals.get(1).portalColor == PortalColor.GREEN) {
                if (portals.get(1).direction == Direction.RIGHT) {
                    this.position.x = level.getPortals().get(1).position.x - 5;
                    this.position.y = level.getPortals().get(1).position.y + 5;
                    velocity.x = -Constants.PORTAL_OUT_VELOCITY.x;
                } else {
                    this.position.x = level.getPortals().get(1).position.x + 5;
                    this.position.y = level.getPortals().get(1).position.y + 5;
                    velocity.x = Constants.PORTAL_OUT_VELOCITY.x;
                }
            } else {
                if (portals.get(0).direction == Direction.RIGHT) {
                    this.position.x = level.getPortals().get(0).position.x - 5;
                    this.position.y = level.getPortals().get(0).position.y + 5;
                    velocity.x = -Constants.PORTAL_OUT_VELOCITY.x;
                } else {
                    this.position.x = level.getPortals().get(0).position.x + 5;
                    this.position.y = level.getPortals().get(0).position.y + 5;
                    velocity.x = Constants.PORTAL_OUT_VELOCITY.x;
                }
            }
        }

        if (amSound.isLoaded("sounds/teleport.ogg")) {
            sound = amSound.get("sounds/teleport.ogg", Sound.class);
            long idSound = sound.play();
            sound.setVolume(idSound,0.3f);
        }
        velocity.y = Constants.PORTAL_OUT_VELOCITY.y;
        level.getViewport().getCamera().position.set(this.position.x, this.position.y, 0);
    }

    public void render(SpriteBatch batch) {

        TextureRegion region = null;
        boolean flipX = false;

        if (direction == Direction.RIGHT && jumpState != JumpState.GROUNDED) {
            float jumpTimeSeconds = MathUtils.nanoToSec * (TimeUtils.nanoTime() - jumpStartTime);
            region = Assets.instance.novaAssets.jumpingAnimation.getKeyFrame(jumpTimeSeconds);
            flipX = false;
        } else if (direction == Direction.RIGHT && runState == RunState.STANDING) {
            float idleTimeSeconds = MathUtils.nanoToSec * (TimeUtils.nanoTime() - idleStartTime);
            region = Assets.instance.novaAssets.idleAnimation.getKeyFrame(idleTimeSeconds);
            flipX = false;
        } else if (direction == Direction.RIGHT && runState == RunState.STANDING_SHOT) {
            region = Assets.instance.novaAssets.shoot;
            flipX = false;
        } else if (direction == Direction.RIGHT && runState == RunState.RUNNING) {
            float runTimeSeconds = MathUtils.nanoToSec * (TimeUtils.nanoTime() - runStartTime);
            region = Assets.instance.novaAssets.runningAnimation.getKeyFrame(runTimeSeconds);
            flipX = false;
        } else if (direction == Direction.RIGHT && runState == RunState.RUNNING_SHOT) {
            float runTimeSeconds = MathUtils.nanoToSec * (TimeUtils.nanoTime() - runShotStartTime);
            region = Assets.instance.novaAssets.runShootAnimation.getKeyFrame(runTimeSeconds);
            flipX = false;
        } else if (direction == Direction.LEFT && jumpState != JumpState.GROUNDED) {
            float jumpTimeSeconds = MathUtils.nanoToSec * (TimeUtils.nanoTime() - jumpStartTime);
            region = Assets.instance.novaAssets.jumpingAnimation.getKeyFrame(jumpTimeSeconds);
            flipX = true;
        } else if (direction == Direction.LEFT && runState == RunState.STANDING) {
            float idleTimeSeconds = MathUtils.nanoToSec * (TimeUtils.nanoTime() - idleStartTime);
            region = Assets.instance.novaAssets.idleAnimation.getKeyFrame(idleTimeSeconds);
            flipX = true;
        } else if (direction == Direction.LEFT && runState == RunState.STANDING_SHOT) {
            region = Assets.instance.novaAssets.shoot;
            flipX = true;
        } else if (direction == Direction.LEFT && runState == RunState.RUNNING) {
            float runTimeSeconds = MathUtils.nanoToSec * (TimeUtils.nanoTime() - runStartTime);
            region = Assets.instance.novaAssets.runningAnimation.getKeyFrame(runTimeSeconds);
            flipX = true;
        } else if (direction == Direction.LEFT && runState == RunState.RUNNING_SHOT) {
            float runTimeSeconds = MathUtils.nanoToSec * (TimeUtils.nanoTime() - runShotStartTime);
            region = Assets.instance.novaAssets.runShootAnimation.getKeyFrame(runTimeSeconds);
            flipX = true;
        }

        Utils.drawTextureRegion(batch, region, position.x - Constants.NOVA_EYE_POSITION.x,
                position.y - Constants.NOVA_EYE_POSITION.y, flipX);
    }

    public void chargeSounds(){
        amSound.load("sounds/teleport.ogg", Sound.class);
        amSound.finishLoading();
        amSound.load("sounds/hit.ogg", Sound.class);
        amSound.finishLoading();
        amSound.load("sounds/jump.ogg", Sound.class);
        amSound.finishLoading();
    }

    public void setOnMobile(boolean onMobile){
        this.onMobile = onMobile;
    }
}
