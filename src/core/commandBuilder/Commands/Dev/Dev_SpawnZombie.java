package core.commandBuilder.Commands.Dev;

import core.commandBuilder.Arg.ArgBase;
import core.commandBuilder.ExecutorBase;
import core.gamePlayer.GPlayer;
import game.Undead.UndeadTypes.BasicUndead;

public class Dev_SpawnZombie extends ExecutorBase {

    @Override
    public void runCommandPlayer(GPlayer gPlayer, ArgBase[] args) {

        new BasicUndead().spawn(gPlayer.getLocation());

    }
}
