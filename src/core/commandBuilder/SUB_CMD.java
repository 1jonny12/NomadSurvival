/*
 * Copyright © 2019 - All Rights Reserved
 * This file belongs to Jonothan Ogden, Zac Malone
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 */

package core.commandBuilder;

import core.commandBuilder.Arg.ArgBase;

public class SUB_CMD {

    private final String SubCommand;
    private final ExecutorBase baseExecutor;
    private final ArgBase[] argBases;

    public SUB_CMD(String subCommand, ExecutorBase baseExecutor, ArgBase... argBases) {
        SubCommand = subCommand;
        this.baseExecutor = baseExecutor;
        this.argBases = argBases;
    }


    //------------------------------------------------------------------------------------------------------------------------------
    // ##############################################################################################################################
    // Default - Getters and Setters
    //##############################################################################################################################
    //------------------------------------------------------------------------------------------------------------------------------

    public String getSubCommand() {
        return SubCommand;
    }

    public ExecutorBase getBaseExecutor() {
        return baseExecutor;
    }

    public ArgBase[] getArgBases() {
        return argBases;
    }

}
