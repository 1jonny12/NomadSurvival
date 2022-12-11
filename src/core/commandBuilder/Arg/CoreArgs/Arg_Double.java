/*
 * Copyright © 2019 - All Rights Reserved
 * This file belongs to Jonothan Ogden, Zac Malone
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 */

package core.commandBuilder.Arg.CoreArgs;

import core.gamePlayer.GPlayer;
import core.utils.Util;
import core.commandBuilder.Arg.ArgBase;

public class Arg_Double extends ArgBase {

    private double argValue = 0;

    public Arg_Double(String argName) {
        super(argName);
    }

    public Arg_Double(String argName, String... tabsForArg) {
        super(argName, tabsForArg);
    }

    public <E extends Enum<E>> Arg_Double(String argName, Class<E> enumTabs) {
        super(argName, enumTabs);
    }

    @Override
    public boolean ValidateArgument(GPlayer gPlayer, String argString) {

        if (Util.NUMBER.isNotDouble(argString)) {
            gPlayer.sendMessage("&7[" + getArgName() + "] &cmust be a decimal.");
            return false;
        }

        argValue = Double.parseDouble(argString);
        return true;
    }


    @Override
    public Object getArgValue() {
        return argValue;
    }
}
