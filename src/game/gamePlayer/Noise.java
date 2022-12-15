package game.gamePlayer;

import core.utils.Util;

public class Noise {

    private int movementNoise = 0;
    private int targetMovementNoise = 0;
    private int actionNoise = 0;
    private int targetActionNoise = 0;
    private int surroundingsNoise = 0;
    private int targetSurroundingsNoise = 0;

    public int getTotalNoise() {
        return movementNoise + actionNoise + surroundingsNoise;
    }

    public void updateNoise() {
        movementNoise += calculateNoiseLevel(targetMovementNoise, movementNoise);
        actionNoise += calculateNoiseLevel(targetActionNoise, actionNoise);
        surroundingsNoise += calculateNoiseLevel(targetSurroundingsNoise, surroundingsNoise);
    }

    public int calculateNoiseLevel(int target, int source) {
        if (source != target)
            return source < target ? 1 + (Util.NUMBER.differance(source, target) / 10) : -(1 + (Util.NUMBER.differance(source, target) / 10));

        return 0;
    }

    public void setMovementTargetNoise(int level) {
        targetMovementNoise = Math.min(level, 250);
    }

    public void setActionTargetNoise(int level) {
        actionNoise = Math.min(level, 250);
    }

    public void setSurroundingsTargetNoise(int level) {
        surroundingsNoise = Math.min(level, 250);
    }

}
