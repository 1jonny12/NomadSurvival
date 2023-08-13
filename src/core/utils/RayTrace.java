package core.utils;

import jline.internal.Nullable;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;


public class RayTrace {

    private final static int MAX_ITERATIONS = 1000;
    private double stepSize = 0.25;
    private double maxDistance = 100.0;
    private boolean terminateOnBlockHit = true;

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
                if (!entity.getBoundingBox().contains(rayLocation.toVector())) continue;
                return new RayTraceResult(null, entity);
            }

            Block currentBlock = rayLocation.getBlock();
            if (currentBlock.getType() != Material.AIR && terminateOnBlockHit) {
                return new RayTraceResult(currentBlock, null);
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
