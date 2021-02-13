package cech12.unlitcampfire.config;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.electronwill.nightconfig.core.io.WritingMode;
import net.minecraftforge.common.ForgeConfigSpec;

import java.nio.file.Path;

public class ServerConfig {
    public static ForgeConfigSpec SERVER_CONFIG;

    public static final ForgeConfigSpec.IntValue CAMPFIRE_LIT_TIME;
    public static final ForgeConfigSpec.BooleanValue UNLIT_CAMPFIRE_WITH_RAIN;
    public static final ForgeConfigSpec.BooleanValue CAMPFIRE_DROPS_ITEMS_WHEN_UNLIT_BY_TIME_OR_RAIN;
    public static final ForgeConfigSpec.BooleanValue CAMPFIRE_BREAKS_WHEN_UNLIT_BY_TIME;

    static {
        final ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();

        builder.push("unlitcampfire");

        //campfire
        CAMPFIRE_LIT_TIME = builder
                .comment("The time (ticks) a campfire burns until it goes out by itself. (2000 ticks default; 0 means it burns forever)")
                .defineInRange("campfireLitTime", 2000, 0, 10000);

        UNLIT_CAMPFIRE_WITH_RAIN = builder
                .comment("Whether a campfire should go out when rain falls on it.")
                .define("unlitCampfireWithRain", true);

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
