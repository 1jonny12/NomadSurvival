/*
 * Copyright © 2019 - All Rights Reserved
 * This file belongs to Jonothan Ogden, Zac Malone
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 */

package core.builders;

import core.nbtTag.NbtManager;
import core.nbtTag.NbtTagValued;
import core.nbtTag.NbtTag_Identifier;
import core.utils.Util;

import game.NomadSurvival;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class ItemBuilder {

    public static NbtTag_Identifier NO_GROUND_PICKUP_NBT = new NbtTag_Identifier("ItemNoGroundPickup");
    public static NbtTag_Identifier NO_INV_TAKE_NBT = new NbtTag_Identifier("ItemNoInvTake");
    public static NbtTag_Identifier NO_DROPPING_NBT = new NbtTag_Identifier("ItemNoDropping");
    public static NbtTag_Identifier INV_REMOVE_WHEN_TAKE = new NbtTag_Identifier("InvRemoveWhenTake");

    private final static Material[] LEATHER_ARMOUR = new Material[]{Material.LEATHER_BOOTS, Material.LEATHER_CHESTPLATE, Material.LEATHER_LEGGINGS,
            Material.LEATHER_HELMET};

    private final ArrayList<Integer> LeatherAmourRGB = new ArrayList<>();
    private ArrayList<NbtTagValued> nbtTagsToAdd = new ArrayList<>();

    private int amount = 1;
    private Material material;
    private String displayName = "";
    private ArrayList<String> lore = new ArrayList<>();

    private boolean NotStackable = false;
    private boolean NoGroundPickup = false;
    private boolean NoInvTake = false;
    private boolean InvRemoveWhenClick = false;
    private boolean NoDropping = false;

    public ItemBuilder(final Material material) {
        this.material = material;
    }

    public ItemBuilder(final ItemStack itemStack) {
        material = itemStack.getType();
        amount = itemStack.getAmount();

        if (itemStack.hasItemMeta()) {
            final ItemMeta itemMeta = itemStack.getItemMeta();

            if (itemMeta != null) {
                if (itemMeta.hasLore()) {
                    lore = (ArrayList<String>) itemMeta.getLore();
                }

                if (itemMeta.hasDisplayName()) {
                    displayName = itemMeta.getDisplayName();
                }
            }
        }

        NbtManager nbtManager = Util.NBT_MANAGER;

        if (nbtManager.hasTag(itemStack, NO_GROUND_PICKUP_NBT)){
            NoGroundPickup = true;
        }

        if (nbtManager.hasTag(itemStack, NO_DROPPING_NBT)){
            NoDropping = true;
        }

        if (nbtManager.hasTag(itemStack, NO_INV_TAKE_NBT)){
            NoInvTake = true;
        }

        if (nbtManager.hasTag(itemStack, INV_REMOVE_WHEN_TAKE)){
            InvRemoveWhenClick = true;
        }
    }

    public ItemStack build() {

        ItemStack itemStack = new ItemStack(material, amount);
        final ItemMeta itemMeta = itemStack.getItemMeta();

        if (NotStackable) {
            lore.add(Util.STRING.toInvisible(String.valueOf(Util.RANDOM.randomInt(0, 999999999))));
        }

        if (NoGroundPickup) {
            nbtTagsToAdd.add(NO_GROUND_PICKUP_NBT.toValued(true));
        }

        if (NoInvTake) {
            nbtTagsToAdd.add(NO_INV_TAKE_NBT.toValued(true));
        }

        if (NoDropping) {
            nbtTagsToAdd.add(NO_DROPPING_NBT.toValued(true));
        }

        if (InvRemoveWhenClick) {
            nbtTagsToAdd.add(INV_REMOVE_WHEN_TAKE.toValued(true));
        }

        if (itemMeta != null) {
            itemMeta.setLore(Util.STRING.formatStringList(lore));

            String name = Util.STRING.formatString(displayName);
            itemMeta.setDisplayName(name);

            itemMeta.setUnbreakable(true);
            itemMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
            itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
            itemMeta.addItemFlags(ItemFlag.HIDE_DESTROYS);
            itemMeta.addItemFlags(ItemFlag.HIDE_PLACED_ON);
            itemMeta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
            itemMeta.addItemFlags(ItemFlag.HIDE_DYE);

            itemStack.setItemMeta(itemMeta);
        }


        //Color leather armour.
        if (LeatherAmourRGB.size() == 3) {
            if (Arrays.asList(LEATHER_ARMOUR).contains(material)) {
                final LeatherArmorMeta leatherArmorMeta = (LeatherArmorMeta) itemStack.getItemMeta();
                leatherArmorMeta.setColor(Color.fromRGB(LeatherAmourRGB.get(0), LeatherAmourRGB.get(1), LeatherAmourRGB.get(2)));
                itemStack.setItemMeta(leatherArmorMeta);
            }
        }

        if (nbtTagsToAdd.size() > 0) {
           // Util.NBT_MANAGER.AddTagS(itemStack, nbtTagsToAdd);
        }


        return itemStack;
    }

    public ItemBuilder setDisplayName(final String DisplayName) {
        this.displayName = DisplayName;
        return this;
    }

    public ItemBuilder setNbtTagsToAdd(ArrayList<NbtTagValued> nbtTagsToAdd) {
        this.nbtTagsToAdd = nbtTagsToAdd;
        return this;
    }

    public ItemBuilder addNbtTagsToAdd(NbtTagValued nbtTagToAdd) {
        nbtTagsToAdd.add(nbtTagToAdd);
        return this;
    }

    public ItemBuilder setLore(final ArrayList<String> lore) {
        this.lore = lore;
        return this;
    }

    public ItemBuilder setLore(final String... Lore) {
        this.lore = new ArrayList<>(Arrays.asList(Lore));
        return this;
    }

    public ItemBuilder addLore(final String Line) {
        lore.add(Line);
        return this;
    }

    public ItemBuilder addLore(final String... Lines) {
        Collections.addAll(lore, Lines);
        return this;
    }


    public ItemBuilder insertLore(final ArrayList<String> Lines, int FromIndex) {
        lore.addAll(FromIndex, Lines);
        return this;
    }

    public ItemBuilder removeLore(int Index) {
        if (lore.size() - 1 >= Index) {
            lore.remove(Index);
        }
        return this;
    }

    public ItemBuilder removeLore(int... Indexs) {
        for (int index : Indexs) {
            removeLore(index);
        }
        return this;
    }

    public ItemBuilder setAmount(final int Amount) {
        this.amount = Amount;
        return this;
    }

    public ItemBuilder setNotStackable() {
        NotStackable = true;
        return this;
    }

    public ItemBuilder setGroundNoPickup() {
        NoGroundPickup = true;
        return this;
    }

    public ItemBuilder setNoInvTake() {
        NoInvTake = true;
        return this;
    }

    public ItemBuilder setInvRemoveWhenClick() {
        InvRemoveWhenClick = true;
        return this;
    }

    public ItemBuilder setNoDropping() {
        NoDropping = true;
        return this;
    }

    public ItemBuilder setLeatherArmorColor_RGB(final int R, final int G, final int B) {
        LeatherAmourRGB.clear();
        LeatherAmourRGB.add(R);
        LeatherAmourRGB.add(G);
        LeatherAmourRGB.add(B);
        return this;
    }

    public ItemBuilder setLeatherArmourColor_Color(final Color color) {
        return setLeatherArmorColor_RGB(color.getRed(), color.getGreen(), color.getBlue());
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

}
