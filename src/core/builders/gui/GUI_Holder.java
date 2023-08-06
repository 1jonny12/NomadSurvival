/*
 * Copyright © 2019 - All Rights Reserved
 * This file belongs to Jonothan Ogden, Zac Malone
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 */

package core.builders.gui;

import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

import java.util.UUID;

public record GUI_Holder(UUID guiID) implements InventoryHolder {

    //----------------------------------------------------
    // [Default] -> Getters and Setters
    //----------------------------------------------------

    @Override
    public Inventory getInventory() {
        return Bukkit.createInventory(this, 9);
    }
}
