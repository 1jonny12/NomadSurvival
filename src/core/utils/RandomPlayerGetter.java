package core.utils;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class RandomPlayerGetter {

    private final ArrayList<Player> Players = new ArrayList<>();

    private final ArrayList<Player> PlayersTempCache = new ArrayList<>();

    public RandomPlayerGetter() {
        Players.addAll(Bukkit.getOnlinePlayers());
    }

    public RandomPlayerGetter filterOut_GameMode(final GameMode gameMode) {
        for (final Player player : Players) {
            if (player.getGameMode() != gameMode) {
                PlayersTempCache.add(player);
            }
        }

        update();
        return this;
    }

    public RandomPlayerGetter filterOut_GameMode(final GameMode... gameModes) {
        for (GameMode gameMode : gameModes) {
            filterOut_GameMode(gameMode);
        }
        return this;

    }

    public RandomPlayerGetter filterOut_Operator() {
        for (final Player player : Players) {
            if (!player.isOp()) {
                PlayersTempCache.add(player);
            }
        }

        update();
        return this;
    }

    public RandomPlayerGetter filterOut_Range(Location locationFrom, int squareRange) {
        for (final Player player : Players) {
            if (player.getLocation().distanceSquared(locationFrom) < squareRange) {
                PlayersTempCache.add(player);
            }
        }

        update();
        return this;
    }

    public RandomPlayerGetter filterOut_Permision(String perm) {
        for (final Player player : Players) {
            if (!player.hasPermission(perm)) {
                PlayersTempCache.add(player);
            }
        }

        update();
        return this;
    }

    private void update() {
        Players.clear();
        Players.addAll(PlayersTempCache);
        PlayersTempCache.clear();
    }

    public boolean hasRandomPlayer() {
        return Players.size() >= 1;
    }

    public Player getRandomPlayer() {

        if (!hasRandomPlayer()) {
            throw new RandomPlayerGetterException("Tried to get a random player without available player.");
        }

        final Player random = Players.get(Util.RANDOM.randomInt(0, Players.size() - 1));
        Players.clear();
        PlayersTempCache.clear();
        return random;
    }

    public class RandomPlayerGetterException extends RuntimeException {

        public RandomPlayerGetterException(final String Info) {
            super(Info);
        }

    }
}
