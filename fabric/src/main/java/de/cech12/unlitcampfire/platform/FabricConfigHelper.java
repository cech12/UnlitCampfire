package de.cech12.unlitcampfire.platform;

import de.cech12.unlitcampfire.Constants;
import de.cech12.unlitcampfire.platform.services.IConfigHelper;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import me.shedaniel.autoconfig.serializer.Toml4jConfigSerializer;

/**
 * The config service implementation for Fabric.
 */
@Config(name = Constants.MOD_ID)
public class FabricConfigHelper implements ConfigData, IConfigHelper {

    @ConfigEntry.Gui.CollapsibleObject
    @ConfigEntry.Gui.Tooltip(count = 2)
    public Campfire CAMPFIRE = new Campfire();

    public static final class Campfire implements ConfigData {

        @ConfigEntry.Gui.Tooltip(count = 6)
        public int CAMPFIRE_LIT_TIME = CAMPFIRE_LIT_TIME_DEFAULT;

        @ConfigEntry.Gui.Tooltip(count = 7)
        public int CAMPFIRE_RUN_OUT_INDICATOR_TIME = CAMPFIRE_RUN_OUT_INDICATOR_TIME_DEFAULT;

        @ConfigEntry.Gui.Tooltip(count = 7)
        public int CAMPFIRE_RAIN_UNLIT_TIME = CAMPFIRE_RAIN_UNLIT_TIME_DEFAULT;

        @ConfigEntry.Gui.Tooltip(count = 6)
        public int CAMPFIRE_RAIN_PARTICLE_FACTOR = CAMPFIRE_RAIN_PARTICLE_FACTOR_DEFAULT;

        @ConfigEntry.Gui.Tooltip(count = 4)
        public boolean CAMPFIRE_DROPS_ITEMS_WHEN_UNLIT_BY_TIME_OR_RAIN = CAMPFIRE_DROPS_ITEMS_WHEN_UNLIT_BY_TIME_OR_RAIN_DEFAULT;

        @ConfigEntry.Gui.Tooltip(count = 4)
        public boolean CAMPFIRE_BREAKS_WHEN_UNLIT_BY_TIME = CAMPFIRE_BREAKS_WHEN_UNLIT_BY_TIME_DEFAULT;

        @ConfigEntry.Gui.Tooltip(count = 4)
        public boolean CAMPFIRE_ADDING_BURNABLES = CAMPFIRE_ADDING_BURNABLES_DEFAULT;

        @ConfigEntry.Gui.Tooltip(count = 5)
        public int CAMPFIRE_MAX_LIT_TIME = CAMPFIRE_MAX_LIT_TIME_EXTENSION_DEFAULT;

        @ConfigEntry.Gui.Tooltip(count = 4)
        public boolean CAMPFIRE_AFFECTED_BY_SLEEP_TIME = CAMPFIRE_AFFECTED_BY_SLEEP_TIME_DEFAULT;

        @ConfigEntry.Gui.Tooltip(count = 4)
        public boolean GENERATED_CAMPFIRE_IS_LIT_INFINITELY = GENERATED_CAMPFIRE_IS_LIT_INFINITELY_DEFAULT;

        @ConfigEntry.Gui.Tooltip(count = 4)
        public boolean INFINITE_CAMPFIRE_IGNORES_RAIN = INFINITE_CAMPFIRE_IGNORES_RAIN_DEFAULT;

        private Campfire() {}
    }

    @ConfigEntry.Gui.CollapsibleObject
    @ConfigEntry.Gui.Tooltip(count = 2)
    public SoulCampfire SOUL_CAMPFIRE = new SoulCampfire();

    public static final class SoulCampfire implements ConfigData {

        @ConfigEntry.Gui.Tooltip(count = 6)
        public int SOUL_CAMPFIRE_LIT_TIME = SOUL_CAMPFIRE_LIT_TIME_DEFAULT;

        @ConfigEntry.Gui.Tooltip(count = 7)
        public int SOUL_CAMPFIRE_RUN_OUT_INDICATOR_TIME = SOUL_CAMPFIRE_RUN_OUT_INDICATOR_TIME_DEFAULT;

        @ConfigEntry.Gui.Tooltip(count = 7)
        public int SOUL_CAMPFIRE_RAIN_UNLIT_TIME = SOUL_CAMPFIRE_RAIN_UNLIT_TIME_DEFAULT;

