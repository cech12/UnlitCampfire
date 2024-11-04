package de.cech12.unlitcampfire.mixin;

import de.cech12.unlitcampfire.CommonLoader;
import net.minecraft.core.Holder;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
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

    @Inject(method = "tick(Ljava/util/function/BooleanSupplier;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/level/ServerLevel;setDayTime(J)V"))
    private void tickProxy(BooleanSupplier booleanSupplier, CallbackInfo info) {
        long dayTime = this.getDayTime();
        long l = dayTime + 24000L;
        long newTime = l - l % 24000L;
        int sleepTime = (int) ((newTime >= dayTime) ? (newTime - dayTime) : (24000L - dayTime + newTime));
        CommonLoader.updateCampfiresAfterSleep(sleepTime);
    }
}