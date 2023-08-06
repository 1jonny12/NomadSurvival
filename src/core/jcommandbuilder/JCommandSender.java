package core.jcommandbuilder;

import core.utils.Util;
import game.gamePlayer.GPlayer;
import jline.internal.Nullable;
import org.bukkit.command.ConsoleCommandSender;

import java.util.List;

public class JCommandSender {

    private final ConsoleCommandSender consoleCommandSender;
    private final GPlayer gPlayer;

    public JCommandSender(ConsoleCommandSender consoleCommandSender, GPlayer gPlayer) {
        this.consoleCommandSender = consoleCommandSender;
        this.gPlayer = gPlayer;
    }

    public boolean isGPlayer(){
        return gPlayer != null;
    }

    public boolean isConsole(){
        return consoleCommandSender != null ;
    }

    @Nullable
    public GPlayer asGPlayer(){
        return gPlayer;
    }

    @Nullable
    public ConsoleCommandSender asConsole(){
        return consoleCommandSender;
    }

    public void sendMessage(String message){
        if (isGPlayer()) asGPlayer().sendMessage(message);
        if (isConsole()) asConsole().sendMessage(Util.STRING.formatString(message));
    }

    public void sendMessage(String[] messages){
        for (String s : messages) sendMessage(s);
    }

    public void sendMessage(List<String>messages){
        for (String s : messages) sendMessage(s);
    }

}
