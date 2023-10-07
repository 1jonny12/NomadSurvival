package game;

import core.scoreboard.ScoreBoardManager;
import core.utils.Util;
import game.ResourcePack.ResourcePackManager;
import game.commands.CommandRegister;
import game.customitems.CustomItemManager;
import game.gamePlayer.GPlayerManager;
import game.Undead.UndeadManager;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class NomadSurvival extends JavaPlugin {


    public static Plugin PLUGIN;

    public static SecureMode SECURE_MODE;
    public static GPlayerManager G_PLAYER_MANAGER;
    public static UndeadManager AI_ENTITY_MANAGER;
    public static ScoreBoardManager SCOREBOARD_MANAGER;
    public static ResourcePackManager RESOURCE_PACK_MANAGER;
    public static CustomItemManager CUSTOM_ITEM_MANAGER;


    public static World VEHICLES_WORLD;

    @Override
    public void onEnable() {
        PLUGIN = this;
        preload();

    }

    public void preload() {
        getServer().getPluginManager().registerEvents( SECURE_MODE = new SecureMode(), this);

        if (isUsingRemappedJar()) {
            load();
        }
    }

        public void load() {
            getServer().getPluginManager().registerEvents(new Events(), this);

        VEHICLES_WORLD = Bukkit.createWorld(new WorldCreator("cars"));
        new CommandRegister();

        SCOREBOARD_MANAGER = new ScoreBoardManager();
        AI_ENTITY_MANAGER = new UndeadManager();
        G_PLAYER_MANAGER = new GPlayerManager();
        RESOURCE_PACK_MANAGER = new ResourcePackManager();
        CUSTOM_ITEM_MANAGER = new CustomItemManager();
    }

    private boolean isUsingRemappedJar() {
        try {
            Class.forName("net.minecraft.network.protocol.game.ClientboundSetEntityDataPacket");
            return true;
        } catch (ClassNotFoundException e) {
           Util.ERROR_REPORTER.reportToConsole(
                    "==================================================",
                    "==================================================",
                    "Plugin not using remapped jar... Running plugin in secure mode only.",
                   "==================================================",
                    "==================================================");

            SECURE_MODE.enableSecureMode("Plugin not using remapped jar");
            return false;
        }
    }


}
