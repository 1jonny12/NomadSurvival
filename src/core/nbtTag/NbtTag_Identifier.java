/*
 * Copyright © 2019 - All Rights Reserved
 * This file belongs to Jonothan Ogden, Zac Malone
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 */

package core.nbtTag;

import org.bukkit.persistence.PersistentDataType;

public class NbtTag_Identifier extends NbtTag {

    public NbtTag_Identifier(String uniqueIdentifier) {
        super(uniqueIdentifier, PersistentDataType.INTEGER);
    }

    public NbtTagValued toValued() {
        return new NbtTagValued(super.uniqueIdentifier(), super.getPersistentDataType(), 1);
    }


}
