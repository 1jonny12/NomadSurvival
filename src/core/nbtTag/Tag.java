package core.nbtTag;

import net.minecraft.nbt.NBTTagCompound;
import org.bukkit.craftbukkit.v1_19_R1.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nullable;

public class Tag {
    // Nms Mappings Info
    /*

     *
     * https://minidigger.github.io/MiniMappingViewer/#/mojang/client/1.18.2/CompoundTag
     * https://minidigger.github.io/MiniMappingViewer/#/mojang/client/1.18.2/net.minecraft.world.item.ItemStack
     *
     * tagCompound.e = HasUUID
     * NMSItem.t() = HasTag
     * NMSItem.u() = GetTag
     * MSItem.c() = SetTag
     */

    private final String tagID;
    private final TagType tagType;

    public Tag(String tagID, TagType tagType) {
        this.tagID = tagID;
        this.tagType = tagType;
    }

    public boolean isOnAny(ItemStack... itemStacks) {
        for (ItemStack itemStack : itemStacks) if (isOn(itemStack)) return true;
        return false;
    }

    public boolean isOn(ItemStack itemStack) {
        return getTag(itemStack) != null;
    }

    protected void addTag(Object data, ItemStack itemStack) {
        checkTagData(data);

        net.minecraft.world.item.ItemStack NMSItem = CraftItemStack.asNMSCopy(itemStack);
        NBTTagCompound tagCompound = NMSItem.t() ? NMSItem.u() : new NBTTagCompound();

        if (tagCompound == null) tagCompound = new NBTTagCompound();

        switch (tagType) {
            case INT -> tagCompound.a(tagID, (Integer) data);
            case STRING -> tagCompound.a(tagID, (String) data);
            case DOUBLE -> tagCompound.a(tagID, (Double) data);
            case BYTE -> tagCompound.a(tagID, (Byte) data);
            case BOOLEAN -> tagCompound.a(tagID, (Boolean) data);
        }

        NMSItem.c(tagCompound);

        ItemStack temp = CraftItemStack.asBukkitCopy(NMSItem);
        itemStack.setItemMeta(temp.getItemMeta());
    }

    /**
     * @return Null if no tag is found.
     */
    @Nullable
    protected Object getTag(ItemStack itemStack) {
        net.minecraft.world.item.ItemStack NMSItem = CraftItemStack.asNMSCopy(itemStack);
        NBTTagCompound tagCompound = NMSItem.t() ? NMSItem.u() : new NBTTagCompound();

        if (tagCompound == null || !tagCompound.e(tagID)) {
            return null;
        }

        switch (tagType) {
            case INT -> {
                return tagCompound.h(tagID);
            }
            case STRING -> {
                return tagCompound.l(tagID);
            }
            case DOUBLE -> {
                return tagCompound.k(tagID);
            }
            case BYTE -> {
                return tagCompound.f(tagID);
            }
            case BOOLEAN -> {
                return tagCompound.q(tagID);
            }

        }
        return null;
    }

    private void checkTagData(Object tagData) {
        if (tagData == null) {
            throw new NbtTagException("Null NBT Tag");
        } else if (tagData.getClass() != tagType.getClassType()) {
            throw new NbtTagException("Tag data does not match. Expected -> " + tagType.getClassType().getName() + " Received -> " + tagData.getClass().getName());
        }

    }
}
