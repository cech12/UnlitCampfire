package cech12.unlitcampfire.mixin;

import cech12.unlitcampfire.config.ServerConfig;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.CampfireBlock;
import net.minecraft.block.ContainerBlock;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Intrinsic;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import javax.annotation.Nonnull;
import java.util.Random;

@Mixin(CampfireBlock.class)
public abstract class CampfireBlockMixin extends ContainerBlock {

    protected CampfireBlockMixin(Properties builder) {
        super(builder);
    }

    @Inject(at = @At("RETURN"), method = "<init>*")
    protected void initProxy(CallbackInfo info) {
        this.setDefaultState(this.getDefaultState().with(CampfireBlock.LIT, false));
    }

    @Inject(at = @At("RETURN"), method = "getStateForPlacement", cancellable = true)
    protected void getStateForPlacementProxy(BlockItemUseContext context, CallbackInfoReturnable<BlockState> cir) {
        if (cir.getReturnValue() != null) {
            cir.setReturnValue(cir.getReturnValue().with(CampfireBlock.LIT, false));
            cir.cancel();
        }
    }

    //overrides animateTick method and has access to the original method
    @Intrinsic(displace = true)
    public void id$animateTick(@Nonnull BlockState stateIn, World worldIn, BlockPos pos, @Nonnull Random rand) {
        int particleFactor = 1;
        if (worldIn.isRainingAt(pos.up())) {
            particleFactor = ServerConfig.CAMPFIRE_RAIN_PARTICLE_FACTOR.get();
        }
        for (int i = 0; i < particleFactor; i++) {
            this.animateTick(stateIn, worldIn, pos, rand);
        }
    }

}
