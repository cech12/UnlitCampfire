package de.cech12.unlitcampfire.platform;

import de.cech12.unlitcampfire.platform.services.IPlatformHelper;
import net.fabricmc.fabric.api.registry.FuelRegistry;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.world.item.ItemStack;

/**
 * The platform service implementation for Fabric.
 */
public class FabricPlatformHelper implements IPlatformHelper {

    @Override
    public String getPlatformName() {
        return "Fabric";
    }

    @Override
    public boolean isModLoaded(String modId) {
        return FabricLoader.getInstance().isModLoaded(modId);
    }

    @Override
    public boolean isDevelopmentEnvironment() {
        return FabricLoader.getInstance().isDevelopmentEnvironment();
    }

    @Override
    public int getBurnTimeOf(ItemStack stack) {
        Integer burnTime = FuelRegistry.INSTANCE.get(stack.getItem());
        return burnTime != null ? burnTime : 0;
    }

    @Override
    public ItemStack getRemainingStackAfterUsage(ItemStack usedStack) {
        return usedStack.getRecipeRemainder();
    }

}
