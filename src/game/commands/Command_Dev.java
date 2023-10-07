package game.commands;

import core.jcommandbuilder.JArgument.JArg;
import core.jcommandbuilder.JCommand;
import core.jcommandbuilder.JCommandPlayerOnly;
import core.jcommandbuilder.JCommandSender;
import core.jcommandbuilder.JSubCommand;
import game.NomadSurvival;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.entity.monster.Slime;
import net.minecraft.world.entity.player.Player;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_19_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_19_R3.entity.CraftArmorStand;
import org.bukkit.craftbukkit.v1_19_R3.entity.CraftSlime;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.HumanEntity;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class Command_Dev {

    public class ModifiedClass {
        public double getMyRidingOffset() {
            // Your new implementation here
            return -0.5;
        }
    }
    @JCommand("dev")
    // /dev test
    @JSubCommand("test")
    public void devTestCmd(JCommandSender jCommandSender){


       //Location spawnLocation = jCommandSender.asGPlayer().getLocation();

        //Player nmsPlayer = jCommandSender.asGPlayer().getHandle();


        //  Slime nmsSlime = ((CraftSlime) spawnLocation.getWorld().spawnEntity(spawnLocation, EntityType.SLIME)).getHandle();

     //   try {
         //   Field field = Slime.class.getDeclaredField("ID_SIZE");
         //   field.setAccessible(true);

       //     nmsSlime.getEntityData().set((EntityDataAccessor<? super Integer>) field.get(nmsSlime), 1);

     //   } //catch (NoSuchFieldException | IllegalAccessException e) {
           // throw new RuntimeException(e);
       // }

        // nmsSlime.reapplyPosition();
      //  nmsSlime.refreshDimensions();




       //CustomAS customAS = new CustomAS(((CraftWorld)spawnLocation.getWorld()).getHandle(), spawnLocation.getX(), spawnLocation.getY(), spawnLocation.getZ());

      //  customAS.teleportRelative(0,0,0);

        //((CraftWorld)spawnLocation.getWorld()).getHandle().addFreshEntity(customAS);


       // ArmorStand armorStand = ((CraftArmorStand) spawnLocation.getWorld().spawnEntity(spawnLocation, EntityType.ARMOR_STAND)).getHandle();

     //   customAS.startRiding(jCommandSender.asGPlayer().getHandle());


       // jCommandSender.asGPlayer().addPassenger(customAS.getBukkitEntity());



       // jCommandSender.asGPlayer().addPassenger(nmsSlime.getBukkitEntity());
       // armorStand.addPassenger(nmsSlime.getBukkitEntity());
        //nmsSlime.riding(jCommandSender.asGPlayer().getHandle(), true);
    }


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
