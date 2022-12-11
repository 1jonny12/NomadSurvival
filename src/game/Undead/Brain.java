package game.Undead;

import net.minecraft.world.EnumHand;
import net.minecraft.world.entity.EntityLiving;
import org.bukkit.craftbukkit.v1_19_R1.entity.CraftEntity;
import org.bukkit.entity.LivingEntity;

public class Brain {

    private final Undead undead;

    public Brain(Undead undead) {
        this.undead = undead;
    }

    public void tick(){



        if (attackEntity != null){
             attack(undead);
        }


    }


    public void attack(Undead undead) {
        LivingEntity target = undead.getBrain().getAttackEntity();
        EntityLiving entityLiving = (EntityLiving) ((CraftEntity) target).getHandle();

        undead.lookAt(target);
        undead.pathTo(target.getLocation(), undead.getEntitySpeed());

        double distanceFrom = undead.distanceSQRFrom(target);
        undead.setNextAttackDelay(Math.max(0, undead.getNextAttackDelay() - 1));

        if (distanceFrom <= getAttackReach(entityLiving) && undead.getNextAttackDelay() <= 0) {
            undead.setNextAttackDelay(20);
            undead.getEntityInsentient().a(EnumHand.a); //Swing Arm
            undead.getEntityInsentient().z(entityLiving); //attack
        }
    }

    public double getAttackReach(EntityLiving entityLiving){
        // cT() = entity width
        return 2.0;// entityLiving.cT() * 2.1F * entityLiving.cT() * 2.1F + entityLiving.cT();
    }

    private LivingEntity attackEntity;

    //----------------------------------------------------
    // [Default] -> Getters and Setters
    //----------------------------------------------------

    public LivingEntity getAttackEntity() {
        return attackEntity;
    }

    public void setAttackEntity(LivingEntity attackEntity) {
        this.attackEntity = attackEntity;
    }

}
