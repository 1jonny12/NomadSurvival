package game.customitems.items;

import core.builders.ItemBuilder;
import game.customitems.CustomItem;
import game.customitems.Functions.Function_Clickable;
import game.customitems.Functions.Function_Lore;
import game.customitems.ICustomItem;
import org.bukkit.Bukkit;
import org.bukkit.Material;

import javax.swing.*;
import java.util.ArrayList;

public class TestItem extends CustomItem implements Function_Clickable, Function_Lore {
    @Override
    public int getModelID() {
        return 0;
    }

    @Override
    public Material getType() {
        return Material.DIAMOND;
    }

    @Override
    public String getDisplayName() {
        return "TestItem";
    }

    @Override
    public void onRightClick() {
        Bukkit.broadcastMessage("Right clicked");
    }

    @Override
    public void onLeftClick() {
        Bukkit.broadcastMessage("left clicked");
    }

    @Override
    public ArrayList<String> getLore() {
        ArrayList<String> lore = new ArrayList<>();
        lore.add("This is a lore1");
        lore.add("This is a lore2");
        return lore;
    }
}
