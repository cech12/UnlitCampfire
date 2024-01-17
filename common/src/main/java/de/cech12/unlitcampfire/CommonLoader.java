package de.cech12.unlitcampfire;

import de.cech12.unlitcampfire.mixinaccess.ICampfireBlockEntityMixin;
import de.cech12.unlitcampfire.mixinaccess.ICampfireBlockMixin;
import de.cech12.unlitcampfire.platform.Services;
import net.minecraft.world.level.block.CampfireBlock;
import net.minecraft.world.level.block.entity.BlockEntity;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A static class for all loaders which initializes everything which is used by all loaders.
 */
public class CommonLoader {

    private static final Set<BlockEntity> CAMPFIRES = new HashSet<>();

    /**
     * Initialize method that should be called by every loader mod in the constructor.
     */
    public static void init() {
        Services.CONFIG.init();
    }

    private CommonLoader() {}

    public static void addCampfire(BlockEntity blockEntity) {
        if (blockEntity != null && blockEntity.getLevel() != null && !blockEntity.getLevel().isClientSide) {
            CAMPFIRES.add(blockEntity);
        }
    }

    public static void updateCampfiresAfterSleep(int sleepTime) {
        CAMPFIRES.removeIf(Objects::isNull);
        CAMPFIRES.removeIf(BlockEntity::isRemoved);
        CAMPFIRES.stream()
                .filter(campfire -> campfire.getBlockState().getValue(CampfireBlock.LIT))
                .filter(campfire -> !campfire.getBlockState().getValue(ICampfireBlockMixin.INFINITE))
                .filter(campfire -> campfire instanceof ICampfireBlockEntityMixin)
                .map(campfire -> (ICampfireBlockEntityMixin) campfire)
                .filter(campfire -> Services.CONFIG.isAffectedBySleepTime(campfire.unlitCampfire$isSoulCampfire()))
                .forEach(campfire -> campfire.unlitCampfire$removeLitTime(sleepTime));
    }

}
