package core.utils;

import core.builders.gui.GuiBuilderManager;
import core.nbtTag.NbtManager;

public class Util {
    public static final ErrorReporter ERROR_REPORTER = new ErrorReporter();
    public static final NbtManager NBT_MANAGER = new NbtManager();
    public static final GuiBuilderManager GUI_BUILDER_MANAGER = new GuiBuilderManager();
    public static final EntityMetaDataBuilder ENTITY_META_DATA_BUILDER = new EntityMetaDataBuilder();

    public static final Util_File FILE = new Util_File();
    public static final Util_Number NUMBER = new Util_Number();
    public static final Util_Player PLAYER = new Util_Player();
    public static final Util_Random RANDOM = new Util_Random();
    public static final Util_String STRING = new Util_String();

}
