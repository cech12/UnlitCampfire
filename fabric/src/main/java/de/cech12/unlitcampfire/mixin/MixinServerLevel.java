package de.cech12.unlitcampfire.mixin;

import de.cech12.unlitcampfire.CommonLoader;
import net.minecraft.core.Holder;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.clock.ClockTimeMarker;
import net.minecraft.world.clock.ClockTimeMarkers;
import net.minecraft.world.clock.ServerClockManager;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.storage.WritableLevelData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.BooleanSupplier;

@Mixin(ServerLevel.class)
public abstract class MixinServerLevel extends Level {

    protected MixinServerLevel(WritableLevelData writableLevelData, ResourceKey<Level> resourceKey, RegistryAccess registryAccess, Holder<DimensionType> holder, boolean bl, boolean bl2, long l, int i) {
        super(writableLevelData, resourceKey, registryAccess, holder, bl, bl2, l, i);
    }

    @Inject(method = "tick(Ljava/util/function/BooleanSupplier;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/clock/ServerClockManager;moveToTimeMarker(Lnet/minecraft/core/Holder;Lnet/minecraft/resources/ResourceKey;)Z"))
    private void tickProxy(BooleanSupplier booleanSupplier, CallbackInfo info) {
        if (this.getServer() == null) return;
        ServerClockManager clockManager = this.getServer().clockManager();
        this.dimensionType().defaultClock().ifPresent(clockHolder -> {
            this.registryAccess().lookupOrThrow(Registries.TIMELINE).listElements().filter(timeline -> timeline.value().clock().equals(clockHolder)).findFirst().ifPresent(timelineHolder -> {
                //only the standard "wake_up_from_sleep" marker is supported here
                ClockTimeMarker wakeUpFromSleepMarker = clockManager.getInstance(clockHolder).timeMarkers.get(ClockTimeMarkers.WAKE_UP_FROM_SLEEP);
                if (wakeUpFromSleepMarker != null) {
                    long totalTicks = timelineHolder.value().getTotalTicks(clockManager);
                    long sleepTicks = wakeUpFromSleepMarker.resolveTimeToMoveTo(totalTicks) - totalTicks;
                    CommonLoader.updateCampfiresAfterSleep(this, sleepTicks);
                }
            });
        });
    }
}