        @ConfigEntry.Gui.Tooltip(count = 6)
        public int SOUL_CAMPFIRE_RAIN_PARTICLE_FACTOR = SOUL_CAMPFIRE_RAIN_PARTICLE_FACTOR_DEFAULT;

        @ConfigEntry.Gui.Tooltip(count = 4)
        public boolean SOUL_CAMPFIRE_DROPS_ITEMS_WHEN_UNLIT_BY_TIME_OR_RAIN = SOUL_CAMPFIRE_DROPS_ITEMS_WHEN_UNLIT_BY_TIME_OR_RAIN_DEFAULT;

        @ConfigEntry.Gui.Tooltip(count = 4)
        public boolean SOUL_CAMPFIRE_BREAKS_WHEN_UNLIT_BY_TIME = SOUL_CAMPFIRE_BREAKS_WHEN_UNLIT_BY_TIME_DEFAULT;

        @ConfigEntry.Gui.Tooltip(count = 4)
        public boolean SOUL_CAMPFIRE_ADDING_BURNABLES = SOUL_CAMPFIRE_ADDING_BURNABLES_DEFAULT;

        @ConfigEntry.Gui.Tooltip(count = 5)
        public int SOUL_CAMPFIRE_MAX_LIT_TIME = SOUL_CAMPFIRE_MAX_LIT_TIME_EXTENSION_DEFAULT;

        @ConfigEntry.Gui.Tooltip(count = 4)
        public boolean SOUL_CAMPFIRE_AFFECTED_BY_SLEEP_TIME = SOUL_CAMPFIRE_AFFECTED_BY_SLEEP_TIME_DEFAULT;

        @ConfigEntry.Gui.Tooltip(count = 4)
        public boolean GENERATED_SOUL_CAMPFIRE_IS_LIT_INFINITELY = GENERATED_SOUL_CAMPFIRE_IS_LIT_INFINITELY_DEFAULT;

        @ConfigEntry.Gui.Tooltip(count = 4)
        public boolean INFINITE_SOUL_CAMPFIRE_IGNORES_RAIN = INFINITE_SOUL_CAMPFIRE_IGNORES_RAIN_DEFAULT;

        private SoulCampfire() {}
    }

    @Override
    public void init() {
        AutoConfig.register(FabricConfigHelper.class, Toml4jConfigSerializer::new);
    }

    private FabricConfigHelper getConfig() {
        return AutoConfig.getConfigHolder(FabricConfigHelper.class).getConfig();
    }

    @Override
    public int getLitTime(boolean isSoulCampfire) {
        FabricConfigHelper config = getConfig();
        return isSoulCampfire ?
                Math.clamp(config.SOUL_CAMPFIRE.SOUL_CAMPFIRE_LIT_TIME, SOUL_CAMPFIRE_LIT_TIME_MIN, SOUL_CAMPFIRE_LIT_TIME_MAX) :
                Math.clamp(config.CAMPFIRE.CAMPFIRE_LIT_TIME, CAMPFIRE_LIT_TIME_MIN, CAMPFIRE_LIT_TIME_MAX);
    }

    @Override
    public int getRunOutIndicatorTime(boolean isSoulCampfire) {
        FabricConfigHelper config = getConfig();
        return isSoulCampfire ?
                Math.clamp(config.SOUL_CAMPFIRE.SOUL_CAMPFIRE_RUN_OUT_INDICATOR_TIME, SOUL_CAMPFIRE_RUN_OUT_INDICATOR_TIME_MIN, SOUL_CAMPFIRE_RUN_OUT_INDICATOR_TIME_MAX) :
                Math.clamp(config.CAMPFIRE.CAMPFIRE_RUN_OUT_INDICATOR_TIME, CAMPFIRE_RUN_OUT_INDICATOR_TIME_MIN, CAMPFIRE_RUN_OUT_INDICATOR_TIME_MAX);
    }

    @Override
    public int getRainUnlitTime(boolean isSoulCampfire) {
        FabricConfigHelper config = getConfig();
        return isSoulCampfire ?
                Math.clamp(config.SOUL_CAMPFIRE.SOUL_CAMPFIRE_RAIN_UNLIT_TIME, SOUL_CAMPFIRE_RAIN_UNLIT_TIME_MIN, SOUL_CAMPFIRE_RAIN_UNLIT_TIME_MAX) :
                Math.clamp(config.CAMPFIRE.CAMPFIRE_RAIN_UNLIT_TIME, CAMPFIRE_RAIN_UNLIT_TIME_MIN, CAMPFIRE_RAIN_UNLIT_TIME_MAX);
    }

