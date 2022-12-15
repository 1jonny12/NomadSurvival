package game.Undead;

import com.google.common.collect.Sets;
import core.utils.Task;
import net.minecraft.world.entity.EntityInsentient;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.ai.goal.PathfinderGoalSelector;
import net.minecraft.world.entity.ai.navigation.NavigationAbstract;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_19_R1.entity.CraftEntity;
import org.bukkit.entity.*;
import org.bukkit.inventory.EntityEquipment;

import java.lang.reflect.Field;

public class Undead {


    private final UndeadBehaviour undeadBehaviour = new UndeadBehaviour(this);
    private NavigationAbstract nav;
    private Task tickTask;
    private LivingEntity bukkitEntity;
    private EntityInsentient entityInsentient;
    private boolean isSpawned = false;

    {
        startEntityTick();
    }

    public void baseTickEntity() {
        if (isSpawned) {
            bukkitEntity.setCustomName("Zombie");
            if (bukkitEntity.isDead()) {
                killAndUnload();
                return;
            }
        }

        undeadBehaviour.tickBehaviour();


    }

    public double getAttackReach() {
        return 2;
    }

    public void damageReceived(LivingEntity damager) {
        undeadBehaviour.setAttackTarget(damager);
    }

    private void startEntityTick() {
        tickTask = Task.repeat(2, this::baseTickEntity);
    }

    public void spawn(Location spawnLocation) {
        bukkitEntity = spawnLocation.getWorld().spawn(spawnLocation, Zombie.class, consumerZombie -> {
            consumerZombie.setAdult();
            consumerZombie.setCustomNameVisible(true);
            consumerZombie.setCanPickupItems(false);
            consumerZombie.setCustomName("DEFAULT");
            EntityEquipment equip = consumerZombie.getEquipment();

            if (equip != null) {
                equip.setHelmetDropChance(0f);
                equip.setChestplateDropChance(0f);
                equip.setLeggingsDropChance(0f);
                equip.setBootsDropChance(0f);
                equip.setItemInMainHandDropChance(0f);
            }
        });


        isSpawned = true;
        entityInsentient = ((EntityInsentient) ((CraftEntity) bukkitEntity).getHandle());
        nav = entityInsentient.D();
        ClearAllGoals();
    }

    private void ClearAllGoals() {
        try {
            final Field bField = PathfinderGoalSelector.class.getDeclaredField("d");
            bField.setAccessible(true);
            bField.set(entityInsentient.bS, Sets.newLinkedHashSet());
            bField.set(entityInsentient.bT, Sets.newLinkedHashSet());
        } catch (final Exception exc) {
            exc.printStackTrace();
        }
    }

    public void killAndUnload() {
        tickTask.stop();
        bukkitEntity.remove();

        Bukkit.broadcastMessage("KILL AND UNLOAD");
    }

    public boolean isNotNavigating() {
        return !nav.m();
    }

    public void stopNavigation() {
        nav.n();
    }

    public void pathTo(Location location, double speed) {
        pathTo(location.getX(), location.getY(), location.getZ(), speed);
    }

    public void pathTo(double x, double y, double z, double speed) {
        nav.a(x, y, z, speed);
    }

    public void lookAt(LivingEntity livingentity, float lookSpeed) {
        EntityLiving entityLiving = (EntityLiving) ((CraftEntity) livingentity).getHandle();
        entityInsentient.z().a(entityLiving, lookSpeed, lookSpeed);
    }

    public double distanceFrom(LivingEntity livingEntity) {
        return distanceFrom(livingEntity.getLocation());
    }

    public double distanceFrom(Location from) {
        return from.distance(bukkitEntity.getLocation());
    }
    public double distanceSQRFrom(LivingEntity livingEntity) {
        return distanceFrom(livingEntity.getLocation());
    }

    //------------------------------------------------------------------------------------------------------------------------------
    // ##############################################################################################################################
    // Default - Getters and Setters
    //##############################################################################################################################
    //------------------------------------------------------------------------------------------------------------------------------

    public Task getTickTask() {
        return tickTask;
    }

    public boolean isSpawned() {
        return isSpawned;
    }

    public LivingEntity getBukkitEntity() {
        return bukkitEntity;
    }

    public EntityInsentient getEntityInsentient() {
        return entityInsentient;
    }

}
