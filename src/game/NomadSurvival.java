package game;

import core.scoreboard.ScoreBoardManager;
import game.ResourcePack.ResourcePackManager;
import game.commands.CommandRegister;
import game.customitems.CustomItemManager;
import game.gamePlayer.GPlayerManager;
import game.Undead.UndeadManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.BoundingBox;
import org.bukkit.util.Vector;

public class NomadSurvival extends JavaPlugin {


    public static Plugin PLUGIN;

    public static GPlayerManager G_PLAYER_MANAGER;
    public static UndeadManager AI_ENTITY_MANAGER;
    public static ScoreBoardManager SCOREBOARD_MANAGER;
    public static ResourcePackManager RESOURCE_PACK_MANAGER;
    public static CustomItemManager CUSTOM_ITEM_MANAGER;


    public static World VEHICLES_WORLD;

    @Override
    public void onEnable() {
        PLUGIN = this;

        getServer().getPluginManager().registerEvents(new Events(), this);

        VEHICLES_WORLD = Bukkit.createWorld(new WorldCreator("cars"));
        new CommandRegister();

        SCOREBOARD_MANAGER = new ScoreBoardManager();
        AI_ENTITY_MANAGER = new UndeadManager();
        G_PLAYER_MANAGER = new GPlayerManager();
        RESOURCE_PACK_MANAGER = new ResourcePackManager();
        CUSTOM_ITEM_MANAGER = new CustomItemManager();
    }


    public Entity rayTraceEntitiesWithParabolicArc(Player player, double maxDistance, double angleDegrees, double initialVelocity, double gravity) {
        Location playerLocation = player.getEyeLocation();
        Vector playerDirection = playerLocation.getDirection().normalize();
        Location rayLocation = playerLocation.clone();
        Vector rayVelocity = playerDirection.clone().multiply(initialVelocity);

        double angleRadians = Math.toRadians(angleDegrees);
        double stepSize = 0.1; // Adjust as needed

        for (double time = 0; time < maxDistance / initialVelocity; ) {
            double x = rayVelocity.getX() * time;
            double y = rayVelocity.getY() * time - 0.5 * gravity * time * time;
            double z = rayVelocity.getZ() * time;

            rayLocation.add(x, y, z);

            boolean hitEntity = false;

            for (Entity entity : rayLocation.getWorld().getEntities()) {
                BoundingBox entityBoundingBox = entity.getBoundingBox();
                if (entityBoundingBox.contains(rayLocation.toVector())) {
                    hitEntity = true;
                    break;
                }
            }

            if (hitEntity) {
                return entity;
            }

            // Adjust step size based on distance to the nearest entity
            double minDistance = Double.MAX_VALUE;
            for (Entity entity : rayLocation.getWorld().getEntities()) {
                double distance = entity.getLocation().distance(rayLocation);
                minDistance = Math.min(minDistance, distance);
            }

            // You can adjust the multiplier as needed
            time += Math.max(stepSize, minDistance * 0.2);
        }

        return null;
    }


}
