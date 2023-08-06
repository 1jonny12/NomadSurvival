package game.gamePlayer;

import core.utils.Task;
import core.utils.Util;
import net.minecraft.network.protocol.Packet;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerMoveEvent;

public class GPlayer extends PlayerWrapper {

    private final GPlayerLoadAndSave loadAndSave;
    private Task GPlayerTickTask;

    private final static int DATA_SAVE_INTERVAL = 3600;
    private int nextDataSave = 0;

    public GPlayer(Player p) {
        super(p);
    }

    {
        startGPlayerTick();
        loadAndSave = new GPlayerLoadAndSave(this);
        loadAndSave.load();

    }

    PlayerMovingState playerMovingState = PlayerMovingState.STANDING_STILL;
    private long firstJoinTimestamp;
    private Noise noise = new Noise();
    private static final double maxSmell = 100;
    private double smell = 0;
    private boolean undeadNameActivityEnabled = true;


    private void startGPlayerTick() {
        GPlayerTickTask = Task.repeat(5, () -> {
            if (!isOnline()) {
                GPlayerTickTask.stop();
            }

            nextDataSave--;
            if (nextDataSave < DATA_SAVE_INTERVAL) {
                nextDataSave = DATA_SAVE_INTERVAL;
                loadAndSave.save();
            }

            updatePlayerSmell();
            noise.updateNoise();

            sendActionBar("Noise = " + noise.getTotalNoise() + " Smell = " + Util.NUMBER.round(smell, 4));
            playerMovingState = PlayerMovingState.STANDING_STILL;
            noise.setMovementTargetNoise(0);
        });
    }

    public void updatePlayerSmell(){
        double change = 0.001;

        //Update smell due to movement.
        switch (playerMovingState){
            case SNEAK_WALKING -> change = 0.01;
            case WALKING -> change = 0.02;
            case SPRINTING -> change = 0.03;
        }
        smell = Math.min(maxSmell, smell + change);
    }

    public void managePlayerMovingState(PlayerMoveEvent e) {
        Location f = e.getFrom();
        Location t = e.getTo();

        if (f.getX() != t.getX() || f.getY() != t.getY() || f.getZ() != t.getZ()) {
            if (isSneaking()) {
                playerMovingState = PlayerMovingState.SNEAK_WALKING;
                noise.setMovementTargetNoise(10);
            } else if (isSprinting()) {
                playerMovingState = PlayerMovingState.SPRINTING;
                noise.setMovementTargetNoise(200);
            } else {
                playerMovingState = PlayerMovingState.WALKING;
                noise.setMovementTargetNoise(100);
            }

        } else {
            playerMovingState = PlayerMovingState.STANDING_STILL;
            noise.setMovementTargetNoise(0);
        }

    }

    public void sendPacket(Packet<?> packet){
       this.getHandle().connection.send(packet);
    }


    //----------------------------------------------------
    // [Default] -> Getters and Setters
    //----------------------------------------------------


    public boolean isUndeadNameActivityEnabled() {
        return undeadNameActivityEnabled;
    }

    public void setUndeadNameActivityEnabled(boolean undeadNameActivityEnabled) {
        this.undeadNameActivityEnabled = undeadNameActivityEnabled;
    }

    public double getSmell() {
        return smell;
    }

    public double getMaxSmell() {
        return maxSmell;
    }

    public Noise getNoise() {
        return noise;
    }

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
