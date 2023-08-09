package game;

import core.scoreboard.Scoreboard;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class Events implements Listener {

    @EventHandler
    public void Event_PlayerQuit(PlayerQuitEvent e) {
        NomadSurvival.G_PLAYER_MANAGER.handleLogout(e);
    }

    @EventHandler
    public void Event_PlayerJoin(PlayerJoinEvent e){
        NomadSurvival.G_PLAYER_MANAGER.handleLogin(e);
        NomadSurvival.SCOREBOARD_MANAGER.assignBoardToPlayer(Scoreboard.MAIN, e.getPlayer());
    }

    @EventHandler
    public void Event_EntityDamageByEntityEvent(EntityDamageByEntityEvent e) {
        NomadSurvival.AI_ENTITY_MANAGER.HandleDamageEvent(e);
    }

    @EventHandler
    public void  Event_EntitySpawnEvent(EntitySpawnEvent e){
        e.setCancelled(!e.getEntity().isCustomNameVisible());

    }

    @EventHandler
    public void Event_PlayerMoveEvent(PlayerMoveEvent e){
        NomadSurvival.G_PLAYER_MANAGER.handlePlayerMove(e);
    }

    @EventHandler
    public void PlayerInteractEvent(PlayerInteractEvent e) {
        NomadSurvival.CUSTOM_ITEM_MANAGER.handleItemListener(e);
    }


}
