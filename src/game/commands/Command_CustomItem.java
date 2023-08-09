package game.commands;

import core.jcommandbuilder.JArgument.JArg;
import core.jcommandbuilder.JArgument.JArgument_Int;
import core.jcommandbuilder.JArgument.JArgument_String;
import core.jcommandbuilder.*;
import game.NomadSurvival;
import game.customitems.CustomItem;
import game.customitems.ItemType;
import game.gamePlayer.GPlayer;
import game.gamePlayer.GPlayerManager;
import org.bukkit.Bukkit;

public class Command_CustomItem {

    @JCommand("CustomItem")
    @JSubCommand("give")
    @JArg(name = "player", type = JArgument_String.class, tabProvider = "OnlineGPlayers")
    @JArg(name = "custom_item", type = JArgument_String.class, tabProvider = "itemtype-enum")
    @JArg(name = "amount", type = JArgument_Int.class)
    @JCommandPermission("CustomItem.Give")
    public void giveCustomItem(JCommandSender jCommandSender, String arg1, String arg2, int arg3) {
        GPlayer p = NomadSurvival.G_PLAYER_MANAGER.getGPlayer(Bukkit.getPlayer(arg1));
        CustomItem item  = ItemType.getItemTypeByName(arg2).toCustomItem();
        p.giveCustomItem(item, arg3);
        jCommandSender.sendMessage("Gave " + arg1 + " " + arg3 + " " + arg2);
    }


}
