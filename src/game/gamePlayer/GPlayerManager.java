package game.gamePlayer;

import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.HashMap;
import java.util.UUID;

public class GPlayerManager {
    private static final HashMap<UUID, GPlayer> LOADED_G_PLAYER = new HashMap<>();

    public GPlayer getGPlayer(Player p){
        UUID playerUUID = p.getUniqueId();
        if (LOADED_G_PLAYER.containsKey(playerUUID)){
            return LOADED_G_PLAYER.get(playerUUID);
        }

        GPlayer GPlayer = new GPlayer( p);
        LOADED_G_PLAYER.put(playerUUID, GPlayer);
        return GPlayer;
    }

    public void saveAllGPlayers() {
        forAllGPlayer(gPlayer -> {gPlayer.getLoadAndSave().save();});
    }

    public void saveGPlayer(Player p){
        LOADED_G_PLAYER.get(p.getUniqueId()).getLoadAndSave().save();
    }


    public void handleLogout(PlayerQuitEvent e) {
        saveGPlayer(e.getPlayer());
        LOADED_G_PLAYER.remove(e.getPlayer().getUniqueId());
    }

    public void handleLogin(PlayerJoinEvent e) {
       getGPlayer(e.getPlayer());
    }


    public void handlePlayerMove(PlayerMoveEvent e){
        forAllGPlayer((gPlayer) -> gPlayer.managePlayerMovingState(e));
    }

    public void forAllGPlayer(ForAllGPlayer forAllGPlayer){
         for (GPlayer gPlayer : LOADED_G_PLAYER.values()){
             forAllGPlayer.run(gPlayer);
         }
    }

    public interface ForAllGPlayer{
      void run(GPlayer gPlayer);
    }

}
