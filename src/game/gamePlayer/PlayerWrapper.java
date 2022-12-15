package game.gamePlayer;

import core.utils.Util;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.craftbukkit.v1_19_R1.CraftServer;
import org.bukkit.craftbukkit.v1_19_R1.entity.CraftPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

public class PlayerWrapper extends CraftPlayer {
    public PlayerWrapper(Player p) {
        super((CraftServer) p.getServer(), ((CraftPlayer) p).getHandle());
    }

    public Entity spawnEntityAtPlayer(EntityType entityType){
        return getWorld().spawnEntity(getLocation(), entityType);
    }




    @Override
    public void sendMessage(String message) {
        super.sendMessage(Util.STRING.formatString(message));
    }

    @Override
    public void sendMessage(String... messages) {
        for (String m : messages) {
            sendMessage(m);
        }
    }

    public void sendActionBar(String message) {
        spigot().sendMessage(net.md_5.bungee.api.ChatMessageType.ACTION_BAR, new TextComponent(Util.STRING.formatString(message)));
    }

}
