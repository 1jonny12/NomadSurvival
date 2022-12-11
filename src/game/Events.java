package game;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class Events implements Listener {

    @EventHandler
    public void Event_PlayerQuit(PlayerQuitEvent e) {
        NomadSurvival.G_PLAYER_MANAGER.handleLogout(e);
    }

    @EventHandler
    public void Event_EntityDamageByEntityEvent(EntityDamageByEntityEvent e) {
        NomadSurvival.AI_ENTITY_MANAGER.HandleDamageEvent(e);
    }

    @EventHandler
    public void  Event_EntitySpawnEvent(EntitySpawnEvent e){
        e.setCancelled(!e.getEntity().isCustomNameVisible());

    }


}
