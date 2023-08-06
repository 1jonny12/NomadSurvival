package core.builders;

import core.utils.Util;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;

public class ItemBuilderEvents {

    private static ItemBuilderEvents itemBuilderEvents = null;

    public static ItemBuilderEvents inst(){
        if (itemBuilderEvents == null){
            itemBuilderEvents = new ItemBuilderEvents();
        }
        return itemBuilderEvents;
    }

    public void handelEvent(InventoryClickEvent e){
        if (Util.NBT_MANAGER.hasTag(e.getCurrentItem(), ItemBuilder.NO_INV_TAKE_NBT)) {
            e.setCancelled(true);
        }

        if (Util.NBT_MANAGER.hasTag(e.getCurrentItem(), ItemBuilder.INV_REMOVE_WHEN_TAKE)) {
            e.getCurrentItem().setAmount(0);
        }
    }

    public void handleEvent(PlayerDropItemEvent e){
        if (Util.NBT_MANAGER.hasTag(e.getItemDrop().getItemStack(), ItemBuilder.NO_DROPPING_NBT)) {
            e.setCancelled(true);
        }
    }

    public void handleEvent(EntityPickupItemEvent e){
        if (Util.NBT_MANAGER.hasTag(e.getItem().getItemStack(), ItemBuilder.NO_GROUND_PICKUP_NBT)) {
            e.setCancelled(true);
        }
    }

}
