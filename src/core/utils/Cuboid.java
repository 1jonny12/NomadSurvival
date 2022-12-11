package core.utils;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;

import java.util.ArrayList;

public class Cuboid {

    private final int x1;
    private final int y1;
    private final int z1;
    private final int x2;
    private final int y2;
    private final int z2;
    private final World world;

    public Cuboid(int x1, int y1, int z1, int x2, int y2, int z2, World world) {
        this.x1 = x1;
        this.y1 = y1;
        this.z1 = z1;
        this.x2 = x2;
        this.y2 = y2;
        this.z2 = z2;
        this.world = world;
    }

    public Cuboid(Location l1, Location l2) {
        x1 = l1.getBlockX();
        y1 = l1.getBlockY();
        z1 = l1.getBlockZ();
        x2 = l2.getBlockX();
        y2 = l2.getBlockY();
        z2 = l2.getBlockZ();
        world = l1.getWorld();
    }

    public Cuboid(SimpleLocation l1, SimpleLocation l2) {
        x1 = l1.getX();
        y1 = l1.getY();
        z1 = l1.getZ();
        x2 = l2.getX();
        y2 = l2.getY();
        z2 = l2.getZ();
        world = Bukkit.getWorld(l1.getWorldName());
    }

    public boolean isWithin(Location locToCheck) {
        boolean isX = Util.NUMBER.IsWithin(locToCheck.getX(), x1, x2, true);
        boolean isY = Util.NUMBER.IsWithin(locToCheck.getY(), y1, y2, true);
        boolean isZ = Util.NUMBER.IsWithin(locToCheck.getZ(), z1, z2, true);
        return isX && isY && isZ;
    }

    public boolean isWithin(SimpleLocation locToCheck) {
        boolean isX = Util.NUMBER.IsWithin(locToCheck.getX(), x1, x2, true);
        boolean isY = Util.NUMBER.IsWithin(locToCheck.getY(), y1, y2, true);
        boolean isZ = Util.NUMBER.IsWithin(locToCheck.getZ(), z1, z2, true);

        return isX && isY && isZ;
    }

    public boolean isWithin(Entity e) {
        return isWithin(e.getLocation());
    }

    public Location getRandomLocationWithin(){
        return new Location(world, Util.RANDOM.randomInt(x1, x2), Util.RANDOM.randomInt(y1, y2), Util.RANDOM.randomInt(z1, z2));
    }

    public void ToYML(String path, FileConfiguration configuration) {
        getP1().ToYML(path + ".Cuboid.P1", configuration);
        getP2().ToYML(path + ".Cuboid.P2", configuration);
    }

    public static Cuboid FromYML(String path, FileConfiguration configuration) {
        SimpleLocation P1 = SimpleLocation.FromYML(path + ".Cuboid.P1", configuration);
        SimpleLocation P2 = SimpleLocation.FromYML(path + ".Cuboid.P2", configuration);
        return new Cuboid(P1, P2);
    }

    public static Cuboid DEFAULT() {
        return new Cuboid(SimpleLocation.DEFAULT(), SimpleLocation.DEFAULT());
    }

    public boolean IS_DEFAULT() {
        return getP1().IS_DEFAULT() && getP2().IS_DEFAULT();
    }

    public SimpleLocation getP1(){
         return new SimpleLocation(x1, y1, z1, world.getName());
    }

    public SimpleLocation getP2(){
        return new SimpleLocation(x2, y2, z2, world.getName());
    }

    public ArrayList<Block> getBlocks(boolean includeAir) {
        ArrayList<Block> blocks = new ArrayList<>();

        for (int i = x1; i < x2; i++) {
            for (int j = y1; j < y2; j++) {
                for (int k = z1; k < z2; k++) {

                    Location loc = new Location(world, i,j,k);
                    Block b = loc.getBlock();

                    if (b.getType() == Material.AIR) {
                        if (includeAir) {
                            blocks.add(b);
                        }
                        continue;
                    }

                    blocks.add(b);
                }
            }
        }

        return blocks;
    }


    // --------------------------------
    // Default -> Getters and Setters
    // --------------------------------

    public int getX1() {
        return x1;
    }

    public int getY1() {
        return y1;
    }

    public int getZ1() {
        return z1;
    }

    public int getX2() {
        return x2;
    }

    public int getY2() {
        return y2;
    }

    public int getZ2() {
        return z2;
    }

    public World getWorld() {
        return world;
    }

}
