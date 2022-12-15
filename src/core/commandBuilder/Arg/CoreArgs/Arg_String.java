/*
 * Copyright © 2019 - All Rights Reserved
 * This file belongs to Jonothan Ogden, Zac Malone
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 */

package core.commandBuilder.Arg.CoreArgs;

import core.commandBuilder.Arg.ArgBase;
import game.gamePlayer.GPlayer;

public class Arg_String extends ArgBase {

    private String argValue = "";

    public Arg_String(String argName) {
        super(argName);
    }

    public Arg_String(String argName, String... tabsForArg) {
        super(argName, tabsForArg);
    }

    public <E extends Enum<E>> Arg_String(String argName, Class<E> enumTabs) {
        super(argName, enumTabs);
    }

    @Override
    public boolean ValidateArgument(GPlayer gPlayer, String argString) {
        argValue = argString;
        return true;
    }

    @Override
    public Object getArgValue() {
        return argValue;
    }
}
