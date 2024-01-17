package de.cech12.unlitcampfire;

import net.minecraft.world.level.LevelAccessor;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.event.level.SleepFinishedTimeEvent;

@Mod(NeoForgeUnlitCampfireMod.MOD_ID)
@Mod.EventBusSubscriber
public class NeoForgeUnlitCampfireMod {
    public static final String MOD_ID = "unlitcampfire";


    public NeoForgeUnlitCampfireMod() {
        CommonLoader.init();
    }

    @SubscribeEvent
    public static void onSleepFinishTimeEvent(SleepFinishedTimeEvent event) {
        LevelAccessor level = event.getLevel();
        if (level.isClientSide()) return;
        int sleepTime = (int) ((event.getNewTime() >= level.dayTime()) ? (event.getNewTime() - level.dayTime()) : (24000L - level.dayTime() + event.getNewTime()));
        CommonLoader.updateCampfiresAfterSleep(sleepTime);
    }

}
