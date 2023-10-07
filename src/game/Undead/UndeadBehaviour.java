package game.Undead;

import core.utils.EntityMetaDataBuilder;
import core.utils.Util;
import game.NomadSurvival;
import game.gamePlayer.GPlayer;
import net.minecraft.network.protocol.game.ClientboundSetEntityDataPacket;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.behavior.RandomStroll;
import net.minecraft.world.entity.ai.util.LandRandomPos;
import net.minecraft.world.phys.Vec3;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.craftbukkit.v1_19_R3.entity.CraftEntity;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.List;

public class UndeadBehaviour {

    private final Undead undead;

    public UndeadBehaviour(Undead undead) {
        this.undead = undead;
    }

    private static final float UNDEAD_FOV = 0.5f;
    private static final double MIN_ENTITY_SPEED = 1.3;
    private final double maxEntitySpeed = Util.RANDOM.randomDouble(1.6, 1.85);

    private BehaviourType behaviourType = BehaviourType.WANDER;
    private LivingEntity attackTarget = null;
    private int lastSeenAttackTarget = 0;
    private Location lastSeenAttackTargetLocation = null;

    private int nextAttackDelay = 10;
    private int randomLookCooldown = 0;
    private int randomMoveCooldown = 0;
    private double randomLookTargetX = 0;
    private double randomLookTargetY = 0;
    private double randomLookTargetZ = 0;
    private double currentEntitySpeed = 0.7;

    public void tickBehaviour() {

            switch (behaviourType){
                case WANDER -> {doWander(); manageTargeting();}
                case ATTACK_TARGET -> doAttackTarget();
            }


            updateBehaviourName();
            visualiseFov();
    }

    public void doWander(){
        randomLook();
        randomMove();
    }

    public void doAttackTarget(){
            if (validateAttackTarget()) {
                attack();

                if (seesEntity(attackTarget)){
                   lastSeenAttackTarget = 0;
                   lastSeenAttackTargetLocation = attackTarget.getLocation();
                }else {
                    lastSeenAttackTarget++;
                }

                lastSeenAttackTarget = seesEntity(attackTarget) ? 0 : lastSeenAttackTarget + 1;

                if (lastSeenAttackTarget > 30){
                    Bukkit.broadcastMessage("FORGET TARGET: NOT SEEN" );
                    forgetTarget();
                }
            }
    }

    public void randomLook() {
        if (Util.RANDOM.percentChance(1) && randomLookCooldown <= 0) {
            randomLookTargetX = undead.getBukkitEntity().getLocation().getX() + Util.RANDOM.randomInt(-25, 25);
            randomLookTargetY = undead.getBukkitEntity().getLocation().getY() + Util.RANDOM.randomInt(-150, 2);
            randomLookTargetZ = undead.getBukkitEntity().getLocation().getZ() + Util.RANDOM.randomInt(-25, 25);
            randomLookCooldown = 50;
            Bukkit.broadcastMessage("RANDOM LOOK");

        } else {
            undead.lookAt(randomLookTargetX, randomLookTargetY, randomLookTargetZ, 1f);
        }

        randomLookCooldown--;
    }

    public void randomMove() {
        if (undead.isNavigating()) return;

        if (Util.RANDOM.percentChance(5) && randomMoveCooldown <= 0) {
            Vec3 randomPos = LandRandomPos.getPos((PathfinderMob) undead.getNmsMob(), 10, 7); //Uses Mojang random land location finder. [Var1 = MaxXZDist] [Var2 = MaxYDist]
            undead.pathTo(randomPos, currentEntitySpeed);
            randomMoveCooldown = 50;
            Bukkit.broadcastMessage("RANDOM MOVE");
        }

        randomMoveCooldown--;

    }


    public void manageTargeting() {
        for (Entity entity : undead.getBukkitEntity().getNearbyEntities(40, 40, 40)) {
            if (entity instanceof Player p) {

                double targetDistance = undead.distanceFrom(p);
                GPlayer gPlayer = NomadSurvival.G_PLAYER_MANAGER.getGPlayer(p);


                if (seesEntity(gPlayer)) {
                 //   Bukkit.broadcastMessage("Sees Target");
                    target(gPlayer);
                }else if (hearsPlayer(gPlayer, targetDistance)) {
                 //   Bukkit.broadcastMessage("Hears Target");
                 //   undead.lookAt(gPlayer, 2);
                }else if (smellsPlayer(gPlayer, targetDistance)){
                //    Bukkit.broadcastMessage("Smells Target");
                //    undead.lookAt(gPlayer, 2);
                }

            }
        }
    }

    public boolean hearsPlayer(GPlayer target, double targetDistance) {
        return target.getNoise().getTotalNoise() > targetDistance * 4;

    }

    public boolean smellsPlayer(GPlayer target, double targetDistance) {
        return target.getSmell() > targetDistance * 3;

    }

    public boolean seesEntity(LivingEntity target) {
        float fovScope = undead.getBukkitEntity().getLocation().getDirection().angle(target.getLocation().toVector().subtract(undead.getBukkitEntity().getLocation().toVector()));
        boolean hasLOS = undead.getBukkitEntity().hasLineOfSight(target);
        return fovScope < UNDEAD_FOV && hasLOS;
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

    public void updateBehaviourName(){

        List<SynchedEntityData.DataValue<?>> data = new EntityMetaDataBuilder().setCustomName(behaviourType.name()).createData();

        NomadSurvival.G_PLAYER_MANAGER.forAllGPlayer(gPlayer -> {

            if (!gPlayer.isOp() || !gPlayer.isUndeadNameActivityEnabled()) return;

            gPlayer.sendPacket(new ClientboundSetEntityDataPacket(undead.getBukkitEntity().getEntityId(), data));

        });

    }

    public void target(LivingEntity livingEntity) {
        attackTarget = livingEntity;
        currentEntitySpeed = MIN_ENTITY_SPEED;
        behaviourType = BehaviourType.ATTACK_TARGET;
    }

    public void forgetTarget() {
       target(null);
    }

    public boolean validateAttackTarget() {
        if (attackTarget == null) return false;

        if (attackTarget.isDead()) {
            forgetTarget();
            return false;
        }

        return true;
    }

    public void attack() {
        undead.lookAt(attackTarget, 15);
        undead.pathTo(attackTarget, currentEntitySpeed);
        currentEntitySpeed = Math.min(currentEntitySpeed + 0.004, maxEntitySpeed);

        double distanceFrom = undead.distanceSQRFrom(attackTarget);
        nextAttackDelay = Math.max(0, nextAttackDelay - 1);

        if (distanceFrom <= undead.getAttackReach() && nextAttackDelay <= 0) {
            nextAttackDelay = 20;
            undead.getNmsMob().swing(InteractionHand.MAIN_HAND);
            undead.getNmsMob().doHurtTarget(((CraftEntity) attackTarget).getHandle());
        }
    }

    public BehaviourType getBehaviourType() {
        return behaviourType;
    }

}
