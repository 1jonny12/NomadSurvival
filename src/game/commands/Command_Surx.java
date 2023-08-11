package game.commands;

import core.jcommandbuilder.*;
import core.jcommandbuilder.JArgument.JArg;
import core.jcommandbuilder.JArgument.JArgument_Int;
import core.jcommandbuilder.JArgument.JArgument_String;
import game.customitems.ItemType;
import org.bukkit.Bukkit;

import java.util.ArrayList;
import java.util.List;

public class Command_Surx {

    @JCommand("Surx")
    @JArg(name = "kill-msg", type=JArgument_String.class, tabProvider = "itemtype-enum")
    @JArg(name = "all", type=JArgument_String.class, tabProvider = "itemtype-yes/no")
    @JSubCommand("kill")
    @JCommandPermission("surx.surxinator")
    @JCommandPlayerOnly
    public void surxinator(JCommandSender jCommandSender, String arg, String arg2) {
        Bukkit.broadcastMessage("killed surxinator" + arg);
    }




}
