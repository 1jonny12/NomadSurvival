package game.customitems.Functions;

import org.bukkit.event.player.PlayerInteractEvent;

public interface Function_Clickable {
    public void onRightClick(PlayerInteractEvent e);
    public void onLeftClick(PlayerInteractEvent e);
}
