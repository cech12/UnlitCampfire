package de.cech12.unlitcampfire;

import de.cech12.unlitcampfire.compat.TOPCompat;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.clock.ClockTimeMarker;
import net.minecraft.world.clock.ServerClockManager;
import net.minecraft.world.level.LevelAccessor;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.ModList;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.util.ClockAdjustment;
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

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onSleepFinishTimeEvent(SleepFinishedTimeEvent event) {
        LevelAccessor level = event.getLevel();
        if (level.isClientSide() || level.getServer() == null) return;
        ClockAdjustment adjustment = event.getAdjustment();

        //if another mod is changing the sleep clock adjustment to a relative time
        if (adjustment instanceof ClockAdjustment.Relative(long ticks) && ticks > 0L) {
            CommonLoader.updateCampfiresAfterSleep(level, ticks);
            return;
        }

        ServerClockManager clockManager = level.getServer().clockManager();
        level.dimensionType().defaultClock().ifPresent(clockHolder -> {
            level.registryAccess().lookupOrThrow(Registries.TIMELINE).listElements().filter(timeline -> timeline.value().clock().equals(clockHolder)).findFirst().ifPresent(timelineHolder -> {
                long totalTicks = timelineHolder.value().getTotalTicks(clockManager);
                switch (adjustment) {
                    //if another mod is changing the sleep clock adjustment to an absolute time value in the future
                    case ClockAdjustment.Absolute(long ticks) -> {
                        if (ticks > totalTicks) {
                            CommonLoader.updateCampfiresAfterSleep(level, ticks - totalTicks);
                        }
                    }
                    //if the standard "wake_up_from_sleep" marker or another modded marker is used, calculate the slept time
                    case ClockAdjustment.Marker(ResourceKey<ClockTimeMarker> marker) -> {
                        ClockTimeMarker wakeUpFromSleepMarker = clockManager.getInstance(clockHolder).timeMarkers.get(marker);
                        if (wakeUpFromSleepMarker != null) {
                            long sleepTicks = wakeUpFromSleepMarker.resolveTimeToMoveTo(totalTicks) - totalTicks;
                            if (sleepTicks > 0) {
                                CommonLoader.updateCampfiresAfterSleep(level, sleepTicks);
                            }
                        }
                    }
                    default -> Constants.LOG.warn("Unknown ClockAdjustment type was used in SleepFinishedTimeEvent: {}", adjustment.getClass().getName());
                }
            });
        });
    }

}
