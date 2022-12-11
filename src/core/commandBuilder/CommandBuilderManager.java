/*
 * Copyright © 2019 - All Rights Reserved
 * This file belongs to Jonothan Ogden, Zac Malone
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 */

package core.commandBuilder;

import core.utils.Util;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;
import org.bukkit.command.PluginCommand;

import java.lang.reflect.Field;

public class CommandBuilderManager {

    public CommandBuilderManager() {
        init();
    }

    private static CommandMap commandMap;

    static void RegisterCommandWithCommandMap(PluginCommand pluginCommand) {
        commandMap.register("", pluginCommand);
    }

    private void init() {
        try {
            Bukkit.getServer();
            final Field bukkitCommandMap = Bukkit.getServer().getClass().getDeclaredField("commandMap");
            bukkitCommandMap.setAccessible(true);
            commandMap = (CommandMap) bukkitCommandMap.get(Bukkit.getServer());
        } catch (Exception e) {
            Util.ERROR_REPORTER.report("Error getting CommandMap. MinevadeCore is unable to register any commands.");
        }
    }

}
