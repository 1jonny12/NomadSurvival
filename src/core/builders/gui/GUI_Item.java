/*
 * Copyright © 2019 - All Rights Reserved
 * This file belongs to Jonothan Ogden, Zac Malone
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 */

package core.builders.gui;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public abstract class GUI_Item {

    private final int slot;
    private final ItemStack item;
    private final boolean canTake;

    public GUI_Item(int slot, ItemStack item, boolean canTake) {
        this.slot = slot;
        this.item = item;
        this.canTake = canTake;
    }

    public GUI_Item(int slot, ItemStack item) {
        this.slot = slot;
        this.item = item;
        canTake = false;
    }

    //Return if to cancel the event or not.
    public abstract boolean onClick(Player p, InventoryClickEvent e);
    ItemStack getItem() {
        return item;
    }

    //------------------------------------------------------------------------------------------------------------------------------
    // ##############################################################################################################################
    // Default - Getters and Setters
    //##############################################################################################################################
    //------------------------------------------------------------------------------------------------------------------------------

    public int getSlot() {
        return slot;
    }


    public boolean isCanTake() {
        return canTake;
    }
}
