package game.customitems.items;

import game.customitems.CustomItem;
import org.bukkit.Material;

public class Nothing_Item extends CustomItem {

    @Override
    public int getModelID() {
        return 0;
    }

    @Override
    public Material getType() {
        return Material.AIR;
    }

    @Override
    public String getDisplayName() {
        return "Nothing ";
    }

    @Override
    public int getStackSize() {
        return 0;
    }


}
