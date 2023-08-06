package game.Undead;

import com.google.common.collect.Sets;
import core.utils.Task;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.GoalSelector;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.phys.Vec3;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_19_R3.entity.CraftEntity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Zombie;
import org.bukkit.inventory.EntityEquipment;

import java.lang.reflect.Field;

public class Undead {

    private final UndeadBehaviour undeadBehaviour = new UndeadBehaviour(this);
    private PathNavigation nav;
    private Task tickTask;
    private LivingEntity bukkitEntity;
    private Mob nmsMob;
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
        undeadBehaviour.target(damager);
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
        nmsMob = ((Mob) ((CraftEntity) bukkitEntity).getHandle());
        nav = nmsMob.getNavigation();
        ClearAllGoals();
    }

    private void ClearAllGoals() {
        try {
            final Field bField = GoalSelector.class.getDeclaredField("availableGoals");
            bField.setAccessible(true);
            bField.set(nmsMob.goalSelector, Sets.newLinkedHashSet());
            bField.set(nmsMob.targetSelector, Sets.newLinkedHashSet());
        } catch (final Exception exc) {
            exc.printStackTrace();
        }
    }

    public void killAndUnload() {
        tickTask.stop();
        bukkitEntity.remove();

        Bukkit.broadcastMessage("KILL AND UNLOAD");
    }

    public boolean isNavigating() {
        return nav.isInProgress();
    }

    public void stopNavigation() {
        nav.stop();
    }

    public void pathTo(Vec3 position, double speed) {
        pathTo(position.x(), position.y(),  position.z(), speed);
    }

    public void pathTo(double x, double y, double z, double speed) {
        nav.moveTo(x, y, z, speed);
    }

    public void pathTo(LivingEntity target, double speed) {
        nav.moveTo((Entity) target, speed);
    }

    public void lookAt(LivingEntity livingentity, float lookSpeed) {
        nmsMob.getLookControl().setLookAt(((CraftEntity) livingentity).getHandle(), lookSpeed, lookSpeed);
    }

    public void lookAt(double x, double y, double z, float lookSpeed) {
        nmsMob.getLookControl().setLookAt(x, y, z, lookSpeed, lookSpeed);
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

    public Mob getNmsMob() {
        return nmsMob;
    }

    public UndeadBehaviour getUndeadBehaviour() {
        return undeadBehaviour;
    }
}