    @Override
    public int getRainParticleFactor(boolean isSoulCampfire) {
        FabricConfigHelper config = getConfig();
        return isSoulCampfire ?
                Math.clamp(config.SOUL_CAMPFIRE.SOUL_CAMPFIRE_RAIN_PARTICLE_FACTOR, SOUL_CAMPFIRE_RAIN_PARTICLE_FACTOR_MIN, SOUL_CAMPFIRE_RAIN_PARTICLE_FACTOR_MAX) :
                Math.clamp(config.CAMPFIRE.CAMPFIRE_RAIN_PARTICLE_FACTOR, CAMPFIRE_RAIN_PARTICLE_FACTOR_MIN, CAMPFIRE_RAIN_PARTICLE_FACTOR_MAX);
    }

    @Override
    public boolean isDroppingItemsWhenUnlitByTimeOrRain(boolean isSoulCampfire) {
        FabricConfigHelper config = getConfig();
        return isSoulCampfire ? config.SOUL_CAMPFIRE.SOUL_CAMPFIRE_DROPS_ITEMS_WHEN_UNLIT_BY_TIME_OR_RAIN : config.CAMPFIRE.CAMPFIRE_DROPS_ITEMS_WHEN_UNLIT_BY_TIME_OR_RAIN;
    }

    @Override
    public boolean isBreakingWhenUnlitByTime(boolean isSoulCampfire) {
        FabricConfigHelper config = getConfig();
        return isSoulCampfire ? config.SOUL_CAMPFIRE.SOUL_CAMPFIRE_BREAKS_WHEN_UNLIT_BY_TIME : config.CAMPFIRE.CAMPFIRE_BREAKS_WHEN_UNLIT_BY_TIME;
    }

    @Override
    public boolean canAddBurnables(boolean isSoulCampfire) {
        FabricConfigHelper config = getConfig();
        return isSoulCampfire ? config.SOUL_CAMPFIRE.SOUL_CAMPFIRE_ADDING_BURNABLES : config.CAMPFIRE.CAMPFIRE_ADDING_BURNABLES;
    }

    @Override
    public int getMaxLitTimeExtension(boolean isSoulCampfire) {
        FabricConfigHelper config = getConfig();
        return isSoulCampfire ?
                Math.clamp(config.SOUL_CAMPFIRE.SOUL_CAMPFIRE_MAX_LIT_TIME, SOUL_CAMPFIRE_MAX_LIT_TIME_EXTENSION_MIN, SOUL_CAMPFIRE_MAX_LIT_TIME_EXTENSION_MAX) :
                Math.clamp(config.CAMPFIRE.CAMPFIRE_MAX_LIT_TIME, CAMPFIRE_MAX_LIT_TIME_EXTENSION_MIN, CAMPFIRE_MAX_LIT_TIME_EXTENSION_MAX);
    }

    @Override
    public boolean isAffectedBySleepTime(boolean isSoulCampfire) {
        FabricConfigHelper config = getConfig();
        return isSoulCampfire ? config.SOUL_CAMPFIRE.SOUL_CAMPFIRE_AFFECTED_BY_SLEEP_TIME : config.CAMPFIRE.CAMPFIRE_AFFECTED_BY_SLEEP_TIME;
    }

    @Override
    public boolean isGeneratedCampfireLitInfinitely(boolean isSoulCampfire) {
        FabricConfigHelper config = getConfig();
        return isSoulCampfire ? config.SOUL_CAMPFIRE.GENERATED_SOUL_CAMPFIRE_IS_LIT_INFINITELY : config.CAMPFIRE.GENERATED_CAMPFIRE_IS_LIT_INFINITELY;
    }

    @Override
    public boolean isInfiniteCampfireIgnoringRain(boolean isSoulCampfire) {
        FabricConfigHelper config = getConfig();
        return isSoulCampfire ? config.SOUL_CAMPFIRE.INFINITE_SOUL_CAMPFIRE_IGNORES_RAIN : config.CAMPFIRE.INFINITE_CAMPFIRE_IGNORES_RAIN;
    }

}
