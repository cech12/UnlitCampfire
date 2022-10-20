package cech12.unlitcampfire;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

import javax.annotation.Nonnull;

public class ModTags {

    public static class Items {

        public static final TagKey<Item> MAKES_CAMPFIRE_INFINITE = tag("makes_campfire_infinite");

        private static TagKey<Item> tag(@Nonnull String name) {
            return TagKey.create(Registry.ITEM_REGISTRY, new ResourceLocation(UnlitCampfireMod.MOD_ID, name));
        }
    }

}
