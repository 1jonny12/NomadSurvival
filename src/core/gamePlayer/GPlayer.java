package core.gamePlayer;

import org.bukkit.craftbukkit.v1_19_R1.entity.CraftZombie;
import org.bukkit.entity.Player;
import core.utils.Task;
import org.bukkit.inventory.meta.ItemMeta;

public class GPlayer extends PlayerWrapper {

    private final GPlayerLoadAndSave loadAndSave;
    private Task GPlayerTickTask;

    private final static int DATA_SAVE_INTERVAL = 900;
    private int nextDataSave = 0;

    public GPlayer(Player p) {
        super(p);
    }

    {
        loadAndSave = new GPlayerLoadAndSave(this);
        loadAndSave.load();
        startGPlayerTick();
    }

    private long firstJoinTimestamp;

    private void startGPlayerTick() {
        GPlayerTickTask = Task.repeat(20, () -> {
            if (!isOnline()) {
                GPlayerTickTask.stop();
            }

            nextDataSave--;
            if (nextDataSave < DATA_SAVE_INTERVAL) {
                nextDataSave = DATA_SAVE_INTERVAL;
                loadAndSave.save();
            }
        });

    }


    //----------------------------------------------------
    // [Default] -> Getters and Setters
    //----------------------------------------------------


    public long getFirstJoinTimestamp() {
        return firstJoinTimestamp;
    }

    public void setFirstJoinTimestamp(long firstJoinTimestamp) {
        this.firstJoinTimestamp = firstJoinTimestamp;
    }

    public GPlayerLoadAndSave getLoadAndSave() {
        return loadAndSave;
    }
}
