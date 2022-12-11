package core.nbtTag;

import org.bukkit.inventory.ItemStack;

import javax.annotation.Nullable;

public class NbtTag extends Tag {
    public NbtTag(String tagID, TagType tagType) {
        super(tagID, tagType);
    }

    public void add(Object data, ItemStack[] itemStacks, NbtTag[] otherTagsToAdd){
        for (ItemStack itemStack : itemStacks) add(data, itemStack, otherTagsToAdd);
    }

    public void add(Object data, ItemStack itemStack, NbtTag[] otherTagsToAdd){
        addTag(data, itemStack);
        for (NbtTag nbtTag : otherTagsToAdd) nbtTag.addTag(data, itemStack);
    }

    public void add(Object data, ItemStack... itemStacks){
        for (ItemStack itemStack : itemStacks) addTag(data, itemStack);
    }


    @Nullable
    public Integer getInt(ItemStack itemStack) {
        Object tagResult = getTag(itemStack);
        checkTagGetter(tagResult, TagType.INT, " get_Int()");
        return (Integer) tagResult;
    }

    @Nullable
    public String getString(ItemStack itemStack) {
        Object tagResult = getTag(itemStack);
        checkTagGetter(tagResult, TagType.STRING, " get_String()");
        return (String) tagResult;
    }

    @Nullable
    public Double getDouble(ItemStack itemStack) {
        Object tagResult = getTag(itemStack);
        checkTagGetter(tagResult, TagType.DOUBLE, " get_Double()");
        return (Double) tagResult;
    }

    @Nullable
    public Byte getByte(ItemStack itemStack) {
        Object tagResult = getTag(itemStack);
        checkTagGetter(tagResult, TagType.BYTE, " get_Byte()");
        return (Byte) tagResult;
    }

    @Nullable
    public Boolean getBoolean(ItemStack itemStack) {
        Object tagResult = getTag(itemStack);
        checkTagGetter(tagResult, TagType.BOOLEAN, " get_Boolean()");
        return (Boolean) tagResult;
    }

    private void checkTagGetter(Object tagData, TagType tagType, String getterType) {
        if (tagData.getClass() != tagType.getClassType()) {
            throw new NbtTagException("Incorrect tag getter for data type. Current Type: " + tagData.getClass().getName() + " Current Getter:" + getterType);
        }

    }

}
