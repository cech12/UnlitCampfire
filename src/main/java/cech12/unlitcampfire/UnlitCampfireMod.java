package cech12.unlitcampfire;

import cech12.unlitcampfire.config.ServerConfig;
import cech12.unlitcampfire.mixinaccess.ICampfireBlockEntityMixin;
import cech12.unlitcampfire.mixinaccess.ICampfireBlockMixin;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.CampfireBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.event.level.SleepFinishedTimeEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.loading.FMLConfig;
import net.minecraftforge.fml.loading.FMLPaths;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Mod(UnlitCampfireMod.MOD_ID)
@Mod.EventBusSubscriber
public class UnlitCampfireMod {
    public static final String MOD_ID = "unlitcampfire";

    private static final Set<BlockEntity> CAMPFIRES = new HashSet<>();

    public UnlitCampfireMod() {
        //Config
        ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, ServerConfig.SERVER_CONFIG);
        ServerConfig.loadConfig(ServerConfig.SERVER_CONFIG, FMLPaths.GAMEDIR.get().resolve(FMLConfig.defaultConfigPath()).resolve(MOD_ID + "-server.toml"));

        //The One Probe registration.
        //if (ModList.get().isLoaded("theoneprobe")) {
            //TOPCompat.register();
        //}
    }

    public static void addCampfire(BlockEntity blockEntity) {
        if (blockEntity != null && blockEntity.getLevel() != null && !blockEntity.getLevel().isClientSide) {
            CAMPFIRES.add(blockEntity);
        }
    }

    @SubscribeEvent
    public static void onSleepFinishTimeEvent(SleepFinishedTimeEvent event) {
        LevelAccessor level = event.getLevel();
        if (level.isClientSide()) return;
        int sleepTime = (int) ((event.getNewTime() >= level.dayTime()) ? (event.getNewTime() - level.dayTime()) : (24000L - level.dayTime() + event.getNewTime()));
        CAMPFIRES.removeIf(Objects::isNull);
        CAMPFIRES.removeIf(BlockEntity::isRemoved);
        CAMPFIRES.stream()
                .filter(campfire -> campfire.getBlockState().getValue(CampfireBlock.LIT))
                .filter(campfire -> !campfire.getBlockState().getValue(ICampfireBlockMixin.INFINITE))
                .filter(campfire -> campfire instanceof ICampfireBlockEntityMixin)
                .map(campfire -> (ICampfireBlockEntityMixin) campfire)
                .filter(campfire -> campfire.isSoulCampfire() ? ServerConfig.SOUL_CAMPFIRE_AFFECTED_BY_SLEEP_TIME.get() : ServerConfig.CAMPFIRE_AFFECTED_BY_SLEEP_TIME.get())
                .forEach(campfire -> campfire.removeLitTime(sleepTime));
    }

}
