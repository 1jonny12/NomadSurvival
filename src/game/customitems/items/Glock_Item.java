package game.customitems.items;

import core.utils.RayTrace;
import core.utils.RayTraceResult;
import game.customitems.CustomItem;
import game.customitems.Functions.Function_Clickable;
import game.customitems.Functions.Function_Lore;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.util.Vector;

import java.util.ArrayList;

public class Glock_Item extends CustomItem implements Function_Lore, Function_Clickable {



    @Override
    public int getModelID() {
        return 0;
    }

    @Override
    public String getDisplayName() {
        return "Glock 17";
    }

    @Override
    public ArrayList<String> getLore() {
        return null;
    }

    @Override
    public void onRightClick(PlayerInteractEvent e) {
        Location startLocation = e.getPlayer().getEyeLocation();
        Vector rayDirection = e.getPlayer().getEyeLocation().getDirection();

        RayTrace rayTrace = new RayTrace();
        rayTrace.terminateOnBlockHit().terminateOnEntityHit();

        RayTraceResult result = rayTrace.castRay(startLocation, rayDirection);
    }

    @Override
    public void onLeftClick(PlayerInteractEvent e) {

    }



}
