package de.cech12.unlitcampfire.platform.services;

/**
 * Common configuration helper service interface.
 */
public interface IConfigHelper {

    int CAMPFIRE_LIT_TIME_DEFAULT = 2000;
    String CAMPFIRE_LIT_TIME_DESCRIPTION = "The time (ticks) a campfire burns until it goes out by itself. (" + CAMPFIRE_LIT_TIME_DEFAULT + " ticks default; 0 means it burns forever)";
    int CAMPFIRE_LIT_TIME_MIN = 0;
    int CAMPFIRE_LIT_TIME_MAX = 2000000;

    int CAMPFIRE_RAIN_UNLIT_TIME_DEFAULT = 160;
    String CAMPFIRE_RAIN_UNLIT_TIME_DESCRIPTION = "The time (ticks) a campfire burns until it goes out during rain. (" + CAMPFIRE_RAIN_UNLIT_TIME_DEFAULT + " ticks [default]; 0: it goes out immediately; -1: it burns during rain).";
    int CAMPFIRE_RAIN_UNLIT_TIME_MIN = -1;
    int CAMPFIRE_RAIN_UNLIT_TIME_MAX = 2000000;

    int CAMPFIRE_RAIN_PARTICLE_FACTOR_DEFAULT = 2;
    String CAMPFIRE_RAIN_PARTICLE_FACTOR_DESCRIPTION = "The Factor of particle count of a campfire during rain. (2: doubled particles count [default]; 1: same particle count)";
    int CAMPFIRE_RAIN_PARTICLE_FACTOR_MIN = 1;
    int CAMPFIRE_RAIN_PARTICLE_FACTOR_MAX = 10;

    boolean CAMPFIRE_DROPS_ITEMS_WHEN_UNLIT_BY_TIME_OR_RAIN_DEFAULT = true;
    String CAMPFIRE_DROPS_ITEMS_WHEN_UNLIT_BY_TIME_OR_RAIN_DESCRIPTION = "Whether the contained items should be dropped when a campfire goes out by itself or by rain.";

    boolean CAMPFIRE_BREAKS_WHEN_UNLIT_BY_TIME_DEFAULT = false;
    String CAMPFIRE_BREAKS_WHEN_UNLIT_BY_TIME_DESCRIPTION = "Whether a campfire should be destroyed when it goes out by itself.";

    boolean CAMPFIRE_ADDING_BURNABLES_DEFAULT = true;
    String CAMPFIRE_ADDING_BURNABLES_DESCRIPTION = "Whether the lit time of a campfire could be extended with combustible/burnable items.";

    boolean CAMPFIRE_AFFECTED_BY_SLEEP_TIME_DEFAULT = false;
    String CAMPFIRE_AFFECTED_BY_SLEEP_TIME_DESCRIPTION = "Whether the lit time of a campfire should be affected by the sleep time.";

    boolean GENERATED_CAMPFIRE_IS_LIT_INFINITELY_DEFAULT = true;
    String GENERATED_CAMPFIRE_IS_LIT_INFINITELY_DESCRIPTION = "Whether generated campfires should be lit infinitely.";

    int SOUL_CAMPFIRE_LIT_TIME_DEFAULT = 2000;
    String SOUL_CAMPFIRE_LIT_TIME_DESCRIPTION = "The time (ticks) a soul campfire burns until it goes out by itself. (" + SOUL_CAMPFIRE_LIT_TIME_DEFAULT + " ticks default; 0 means it burns forever)";
    int SOUL_CAMPFIRE_LIT_TIME_MIN = 0;
    int SOUL_CAMPFIRE_LIT_TIME_MAX = 2000000;

    int SOUL_CAMPFIRE_RAIN_UNLIT_TIME_DEFAULT = -1;
    String SOUL_CAMPFIRE_RAIN_UNLIT_TIME_DESCRIPTION = "The time (ticks) a soul campfire burns until it goes out during rain. (" + SOUL_CAMPFIRE_RAIN_UNLIT_TIME_DEFAULT + " ticks [default]; 0: it goes out immediately; -1: it burns during rain).";
    int SOUL_CAMPFIRE_RAIN_UNLIT_TIME_MIN = -1;
    int SOUL_CAMPFIRE_RAIN_UNLIT_TIME_MAX = 2000000;

