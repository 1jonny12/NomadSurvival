/*
 * Copyright © 2019 - All Rights Reserved
 * This file belongs to Jonothan Ogden, Zac Malone
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 */

package core.commandBuilder.Arg.CoreArgs;

import game.gamePlayer.GPlayer;
import core.commandBuilder.Arg.ArgBase;

public class Arg_Boolean extends ArgBase {

    private boolean argValue = true;

    public Arg_Boolean(String argName) {
        super(argName, "True, False");
    }

    @Override
    public boolean ValidateArgument(GPlayer gPlayer, String argString) {

        if (!argString.toUpperCase().matches("TRUE|FALSE")) {
            gPlayer.sendMessage("&7[" + getArgName() + "] &cmust be True or False");
            return false;
        }

        if (argString.equalsIgnoreCase("false")) {
            argValue = false;
        }
        return true;
    }


    @Override
    public Object getArgValue() {
        return argValue;
    }
}
