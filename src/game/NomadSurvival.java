package game;

import core.commandBuilder.CommandBuilderManager;
import core.commandBuilder.CommandRegister;
import core.gamePlayer.GPlayerManager;
import game.Undead.UndeadManager;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class NomadSurvival extends JavaPlugin {


    public static Plugin PLUGIN;

    public static GPlayerManager G_PLAYER_MANAGER;
    public static UndeadManager AI_ENTITY_MANAGER;

    @Override
    public void onEnable() {
        PLUGIN = this;

        getServer().getPluginManager().registerEvents(new Events(), this);
        new CommandBuilderManager();
        new CommandRegister();

        AI_ENTITY_MANAGER = new UndeadManager();
        G_PLAYER_MANAGER = new GPlayerManager();
    }

}
