package game.customitems;

import core.builders.ItemBuilder;
import core.utils.Util;
import game.NomadSurvival;
import game.customitems.Functions.Function_Lore;
import game.gamePlayer.GPlayer;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public abstract class CustomItem {

    public abstract int getModelID();
    public abstract String getDisplayName();

    public String getUniqueID() {
        return this.getClass().getName();
    }

    public Material getType() {
        return Material.GOLDEN_HOE;
    }

    public boolean isNothing() {
        return this == ItemType.NOTHING.toCustomItem();
    }

    public ItemBuilder toItemBuilder() {
        ItemBuilder builtItem = new ItemBuilder(getType());

        builtItem.setDisplayName(getDisplayName());
        builtItem.setModelData(getModelID());
        builtItem.addNbtTagsToAdd(CustomItemManager.CUSTOM_ITEM_IDENTIFIER.toValued(getUniqueID()));

        if (this instanceof Function_Lore) {
            builtItem.setLore(((Function_Lore) this).getLore());
        }

        // lay out other possible functions of items here...

        return builtItem;
    }

    public ItemStack toItemStack() {
        return toItemBuilder().build();
    }
}
