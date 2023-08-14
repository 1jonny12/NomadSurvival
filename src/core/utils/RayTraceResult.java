package core.utils;

import jline.internal.Nullable;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;

public record RayTraceResult(@Nullable Block block, @Nullable Entity entity, @Nullable boolean wasHeadshot) {
    public RayTraceResult(Block block, Entity entity, boolean wasHeadshot) {
        this.block = block;
        this.entity = entity;
        this.wasHeadshot = wasHeadshot;
    }
}
