package core.gamePlayer;

import org.bukkit.configuration.file.YamlConfiguration;
import core.utils.Util;

import java.io.File;
import java.io.IOException;

public class GPlayerLoadAndSave {

    private final File file;
    private final YamlConfiguration config;

    private final GPlayer gPlayer;

    public GPlayerLoadAndSave(GPlayer gPlayer) {
        this.gPlayer = gPlayer;

        file = Util.FILE.getPlayerFile(gPlayer.getUniqueId());
        config = YamlConfiguration.loadConfiguration(file);
    }

    public void load(){
        gPlayer.setFirstJoinTimestamp(config.getLong("FirstJoinTimeStamp"));
    }

    public void save(){
        config.set("FirstJoinTimeStamp", gPlayer.getFirstJoinTimestamp());

        try {
            config.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
