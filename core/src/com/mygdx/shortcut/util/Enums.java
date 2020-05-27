package com.mygdx.shortcut.util;

public class Enums {

    public enum Direction {
        LEFT, RIGHT, STATIC
    }

    public enum JumpState {
        JUMPING,
        FALLING,
        GROUNDED,
        RECOILING
    }

    public enum RunState {
        STANDING,
        STANDING_SHOT,
        RUNNING,
        RUNNING_SHOT
    }

    public enum PortalState {
        OPENING,
        HOLD,
        CLOSING
    }

    public enum PortalColor {
        GREEN,
        PURPLE
    }
}
