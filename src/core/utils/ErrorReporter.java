package core.utils;

import game.gamePlayer.GPlayer;
import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;

public class ErrorReporter {

    public void reportToConsole(String... errorDescription){
        reportToConsole(false, false,errorDescription);
    }

    public void reportToConsole(boolean addStackTrace, boolean includeFullError, String... errorDescription){
        ConsoleCommandSender sender = Bukkit.getServer().getConsoleSender();
        sender.sendMessage("---------- [Monster War Error] ----------");
        sender.sendMessage(errorDescription);
        if (addStackTrace){
            for (String s : Util.STRING.getCallTree(includeFullError)){
                sender.sendMessage(s);
            }
        }
        sender.sendMessage("--------------------------------------");
    }

    public void reportToPlayer(GPlayer gPlayer, String... errorDescription){
        reportToPlayer(gPlayer, false, false,errorDescription);
    }

    public void reportToPlayer(GPlayer gPlayer, boolean addStackTrace, boolean includeFullError, String... errorDescription){
        gPlayer.sendMessage("---------- [Space Travelers Error] ----------");
        gPlayer.sendMessage(errorDescription);
        if (addStackTrace){
            for (String s : Util.STRING.getCallTree(includeFullError)){
                gPlayer.sendMessage(s);
            }
        }
        gPlayer.sendMessage("------------------------------");
    }
}
