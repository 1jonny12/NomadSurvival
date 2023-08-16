package core.utils;

import jline.internal.Nullable;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_19_R3.CraftFluidCollisionMode;
import org.bukkit.craftbukkit.v1_19_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_19_R3.util.CraftLocation;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Zombie;
import org.bukkit.util.BoundingBox;
import org.bukkit.util.Vector;

import java.util.ArrayList;


public class RayTrace {

    private final static int MAX_ITERATIONS = 1000;
    private double stepSize = 0.4;
    private double maxDistance = 100.0;

    private boolean hitEntitys = true;
    private boolean hitBlocks = true;
    private boolean terminateOnBlockHit = false;
    private boolean terminateOnEntityHit = false;

    @Nullable
    public RayTraceResult castRay(Location startLocation, Vector rayDirection) {
        rayDirection.normalize().multiply(stepSize);
        int counter = 0;
        double distance = 0.00;
        Location rayLocation = startLocation;

        ArrayList<RayTraceResult.RayTraceResultBlockHit> rayHitBlocks = new ArrayList<>();
        ArrayList<RayTraceResult.RayTraceResultEntityHit> rayHitEntitys = new ArrayList<>();

        while ((distance += stepSize) < maxDistance && counter++ < MAX_ITERATIONS) {

            if (rayLocation.getWorld() == null) return null;

            rayLocation.add(rayDirection).add(0, -counter * 0.001, 0);

            rayLocation.getWorld().spawnParticle(Particle.VILLAGER_HAPPY, rayLocation, 1);

            if (hitBlocks)
                if (hitBlock(rayDirection, rayLocation)) {
                    rayHitBlocks.add(new RayTraceResult.RayTraceResultBlockHit(distance, rayLocation.getBlock()));
                    if (terminateOnBlockHit) break;
                }


            if (hitEntitys)
                for (Entity entity : rayLocation.getWorld().getNearbyEntities(rayLocation, 0.1, 0.1, 0.1)) {
                    if (!(entity instanceof Zombie)) continue;

                    BoundingBox entBox = entity.getBoundingBox();

                    if (!entBox.contains(rayLocation.toVector())) continue;

                    entBox.shift(0, 1.5, 0);
                    rayHitEntitys.add(new RayTraceResult.RayTraceResultEntityHit(distance, entity, entBox.contains(rayLocation.toVector())));


                    if (terminateOnEntityHit) break;
                }
        }

        return new RayTraceResult(rayHitBlocks, rayHitEntitys);
    }

    public boolean hitBlock(Vector rayDirection, Location rayLoc) {
        Vec3 startPos = CraftLocation.toVec3D(rayLoc);
        Vec3 endPos = startPos.add(rayDirection.getX(), rayDirection.getY(), rayDirection.getZ());
        HitResult nmsHitResult = ((CraftWorld) rayLoc.getWorld()).getHandle().clip(new ClipContext(startPos, endPos, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, null));

        return nmsHitResult.getType() != HitResult.Type.MISS;
    }

    public RayTrace setStepSize(double stepSize) {
        this.stepSize = stepSize;
        return this;
    }

    public RayTrace setMaxDistance(double maxDistance) {
        this.maxDistance = maxDistance;
        return this;
    }

    public RayTrace terminateOnBlockHit() {
        terminateOnBlockHit = true;
        return this;
    }

    public RayTrace terminateOnEntityHit() {
        terminateOnEntityHit = true;
        return this;
    }

    public RayTrace excludeEntitys() {
        hitEntitys = false;
        return this;
    }

    public RayTrace excludeBlocks() {
        hitBlocks = false;
        return this;
    }

}
