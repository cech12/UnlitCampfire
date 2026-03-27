package de.cech12.unlitcampfire;

import net.minecraft.world.level.LevelAccessor;
import net.minecraftforge.event.level.SleepFinishedTimeEvent;
import net.minecraftforge.eventbus.api.listener.Priority;
import net.minecraftforge.eventbus.api.listener.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@SuppressWarnings("unused")
@Mod(Constants.MOD_ID)
@Mod.EventBusSubscriber(modid = Constants.MOD_ID)
public class ForgeUnlitCampfireMod {

    public ForgeUnlitCampfireMod() {
        CommonLoader.init();
    }

    @SubscribeEvent(priority = Priority.LOWEST)
    public static void onSleepFinishTimeEvent(SleepFinishedTimeEvent event) {
        LevelAccessor level = event.getLevel();
        if (level.isClientSide() || level.getServer() == null) return;
        level.dimensionType().defaultClock().ifPresent(clock -> {
            long currentTime = level.getServer().clockManager().getTotalTicks(clock);
            long sleepTime = event.getNewTime() - currentTime;
            CommonLoader.updateCampfiresAfterSleep(level, sleepTime);
        });
    }

}
