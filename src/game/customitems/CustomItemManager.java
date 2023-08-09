package game.customitems;

import core.nbtTag.NbtTag;
import core.utils.Util;
import game.customitems.Functions.Function_Clickable;
import jline.internal.Nullable;
import org.bukkit.Bukkit;
import org.bukkit.entity.Item;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

import java.util.HashMap;

public class CustomItemManager {

    private HashMap<String, CustomItem> loadedCustomItems;
    protected static final NbtTag CUSTOM_ITEM_IDENTIFIER = new NbtTag("CustomItem", PersistentDataType.STRING);

    {
        init();
    }

    private void init() {
        loadedCustomItems = new HashMap<>();
        loadCustomItems();
    }

    public void handleItemListener(PlayerInteractEvent event) {
        CustomItem clickedItem = getCustomItem(event.getItem());

        if (clickedItem.isNothing()) {
            return;
        }

        if (!(clickedItem instanceof Function_Clickable clickable)) {
            return;
        }

        Action eventAction = event.getAction();

        if (eventAction == Action.LEFT_CLICK_AIR || eventAction == Action.LEFT_CLICK_BLOCK) {
            clickable.onLeftClick();
        } else if (eventAction == Action.RIGHT_CLICK_AIR || eventAction == Action.RIGHT_CLICK_BLOCK) {
            clickable.onRightClick();
        }
    }

    public void loadCustomItems() {
        for (ItemType itemType : ItemType.values()) {
            loadedCustomItems.put(itemType.toCustomItem().getUniqueID(), itemType.toCustomItem());
        }
    }

    public CustomItem getCustomItem(ItemStack itemStack) {
        Object nbtTagValue = Util.NBT_MANAGER.getTag(itemStack, CUSTOM_ITEM_IDENTIFIER);

        if (nbtTagValue == null) {
            return ItemType.NOTHING.toCustomItem();
        }

        for (CustomItem customItem : loadedCustomItems.values()) {
            if (customItem.getUniqueID().equals(nbtTagValue)) {
                return customItem;
            }
        }

        return ItemType.NOTHING.toCustomItem();
    }

    // getters

    public HashMap<String, CustomItem> getLoadedCustomItems() {
        return loadedCustomItems;
    }

}
