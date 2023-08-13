package game.customitems.items;

import game.customitems.CustomItem;
import game.customitems.Functions.Function_Clickable;
import game.customitems.Functions.Function_Lore;
import org.bukkit.Material;
import org.bukkit.event.player.PlayerInteractEvent;

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
    public ArrayList<String> getLore() {
        ArrayList<String> lore = new ArrayList<>();
        lore.add("This is a lore1");
        lore.add("This is a lore2");
        return lore;
    }

    @Override
    public void onRightClick(PlayerInteractEvent e) {

    }

    @Override
    public void onLeftClick(PlayerInteractEvent e) {

    }
}
