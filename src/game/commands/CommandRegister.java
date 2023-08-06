package game.commands;

import core.jcommandbuilder.JCommandManager;
import game.NomadSurvival;

public class CommandRegister {

    {registerCommands();}

    public void registerCommands(){
        JCommandManager.getInstance().registerAnnotationCommand(new Command_Dev());
    }

}
