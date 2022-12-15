/*
 * Copyright © 2019 - All Rights Reserved
 * This file belongs to Jonothan Ogden, Zac Malone
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 */

package core.commandBuilder.Arg.CoreArgs;


import game.NomadSurvival;
import game.gamePlayer.GPlayer;
import core.commandBuilder.Arg.ArgBase;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

public class Arg_PlayerByName extends ArgBase {

    private static final String argName = "Player Name";
    private GPlayer argValue;

    public Arg_PlayerByName() {
        super(argName);
    }

    public Arg_PlayerByName(String... tabsForArg) {
        super(argName, tabsForArg);
    }

    public <E extends Enum<E>> Arg_PlayerByName(Class<E> enumTabs) {
        super(argName, enumTabs);
    }

    @SuppressWarnings("deprecation")
    @Override
    public boolean ValidateArgument(GPlayer gPlayer, String argString) {

        if (Bukkit.getPlayer(argString) != null) {
            Player p = Bukkit.getPlayer(argString);

            if (p != null) {
                argValue = NomadSurvival.G_PLAYER_MANAGER.getGPlayer(p);
            }

            return true;
        }

        OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(argString);

        if (offlinePlayer.getPlayer() != null) {
            argValue = NomadSurvival.G_PLAYER_MANAGER.getGPlayer(offlinePlayer.getPlayer());
            return true;
        }


        gPlayer.sendMessage("&7[" + getArgName() + "] &cUnable to find a player with the name: " + argString);
        return false;
    }


    @Override
    public Object getArgValue() {
        return argValue;
    }
}
