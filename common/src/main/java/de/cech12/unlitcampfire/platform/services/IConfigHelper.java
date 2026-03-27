package de.cech12.unlitcampfire.platform.services;

/**
 * Common configuration helper service interface.
 */
public interface IConfigHelper {

    long CAMPFIRE_LIT_TIME_DEFAULT = 2000L;
    String CAMPFIRE_LIT_TIME_DESCRIPTION = "The time (ticks) a campfire burns until it goes out by itself. (" + CAMPFIRE_LIT_TIME_DEFAULT + " ticks default; 0 means it burns forever)";
    long CAMPFIRE_LIT_TIME_MIN = 0L;
    long CAMPFIRE_LIT_TIME_MAX = 2000000L;

    long CAMPFIRE_RUN_OUT_INDICATOR_TIME_DEFAULT = 600L;
    String CAMPFIRE_RUN_OUT_INDICATOR_TIME_DESCRIPTION = "The light level of a campfire decreases when the remaining time (ticks) is lower than this configured value. (" + CAMPFIRE_RUN_OUT_INDICATOR_TIME_DEFAULT + " ticks default; 0 deactivates this behaviour)";
    long CAMPFIRE_RUN_OUT_INDICATOR_TIME_MIN = 0L;
    long CAMPFIRE_RUN_OUT_INDICATOR_TIME_MAX = 2000000L;

    long CAMPFIRE_RAIN_UNLIT_TIME_DEFAULT = 160L;
    String CAMPFIRE_RAIN_UNLIT_TIME_DESCRIPTION = "The time (ticks) a campfire burns until it goes out during rain. (" + CAMPFIRE_RAIN_UNLIT_TIME_DEFAULT + " ticks [default]; 0: it goes out immediately; -1: it burns during rain).";
    long CAMPFIRE_RAIN_UNLIT_TIME_MIN = -1L;
    long CAMPFIRE_RAIN_UNLIT_TIME_MAX = 2000000L;

    int CAMPFIRE_RAIN_PARTICLE_FACTOR_DEFAULT = 2;
    String CAMPFIRE_RAIN_PARTICLE_FACTOR_DESCRIPTION = "The Factor of particle count of a campfire during rain. (2: doubled particles count [default]; 1: same particle count)";
    int CAMPFIRE_RAIN_PARTICLE_FACTOR_MIN = 1;
    int CAMPFIRE_RAIN_PARTICLE_FACTOR_MAX = 10;

    boolean CAMPFIRE_BREAKS_WHEN_UNLIT_BY_TIME_DEFAULT = false;
    String CAMPFIRE_BREAKS_WHEN_UNLIT_BY_TIME_DESCRIPTION = "Whether a campfire should be destroyed when it goes out by itself.";

    boolean CAMPFIRE_ADDING_BURNABLES_DEFAULT = true;
    String CAMPFIRE_ADDING_BURNABLES_DESCRIPTION = "Whether the lit time of a campfire could be extended with combustible/burnable items.";

    long CAMPFIRE_MAX_LIT_TIME_EXTENSION_DEFAULT = 2000L;
    String CAMPFIRE_MAX_LIT_TIME_EXTENSION_DESCRIPTION = "The time (ticks) a campfire can be filled up additionally until adding burnables is not possible. (is added to the configured lit time) (" + CAMPFIRE_MAX_LIT_TIME_EXTENSION_DEFAULT + " ticks default)";
    long CAMPFIRE_MAX_LIT_TIME_EXTENSION_MIN = 1L;
    long CAMPFIRE_MAX_LIT_TIME_EXTENSION_MAX = 2000000L;

    boolean CAMPFIRE_AFFECTED_BY_SLEEP_TIME_DEFAULT = false;
    String CAMPFIRE_AFFECTED_BY_SLEEP_TIME_DESCRIPTION = "Whether the lit time of a campfire should be affected by the sleep time.";

    boolean GENERATED_CAMPFIRE_IS_LIT_INFINITELY_DEFAULT = true;
    String GENERATED_CAMPFIRE_IS_LIT_INFINITELY_DESCRIPTION = "Whether generated campfires should be lit infinitely.";

    boolean INFINITE_CAMPFIRE_IGNORES_RAIN_DEFAULT = true;
    String INFINITE_CAMPFIRE_IGNORES_RAIN_DESCRIPTION = "Whether infinite campfires should stay lit in rain.";

    long SOUL_CAMPFIRE_LIT_TIME_DEFAULT = 2000L;
    String SOUL_CAMPFIRE_LIT_TIME_DESCRIPTION = "The time (ticks) a soul campfire burns until it goes out by itself. (" + SOUL_CAMPFIRE_LIT_TIME_DEFAULT + " ticks default; 0 means it burns forever)";
    long SOUL_CAMPFIRE_LIT_TIME_MIN = 0L;
    long SOUL_CAMPFIRE_LIT_TIME_MAX = 2000000L;

