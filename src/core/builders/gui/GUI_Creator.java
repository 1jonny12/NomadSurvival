/*
 * Copyright © 2019 - All Rights Reserved
 * This file belongs to Jonothan Ogden, Zac Malone
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 */

package core.builders.gui;


import RPGPRISON.RpgPrison;
import core.utils.Util;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.UUID;

public class GUI_Creator {

    private final String name;
    private final int size;

    private final ArrayList<GUI_Item> items = new ArrayList<>();

    public GUI_Creator(String name, int size) {
        this.name = name;
        this.size = size;
    }

    public void AddItem(GUI_Item gui_item) {
        items.add(gui_item);
    }

    public boolean hasSlotAssigned(int slot){
        for (GUI_Item gui_item : items){
            if (gui_item.getSlot() == slot){
                return true;
            }
        }

        return false;
    }

    public void AddItem(int inventorySlot, ItemStack itemStack) {
        items.add(new GUI_Item(inventorySlot, itemStack) {
            @Override
            public boolean onClick(Player p, InventoryClickEvent e) {
                return true;
            }
        });
    }

    public GUI_Creator AddItems(int[] inventorySlots, ItemStack itemStack) {
        for (int slots : inventorySlots) {
            AddItem(slots, itemStack);
        }

        return this;
    }

    public GUI BuildGui() {
        UUID guiID = UUID.randomUUID();
        Inventory inventory = Bukkit.createInventory(new GUI_Holder(guiID), size, Util.STRING.formatString(name));

        for (GUI_Item gui_item : items) {
            inventory.setItem(gui_item.getSlot(), gui_item.getItem());
        }

        GUI gui = new GUI(inventory, items);
        RpgPrison.GUI_BUILDER_MANAGER.registerGUI(guiID, gui);
        return gui;
    }


    public void fillAir(Material material) {
        for (int i = 0; i < size; i++) {
            if (!hasSlotAssigned(i)) {
                AddItem(i, new ItemStack(material));
            }
        }
    }

    // --------------------------------
    // Default -> Getters and Setters
    // --------------------------------

}
