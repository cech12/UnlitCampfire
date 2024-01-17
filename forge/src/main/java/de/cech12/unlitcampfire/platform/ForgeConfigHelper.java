package de.cech12.unlitcampfire.platform;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.electronwill.nightconfig.core.io.WritingMode;
import de.cech12.unlitcampfire.Constants;
import de.cech12.unlitcampfire.ForgeUnlitCampfireMod;
import de.cech12.unlitcampfire.mixinaccess.ICampfireBlockMixin;
import de.cech12.unlitcampfire.platform.services.IConfigHelper;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CampfireBlock;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.config.ModConfigEvent;
import net.minecraftforge.fml.loading.FMLConfig;
import net.minecraftforge.fml.loading.FMLPaths;

import java.nio.file.Path;

/**
 * The config service implementation for Forge.
 */
@Mod.EventBusSubscriber(modid = ForgeUnlitCampfireMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ForgeConfigHelper implements IConfigHelper {

    private static final ForgeConfigSpec SERVER_CONFIG;

    public static final ForgeConfigSpec.IntValue CAMPFIRE_LIT_TIME;
    public static final ForgeConfigSpec.IntValue CAMPFIRE_RAIN_UNLIT_TIME;
    public static final ForgeConfigSpec.IntValue CAMPFIRE_RAIN_PARTICLE_FACTOR;
    public static final ForgeConfigSpec.BooleanValue CAMPFIRE_DROPS_ITEMS_WHEN_UNLIT_BY_TIME_OR_RAIN;
    public static final ForgeConfigSpec.BooleanValue CAMPFIRE_BREAKS_WHEN_UNLIT_BY_TIME;
    public static final ForgeConfigSpec.BooleanValue CAMPFIRE_ADDING_BURNABLES;
    public static final ForgeConfigSpec.BooleanValue CAMPFIRE_AFFECTED_BY_SLEEP_TIME;
    public static final ForgeConfigSpec.BooleanValue GENERATED_CAMPFIRE_IS_LIT_INFINITELY;

    public static final ForgeConfigSpec.IntValue SOUL_CAMPFIRE_LIT_TIME;
    public static final ForgeConfigSpec.IntValue SOUL_CAMPFIRE_RAIN_UNLIT_TIME;
    public static final ForgeConfigSpec.IntValue SOUL_CAMPFIRE_RAIN_PARTICLE_FACTOR;
    public static final ForgeConfigSpec.BooleanValue SOUL_CAMPFIRE_DROPS_ITEMS_WHEN_UNLIT_BY_TIME_OR_RAIN;
    public static final ForgeConfigSpec.BooleanValue SOUL_CAMPFIRE_BREAKS_WHEN_UNLIT_BY_TIME;
    public static final ForgeConfigSpec.BooleanValue SOUL_CAMPFIRE_ADDING_BURNABLES;
    public static final ForgeConfigSpec.BooleanValue SOUL_CAMPFIRE_AFFECTED_BY_SLEEP_TIME;
    public static final ForgeConfigSpec.BooleanValue GENERATED_SOUL_CAMPFIRE_IS_LIT_INFINITELY;

    static {
        final ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();

        builder.push("unlitcampfire");

        CAMPFIRE_LIT_TIME = builder
                .comment(CAMPFIRE_LIT_TIME_DESCRIPTION)
                .defineInRange("campfireLitTime", CAMPFIRE_LIT_TIME_DEFAULT, CAMPFIRE_LIT_TIME_MIN, CAMPFIRE_LIT_TIME_MAX);

        CAMPFIRE_RAIN_UNLIT_TIME = builder
                .comment(CAMPFIRE_RAIN_UNLIT_TIME_DESCRIPTION)
                .defineInRange("campfireRainUnlitTime", CAMPFIRE_RAIN_UNLIT_TIME_DEFAULT, CAMPFIRE_RAIN_UNLIT_TIME_MIN, CAMPFIRE_RAIN_UNLIT_TIME_MAX);

        CAMPFIRE_RAIN_PARTICLE_FACTOR = builder
                .comment(CAMPFIRE_RAIN_PARTICLE_FACTOR_DESCRIPTION)
                .defineInRange("campfireRainParticleFactor", CAMPFIRE_RAIN_PARTICLE_FACTOR_DEFAULT, CAMPFIRE_RAIN_PARTICLE_FACTOR_MIN, CAMPFIRE_RAIN_PARTICLE_FACTOR_MAX);

        CAMPFIRE_DROPS_ITEMS_WHEN_UNLIT_BY_TIME_OR_RAIN = builder
                .comment(CAMPFIRE_DROPS_ITEMS_WHEN_UNLIT_BY_TIME_OR_RAIN_DESCRIPTION)
                .define("campfireDropsItemsWhenUnlitByTimeOrRain", CAMPFIRE_DROPS_ITEMS_WHEN_UNLIT_BY_TIME_OR_RAIN_DEFAULT);

        CAMPFIRE_BREAKS_WHEN_UNLIT_BY_TIME = builder
                .comment(CAMPFIRE_BREAKS_WHEN_UNLIT_BY_TIME_DESCRIPTION)
                .define("campfireBreaksWhenUnlitByTime", CAMPFIRE_BREAKS_WHEN_UNLIT_BY_TIME_DEFAULT);

        CAMPFIRE_ADDING_BURNABLES = builder
                .comment(CAMPFIRE_ADDING_BURNABLES_DESCRIPTION)
                .define("campfireAddingBurnables", CAMPFIRE_ADDING_BURNABLES_DEFAULT);

        CAMPFIRE_AFFECTED_BY_SLEEP_TIME = builder
                .comment(CAMPFIRE_AFFECTED_BY_SLEEP_TIME_DESCRIPTION)
                .define("campfireAffectedBySleepTime", CAMPFIRE_AFFECTED_BY_SLEEP_TIME_DEFAULT);

        GENERATED_CAMPFIRE_IS_LIT_INFINITELY = builder
                .comment(GENERATED_CAMPFIRE_IS_LIT_INFINITELY_DESCRIPTION)
                .define("generatedCampfireIsLitInfinitely", GENERATED_CAMPFIRE_IS_LIT_INFINITELY_DEFAULT);

        SOUL_CAMPFIRE_LIT_TIME = builder
                .comment(SOUL_CAMPFIRE_LIT_TIME_DESCRIPTION)
                .defineInRange("soulCampfireLitTime", SOUL_CAMPFIRE_LIT_TIME_DEFAULT, SOUL_CAMPFIRE_LIT_TIME_MIN, SOUL_CAMPFIRE_LIT_TIME_MAX);

        SOUL_CAMPFIRE_RAIN_UNLIT_TIME = builder
                .comment(SOUL_CAMPFIRE_RAIN_UNLIT_TIME_DESCRIPTION)
                .defineInRange("soulCampfireRainUnlitTime", SOUL_CAMPFIRE_RAIN_UNLIT_TIME_DEFAULT, SOUL_CAMPFIRE_RAIN_UNLIT_TIME_MIN, SOUL_CAMPFIRE_RAIN_UNLIT_TIME_MAX);

        SOUL_CAMPFIRE_RAIN_PARTICLE_FACTOR = builder
                .comment(SOUL_CAMPFIRE_RAIN_PARTICLE_FACTOR_DESCRIPTION)
                .defineInRange("soulCampfireRainParticleFactor", SOUL_CAMPFIRE_RAIN_PARTICLE_FACTOR_DEFAULT, SOUL_CAMPFIRE_RAIN_PARTICLE_FACTOR_MIN, SOUL_CAMPFIRE_RAIN_PARTICLE_FACTOR_MAX);

        SOUL_CAMPFIRE_DROPS_ITEMS_WHEN_UNLIT_BY_TIME_OR_RAIN = builder
                .comment(SOUL_CAMPFIRE_DROPS_ITEMS_WHEN_UNLIT_BY_TIME_OR_RAIN_DESCRIPTION)
                .define("soulCampfireDropsItemsWhenUnlitByTimeOrRain", SOUL_CAMPFIRE_DROPS_ITEMS_WHEN_UNLIT_BY_TIME_OR_RAIN_DEFAULT);

        SOUL_CAMPFIRE_BREAKS_WHEN_UNLIT_BY_TIME = builder
                .comment(SOUL_CAMPFIRE_BREAKS_WHEN_UNLIT_BY_TIME_DESCRIPTION)
                .define("soulCampfireBreaksWhenUnlitByTime", SOUL_CAMPFIRE_BREAKS_WHEN_UNLIT_BY_TIME_DEFAULT);

        SOUL_CAMPFIRE_ADDING_BURNABLES = builder
                .comment(SOUL_CAMPFIRE_ADDING_BURNABLES_DESCRIPTION)
                .define("soulCampfireAddingBurnables", SOUL_CAMPFIRE_ADDING_BURNABLES_DEFAULT);

        SOUL_CAMPFIRE_AFFECTED_BY_SLEEP_TIME = builder
                .comment(SOUL_CAMPFIRE_AFFECTED_BY_SLEEP_TIME_DESCRIPTION)
                .define("soulCampfireAffectedBySleepTime", SOUL_CAMPFIRE_AFFECTED_BY_SLEEP_TIME_DEFAULT);

        GENERATED_SOUL_CAMPFIRE_IS_LIT_INFINITELY = builder
                .comment(GENERATED_SOUL_CAMPFIRE_IS_LIT_INFINITELY_DESCRIPTION)
                .define("generatedSoulCampfireIsLitInfinitely", GENERATED_SOUL_CAMPFIRE_IS_LIT_INFINITELY_DEFAULT);

        builder.pop();

        SERVER_CONFIG = builder.build();
    }

    @Override
    public void init() {
        ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, SERVER_CONFIG);
        Path path = FMLPaths.GAMEDIR.get().resolve(FMLConfig.defaultConfigPath()).resolve(Constants.MOD_ID + "-server.toml");
        final CommentedFileConfig configData = CommentedFileConfig.builder(path).sync().autosave().writingMode(WritingMode.REPLACE).build();
        configData.load();
        SERVER_CONFIG.setConfig(configData);
    }

    @Override
    public int getLitTime(boolean isSoulCampfire) {
        try {
            return isSoulCampfire ? SOUL_CAMPFIRE_LIT_TIME.get() : CAMPFIRE_LIT_TIME.get();
        } catch (IllegalStateException ex) {
            return isSoulCampfire ? SOUL_CAMPFIRE_LIT_TIME_DEFAULT : CAMPFIRE_LIT_TIME_DEFAULT;
        }
    }

    @Override
    public int getRainUnlitTime(boolean isSoulCampfire) {
        try {
            return isSoulCampfire ? SOUL_CAMPFIRE_RAIN_UNLIT_TIME.get() : CAMPFIRE_RAIN_UNLIT_TIME.get();
        } catch (IllegalStateException ex) {
            return isSoulCampfire ? SOUL_CAMPFIRE_RAIN_UNLIT_TIME_DEFAULT : CAMPFIRE_RAIN_UNLIT_TIME_DEFAULT;
        }
    }

    @Override
    public int getRainParticleFactor(boolean isSoulCampfire) {
        try {
            return isSoulCampfire ? SOUL_CAMPFIRE_RAIN_PARTICLE_FACTOR.get() : CAMPFIRE_RAIN_PARTICLE_FACTOR.get();
        } catch (IllegalStateException ex) {
            return isSoulCampfire ? SOUL_CAMPFIRE_RAIN_PARTICLE_FACTOR_DEFAULT : CAMPFIRE_RAIN_PARTICLE_FACTOR_DEFAULT;
        }
    }

    @Override
    public boolean isDroppingItemsWhenUnlitByTimeOrRain(boolean isSoulCampfire) {
        try {
            return isSoulCampfire ? SOUL_CAMPFIRE_DROPS_ITEMS_WHEN_UNLIT_BY_TIME_OR_RAIN.get() : CAMPFIRE_DROPS_ITEMS_WHEN_UNLIT_BY_TIME_OR_RAIN.get();
        } catch (IllegalStateException ex) {
            return isSoulCampfire ? SOUL_CAMPFIRE_DROPS_ITEMS_WHEN_UNLIT_BY_TIME_OR_RAIN_DEFAULT : CAMPFIRE_DROPS_ITEMS_WHEN_UNLIT_BY_TIME_OR_RAIN_DEFAULT;
        }
    }

    @Override
    public boolean isBreakingWhenUnlitByTime(boolean isSoulCampfire) {
        try {
            return isSoulCampfire ? SOUL_CAMPFIRE_BREAKS_WHEN_UNLIT_BY_TIME.get() : CAMPFIRE_BREAKS_WHEN_UNLIT_BY_TIME.get();
        } catch (IllegalStateException ex) {
            return isSoulCampfire ? SOUL_CAMPFIRE_BREAKS_WHEN_UNLIT_BY_TIME_DEFAULT : CAMPFIRE_BREAKS_WHEN_UNLIT_BY_TIME_DEFAULT;
        }
    }

    @Override
    public boolean canAddBurnables(boolean isSoulCampfire) {
        try {
            return isSoulCampfire ? SOUL_CAMPFIRE_ADDING_BURNABLES.get() : CAMPFIRE_ADDING_BURNABLES.get();
        } catch (IllegalStateException ex) {
            return isSoulCampfire ? SOUL_CAMPFIRE_ADDING_BURNABLES_DEFAULT : CAMPFIRE_ADDING_BURNABLES_DEFAULT;
        }
    }

    @Override
    public boolean isAffectedBySleepTime(boolean isSoulCampfire) {
        try {
            return isSoulCampfire ? SOUL_CAMPFIRE_AFFECTED_BY_SLEEP_TIME.get() : CAMPFIRE_AFFECTED_BY_SLEEP_TIME.get();
        } catch (IllegalStateException ex) {
            return isSoulCampfire ? SOUL_CAMPFIRE_AFFECTED_BY_SLEEP_TIME_DEFAULT : CAMPFIRE_AFFECTED_BY_SLEEP_TIME_DEFAULT;
        }
    }

    @Override
    public boolean isGeneratedCampfireLitInfinitely(boolean isSoulCampfire) {
        try {
            return isSoulCampfire ? GENERATED_SOUL_CAMPFIRE_IS_LIT_INFINITELY.get() : GENERATED_CAMPFIRE_IS_LIT_INFINITELY.get();
        } catch (IllegalStateException ex) {
            return isSoulCampfire ? GENERATED_SOUL_CAMPFIRE_IS_LIT_INFINITELY_DEFAULT : GENERATED_CAMPFIRE_IS_LIT_INFINITELY_DEFAULT;
        }
    }


    private static void setGeneratedCampfireIsLitInfinitely(Block block, boolean litInfinitely) {
        block.registerDefaultState(block.defaultBlockState()
                .setValue(CampfireBlock.LIT, litInfinitely)
                .setValue(ICampfireBlockMixin.INFINITE, litInfinitely));
    }

    private static void loadChangedConfigData(ModConfigEvent configEvent) {
        if (!configEvent.getConfig().getModId().equals(ForgeUnlitCampfireMod.MOD_ID)) {
            return;
        }
        boolean campfireSpawn = configEvent.getConfig().getConfigData().get(GENERATED_CAMPFIRE_IS_LIT_INFINITELY.getPath());
        setGeneratedCampfireIsLitInfinitely(Blocks.CAMPFIRE, campfireSpawn);
        boolean soulCampfireSpawn = configEvent.getConfig().getConfigData().get(GENERATED_SOUL_CAMPFIRE_IS_LIT_INFINITELY.getPath());
        setGeneratedCampfireIsLitInfinitely(Blocks.SOUL_CAMPFIRE, soulCampfireSpawn);
    }

    @SubscribeEvent
    public static void onLoad(final ModConfigEvent.Loading configEvent) {
        loadChangedConfigData(configEvent);
    }

    @SubscribeEvent
    public static void onChange(final ModConfigEvent.Reloading configEvent) {
        loadChangedConfigData(configEvent);
    }

}
