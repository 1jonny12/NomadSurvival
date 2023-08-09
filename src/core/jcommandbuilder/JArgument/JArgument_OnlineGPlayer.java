package core.jcommandbuilder.JArgument;

import game.NomadSurvival;
import game.gamePlayer.GPlayer;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import javax.swing.*;

public class JArgument_OnlineGPlayer extends JArgument<GPlayer> {
    public JArgument_OnlineGPlayer(String argName) {
        super(argName);
    }

    @Override
    protected GPlayer convert(String rawGivenArg) {
        Player p = Bukkit.getPlayer(rawGivenArg);

        if (p != null) return NomadSurvival.G_PLAYER_MANAGER.getGPlayer(p);


        p = Bukkit.getOfflinePlayer(rawGivenArg).getPlayer();

        // ide does not recognize this, it will not be null as validate() is called before this method.
        return NomadSurvival.G_PLAYER_MANAGER.getGPlayer(p);
    }

    @Override
    public JArgumentValidateResponse validate(String rawGivenArg) {

        Player p = Bukkit.getPlayer(rawGivenArg);
        if (p == null) return new JArgumentValidateResponse(true, "Requested player was not found.");

        p = Bukkit.getOfflinePlayer(rawGivenArg).getPlayer();
        if (p == null) return new JArgumentValidateResponse(true, "Requested player was not found.");

        if (!p.isOnline()) return new JArgumentValidateResponse(true, "Requested player is not online.");

        return VALID;

    }
}
