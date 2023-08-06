/*
 * Copyright © 2019 - All Rights Reserved
 * This file belongs to Jonothan Ogden, Zac Malone
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 */

package core.nbtTag;

import org.bukkit.persistence.PersistentDataType;

public class NbtTag{

    private final String uniqueIdentifier;
    private final PersistentDataType persistentDataType;

    public NbtTag(String uniqueIdentifier, PersistentDataType persistentDataType) {
        this.uniqueIdentifier = uniqueIdentifier;
        this.persistentDataType = persistentDataType;
    }

    public NbtTagValued toValued(Object data) {
        return new NbtTagValued(uniqueIdentifier, persistentDataType, data);
    }

    public String uniqueIdentifier() {
        return uniqueIdentifier;
    }

    public PersistentDataType getPersistentDataType() {
        return persistentDataType;
    }
}
