package core.utils;

import game.NomadSurvival;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class Util_File {

    public File getFileInPluginsFolder(String filePath) {
        final File file = new File(NomadSurvival.PLUGIN.getDataFolder() + File.separator + filePath);
        if (!file.exists()) {
            file.getParentFile().mkdirs();
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return file;
    }

    public boolean hasFile(String filePath) {


        return new File(NomadSurvival.PLUGIN.getDataFolder() + File.separator + filePath).exists();
    }
    public File getPlayerFile(UUID playerUUID) {
        return getFileInPluginsFolder("PlayerData" + File.separator + playerUUID.toString() + ".yml");
    }


}
