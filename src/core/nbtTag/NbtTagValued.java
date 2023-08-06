/*
 * Copyright © 2019 - All Rights Reserved
 * This file belongs to Jonothan Ogden, Zac Malone
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 */

package core.nbtTag;

import org.bukkit.persistence.PersistentDataType;

public class NbtTagValued extends NbtTag  {

    private final Object data;

    public NbtTagValued(String uniqueIdentifier, PersistentDataType persistentDataType, Object data) {
        super(uniqueIdentifier, persistentDataType);
        this.data = data;
    }

    public Object data() {
        return data;
    }
}
