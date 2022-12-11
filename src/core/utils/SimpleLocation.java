/*
 * Copyright © 2019 - All Rights Reserved
 * This file belongs to Jonothan Ogden, Zac Malone
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 */

package core.utils;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.Objects;

public class SimpleLocation {

    private final String worldName;
    private int x, y, z;

    public SimpleLocation(int x, int y, int z, String worldName) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.worldName = worldName;
    }

    public SimpleLocation(Location bukkitLocation) {
        x = bukkitLocation.getBlockX();
        y = bukkitLocation.getBlockY();
        z = bukkitLocation.getBlockZ();
        worldName = bukkitLocation.getWorld().getName();
    }

    public static SimpleLocation DEFAULT() {
        return new SimpleLocation(0, 255, 0, Bukkit.getWorlds().get(0).getName());
    }

    public static SimpleLocation FromYML(String path, FileConfiguration configuration) {
        if (!configuration.contains(path)) {
            return DEFAULT();
        }

        int X = configuration.getInt(path + ".SimpleLocation.X");
        int Y = configuration.getInt(path + ".SimpleLocation.Y");
        int Z = configuration.getInt(path + ".SimpleLocation.Z");
        String WorldName = configuration.getString(path + ".SimpleLocation.WorldName");
        return new SimpleLocation(X, Y, Z, WorldName);
    }

    public boolean IS_DEFAULT() {
        return x == 0 && y == 255 && z == 0;
    }

    public void ToYML(String path, FileConfiguration configuration) {
        configuration.set(path + ".SimpleLocation.X", x);
        configuration.set(path + ".SimpleLocation.Y", y);
        configuration.set(path + ".SimpleLocation.Z", z);
        configuration.set(path + ".SimpleLocation.WorldName", worldName);
    }

    public Location ToMc() {
        return new Location(Bukkit.getWorld(worldName), x, y, z);
    }

    public SimpleLocation Clone() {
        return new SimpleLocation(x, y, z, worldName);
    }

    public SimpleLocation Add(int x, int y, int z) {
        this.x += x;
        this.y += y;
        this.z += z;
        return this;
    }

    public SimpleLocation Add(SimpleLocation simpleLocation) {
        this.x += simpleLocation.x;
        this.y += simpleLocation.y;
        this.z += simpleLocation.z;
        return this;
    }

    public SimpleLocation AddX(int x) {
        this.x += x;
        return this;
    }

    public SimpleLocation AddY(int y) {
        this.y += y;
        return this;
    }

    public SimpleLocation AddZ(int z) {
        this.z += z;
        return this;
    }

    public SimpleLocation SetX(int x) {
        this.x = x;
        return this;
    }

    public SimpleLocation SetY(int y) {
        this.y = y;
        return this;
    }

    public SimpleLocation SetZ(int z) {
        this.z = z;
        return this;
    }

    public boolean XYZSame(SimpleLocation simpleLocation) {
        return x == simpleLocation.x && z == simpleLocation.z && y == simpleLocation.y;

    }

    public void TpPlayer(Player p) {
        p.teleport(ToMc().clone().add(0.5, 0.5, 0.5));
    }

    public Block getBlock() {
        return ToMc().getBlock();
    }

    public String toXYZString() {
        return x + "" + y + "" + z + "";
    }
//------------------------------------------------------------------------------------------------------------------------------
    //##############################################################################################################################
    //Default - Getters and Setters
    //##############################################################################################################################
    //------------------------------------------------------------------------------------------------------------------------------


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SimpleLocation that = (SimpleLocation) o;
        return x == that.x &&
                y == that.y &&
                z == that.z &&
                worldName.equals(that.worldName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, z, worldName);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getZ() {
        return z;
    }

    public String getWorldName() {
        return worldName;
    }

    @Override
    public String toString() {
        return "SimpleLocation{" +
                "x=" + x +
                ", y=" + y +
                ", z=" + z +
                ", worldName='" + worldName + '\'' +
                '}';
    }


}
