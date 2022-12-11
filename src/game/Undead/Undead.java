package game.Undead;

import com.google.common.collect.Sets;
import core.utils.Task;
import game.Undead.Brain;
import net.minecraft.world.entity.EntityInsentient;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.ai.goal.PathfinderGoalSelector;
import net.minecraft.world.entity.ai.navigation.NavigationAbstract;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_19_R1.entity.CraftEntity;
import org.bukkit.entity.Husk;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.EntityEquipment;

import java.lang.reflect.Field;

public abstract class Undead {

    private NavigationAbstract nav;
    private Task tickTask;
    private LivingEntity bukkitEntity;
    private EntityInsentient entityInsentient;
    private final Brain brain = new Brain(this);
    private boolean isSpawned = false;
    private String name;
    private int nextAttackDelay = 10;

    private double entitySpeed = 0;

    {
        startEntityTick();
        entitySpeed = getDefaultSpeed();
    }

    public void baseTickEntity(){
        if (isSpawned) {
            bukkitEntity.setCustomName(name);
            if (bukkitEntity.isDead()) {
                killAndUnload();
                return;
            }
        }

        brain.tick();
        tickEntity();
    }

    public abstract void tickEntity();

    public abstract double getDefaultSpeed();

    public void damageReceived(LivingEntity damager){
        attackLivingEntity(damager);
    }

    public void attackLivingEntity(LivingEntity livingEntity){
        brain.setAttackEntity(livingEntity);
    }

    private void startEntityTick() {
            tickTask = Task.repeat(20, this::baseTickEntity);
    }

    public void spawn(Location spawnLocation) {
        bukkitEntity = spawnLocation.getWorld().spawn(spawnLocation, Husk.class, consumerZombie -> {
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

    public void lookAt(LivingEntity livingentity) {
        EntityLiving entityLiving = (EntityLiving) ((CraftEntity) livingentity).getHandle();
        entityInsentient.z().a(entityLiving, 30.0F, 30.0F);
    }

    public double distanceSQRFrom(LivingEntity livingEntity) {
        return distanceSQRFrom(livingEntity.getLocation());
    }

    public double distanceSQRFrom(Location from) {
        return from.distanceSquared(bukkitEntity.getLocation());
    }

    public double distanceSQRFromExcludeY(LivingEntity livingEntity) {
        return distanceSQRFrom(livingEntity.getLocation());
    }

    public double distanceSQRFromExcludeY(Location from) {
        Location to = bukkitEntity.getLocation();
        double x = to.getX() - from.getX();
        double z = to.getZ() - from.getZ();
        return x * x + z * z;
    }



    //------------------------------------------------------------------------------------------------------------------------------
    // ##############################################################################################################################
    // Default - Getters and Setters
    //##############################################################################################################################
    //------------------------------------------------------------------------------------------------------------------------------


    public Brain getBrain() {
        return brain;
    }

    public int getNextAttackDelay() {
        return nextAttackDelay;
    }

    public void setNextAttackDelay(int nextAttackDelay) {
        this.nextAttackDelay = nextAttackDelay;
    }


    public Task getTickTask() {
        return tickTask;
    }

    public boolean isSpawned() {
        return isSpawned;
    }

    public LivingEntity getBukkitEntity() {
        return bukkitEntity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public EntityInsentient getEntityInsentient() {
        return entityInsentient;
    }

    public double getEntitySpeed() {
        return entitySpeed;
    }

}
