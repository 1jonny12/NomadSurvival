package core.utils;

import jline.internal.Nullable;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;
import org.bukkit.util.BoundingBox;
import org.bukkit.util.Vector;


public class RayTrace {

    private final static int MAX_ITERATIONS = 1000;
    private double stepSize = 1;
    private double maxDistance = 100.0;
    private boolean terminateOnBlockHit = false;

    @Nullable
    public RayTraceResult castRay(Location startLocation, Vector rayDirection) {
        rayDirection.normalize().multiply(stepSize);
        int counter = 0;
        double distance = 0.00;
        Location rayLocation = startLocation;


        while (distance < maxDistance && counter < MAX_ITERATIONS) {
            counter++;
            distance += stepSize;

            if (rayLocation.getWorld() == null) return null;

            rayLocation.add(rayDirection).add(0, -counter * 0.001, 0);
            rayLocation.getWorld().spawnParticle(Particle.VILLAGER_HAPPY, rayLocation, 1);

            for (Entity entity : rayLocation.getWorld().getNearbyEntities(rayLocation, 0.1, 0.1, 0.1)) {
                if (entity instanceof Player) continue;
                if (!(entity instanceof Zombie)) continue;

                BoundingBox entBox = entity.getBoundingBox();

                if (!entBox.contains(rayLocation.toVector())) continue;

                /*
                    we can use the zombie's location, add it's height to get the top of it's head.
                    zombie is 2 blocks tall, and his head is 25% (0.5)
                    make a bounding box with ent's bounding box as max's and add 1.5 to the y for the min
                    that should be headshot?
                 */

                entBox.shift(0, 1.5, 0);

                if (entBox.contains(rayLocation.toVector())) {
                    return new RayTraceResult(null, entity, true);
                }

                return new RayTraceResult(null, entity, false);
            }

            Block currentBlock = rayLocation.getBlock();
            if (currentBlock.getType() != Material.AIR && terminateOnBlockHit) {
                return new RayTraceResult(currentBlock, null, false);
            }

        }

        return null;
    }

    public RayTrace setStepSize(double stepSize) {
        this.stepSize = stepSize;
        return this;
    }

    public RayTrace setMaxDistance(double maxDistance) {
        this.maxDistance = maxDistance;
        return this;
    }

    public RayTrace setTerminateOnBlockHit(boolean terminateOnBlockHit){
        this.terminateOnBlockHit = terminateOnBlockHit;
        return this;
    }

}