    int SOUL_CAMPFIRE_RAIN_PARTICLE_FACTOR_DEFAULT = 2;
    String SOUL_CAMPFIRE_RAIN_PARTICLE_FACTOR_DESCRIPTION = "The Factor of particle count of a soul campfire during rain. (2: doubled particles count [default]; 1: same particle count)";
    int SOUL_CAMPFIRE_RAIN_PARTICLE_FACTOR_MIN = 1;
    int SOUL_CAMPFIRE_RAIN_PARTICLE_FACTOR_MAX = 10;

    boolean SOUL_CAMPFIRE_DROPS_ITEMS_WHEN_UNLIT_BY_TIME_OR_RAIN_DEFAULT = true;
    String SOUL_CAMPFIRE_DROPS_ITEMS_WHEN_UNLIT_BY_TIME_OR_RAIN_DESCRIPTION = "Whether the contained items should be dropped when a soul campfire goes out by itself or by rain.";

    boolean SOUL_CAMPFIRE_BREAKS_WHEN_UNLIT_BY_TIME_DEFAULT = false;
    String SOUL_CAMPFIRE_BREAKS_WHEN_UNLIT_BY_TIME_DESCRIPTION = "Whether a soul campfire should be destroyed when it goes out by itself.";

    boolean SOUL_CAMPFIRE_ADDING_BURNABLES_DEFAULT = true;
    String SOUL_CAMPFIRE_ADDING_BURNABLES_DESCRIPTION = "Whether the lit time of a soul campfire could be extended with combustible/burnable items.";

    boolean SOUL_CAMPFIRE_AFFECTED_BY_SLEEP_TIME_DEFAULT = false;
    String SOUL_CAMPFIRE_AFFECTED_BY_SLEEP_TIME_DESCRIPTION = "Whether the lit time of a soul campfire should be affected by the sleep time.";

    boolean GENERATED_SOUL_CAMPFIRE_IS_LIT_INFINITELY_DEFAULT = true;
    String GENERATED_SOUL_CAMPFIRE_IS_LIT_INFINITELY_DESCRIPTION = "Whether generated soul campfires should be lit infinitely.";

    /**
     * Initialization method for the Service implementations.
     */
    void init();

    /**
     * Gets the configured lit time value.
     *
     * @param isSoulCampfire parameter which indicates if the campfire or soul campfire value should be returned
     * @return configured lit time value
     */
    int getLitTime(boolean isSoulCampfire);

    /**
     * Gets the configured rain unlit time value.
     *
     * @param isSoulCampfire parameter which indicates if the campfire or soul campfire value should be returned
     * @return configured rain unlit time value
     */
    int getRainUnlitTime(boolean isSoulCampfire);

    /**
     * Gets the configured rain particle factor value.
     *
     * @param isSoulCampfire parameter which indicates if the campfire or soul campfire value should be returned
     * @return configured rain particle factor value
     */
    int getRainParticleFactor(boolean isSoulCampfire);

    /**
     * Gets the configured "drops items when unlit by time or rain" value.
     *
     * @param isSoulCampfire parameter which indicates if the campfire or soul campfire value should be returned
     * @return configured "drops items when unlit by time or rain" value
     */
    boolean isDroppingItemsWhenUnlitByTimeOrRain(boolean isSoulCampfire);

    /**
     * Gets the configured "breaks when unlit by time" value.
     *
     * @param isSoulCampfire parameter which indicates if the campfire or soul campfire value should be returned
     * @return configured "breaks when unlit by time" value
     */
    boolean isBreakingWhenUnlitByTime(boolean isSoulCampfire);

    /**
     * Gets the configured "adding burnables" value.
     *
     * @param isSoulCampfire parameter which indicates if the campfire or soul campfire value should be returned
     * @return configured "adding burnables" value
     */
    boolean canAddBurnables(boolean isSoulCampfire);

    /**
     * Gets the configured "affected by sleep time" value.
     *
     * @param isSoulCampfire parameter which indicates if the campfire or soul campfire value should be returned
     * @return configured "affected by sleep time" value
     */
    boolean isAffectedBySleepTime(boolean isSoulCampfire);

    /**
     * Gets the configured "generated campfire is lit infinitely" value.
     *
     * @param isSoulCampfire parameter which indicates if the campfire or soul campfire value should be returned
     * @return configured "generated campfire is lit infinitely" value
     */
    boolean isGeneratedCampfireLitInfinitely(boolean isSoulCampfire);

}