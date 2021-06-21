package cech12.unlitcampfire.config;

import cech12.unlitcampfire.UnlitCampfireMod;
import me.sargunvohra.mcmods.autoconfig1u.ConfigData;
import me.sargunvohra.mcmods.autoconfig1u.annotation.Config;
import me.sargunvohra.mcmods.autoconfig1u.annotation.ConfigEntry;

@Config(name = UnlitCampfireMod.MOD_ID)
public class UnlitCampfireConfig implements ConfigData {

    @ConfigEntry.Gui.CollapsibleObject
    @ConfigEntry.Gui.Tooltip(count = 2)
    public Campfire CAMPFIRE = new Campfire();

    public static final class Campfire implements ConfigData {

        @ConfigEntry.Gui.Tooltip(count = 5)
        @ConfigEntry.BoundedDiscrete(max = 10000)
        public int CAMPFIRE_LIT_TIME = 2000;
        
        @ConfigEntry.Gui.Tooltip(count = 6)
        @ConfigEntry.BoundedDiscrete(max = 10000, min = -1)
        public int CAMPFIRE_RAIN_UNLIT_TIME = 160;
        
        @ConfigEntry.Gui.Tooltip(count = 5)
        @ConfigEntry.BoundedDiscrete(max = 10, min = 1)
        public int CAMPFIRE_RAIN_PARTICLE_FACTOR = 2;

                /*.comment("The time (ticks) a campfire burns until it goes out during rain. (160 ticks [default]; 0: it goes out immediately; -1: it burns during rain).")

                .comment("The Factor of particle count of a campfire during rain. (2: doubled particles count [default]; 1: same particle count)")*/
        
        @ConfigEntry.Gui.Tooltip(count = 4)
        public boolean CAMPFIRE_DROPS_ITEMS_WHEN_UNLIT_BY_TIME_OR_RAIN = true;
        
        @ConfigEntry.Gui.Tooltip(count = 4)
        public boolean CAMPFIRE_BREAKS_WHEN_UNLIT_BY_TIME = false;
            
        private Campfire() {}
    }

    @ConfigEntry.Gui.CollapsibleObject
    @ConfigEntry.Gui.Tooltip(count = 2)
    public SoulCampfire SOUL_CAMPFIRE = new SoulCampfire();

    public static final class SoulCampfire implements ConfigData {

        @ConfigEntry.Gui.Tooltip(count = 5)
        @ConfigEntry.BoundedDiscrete(max = 10000)
        public int SOUL_CAMPFIRE_LIT_TIME = 2000;
        
        @ConfigEntry.Gui.Tooltip(count = 6)
        @ConfigEntry.BoundedDiscrete(max = 10000, min = -1)
        public int SOUL_CAMPFIRE_RAIN_UNLIT_TIME = -1;
        
        @ConfigEntry.Gui.Tooltip(count = 5)
        @ConfigEntry.BoundedDiscrete(max = 10, min = 1)
        public int SOUL_CAMPFIRE_RAIN_PARTICLE_FACTOR = 2;
        
        @ConfigEntry.Gui.Tooltip(count = 4)
        public boolean SOUL_CAMPFIRE_DROPS_ITEMS_WHEN_UNLIT_BY_TIME_OR_RAIN = true;
        
        @ConfigEntry.Gui.Tooltip(count = 4)
        public boolean SOUL_CAMPFIRE_BREAKS_WHEN_UNLIT_BY_TIME = false;
            
        private SoulCampfire() {}
    }

}
