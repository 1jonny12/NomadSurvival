package game.Undead;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Zombie;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import java.lang.reflect.InvocationTargetException;

public class UndeadManager {

    private final UndeadSpawnController spawnController = new UndeadSpawnController(this);
    private final UndeadTracker undeadTracker = new UndeadTracker();


    public void spawnUndead(UndeadType undeadType, Location location){

        try {
            Undead undead = undeadType.getUndeadType().getDeclaredConstructor().newInstance();
            undead.spawn(location);
            undeadTracker.addUndead(undead);
        }
        catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }

    }

    public void HandleDamageEvent(EntityDamageByEntityEvent e) {
        Entity entity = e.getEntity();
        Entity attacker = e.getDamager();

        if (entity instanceof Zombie && attacker instanceof LivingEntity) {

            if (undeadTracker.isSpawnUndead(entity.getUniqueId())) {
                Undead aiEntity = undeadTracker.getUndead(entity.getUniqueId());
                if (aiEntity != null) {
                    aiEntity.damageReceived((LivingEntity) attacker);
                }
            }

        }
    }


}
