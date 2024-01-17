package de.cech12.unlitcampfire;

import net.minecraft.world.level.LevelAccessor;
import net.minecraftforge.event.level.SleepFinishedTimeEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;

@Mod(ForgeUnlitCampfireMod.MOD_ID)
@Mod.EventBusSubscriber
public class ForgeUnlitCampfireMod {
    public static final String MOD_ID = "unlitcampfire";


    public ForgeUnlitCampfireMod() {
        CommonLoader.init();

        //The One Probe registration.
        if (ModList.get().isLoaded("theoneprobe")) {
            //TOPCompat.register();
        }
    }

    @SubscribeEvent
    public static void onSleepFinishTimeEvent(SleepFinishedTimeEvent event) {
        LevelAccessor level = event.getLevel();
        if (level.isClientSide()) return;
        int sleepTime = (int) ((event.getNewTime() >= level.dayTime()) ? (event.getNewTime() - level.dayTime()) : (24000L - level.dayTime() + event.getNewTime()));
        CommonLoader.updateCampfiresAfterSleep(sleepTime);
    }

}
