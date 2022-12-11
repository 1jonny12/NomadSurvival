/*
 * Copyright © 2019 - All Rights Reserved
 * This file belongs to Jonothan Ogden, Zac Malone
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 */

package core.builders.gui;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class GUI {

    private final Inventory guiInventory;
    private final ArrayList<GUI_Item> clickableItems;

    public GUI(Inventory guiInventory, ArrayList<GUI_Item> clickableItems) {
        this.guiInventory = guiInventory;
        this.clickableItems = clickableItems;
    }

    //Return if to cancel the event or not.
    boolean processClick(Player p, ItemStack itemClicked, InventoryClickEvent e) {

        if ((itemClicked == null || itemClicked.getType() == Material.AIR)) {
            return true;
        }

        for (GUI_Item items : clickableItems) {
            if (doesCompare(items.getItem(), itemClicked)) {
                try {
                   return items.onClick(p, e);
                } catch (Exception ex) {
                    ex.printStackTrace();
                    return true;

                }
            }
        }

        return false;
    }

    private boolean doesCompare(ItemStack i1, ItemStack i2) {

        if (i1 == null || i2 == null) {
            return false;
        }

        Material m1 = i1.getType();
        Material m2 = i2.getType();

        if (m1 != m2) {
            return false;
        }

        ItemMeta i1m = i1.getItemMeta();
        ItemMeta i2m = i2.getItemMeta();

        if (i1m == null || i2m == null) {
            return true;
        }

        if (i1m.hasDisplayName() && i2m.hasDisplayName()) {
            String d1 = i1m.getDisplayName();
            String d2 = i2m.getDisplayName();

            return d1.equalsIgnoreCase(d2);
        }

        if (i1m.hasLore() && i2m.hasLore()) {
            List<String> lore1 = i1m.getLore();
            List<String> lore2 = i2m.getLore();

            if (lore1 == null || lore2 == null) {
                return false;
            }

            for (String s : lore1) {
                if (!lore2.contains(s)) {
                    return false;
                }
            }

        }

        return true;

    }

    public void openGui(Player p) {
        p.openInventory(guiInventory);
    }

    //------------------------------------------------------------------------------------------------------------------------------
    // ##############################################################################################################################
    // Default - Getters and Setters
    //##############################################################################################################################
    //------------------------------------------------------------------------------------------------------------------------------

    public Inventory getGuiInventory() {
        return guiInventory;
    }

}
