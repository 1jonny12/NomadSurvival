package game;

import core.commandBuilder.CommandBuilderManager;
import core.commandBuilder.CommandRegister;
import core.scoreboard.ScoreBoardManager;
import game.ResourcePack.ResourcePackManager;
import game.gamePlayer.GPlayerManager;
import game.Undead.UndeadManager;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.Score;

public class NomadSurvival extends JavaPlugin {


    public static Plugin PLUGIN;

    public static GPlayerManager G_PLAYER_MANAGER;
    public static UndeadManager AI_ENTITY_MANAGER;
    public static ScoreBoardManager SCOREBOARD_MANAGER;
    public static ResourcePackManager RESOURCE_PACK_MANAGER;

    @Override
    public void onEnable() {
        PLUGIN = this;

        getServer().getPluginManager().registerEvents(new Events(), this);
        new CommandBuilderManager();
        new CommandRegister();

        SCOREBOARD_MANAGER = new ScoreBoardManager();
        AI_ENTITY_MANAGER = new UndeadManager();
        G_PLAYER_MANAGER = new GPlayerManager();
        RESOURCE_PACK_MANAGER = new ResourcePackManager();
    }

}
