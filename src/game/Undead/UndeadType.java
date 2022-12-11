package game.Undead;

import game.Undead.UndeadTypes.BasicUndead;

public enum UndeadType {

    BASIC(BasicUndead.class);

    UndeadType(Class<? extends Undead> undeadType) {
        this.undeadType = undeadType;
    }

    private final Class<? extends Undead> undeadType;

    //----------------------------------------------------
    // [Default] -> Getters and Setters
    //----------------------------------------------------


    public Class<? extends Undead> getUndeadType() {
        return undeadType;
    }
}
