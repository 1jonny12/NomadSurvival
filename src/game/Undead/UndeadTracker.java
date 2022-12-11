package game.Undead;

import org.bukkit.entity.Entity;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.UUID;

public class UndeadTracker {

    private final HashMap<UUID, Undead> SpawnedUndeadByUUID = new HashMap<>();

    public void addUndead(Undead undead) {
        SpawnedUndeadByUUID.putIfAbsent(undead.getBukkitEntity().getUniqueId(), undead);
    }

    public void removeUndead(UUID EntityUUID) {
        SpawnedUndeadByUUID.remove(EntityUUID);
    }

    public boolean isSpawnedUndead(Entity entity) {
        return isSpawnUndead(entity.getUniqueId());
    }

    public boolean isSpawnUndead(UUID EntityUUID) {
        return SpawnedUndeadByUUID.containsKey(EntityUUID);
    }

    /**
     * @return Null is there is no entity tracked with that UUID
     */
    @Nullable
    public Undead getUndead(UUID EntityUUID) {
        return SpawnedUndeadByUUID.getOrDefault(EntityUUID, null);
    }

    public boolean hasUndead(UUID EntityUUID) {
        return SpawnedUndeadByUUID.containsKey(EntityUUID);
    }

}