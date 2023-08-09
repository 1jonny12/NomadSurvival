/*
 * Copyright © 2019 - All Rights Reserved
 * This file belongs to Jonothan Ogden, Zac Malone
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 */

package core.nbtTag;

import game.NomadSurvival;
import jline.internal.Nullable;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;

import java.util.List;

public class NbtManager {

    public void AddTags(ItemMeta itemMeta, List<NbtTagValued> tags) {
        tags.forEach(nbtTagValued -> AddTag(itemMeta, nbtTagValued));
    }

    public void AddTag(ItemMeta itemMeta, NbtTagValued nbtTagValued) {
        PersistentDataContainer persistentDataContainer = itemMeta.getPersistentDataContainer();
        persistentDataContainer.set(new NamespacedKey(NomadSurvival.PLUGIN, nbtTagValued.uniqueIdentifier()), nbtTagValued.getPersistentDataType(), nbtTagValued.data());
    }

    public boolean hasTag(ItemStack itemStack, NbtTag nbtTag) {
        if (itemStack.getItemMeta() == null) return false;

        PersistentDataContainer persistentDataContainer = itemStack.getItemMeta().getPersistentDataContainer();
       return persistentDataContainer.has(new NamespacedKey(NomadSurvival.PLUGIN, nbtTag.uniqueIdentifier()), nbtTag.getPersistentDataType());
    }

    @Nullable
    public Object getTag(ItemStack itemStack, NbtTag nbtTag) {
        if (itemStack.getItemMeta() == null) {
            Bukkit.broadcastMessage("getTag():nbtmanager - itemmeta is null");
            return null;
        }
        PersistentDataContainer dataContainer = itemStack.getItemMeta().getPersistentDataContainer();
        Bukkit.broadcastMessage(dataContainer.getKeys().toString());
        return dataContainer.get(new NamespacedKey(NomadSurvival.PLUGIN, nbtTag.uniqueIdentifier()), nbtTag.getPersistentDataType());
    }

}
