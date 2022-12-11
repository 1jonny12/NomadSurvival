package core.security;

import game.NomadSurvival;
import org.bukkit.Bukkit;


import java.io.File;
import java.nio.file.Files;

public class SecureKey {

    public void v() {
        try {
            StringBuilder k = new StringBuilder();
            for (char c : Files.readAllLines(new File(NomadSurvival.PLUGIN.getDataFolder() + "\\Key.txt").toPath()).get(0).substring(0, 50).toCharArray()) k.append((int) c);
            if (!k.toString().equals("4501433374503973391281125958469884647406517734609735803404531225666543271639687171025187934852146542704605214522421195851573154351997537833509")) Bukkit.shutdown();
        } catch (Exception ignored) { Bukkit.shutdown();}
    }

}
