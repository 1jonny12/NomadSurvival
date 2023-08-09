package game.commands;

import core.jcommandbuilder.JCommandManager;
import core.jcommandbuilder.StaticJTabProvider;
import game.NomadSurvival;
import game.customitems.ItemType;

public class CommandRegister {

    {
        registerTabProviders();
        registerCommands();
    }

    public void registerTabProviders() {
        JCommandManager.getInstance().registerTabProvider("itemtype-enum", ItemType.NOTHING);
        JCommandManager.getInstance().registerTabProvider("itemtype-yes/no", new StaticJTabProvider("yes", "no"));
        JCommandManager.getInstance().registerTabProvider("OnlineGPlayers", NomadSurvival.G_PLAYER_MANAGER);
    }

    public void registerCommands(){
        JCommandManager.getInstance().registerAnnotationCommand(new Command_Dev());
        JCommandManager.getInstance().registerAnnotationCommand(new Command_Surx());
        JCommandManager.getInstance().registerAnnotationCommand(new Command_CustomItem());
    }



}
