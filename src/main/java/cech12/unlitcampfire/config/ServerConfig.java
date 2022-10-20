package cech12.unlitcampfire.config;

import cech12.unlitcampfire.UnlitCampfireMod;
import cech12.unlitcampfire.mixinaccess.ICampfireBlockMixin;
import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.electronwill.nightconfig.core.io.WritingMode;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CampfireBlock;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.config.ModConfigEvent;

import java.nio.file.Path;

@Mod.EventBusSubscriber(modid = UnlitCampfireMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ServerConfig {
    public static ForgeConfigSpec SERVER_CONFIG;

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

        //campfire
        CAMPFIRE_LIT_TIME = builder
                .comment("The time (ticks) a campfire burns until it goes out by itself. (2000 ticks default; 0 means it burns forever)")
                .defineInRange("campfireLitTime", 2000, 0, 2000000);

        CAMPFIRE_RAIN_UNLIT_TIME = builder
                .comment("The time (ticks) a campfire burns until it goes out during rain. (160 ticks [default]; 0: it goes out immediately; -1: it burns during rain).")
                .defineInRange("campfireRainUnlitTime", 160, -1, 2000000);

        CAMPFIRE_RAIN_PARTICLE_FACTOR = builder
                .comment("The Factor of particle count of a campfire during rain. (2: doubled particles count [default]; 1: same particle count)")
                .defineInRange("campfireRainParticleFactor", 2, 1, 10);

        CAMPFIRE_DROPS_ITEMS_WHEN_UNLIT_BY_TIME_OR_RAIN = builder
                .comment("Whether the contained items should be dropped when a campfire goes out by itself or by rain.")
                .define("campfireDropsItemsWhenUnlitByTimeOrRain", true);

        CAMPFIRE_BREAKS_WHEN_UNLIT_BY_TIME = builder
                .comment("Whether a campfire should be destroyed when it goes out by itself.")
                .define("campfireBreaksWhenUnlitByTime", false);

        CAMPFIRE_ADDING_BURNABLES = builder
                .comment("Whether the lit time of a campfire could be extended with combustible/burnable items.")
                .define("campfireAddingBurnables", true);

        CAMPFIRE_AFFECTED_BY_SLEEP_TIME = builder
                .comment("Whether the lit time of a campfire should be affected by the sleep time.")
                .define("campfireAffectedBySleepTime", false);

        GENERATED_CAMPFIRE_IS_LIT_INFINITELY = builder
                .comment("Whether generated campfires should be lit infinitely.")
                .define("generatedCampfireIsLitInfinitely", true);

        //soul campfire
        SOUL_CAMPFIRE_LIT_TIME = builder
                .comment("The time (ticks) a soul campfire burns until it goes out by itself. (2000 ticks default; 0 means it burns forever)")
                .defineInRange("soulCampfireLitTime", 2000, 0, 2000000);

        SOUL_CAMPFIRE_RAIN_UNLIT_TIME = builder
                .comment("The time (ticks) a soul campfire burns until it goes out during rain. (0: it goes out immediately; -1: it burns during rain [default]).")
                .defineInRange("soulCampfireRainUnlitTime", -1, -1, 2000000);

        SOUL_CAMPFIRE_RAIN_PARTICLE_FACTOR = builder
                .comment("The Factor of particle count of a soul campfire during rain. (2: doubled particles count (default); 1: same particle count)")
                .defineInRange("soulCampfireRainParticleFactor", 2, 1, 10);

        SOUL_CAMPFIRE_DROPS_ITEMS_WHEN_UNLIT_BY_TIME_OR_RAIN = builder
                .comment("Whether the contained items should be dropped when a soul campfire goes out by itself or by rain.")
                .define("soulCampfireDropsItemsWhenUnlitByTimeOrRain", true);

        SOUL_CAMPFIRE_BREAKS_WHEN_UNLIT_BY_TIME = builder
                .comment("Whether a soul campfire should be destroyed when it goes out by itself.")
                .define("soulCampfireBreaksWhenUnlitByTime", false);

        SOUL_CAMPFIRE_ADDING_BURNABLES = builder
                .comment("Whether the lit time of a soul campfire could be extended with combustible/burnable items.")
                .define("soulCampfireAddingBurnables", true);

        SOUL_CAMPFIRE_AFFECTED_BY_SLEEP_TIME = builder
                .comment("Whether the lit time of a campfire should be affected by the sleep time.")
                .define("soulCampfireAffectedBySleepTime", false);

        GENERATED_SOUL_CAMPFIRE_IS_LIT_INFINITELY = builder
                .comment("Whether generated soul campfires should be lit infinitely.")
                .define("generatedSoulCampfireIsLitInfinitely", true);

        builder.pop();

        SERVER_CONFIG = builder.build();
    }

    public static void loadConfig(ForgeConfigSpec spec, Path path) {
        final CommentedFileConfig configData = CommentedFileConfig.builder(path).sync().autosave().writingMode(WritingMode.REPLACE).build();
        configData.load();
        spec.setConfig(configData);
    }

    private static void setGeneratedCampfireIsLitInfinitely(Block block, boolean litInfinitely) {
        block.registerDefaultState(block.defaultBlockState()
                .setValue(CampfireBlock.LIT, litInfinitely)
                .setValue(ICampfireBlockMixin.INFINITE, litInfinitely));
    }

    private static void loadChangedConfigData(ModConfigEvent configEvent) {
        if (!configEvent.getConfig().getModId().equals(UnlitCampfireMod.MOD_ID)) {
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
