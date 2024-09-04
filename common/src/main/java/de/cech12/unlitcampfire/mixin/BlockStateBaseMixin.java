package de.cech12.unlitcampfire.mixin;

import com.mojang.serialization.MapCodec;
import de.cech12.unlitcampfire.mixinaccess.ICampfireBlockMixin;
import it.unimi.dsi.fastutil.objects.Reference2ObjectArrayMap;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CampfireBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BlockBehaviour.BlockStateBase.class)
public abstract class BlockStateBaseMixin {

    @Shadow protected abstract BlockState asState();

    @Mutable
    @Shadow @Final private int lightEmission;

    @Inject(at = @At("RETURN"), method = "<init>*")
    protected void initProxy(Block block, Reference2ObjectArrayMap<Property<?>, Comparable<?>> ref, MapCodec<BlockState> codec, CallbackInfo callbackInfo) {
        if (block instanceof CampfireBlock && this.asState().getValue(CampfireBlock.LIT) && this.asState().getValue(ICampfireBlockMixin.RUNS_OUT)) {
            lightEmission = lightEmission * 2 / 3;
        }
    }

}
