/*
 * Copyright © 2019 - All Rights Reserved
 * This file belongs to Jonothan Ogden, Zac Malone
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 */

package core.commandBuilder.Arg.CoreArgs;

import game.gamePlayer.GPlayer;
import core.commandBuilder.Arg.ArgBase;

import java.util.ArrayList;
import java.util.EnumSet;

public class Arg_Enum extends ArgBase {

    private Object enumValue;
    private String type;
    private EnumSet<?> enumSet;

    public <E extends Enum<E>> Arg_Enum(String argName, Class<E> enumClass) {
        super(argName);
        init(enumClass);
    }

    private <E extends Enum<E>> void init(Class<E> enumClass) {
        type = enumClass.getSimpleName();
        enumSet = EnumSet.allOf(enumClass);

        final ArrayList<String> Values = new ArrayList<>();
        enumSet.forEach(E -> Values.add(E.name()));
        super.setTabsForArg(Values);
    }

    @Override
    public boolean ValidateArgument(GPlayer gPlayer, String argString) {
        enumSet.forEach(E -> {
            if (argString.equalsIgnoreCase(E.name())) {
                enumValue = E;
            }
        });

        if (enumValue == null) {
           gPlayer.sendMessage("&7[" + getArgName() + "] &cmust be a " + type);
            return false;
        }

        return true;
    }


    @Override
    public Object getArgValue() {
        return enumValue;
    }
}
