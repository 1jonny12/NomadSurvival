package core.nbtTag;

public enum TagType {

    INT(Integer.class),
    DOUBLE(Double.class),
    BYTE(Byte.class),
    STRING(String.class),
    BOOLEAN(Boolean.class);

    private final Class<?> classType;

    TagType(Class<?> classType) {
        this.classType = classType;
    }

    //----------------------------------------------------
    // [Default] -> Getters and Setters
    //----------------------------------------------------

    public Class<?> getClassType() {
        return classType;
    }
}