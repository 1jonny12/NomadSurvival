package game;

import core.utils.Task;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.LeavesDecayEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.player.PlayerLoginEvent;

public class SecureMode implements Listener {
    //A secure class that will always load, this class is loaded before other class of the plugin.

    //NOTE::: some code in the class maybe used with secure mode enabled or disabled.

    //If the plugin is in secure mode.
    private boolean isSecureMode = false;
    private String secureModeReason = "";


    public void enableSecureMode(String reason){
        isSecureMode = true;
        secureModeReason = reason;
    }


    @EventHandler
    public void event_LeavesDecayEvent(LeavesDecayEvent e) {
            e.setCancelled(true);
    }

    @EventHandler
    public void event_MobSpawnEvent(EntitySpawnEvent e) {
        if (!(e.getEntity() instanceof Player)){
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void event_Login(PlayerLoginEvent e) {

        if (!isSecureMode) return;

        Player p = e.getPlayer();

        if (p.isOp()){
            Task.delay(20, () -> {
            p.sendMessage(ChatColor.GRAY + "===================================");
            p.sendMessage(ChatColor.GRAY + "===================================");
            p.sendMessage(ChatColor.RED + "Warning plugin running in secure mode");
            p.sendMessage(ChatColor.RED + "Reason: " + secureModeReason);
            p.sendMessage(ChatColor.GRAY + "===================================");
            p.sendMessage(ChatColor.GRAY + "===================================");
            });
        }else {
            p.kickPlayer(ChatColor.RED + "A fatal server error has occurred. We aim to get the server back up and running as fast as we can. Sorry for any inconvenience. ");
        }

    }


}
