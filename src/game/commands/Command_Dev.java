package game.commands;

import core.jcommandbuilder.JCommand;
import core.jcommandbuilder.JCommandPlayerOnly;
import core.jcommandbuilder.JCommandSender;
import core.jcommandbuilder.JSubCommand;
import game.NomadSurvival;
import org.bukkit.Bukkit;
import org.bukkit.Location;

public class Command_Dev {


    @JCommand("Dev")
    @JSubCommand("TeleportVehicles")
    @JCommandPlayerOnly
    public void teleportVehicles(JCommandSender jCommandSender){
        jCommandSender.asGPlayer().teleport(new Location(NomadSurvival.VEHICLES_WORLD,0,0,0));
        jCommandSender.sendMessage("Teleporting");
    }


    @JCommand("Dev")
    @JSubCommand("SpawnZombie")
    @JCommandPlayerOnly
    public void spawnZombie(JCommandSender jCommandSender){
        NomadSurvival.AI_ENTITY_MANAGER.spawnUndead(jCommandSender.asGPlayer().getLocation());
        jCommandSender.sendMessage("Spawning");
    }

}
