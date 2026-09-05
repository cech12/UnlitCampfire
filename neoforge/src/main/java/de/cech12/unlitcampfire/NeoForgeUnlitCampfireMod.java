package de.cech12.unlitcampfire;

import de.cech12.unlitcampfire.compat.TOPCompat;
import net.minecraft.world.level.LevelAccessor;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.ModList;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.event.level.SleepFinishedTimeEvent;

@SuppressWarnings("unused")
@Mod(Constants.MOD_ID)
@EventBusSubscriber(modid = Constants.MOD_ID)
public class NeoForgeUnlitCampfireMod {

    public NeoForgeUnlitCampfireMod(ModContainer modContainer) {
        CommonLoader.init();

        //The One Probe registration.
        if (ModList.get().isLoaded("theoneprobe")) {
            TOPCompat.register();
        }
    }

    @SubscribeEvent
    public static void onSleepFinishTimeEvent(SleepFinishedTimeEvent event) {
        LevelAccessor level = event.getLevel();
        if (level.isClientSide()) return;
        int sleepTime = (int) ((event.getNewTime() >= level.getGameTime()) ? (event.getNewTime() - level.getGameTime()) : (24000L - level.getGameTime() + event.getNewTime()));
        CommonLoader.updateCampfiresAfterSleep(sleepTime);
    }

}
