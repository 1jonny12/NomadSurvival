/*
 * Copyright © 2019 - All Rights Reserved
 * This file belongs to Jonothan Ogden, Zac Malone
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 */

package core.jcommandbuilder;

import core.jcommandbuilder.JArgument.JArgument;
import core.utils.CaseInsensitiveString;
import jline.internal.Nullable;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

public class JCmd {

    private boolean failedToLoad = false;
    private final Method commandMethod;
    private final Object commandClassInstance;

    private final CaseInsensitiveString coreCommandName;
    private final CaseInsensitiveString subCommandName;
    private final JArgument<?>[] jArguments;
    private final boolean playerOnlyCommand;
    private final boolean consoleOnlyCommand;
    private final String permission;

    public JCmd(Method commandMethod, Object commandClassInstance, CaseInsensitiveString coreCommandName, CaseInsensitiveString subCommandName, JArgument<?>[] jArguments, boolean playerOnlyCommand, boolean consoleOnlyCommand, String permission) {
        this.commandMethod = commandMethod;
        this.commandClassInstance = commandClassInstance;
        this.coreCommandName = coreCommandName;
        this.subCommandName = subCommandName;
        this.jArguments = jArguments;
        this.playerOnlyCommand = playerOnlyCommand;
        this.consoleOnlyCommand = consoleOnlyCommand;
        this.permission = permission;
        initialize();
    }

    private int rawCommandArgStart;
    private int rawCommandArgsMin;
    private String usageString;



    private void initialize() {
        boolean isCoreCommand = subCommandName.isEqual("*");
        rawCommandArgsMin = isCoreCommand ? jArguments.length : jArguments.length + 1;
        rawCommandArgStart = isCoreCommand ? 0 : 1;
        generateUsageString();

    }

    public void callCommand(JCommandSender jCommandSender, String[] rawCommandArgs) {
        if (failedToLoad) {
            jCommandSender.sendMessage("&cError Loading Command. See Console @ server loading.");
            return;
        }

        if (playerOnlyCommand && !jCommandSender.isGPlayer()) {
            jCommandSender.sendMessage("^eYou must be a player to use this command");
            return;
        }

        if (consoleOnlyCommand && !jCommandSender.isConsole()) {
            jCommandSender.sendMessage("^eThis command can only be ran from console.");
            return;
        }

        if (jCommandSender.isGPlayer() && !permission.equalsIgnoreCase("NO_PERMISSION") && !jCommandSender.asGPlayer().hasPermission(permission)) {
            jCommandSender.sendMessage("^eYou do not have the correct permission to run this command.");
            return;
        }


        if (!processJArguments(jCommandSender, rawCommandArgs)) return;

        ArrayList<Object> methodParameters = new ArrayList<>();
        methodParameters.add(jCommandSender);

        for (JArgument<?> arguments : jArguments) {
            methodParameters.add(arguments.getValue());
            arguments.reset();
        }

        try {
            commandMethod.invoke(commandClassInstance, methodParameters.toArray());
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean processJArguments(JCommandSender jCommandSender, String[] rawCommandArgs) {
        int rawCommandArgTracker = rawCommandArgStart;
        for (JArgument<?> jArgument : jArguments) {

            if (rawCommandArgs.length - 1 >= rawCommandArgTracker) {
                String rawCommandArg = rawCommandArgs[rawCommandArgTracker];

                JArgument.JArgumentValidateResponse validateResponse = jArgument.validate(rawCommandArg);

                if (validateResponse.wasInvalid()) {
                    jCommandSender.sendMessage("&cIncorrect Use: " + validateResponse.reason());
                    jCommandSender.sendMessage(usageString);
                    return false;
                }

                jArgument.setValue(rawCommandArg);
                rawCommandArgTracker++;
                continue;
            }

            if (!jArgument.hasDefaultValue()) {
                jCommandSender.sendMessage("&cIncorrect Use: Args Missing");
                jCommandSender.sendMessage(usageString);
                return false;
            }

        }

        return true;
    }

    public void generateUsageString() {

        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("^m/").append(coreCommandName);

        if (!subCommandName.isEqual("*"))
            stringBuilder.append("^s ").append(subCommandName);

        for (JArgument<?> jArgument : jArguments)
            stringBuilder.append(" &7").append(jArgument.getNameWithBrackets());

        usageString = stringBuilder.toString();
    }


    public void setFailedToLoad() {
        failedToLoad = true;
    }

    @Nullable
    public JArgument<?> getJArgument(int argNumber) {
        if (jArguments.length - 1 < argNumber)
          return null;

        return jArguments[argNumber];
    }

    //----------------------------------------------------
    // [Default] -> Getters and Setters
    //----------------------------------------------------

    public String getUsageString() {
        return usageString;
    }

    public CaseInsensitiveString getCoreCommandName() {
        return coreCommandName;
    }

    public CaseInsensitiveString getSubCommandName() {
        return subCommandName;
    }
}
