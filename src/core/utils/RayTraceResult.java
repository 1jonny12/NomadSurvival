package core.utils;

import jline.internal.Nullable;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;

import java.util.ArrayList;

public record RayTraceResult(@Nullable ArrayList<RayTraceResultBlockHit> hitBlocks,
                             @Nullable ArrayList<RayTraceResultEntityHit> hitEntity) {


    public boolean hasBlock() {
        return !hitBlocks.isEmpty();
    }

    public boolean hasEntity() {
        return !hitEntity().isEmpty();
    }

    public static class RayTraceResultHit {
        private final double hitDistance;

        public RayTraceResultHit(double hitDistance) {
            this.hitDistance = hitDistance;
        }

        public double getHitDistance() {
            return hitDistance;
        }
    }

    public static class RayTraceResultBlockHit extends RayTraceResultHit {
        private final Block hitBlock;

        public RayTraceResultBlockHit(double hitDistance, Block hitBlock) {
            super(hitDistance);
            this.hitBlock = hitBlock;
        }

        public Block getHitBlock() {
            return hitBlock;
        }
    }

    public static class RayTraceResultEntityHit extends RayTraceResultHit{
        private final Entity hitEntity;
        private final boolean wasHeadShot;

        public RayTraceResultEntityHit(double hitDistance, Entity hitEntity, boolean wasHeadShot) {
            super(hitDistance);
            this.hitEntity = hitEntity;
            this.wasHeadShot = wasHeadShot;
        }

        public Entity getHitEntity() {
            return hitEntity;
        }

        public boolean isWasHeadShot() {
            return wasHeadShot;
        }
    }

}
