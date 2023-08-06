package game;

import core.scoreboard.ScoreBoardManager;
import game.ResourcePack.ResourcePackManager;
import game.commands.CommandRegister;
import game.gamePlayer.GPlayerManager;
import game.Undead.UndeadManager;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class NomadSurvival extends JavaPlugin {


    public static Plugin PLUGIN;

    public static GPlayerManager G_PLAYER_MANAGER;
    public static UndeadManager AI_ENTITY_MANAGER;
    public static ScoreBoardManager SCOREBOARD_MANAGER;
    public static ResourcePackManager RESOURCE_PACK_MANAGER;


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
    }

}
