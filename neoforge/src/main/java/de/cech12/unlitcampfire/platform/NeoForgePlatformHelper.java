package de.cech12.unlitcampfire.platform;

import de.cech12.unlitcampfire.platform.services.IPlatformHelper;
import net.minecraft.world.item.ItemStack;
import net.neoforged.fml.ModList;
import net.neoforged.fml.loading.FMLLoader;

/**
 * The platform service implementation for NeoForge.
 */
public class NeoForgePlatformHelper implements IPlatformHelper {

    @Override
    public String getPlatformName() {
        return "NeoForge";
    }

    @Override
    public boolean isModLoaded(String modId) {
        return ModList.get().isLoaded(modId);
    }

    @Override
    public boolean isDevelopmentEnvironment() {
        return !FMLLoader.isProduction();
    }

    @Override
    public int getBurnTimeOf(ItemStack stack) {
        return stack.getBurnTime(null);
    }

    @Override
    public ItemStack getRemainingStackAfterUsage(ItemStack usedStack) {
        return usedStack.getCraftingRemainingItem();
    }

}
