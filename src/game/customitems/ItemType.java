package game.customitems;

import core.jcommandbuilder.JTabProvider;
import game.customitems.items.Glock_Item;
import game.customitems.items.Nothing_Item;
import game.customitems.items.TestItem;

import java.util.ArrayList;
import java.util.List;

public enum ItemType implements ICustomItem, JTabProvider {

    TEST_ITEM(new TestItem()),
    Glock_Gun(new Glock_Item()),
    NOTHING(new Nothing_Item()); //air item

    private final CustomItem customItem;
    static ArrayList<String> tabs = new ArrayList<>();

    ItemType(CustomItem customItem) {
        this.customItem = customItem;
    }

    public static ItemType getItemTypeByName(String name) {
        for (ItemType itemType : ItemType.values()) {
            if (itemType.name().equals(name)) {
                return itemType;
            }
        }
        return NOTHING;
    }

    @Override
    public CustomItem toCustomItem() {
        return customItem;
    }

    @Override
    public List<String> getTabs() {
        if (tabs.isEmpty()) {
            for (ItemType itemType : ItemType.values()) {
                tabs.add(itemType.name());
            }
        }

        return tabs;
    }
}
