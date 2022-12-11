/*
 * Copyright © 2019 - All Rights Reserved
 * This file belongs to Jonothan Ogden, Zac Malone
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 */

package core.builders.gui;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.UUID;

public class GuiBuilderManager {

    private final HashMap<UUID, GUI> activeGuis = new HashMap<>();

    void registerGUI(UUID guiID, GUI gui) {
        activeGuis.put(guiID, gui);
    }

    public void manageInventoryClose(InventoryCloseEvent e) {
        Inventory inventory = e.getInventory();

        if (inventory.getHolder() instanceof GUI_Holder gui_holder) {
            activeGuis.remove(gui_holder.guiID());
        }
    }

    public void manageGuiDrag(InventoryDragEvent e) {
        Inventory inventory = e.getInventory();

        if (inventory.getHolder() instanceof GUI_Holder gui_holder) {
            GUI gui = activeGuis.get(gui_holder.guiID());

            if (gui != null) {
                e.setCancelled(true);
            }


        }
    }

    public void manageGuiClick(InventoryClickEvent e) {
        Inventory inventory = e.getClickedInventory();
        if (e.getAction() == InventoryAction.MOVE_TO_OTHER_INVENTORY) {
            e.setCancelled(true);
        }
        if (inventory != null && inventory.getHolder() instanceof GUI_Holder gui_holder) {
            Player p = (Player) e.getWhoClicked();
            ItemStack itemClicked = e.getCurrentItem();

            GUI gui = activeGuis.get(gui_holder.guiID());

            if (gui != null) {
                e.setCancelled(gui.processClick(p, itemClicked, e));
            }


        }

    }



}
