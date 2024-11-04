package de.cech12.unlitcampfire.platform;

import de.cech12.unlitcampfire.platform.services.IPlatformHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.loading.FMLLoader;

/**
 * The platform service implementation for Forge.
 */
public class ForgePlatformHelper implements IPlatformHelper {

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
    public int getBurnTimeOf(Level level, ItemStack stack) {
        return level.fuelValues().burnDuration(stack);
    }

    @Override
    public ItemStack getRemainingStackAfterUsage(ItemStack usedStack) {
        return usedStack.getItem().getCraftingRemainder(usedStack);
    }

}
