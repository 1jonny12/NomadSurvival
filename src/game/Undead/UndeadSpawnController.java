package game.Undead;

import core.utils.Task;
import core.utils.Util;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;

public class UndeadSpawnController {

    private final UndeadManager undeadManager;

    public UndeadSpawnController(UndeadManager undeadManager) {
        this.undeadManager = undeadManager;
        startEntitySpawnTask();
    }

    public void startEntitySpawnTask() {
        Task.repeat(20, () -> {
            for (Player p : Bukkit.getOnlinePlayers()) {
                if (p.getGameMode() == GameMode.SURVIVAL) {
                    attemptSpawn(p);
                }
            }
        });
    }


    public void attemptSpawn(Player p) {
        int entityCountAroundPlayer = 0;

        Bukkit.broadcastMessage("1");

        for (Entity entity : p.getNearbyEntities(150, 150, 150)) {
            if (entity instanceof Zombie) entityCountAroundPlayer++;
        }

        if (entityCountAroundPlayer < 10) {
            Bukkit.broadcastMessage("2");
            Location spawnLocation = getRandomSpawnLocation(p);

            if (spawnLocation != null) undeadManager.spawnUndead(UndeadType.BASIC, spawnLocation);
        }
    }

    public Location getRandomSpawnLocation(Player p) {
        int maxDistance = 50;
        int minDistance = 20;

        int randX = Util.RANDOM.percentChance(50) ? Util.RANDOM.randomInt(minDistance, maxDistance) : -Util.RANDOM.randomInt(minDistance, maxDistance);
        int randZ = Util.RANDOM.percentChance(50) ? Util.RANDOM.randomInt(minDistance, maxDistance) : -Util.RANDOM.randomInt(minDistance, maxDistance);

        Location random = p.getLocation().add(randX, 8, randZ);

        for (int i = 0; i < 18; i++) {
            Block b = random.add(0, -1, 0).getBlock();

            if (b.getType() == Material.LIME_WOOL) return b.getLocation().add(0, 3, 0);

        }

        return null;
    }

}
