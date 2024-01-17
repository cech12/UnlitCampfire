package de.cech12.unlitcampfire.platform.services;

import net.minecraft.world.item.ItemStack;

/**
 * Common platform helper service interface.
 */
public interface IPlatformHelper {

    /**
     * Gets the name of the current platform
     *
     * @return The name of the current platform.
     */
    String getPlatformName();

    /**
     * Checks if a mod with the given id is loaded.
     *
     * @param modId The mod to check if it is loaded.
     * @return True if the mod is loaded, false otherwise.
     */
    boolean isModLoaded(String modId);

    /**
     * Check if the game is currently in a development environment.
     *
     * @return True if in a development environment, false otherwise.
     */
    boolean isDevelopmentEnvironment();

    /**
     * Gets the name of the environment type as a string.
     *
     * @return The name of the environment type.
     */
    default String getEnvironmentName() {
        return isDevelopmentEnvironment() ? "development" : "production";
    }

    /**
     * Gets the burn time of a given item stack
     *
     * @param stack item stack
     * @return burn time of the given item stack
     */
    int getBurnTimeOf(ItemStack stack);

    /**
     * Gets the item stack that remains after usage of the given item stack.
     *
     * @param usedStack used item stack
     * @return remaining item stack after usage
     */
    ItemStack getRemainingStackAfterUsage(ItemStack usedStack);
}