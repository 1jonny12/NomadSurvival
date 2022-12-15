package game.Undead;

import core.utils.Util;
import game.NomadSurvival;
import game.gamePlayer.GPlayer;
import net.minecraft.world.EnumHand;
import net.minecraft.world.entity.EntityLiving;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.craftbukkit.v1_19_R1.entity.CraftEntity;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class UndeadBehaviour {

    private final Undead undead;

    public UndeadBehaviour(Undead undead) {
        this.undead = undead;
    }

    private LivingEntity attackTarget = null;
    private static final float UNDEAD_FOV = 0.5f;
    private static final double MIN_ENTITY_SPEED = 1.3;
    private final double maxEntitySpeed = Util.RANDOM.randomDouble(1.6, 1.85);
    private int nextAttackDelay = 10;
    private int randomLookCooldown = 0;
    private double randomLookTargetX = 0;
    private double randomLookTargetY = 0;
    private double randomLookTargetZ = 0;
    private double currentEntitySpeed = 0.7;

    public void tickBehaviour() {

        if (attackTarget != null) {
            if (validateAttackTarget()) {
                attack();
            }
        } else {
            randomLook();
            manageTargeting();
            visualiseFov();
        }


    }

    public void randomLook() {
        if (Util.RANDOM.percentChance(1) && randomLookCooldown <= 0) {
            randomLookTargetX = undead.getBukkitEntity().getLocation().getX() + Util.RANDOM.randomInt(-25, 25);
            randomLookTargetY = undead.getBukkitEntity().getLocation().getY() + Util.RANDOM.randomInt(-150, 2);
            randomLookTargetZ = undead.getBukkitEntity().getLocation().getZ() + Util.RANDOM.randomInt(-25, 25);
            randomLookCooldown = 50;
        } else {
            undead.getEntityInsentient().z().a(randomLookTargetX, randomLookTargetY, randomLookTargetZ, 1f, 1f);
        }

        randomLookCooldown--;
    }


    public void manageTargeting() {
        for (Entity entity : undead.getBukkitEntity().getNearbyEntities(40, 40, 40)) {
            if (entity instanceof Player p) {

                double targetDistance = undead.distanceFrom(p);
                GPlayer gPlayer = NomadSurvival.G_PLAYER_MANAGER.getGPlayer(p);


                if (seesTarget(gPlayer, targetDistance)) {
                    Bukkit.broadcastMessage("Sees Target");
                    setAttackTarget(gPlayer);
                }else if (hearsTarget(gPlayer, targetDistance)) {
                    Bukkit.broadcastMessage("Hears Target");
                    undead.lookAt(gPlayer, 2);
                }else if (smellsTarget(gPlayer, targetDistance)){
                    Bukkit.broadcastMessage("Smells Target");
                    undead.lookAt(gPlayer, 2);
                }

            }
        }
    }

    public boolean hearsTarget(GPlayer target, double targetDistance) {
        return target.getNoise().getTotalNoise() > targetDistance * 4;

    }

    public boolean smellsTarget(GPlayer target, double targetDistance) {
        return target.getSmell() > targetDistance * 3;

    }

    public boolean seesTarget(GPlayer target, double targetDistance) {
        float fovScope = undead.getBukkitEntity().getLocation().getDirection().angle(target.getLocation().toVector().subtract(undead.getBukkitEntity().getLocation().toVector()));
        return fovScope < UNDEAD_FOV;
    }

    public void visualiseFov() {
        Location location = undead.getBukkitEntity().getLocation();
        Vector leftVector = location.getDirection().rotateAroundY(UNDEAD_FOV).setY(0);
        Vector rightVector = location.getDirection().rotateAroundY(-UNDEAD_FOV).setY(0);
        Location left = location.clone();
        Location right = location.clone();

        for (int i = 0; i < 30; i++) {
            left.add(leftVector.normalize());
            right.add(rightVector.normalize());
            left.getWorld().spawnParticle(Particle.COMPOSTER, left.clone().add(0, 0.2, 0), 1);
            right.getWorld().spawnParticle(Particle.COMPOSTER, right.clone().add(0, 0.2, 0), 1);
        }

    }

    public boolean validateAttackTarget() {
        if (attackTarget.isDead() || undead.distanceSQRFrom(attackTarget) > 100) {
            attackTarget = null;
            setAttackTarget(null);
            return false;
        }

        return true;
    }

    public void setAttackTarget(LivingEntity livingEntity) {
        attackTarget = livingEntity;
        currentEntitySpeed = MIN_ENTITY_SPEED;
    }

    public void attack() {
        EntityLiving entityLiving = (EntityLiving) ((CraftEntity) attackTarget).getHandle();

        undead.lookAt(attackTarget, 15);
        undead.pathTo(attackTarget.getLocation(), currentEntitySpeed);
        currentEntitySpeed = Math.min(currentEntitySpeed + 0.004, maxEntitySpeed);

        double distanceFrom = undead.distanceSQRFrom(attackTarget);
        nextAttackDelay = Math.max(0, nextAttackDelay - 1);

        if (distanceFrom <= undead.getAttackReach() && nextAttackDelay <= 0) {
            nextAttackDelay = 20;
            undead.getEntityInsentient().a(EnumHand.a); //Swing Arm
            undead.getEntityInsentient().z(entityLiving); //attack
        }
    }
}
