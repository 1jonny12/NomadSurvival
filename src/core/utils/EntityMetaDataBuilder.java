package core.utils;

import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.Pose;
import org.bukkit.craftbukkit.v1_19_R3.util.CraftChatMessage;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class EntityMetaDataBuilder implements  Cloneable {

    /**
     * Entity MetaData Information can be found here.
     * https://wiki.vg/Entity_metadata
     **/

    //Entity MetaData
    private boolean isOnFire = false;
    private boolean isInvisible = false;
    private boolean isGlowing = false;
    private boolean isFlying = false;
    private Pose pose = Pose.STANDING;
    private String customName = null;


    public EntityMetaDataBuilder setOnFire(boolean value){
        isOnFire = value;
      return this;
    }

    public EntityMetaDataBuilder setInvisible(boolean value){
        isInvisible = value;
        return this;
    }

    public EntityMetaDataBuilder setGlowing(boolean value){
        isGlowing = value;
        return this;
    }

    public EntityMetaDataBuilder setFlying(boolean value){
        isFlying = value;
        return this;
    }

    public EntityMetaDataBuilder setPose(Pose value){
        pose = value;
        return this;
    }

    /**
     * Does not work on player. Player will always how their name.
     */
    public EntityMetaDataBuilder setCustomName(String name){
        customName = name;
        return this;
    }


    private byte createIndex1Byte(){
        byte data = 0;

        if (isOnFire) data += 0x01;
        if (isInvisible) data += 0x20;
        if (isGlowing) data += 0x40;
        if (isFlying) data += 0x80;

        return data;
    }

   public List<SynchedEntityData.DataValue<?>> createData(){
        List<SynchedEntityData.DataValue<?>> data = new ArrayList<>();

   //     data.add(new SynchedEntityData.DataValue<>(0, EntityDataSerializers.BYTE, createIndex1Byte()));
  //      data.add(new SynchedEntityData.DataValue<>(6, EntityDataSerializers.POSE, pose));

        if (customName != null) {
            Optional<Component> optionalName = Optional.ofNullable(CraftChatMessage.fromStringOrNull(customName));
            data.add(new SynchedEntityData.DataValue<>(2, EntityDataSerializers.OPTIONAL_COMPONENT, optionalName));
            data.add(new SynchedEntityData.DataValue<>(3, EntityDataSerializers.BOOLEAN, true));
        }

        return data;
    }

    @Override
    public EntityMetaDataBuilder clone() {
        try {
            return (EntityMetaDataBuilder) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
