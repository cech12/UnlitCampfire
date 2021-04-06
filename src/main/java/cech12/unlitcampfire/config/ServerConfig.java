package cech12.unlitcampfire.config;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.electronwill.nightconfig.core.io.WritingMode;
import net.minecraftforge.common.ForgeConfigSpec;

import java.nio.file.Path;

public class ServerConfig {
    public static ForgeConfigSpec SERVER_CONFIG;

    public static final ForgeConfigSpec.IntValue CAMPFIRE_LIT_TIME;
    public static final ForgeConfigSpec.IntValue CAMPFIRE_RAIN_UNLIT_TIME;
    public static final ForgeConfigSpec.IntValue CAMPFIRE_RAIN_PARTICLE_FACTOR;
    public static final ForgeConfigSpec.BooleanValue CAMPFIRE_DROPS_ITEMS_WHEN_UNLIT_BY_TIME_OR_RAIN;
    public static final ForgeConfigSpec.BooleanValue CAMPFIRE_BREAKS_WHEN_UNLIT_BY_TIME;

    static {
        final ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();

        builder.push("unlitcampfire");

        //campfire
        CAMPFIRE_LIT_TIME = builder
                .comment("The time (ticks) a campfire burns until it goes out by itself. (2000 ticks default; 0 means it burns forever)")
                .defineInRange("campfireLitTime", 2000, 0, 10000);

        CAMPFIRE_RAIN_UNLIT_TIME = builder
                .comment("The time (ticks) a campfire burns until it goes out during rain. (160 ticks [default]; 0: it goes out immediately; -1: it burns during rain).")
                .defineInRange("campfireRainUnlitTime", 160, -1, 10000);

        CAMPFIRE_RAIN_PARTICLE_FACTOR = builder
                .comment("The Factor of particle count of a campfire during rain. (2: doubled particles count [default]; 1: same particle count)")
                .defineInRange("campfireRainParticleFactor", 2, 1, 10);

        CAMPFIRE_DROPS_ITEMS_WHEN_UNLIT_BY_TIME_OR_RAIN = builder
                .comment("Whether the contained items should be dropped when a campfire goes out by itself or by rain.")
                .define("campfireDropsItemsWhenUnlitByTimeOrRain", false); //for 1.15 default is false

        CAMPFIRE_BREAKS_WHEN_UNLIT_BY_TIME = builder
                .comment("Whether a campfire should be destroyed when it goes out by itself.")
                .define("campfireBreaksWhenUnlitByTime", false);

        builder.pop();

        SERVER_CONFIG = builder.build();
    }

    public static void loadConfig(ForgeConfigSpec spec, Path path) {
        final CommentedFileConfig configData = CommentedFileConfig.builder(path).sync().autosave().writingMode(WritingMode.REPLACE).build();
        configData.load();
        spec.setConfig(configData);
    }

}
