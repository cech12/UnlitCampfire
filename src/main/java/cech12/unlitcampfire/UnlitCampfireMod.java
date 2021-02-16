package cech12.unlitcampfire;

import cech12.unlitcampfire.config.UnlitCampfireConfig;
import me.sargunvohra.mcmods.autoconfig1u.AutoConfig;
import me.sargunvohra.mcmods.autoconfig1u.serializer.GsonConfigSerializer;
import net.fabricmc.api.ModInitializer;

public class UnlitCampfireMod implements ModInitializer {

    public static UnlitCampfireConfig CONFIG;
    public static final String MOD_ID = "unlitcampfire";

    @Override
    public void onInitialize() {
        //Config
        AutoConfig.register(UnlitCampfireConfig.class, GsonConfigSerializer::new);
        CONFIG = AutoConfig.getConfigHolder(UnlitCampfireConfig.class).getConfig();
    }

}
