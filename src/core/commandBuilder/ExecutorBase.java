/*
 * Copyright © 2019 - All Rights Reserved
 * This file belongs to Jonothan Ogden, Zac Malone
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 */

package core.commandBuilder;

import core.commandBuilder.Arg.ArgBase;
import core.gamePlayer.GPlayer;
import org.bukkit.command.ConsoleCommandSender;

public class ExecutorBase {

    public void runCommandPlayer(GPlayer gPlayer, ArgBase[] args){
        gPlayer.sendMessage("Using default run command block.");
    }

    public void runCommandConsole(ConsoleCommandSender console, ArgBase[] args){
        console.sendMessage("Using default run command block.");
    }

}
