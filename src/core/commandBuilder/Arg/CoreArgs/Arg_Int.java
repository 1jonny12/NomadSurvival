/*
 * Copyright © 2019 - All Rights Reserved
 * This file belongs to Jonothan Ogden, Zac Malone
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 */

package core.commandBuilder.Arg.CoreArgs;

import game.gamePlayer.GPlayer;
import core.utils.Util;
import core.commandBuilder.Arg.ArgBase;

public class Arg_Int extends ArgBase {

    private int argValue = 0;

    public Arg_Int(String argName) {
        super(argName);
    }

    public Arg_Int(String argName, String... tabsForArg) {
        super(argName, tabsForArg);
    }

    public <E extends Enum<E>> Arg_Int(String argName, Class<E> enumTabs) {
        super(argName, enumTabs);
    }


    @Override
    public boolean ValidateArgument(GPlayer gPlayer, String argString) {

        if (Util.NUMBER.isNotInt(argString)) {
            gPlayer.sendMessage("&7[" + getArgName() + "] &cmust be a whole number");
            return false;
        }

        argValue = Integer.parseInt(argString);
        return true;
    }


    @Override
    public Object getArgValue() {
        return argValue;
    }
}
