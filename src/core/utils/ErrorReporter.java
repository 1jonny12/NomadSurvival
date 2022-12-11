package core.utils;

import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;

public class ErrorReporter {

    public void report(String... errorDescription){
        report(false, false,errorDescription);
    }

    public void report(boolean addStackTrace, boolean includeFullError, String... errorDescription){
        ConsoleCommandSender sender = Bukkit.getServer().getConsoleSender();
        sender.sendMessage("---------- [Monster War Error] ----------");
        sender.sendMessage(errorDescription);
        if (addStackTrace){
            for (String s : Util.STRING.getCallTree(includeFullError)){
                sender.sendMessage(s);

            }
        }
        sender.sendMessage("------------------------------");
    }

}
