package core.utils;

import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Util_Player {

    public List<String> getOnlinePlayerNameList(boolean excludeOp) {
        ArrayList<String> names = new ArrayList<>();

        for (Player p : Bukkit.getOnlinePlayers()) {
            if (excludeOp && p.isOp()) {
                continue;
            }
            names.add(p.getName());
        }

        return names;
    }


    public int getItemAmount(Player p, Material material) {
        int Amount = 0;
        for (ItemStack itemStack : p.getInventory().getContents()) {
            if (itemStack != null && itemStack.getType() == material) {
                Amount += itemStack.getAmount();
            }
        }
        return Amount;
    }

    public int removeItemAmount(Inventory inventory, ItemStack itemStack, int amount) {
        int toRemove = amount;

        for (int i = 0; i < inventory.getSize(); i++) {
            final ItemStack stack = inventory.getItem(i);
            if (stack == null || itemStack == null) {
                continue;
            }

            if (stack.getType() != itemStack.getType()) {
                continue;
            }

            if (!stack.getItemMeta().equals(itemStack.getItemMeta())) {
                continue;
            }

            if (toRemove >= stack.getAmount()) {
                toRemove -= stack.getAmount();
                inventory.clear(i);
            }
            else if (toRemove > 0) {
                stack.setAmount(stack.getAmount() - toRemove);
                toRemove = 0;
            }
            else {
                break;
            }
        }
        return amount - toRemove;
    }
}
