package core.utils;
import game.NomadSurvival;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitTask;

public class Task {
    private static final Plugin PLUGIN = NomadSurvival.PLUGIN;
    private final Runnable runnable;
    private final boolean repeating;
    private int delay;
    private BukkitTask task;

    private Task(int delay, Runnable runnable, boolean repeating) {
        this.delay = delay;
        this.runnable = runnable;
        this.repeating = repeating;
        start();
    }

    public static Task repeat(int delay, Runnable runnable) {
        return new Task(delay, runnable, true);
    }

    public static Task delay(int delay, Runnable runnable) {
        return new Task(delay, runnable, false);
    }

    public void start() {
        if (task == null) {
            callTaskFromType();
        }
    }

    public void stop() {
        task.cancel();
        task = null;
    }

    public void restart() {
        stop();
        start();
    }

    public void changeDelay(final int newDelay) {
        if (repeating) {
            stop();
            delay = newDelay;
            start();
        }
    }

    private void callTaskFromType() {
        if (repeating) {
            task = Bukkit.getScheduler().runTaskTimer(PLUGIN, runnable, 1, delay);
        } else {
            task = Bukkit.getScheduler().runTaskLater(PLUGIN, runnable, delay);
        }
    }
}


