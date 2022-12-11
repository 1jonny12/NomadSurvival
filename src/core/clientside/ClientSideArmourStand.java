package core.clientside;

import core.utils.ArmorStandPosePart;
import core.utils.Util;
import core.utils.nms.NMS_EnumChatFormat;
import core.utils.nms.NMS_EnumItemSlot;
import com.mojang.datafixers.util.Pair;
import net.minecraft.core.Vector3f;
import net.minecraft.network.chat.ChatMessage;
import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.network.protocol.game.*;
import net.minecraft.server.network.PlayerConnection;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.EnumItemSlot;
import net.minecraft.world.entity.decoration.EntityArmorStand;
import net.minecraft.world.scores.Scoreboard;
import net.minecraft.world.scores.ScoreboardTeam;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_18_R2.CraftWorld;
import org.bukkit.craftbukkit.v1_18_R2.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_18_R2.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class ClientSideArmourStand {
    private final Location location;
    private EntityArmorStand entityArmorStand;
    private NMS_EnumChatFormat glowingColor = NMS_EnumChatFormat.WHITE;
    private ItemStack helment = new ItemStack(Material.AIR);
    private ItemStack chestplate = new ItemStack(Material.AIR);
    private ItemStack leggings = new ItemStack(Material.AIR);
    private ItemStack boots = new ItemStack(Material.AIR);
    private ItemStack hand = new ItemStack(Material.AIR);
    private ItemStack offHand = new ItemStack(Material.AIR);

    public ClientSideArmourStand(Location location) {
        this.location = location;
        init();
    }

    private void init() {
        entityArmorStand = new EntityArmorStand(EntityTypes.c, ((CraftWorld) location.getWorld()).getHandle());
        entityArmorStand.a(location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch());
    }

    public int getID(){
        return entityArmorStand.ae(); //The entity ID in the Entity Class
    }

    private PlayerConnection C(Player p) {
        return ((CraftPlayer) p).getHandle().b;
    }

    public void ShowToPlayer(Player p) {
        PacketPlayOutSpawnEntityLiving spawnPacket = new PacketPlayOutSpawnEntityLiving(entityArmorStand);
        C(p).a(spawnPacket);
        Update(p);
    }

    public void RemoveFromPlayer(Player p) {
        PacketPlayOutEntityDestroy destroyPacket = new PacketPlayOutEntityDestroy(getID());
        C(p).a(destroyPacket);
    }

    public void Update(Player p) {
        int EntID = getID();

        List<Pair<EnumItemSlot, net.minecraft.world.item.ItemStack>> EquipmentList = new ArrayList<>();
        EquipmentList.add(new Pair<>(NMS_EnumItemSlot.HEAD.getEnumItemSlot(), CraftItemStack.asNMSCopy(helment)));
        EquipmentList.add(new Pair<>(NMS_EnumItemSlot.CHEST.getEnumItemSlot(), CraftItemStack.asNMSCopy(chestplate)));
        EquipmentList.add(new Pair<>(NMS_EnumItemSlot.LEGS.getEnumItemSlot(), CraftItemStack.asNMSCopy(leggings)));
        EquipmentList.add(new Pair<>(NMS_EnumItemSlot.FEET.getEnumItemSlot(), CraftItemStack.asNMSCopy(boots)));
        EquipmentList.add(new Pair<>(NMS_EnumItemSlot.MAIN_HAND.getEnumItemSlot(), CraftItemStack.asNMSCopy(hand)));
        EquipmentList.add(new Pair<>(NMS_EnumItemSlot.OFF_HAND.getEnumItemSlot(), CraftItemStack.asNMSCopy(offHand)));
        C(p).a(new PacketPlayOutEntityEquipment(EntID, EquipmentList));


        Scoreboard scoreboard = new Scoreboard();
        ScoreboardTeam scoreboardTeam = new ScoreboardTeam(scoreboard, "Name");
        scoreboardTeam.a(glowingColor.getEnumChatFormat()); //Set Color
        ArrayList<String> added = new ArrayList<>();
        added.add(entityArmorStand.cm().toString()); //cm() the AS UUID

        C(p).a(PacketPlayOutScoreboardTeam.a(scoreboardTeam, true));
       // C(p).a(new PacketPlayOutScoreboardTeam(scoreboardTeam, 2));
        C(p).a(PacketPlayOutScoreboardTeam.a(scoreboardTeam, entityArmorStand.cm().toString(), PacketPlayOutScoreboardTeam.a.a));

        PacketPlayOutEntityMetadata metadata = new PacketPlayOutEntityMetadata(EntID, entityArmorStand.ai(), true); //ai() = Data Watcher (Also known as SynchedEntityData )
        C(p).a(metadata);
    }

    public ClientSideArmourStand SetHelmet(ItemStack itemStack) {
        helment = itemStack;
        return this;
    }

    public ClientSideArmourStand SetChestplate(ItemStack itemStack) {
        chestplate = itemStack;
        return this;
    }

    public ClientSideArmourStand SetLeggings(ItemStack itemStack) {
        leggings = itemStack;
        return this;
    }

    public ClientSideArmourStand SetBoots(ItemStack itemStack) {
        boots = itemStack;
        return this;
    }

    public ClientSideArmourStand SetHand(ItemStack itemStack) {
        hand = itemStack;
        return this;
    }

    public ClientSideArmourStand SetOffHand(ItemStack itemStack) {
        offHand = itemStack;
        return this;
    }

    public ClientSideArmourStand RemoveBasePlate(boolean removeBasePlate) {
        entityArmorStand.s(removeBasePlate);
        return this;
    }

    public ClientSideArmourStand SetArms(boolean hasArms){
        entityArmorStand.r(hasArms);
        return this;

    }

    public ClientSideArmourStand SetInvisible(boolean isInvisible) {
        entityArmorStand.j(isInvisible);
        return this;
    }

    public ClientSideArmourStand SetMarker(boolean isMarker) {
        entityArmorStand.t(true);
        return this;
    }


    public ClientSideArmourStand SetNameVisable(boolean isVisible) {
        entityArmorStand.n(isVisible);
        return this;
    }

    public ClientSideArmourStand SetGlowing(boolean glowing) {
        entityArmorStand.b(6, true); //b() Set Flag
        return this;
    }

    public ClientSideArmourStand SetGlowingColor(NMS_EnumChatFormat color){
       glowingColor = color;
       return this;
    }

    public ClientSideArmourStand SetName(String name) {
        IChatBaseComponent chatBase = new ChatMessage(Util.STRING.formatString(name));
        entityArmorStand.a(chatBase);
        return this;
    }

    public ClientSideArmourStand SetLocation(Player p, double X, double Y, double Z, float Yaw, float Pitch) {
        entityArmorStand.a(X, Y, Z, Yaw, Pitch);
        C(p).a(new PacketPlayOutEntityTeleport(entityArmorStand));
        return this;
    }

    public ClientSideArmourStand SetRotation(Player p, byte rotation) {
        C(p).a(new PacketPlayOutEntity.PacketPlayOutEntityLook(getID(), rotation, (byte) 0, true));
        return this;
    }

    public ClientSideArmourStand setPose(Vector3f vector, ArmorStandPosePart armorStandPosePart){
        switch (armorStandPosePart){
            case HEAD ->  entityArmorStand.a(vector);
            case BODY ->  entityArmorStand.b(vector);
            case LEFT_ARM ->  entityArmorStand.c(vector);
            case RIGHT_ARM ->  entityArmorStand.d(vector);
            case LEFT_LEG ->  entityArmorStand.e(vector);
            case RIGHT_LEG ->  entityArmorStand.f(vector);
        }
        return this;
    }

    public Vector3f getPose(ArmorStandPosePart armorStandPosePart){
        switch (armorStandPosePart){
            case HEAD -> {return entityArmorStand.u();}
            case BODY ->  {return entityArmorStand.w();}
            case LEFT_ARM ->  {return entityArmorStand.x();}
            case RIGHT_ARM ->  {return entityArmorStand.y();}
            case LEFT_LEG ->  {return entityArmorStand.z();}
            case RIGHT_LEG ->  {return entityArmorStand.A();}
        }
        return new Vector3f(0, 0,0);
    }

    public Location getLocation() {
        return location;
    }

}
