package core.nbtTag;

import org.bukkit.inventory.ItemStack;

public class NbtTag_Marker extends Tag {


    public NbtTag_Marker(String tagID) {
        super(tagID, TagType.BOOLEAN);
    }

    public void add(ItemStack... itemStacks){
       for (ItemStack itemStack : itemStacks) add(itemStack);
    }

    public void add(ItemStack itemStack){
        addTag(true, itemStack);
    }
}
