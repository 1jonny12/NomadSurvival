/*
 * Copyright © 2019 - All Rights Reserved
 * This file belongs to Jonothan Ogden, Zac Malone
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 */

package core.commandBuilder.Arg;

import game.gamePlayer.GPlayer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.List;

public abstract class ArgBase {

    private final String argName;
    private List<String> tabsForArg;

    public ArgBase(String argName) {
        this.argName = argName;
        tabsForArg = new ArrayList<>();
    }

    public ArgBase(String argName, String... tabsForArg) {
        this.argName = argName;
        this.tabsForArg = Arrays.asList(tabsForArg);
    }

    public <E extends Enum<E>> ArgBase(String argName, Class<E> enumTabs) {
        this.argName = argName;
        final ArrayList<String> Values = new ArrayList<>();
        EnumSet.allOf(enumTabs).forEach(E -> Values.add(E.name()));
        tabsForArg = Values;
    }

    public abstract boolean ValidateArgument(GPlayer gPlayer, String argString);

    public abstract Object getArgValue();

    public boolean Validate(GPlayer gPlayer, int argPosition, String[] argStrings) {
        return HasArgCount(gPlayer, argPosition, argStrings) && ValidateArgument(gPlayer, argStrings[argPosition]);
    }

    private boolean HasArgCount(GPlayer gPlayer, int argPosition, String[] argStrings) {
        if (argStrings.length <= argPosition) {
            gPlayer.sendMessage("&cArg Missing: &7[" + argName + "]");
            return false;
        }

        return true;
    }

    public List<String> getTabsForArg() {
        return tabsForArg;
    }

    //------------------------------------------------------------------------------------------------------------------------------
    // ##############################################################################################################################
    // Default - Getters and Setters
    //##############################################################################################################################
    //------------------------------------------------------------------------------------------------------------------------------

    public String getArgName() {
        return argName;
    }


    public void setTabsForArg(List<String> tabsForArg) {
        this.tabsForArg = tabsForArg;
    }
}