    long SOUL_CAMPFIRE_RUN_OUT_INDICATOR_TIME_DEFAULT = 600L;
    String SOUL_CAMPFIRE_RUN_OUT_INDICATOR_TIME_DESCRIPTION = "The light level of a soul campfire decreases when the remaining time (ticks) is lower than this configured value. (" + SOUL_CAMPFIRE_RUN_OUT_INDICATOR_TIME_DEFAULT + " ticks default; 0 deactivates this behaviour)";
    long SOUL_CAMPFIRE_RUN_OUT_INDICATOR_TIME_MIN = 0L;
    long SOUL_CAMPFIRE_RUN_OUT_INDICATOR_TIME_MAX = 2000000L;

    long SOUL_CAMPFIRE_RAIN_UNLIT_TIME_DEFAULT = -1L;
    String SOUL_CAMPFIRE_RAIN_UNLIT_TIME_DESCRIPTION = "The time (ticks) a soul campfire burns until it goes out during rain. (" + SOUL_CAMPFIRE_RAIN_UNLIT_TIME_DEFAULT + " ticks [default]; 0: it goes out immediately; -1: it burns during rain).";
    long SOUL_CAMPFIRE_RAIN_UNLIT_TIME_MIN = -1L;
    long SOUL_CAMPFIRE_RAIN_UNLIT_TIME_MAX = 2000000L;

    int SOUL_CAMPFIRE_RAIN_PARTICLE_FACTOR_DEFAULT = 2;
    String SOUL_CAMPFIRE_RAIN_PARTICLE_FACTOR_DESCRIPTION = "The Factor of particle count of a soul campfire during rain. (2: doubled particles count [default]; 1: same particle count)";
    int SOUL_CAMPFIRE_RAIN_PARTICLE_FACTOR_MIN = 1;
    int SOUL_CAMPFIRE_RAIN_PARTICLE_FACTOR_MAX = 10;

    boolean SOUL_CAMPFIRE_BREAKS_WHEN_UNLIT_BY_TIME_DEFAULT = false;
    String SOUL_CAMPFIRE_BREAKS_WHEN_UNLIT_BY_TIME_DESCRIPTION = "Whether a soul campfire should be destroyed when it goes out by itself.";

    boolean SOUL_CAMPFIRE_ADDING_BURNABLES_DEFAULT = true;
    String SOUL_CAMPFIRE_ADDING_BURNABLES_DESCRIPTION = "Whether the lit time of a soul campfire could be extended with combustible/burnable items.";

    long SOUL_CAMPFIRE_MAX_LIT_TIME_EXTENSION_DEFAULT = 2000L;
    String SOUL_CAMPFIRE_MAX_LIT_TIME_EXTENSION_DESCRIPTION = "The time (ticks) a soul campfire can be filled up additionally until adding burnables is not possible. (is added to the configured lit time) (" + SOUL_CAMPFIRE_MAX_LIT_TIME_EXTENSION_DEFAULT + " ticks default)";
    long SOUL_CAMPFIRE_MAX_LIT_TIME_EXTENSION_MIN = 1L;
    long SOUL_CAMPFIRE_MAX_LIT_TIME_EXTENSION_MAX = 2000000L;

    boolean SOUL_CAMPFIRE_AFFECTED_BY_SLEEP_TIME_DEFAULT = false;
    String SOUL_CAMPFIRE_AFFECTED_BY_SLEEP_TIME_DESCRIPTION = "Whether the lit time of a soul campfire should be affected by the sleep time.";

    boolean GENERATED_SOUL_CAMPFIRE_IS_LIT_INFINITELY_DEFAULT = true;
    String GENERATED_SOUL_CAMPFIRE_IS_LIT_INFINITELY_DESCRIPTION = "Whether generated soul campfires should be lit infinitely.";

    boolean INFINITE_SOUL_CAMPFIRE_IGNORES_RAIN_DEFAULT = true;
    String INFINITE_SOUL_CAMPFIRE_IGNORES_RAIN_DESCRIPTION = "Whether infinite soul campfires should stay lit in rain.";

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
    long getLitTime(boolean isSoulCampfire);

    /**
     * Gets the configured rain unlit time value.
     *
     * @param isSoulCampfire parameter which indicates if the campfire or soul campfire value should be returned
     * @return configured rain unlit time value
     */
    long getRainUnlitTime(boolean isSoulCampfire);

    /**
     * Gets the configured "runs out" indicator time value.
     *
     * @param isSoulCampfire parameter which indicates if the campfire or soul campfire value should be returned
     * @return configured "runs out" indicator time value
     */
    long getRunOutIndicatorTime(boolean isSoulCampfire);

    /**
     * Gets the configured rain particle factor value.
     *
     * @param isSoulCampfire parameter which indicates if the campfire or soul campfire value should be returned
     * @return configured rain particle factor value
     */
    int getRainParticleFactor(boolean isSoulCampfire);

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
     * Gets the configured max lit time extension value.
     *
     * @param isSoulCampfire parameter which indicates if the campfire or soul campfire value should be returned
     * @return configured lit time value
     */
    long getMaxLitTimeExtension(boolean isSoulCampfire);

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

    /**
     * Gets the configured "infinite campfire ignores rain" value.
     *
     * @param isSoulCampfire parameter which indicates if the campfire or soul campfire value should be returned
     * @return configured "infinite campfire ignores rain" value
     */
    boolean isInfiniteCampfireIgnoringRain(boolean isSoulCampfire);

}