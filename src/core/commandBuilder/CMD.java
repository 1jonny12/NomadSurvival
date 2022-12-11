/*
 * Copyright © 2019 - All Rights Reserved
 * This file belongs to Jonothan Ogden, Zac Malone
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 */

package core.commandBuilder;

import game.NomadSurvival;
import core.gamePlayer.GPlayer;
import core.utils.Util;
import core.commandBuilder.Arg.ArgBase;
import core.commandBuilder.Arg.CoreArgs.Arg_PlayerByName;
import core.commandBuilder.Arg.CoreArgs.Arg_String;
import org.apache.commons.lang.ArrayUtils;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class CMD implements TabCompleter, CommandExecutor {

    private static final boolean OpsHasAllCommandPerms = true;
    private static final boolean StarHasAllCommandPerms = true;

    private final String command;
    private final HashMap<String, SUB_CMD> subCommands = new HashMap<>();
    private ExecutorBase coreExecutorBase;
    private ArgBase[] coreArgBases = new ArgBase[]{new Arg_String("Sub Command")};
    private boolean coreCommandAsHelp = true;


    public CMD(String command) {
        this.command = command;
    }


    public CMD RegisterSubCommand(String SubCommand, ExecutorBase baseExecutor, ArgBase... argBases) {
        subCommands.put(SubCommand, new SUB_CMD(SubCommand, baseExecutor, argBases));
        return this;
    }

    public CMD RegisterCoreCommand(ExecutorBase baseExecutor, ArgBase... argBases) {
        coreExecutorBase = baseExecutor;
        coreArgBases = argBases;
        coreCommandAsHelp = false;
        return this;
    }

    public void RegisterCommand() {
        try {


            final Constructor<PluginCommand> constructor = PluginCommand.class.getDeclaredConstructor(String.class, Plugin.class);
            constructor.setAccessible(true);

            final PluginCommand pluginCommand = constructor.newInstance(command, NomadSurvival.PLUGIN);
            pluginCommand.setUsage("-");
            pluginCommand.setExecutor(this);
            pluginCommand.setTabCompleter(this);
            CommandBuilderManager.RegisterCommandWithCommandMap(pluginCommand);


            appendAllCoreTabs();
        } catch (final Exception e) {
            // MineVadeCore.getLogger().Error("fd42sdA2D", "Plugin was unable to register the command (/" + command + ")");
            e.printStackTrace();
        }
    }

    private void appendAllCoreTabs() {
        if (coreArgBases.length >= 1) {
            ArgBase argBase = coreArgBases[0];
            List<String> tabs = new ArrayList<>();
            tabs.addAll(subCommands.keySet());
            tabs.addAll(argBase.getTabsForArg());
            argBase.setTabsForArg(tabs);
        }
    }

    private boolean validateAllArgs(GPlayer gPlayer, ArgBase[] argBases, String[] argStrings, String subCommandUsed) {
        int argPosition = 0;

        for (final ArgBase args : argBases) {
            if (!args.Validate(gPlayer, argPosition, argStrings)) {
                gPlayer.sendMessage(getUseageString(argBases, subCommandUsed));
                return false;
            }

            argPosition++;
        }

        return true;
    }

    private boolean validatePerms(final GPlayer gPlayer, String perm) {

            if (OpsHasAllCommandPerms && gPlayer.isOp()) {
                return true;
            }

            if (StarHasAllCommandPerms && gPlayer.hasPermission("*")) {
                return true;
            }

            if (!gPlayer.hasPermission(perm)) {
               gPlayer.sendMessage( "&cYou do not have the correct permission to use this command.");
                return false;
            }


        return true;
    }

    private String getUseageString(ArgBase[] argBases, String subCommandUsed) {

        final StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("&cCommand Usage: &7/").append("&9");
        stringBuilder.append(command);
        stringBuilder.append(" ");

        if (!subCommandUsed.equalsIgnoreCase("NO_SUB_COMMAND")) {
            stringBuilder.append("^s");
            stringBuilder.append(subCommandUsed);
            stringBuilder.append(" ");
        }

        for (final ArgBase arg : argBases) {
            stringBuilder.append("&7(");
            stringBuilder.append(arg.getArgName());
            stringBuilder.append(") ");
        }

        return stringBuilder.toString();
    }

    private void sendHelpCommand(GPlayer gPlayer) {
        gPlayer.sendMessage("^m-=- " + command + " Utils.commandBuilder.Commands -=-");

        for (SUB_CMD sub_cmd : subCommands.values()) {

            StringBuilder commandSub = new StringBuilder("&7/" + command + "^s " + sub_cmd.getSubCommand() + " &8");

            for (ArgBase argBase : sub_cmd.getArgBases()) {
                commandSub.append("(").append(argBase.getArgName()).append(") ");
            }

            gPlayer.sendMessage(commandSub.toString());

        }
    }

    private SUB_CMD lookForSubCommand(String[] rawArgs) {
        if (rawArgs.length >= 1) {
            for (SUB_CMD subCommands : subCommands.values()){
                if (subCommands.getSubCommand().equalsIgnoreCase(rawArgs[0])){
                    return subCommands;
                }
            }
        }

        return null;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String commandString, String[] args) {
        SUB_CMD subCmd = lookForSubCommand(args);
        GPlayer gPlayer = NomadSurvival.G_PLAYER_MANAGER.getGPlayer((Player) commandSender);
        String perm = NomadSurvival.PLUGIN.getName() + "." + this.command;

        if (subCmd != null) {
            perm = perm + "." + subCmd.getSubCommand();
            onCommand(gPlayer, args[0], (String[]) ArrayUtils.remove(args, 0), subCmd.getArgBases(), perm, subCmd.getBaseExecutor());
            return true;
        }

        if (coreCommandAsHelp) {
            sendHelpCommand(gPlayer);
        } else {


            onCommand(gPlayer, "NO_SUB_COMMAND", args, coreArgBases, perm, coreExecutorBase);
        }
        return true;
    }

    private void onCommand(GPlayer gPlayer, String subCommandUsed, String[] argStrings, ArgBase[] argBases, String perm, ExecutorBase executorBase) {


        if (validateAllArgs(gPlayer, argBases, argStrings, subCommandUsed) && validatePerms(gPlayer, perm)) {

            if (gPlayer != null){
                 executorBase.runCommandPlayer(gPlayer, argBases);
            }else {
                executorBase.runCommandConsole(null, argBases);
            }


        }

    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] args) {

        int rawArgNumber = args.length - 1;

        String tabWorld = args[rawArgNumber];

        List<String> possibleTabsForArgument = getTabsForArgument(rawArgNumber, args);


        if (tabWorld.equalsIgnoreCase("")) {
            return possibleTabsForArgument;
        }


        ArrayList<String> Tabs = new ArrayList<>();

        for (String possTabs : possibleTabsForArgument) {
            if (possTabs != null && possTabs.toUpperCase().startsWith(tabWorld.toUpperCase())) {
                Tabs.add(possTabs);
            }
        }

        return Tabs;

    }

    private List<String> getTabsForArgument(int rawArgNumber, String[] args) {
        SUB_CMD subCmd = lookForSubCommand(args);

        ArgBase possibleTabsForArgument = null;

        if (subCmd != null) {
            if (subCmd.getArgBases().length >= rawArgNumber && rawArgNumber - 1 >= 0) {
                possibleTabsForArgument = subCmd.getArgBases()[rawArgNumber - 1];
            }
        } else {
            if (coreArgBases != null && coreArgBases.length >= rawArgNumber + 1) {
                possibleTabsForArgument = coreArgBases[rawArgNumber];
            }
        }

        if (possibleTabsForArgument != null) {
            if (possibleTabsForArgument instanceof Arg_PlayerByName) {
                return Util.PLAYER.getOnlinePlayerNameList(true);
            }
            return possibleTabsForArgument.getTabsForArg();
        }


        return new ArrayList<>();
    }


}
