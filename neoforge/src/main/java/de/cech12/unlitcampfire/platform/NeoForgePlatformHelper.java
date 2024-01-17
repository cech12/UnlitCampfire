package de.cech12.unlitcampfire.platform;

import de.cech12.unlitcampfire.platform.services.IPlatformHelper;
import net.minecraft.world.item.ItemStack;
import net.neoforged.fml.ModList;
import net.neoforged.fml.loading.FMLLoader;
import net.neoforged.neoforge.common.CommonHooks;

/**
 * The platform service implementation for Forge.
 */
public class NeoForgePlatformHelper implements IPlatformHelper {

    @Override
    public String getPlatformName() {
        return "Forge";
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
        return CommonHooks.getBurnTime(stack, null);
    }

    @Override
    public ItemStack getRemainingStackAfterUsage(ItemStack usedStack) {
        return usedStack.getCraftingRemainingItem();
    }

}
