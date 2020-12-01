package cech12.unlitcampfire.config;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.electronwill.nightconfig.core.io.WritingMode;
import net.minecraftforge.common.ForgeConfigSpec;

import java.nio.file.Path;

public class ServerConfig {
    public static ForgeConfigSpec SERVER_CONFIG;

    public static final ForgeConfigSpec.IntValue CAMPFIRE_LIT_TIME;
    public static final ForgeConfigSpec.BooleanValue CAMPFIRE_DROPS_ITEMS_AFTER_LIT_TIME;
    public static final ForgeConfigSpec.BooleanValue CAMPFIRE_BREAKS_AFTER_LIT_TIME;

    static {
        final ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();

        builder.push("unlitcampfire");

        CAMPFIRE_LIT_TIME = builder
                .comment("The time (ticks) a campfire burns until it goes out by itself. (2000 ticks default; 0 means it burns forever)")
                .defineInRange("campfireLitTime", 2000, 0, 10000);

        CAMPFIRE_DROPS_ITEMS_AFTER_LIT_TIME = builder
                .comment("Whether the containing items should be dropped when a campfire goes out by itself.")
                .define("campfireDropsItemsAfterLitTime", true);

        CAMPFIRE_BREAKS_AFTER_LIT_TIME = builder
                .comment("Whether the campfire should be destroyed when it goes out by itself.")
                .define("campfireBreaksAfterLitTime", false);

        builder.pop();

        SERVER_CONFIG = builder.build();
    }

    public static void loadConfig(ForgeConfigSpec spec, Path path) {
        final CommentedFileConfig configData = CommentedFileConfig.builder(path).sync().autosave().writingMode(WritingMode.REPLACE).build();
        configData.load();
        spec.setConfig(configData);
    }

}
