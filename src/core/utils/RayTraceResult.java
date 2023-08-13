package core.utils;

import org.bukkit.block.Block;
import org.bukkit.entity.Entity;

public class RayTraceResult {

    private final Block block;
    private final Entity entity;

    public RayTraceResult(Block block, Entity entity) {
        this.block = block;
        this.entity = entity;
    }

    public Block getBlock() {
        return block;
    }

    public Entity getEntity() {
        return entity;
    }
}
