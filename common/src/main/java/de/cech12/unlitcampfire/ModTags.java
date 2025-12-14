package de.cech12.unlitcampfire;

import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import org.jetbrains.annotations.NotNull;

public class ModTags {

    public static class Items {

        public static final TagKey<Item> MAKES_CAMPFIRE_INFINITE = tag("makes_campfire_infinite");

        private static TagKey<Item> tag(@NotNull String name) {
            return TagKey.create(Registries.ITEM, Constants.id(name));
        }
    }

}
