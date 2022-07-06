package cech12.unlitcampfire.mixin;

import cech12.unlitcampfire.config.ServerConfig;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CampfireBlock;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Intrinsic;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import javax.annotation.Nonnull;

@Mixin(CampfireBlock.class)
public abstract class CampfireBlockMixin extends BaseEntityBlock {

    protected CampfireBlockMixin(Properties builder) {
        super(builder);
    }

    @Inject(at = @At("RETURN"), method = "<init>*")
    protected void initProxy(boolean spawnParticles, int fireDamage, BlockBehaviour.Properties properties, CallbackInfo info) {
        this.registerDefaultState(this.defaultBlockState().setValue(CampfireBlock.LIT, false));
    }

    @Inject(at = @At("RETURN"), method = "getStateForPlacement", cancellable = true)
    protected void getStateForPlacementProxy(BlockPlaceContext context, CallbackInfoReturnable<BlockState> cir) {
        if (cir.getReturnValue() != null) {
            cir.setReturnValue(cir.getReturnValue().setValue(CampfireBlock.LIT, false));
            cir.cancel();
        }
    }

    //overrides animateTick method and has access to the original method
    @Intrinsic(displace = true)
    public void id$animateTick(@Nonnull BlockState stateIn, Level worldIn, BlockPos pos, @Nonnull RandomSource rand) {
        int particleFactor = 1;
        if (worldIn.isRainingAt(pos.above())) {
            if (worldIn.getBlockState(pos).getBlock() == Blocks.SOUL_CAMPFIRE) {
                particleFactor = ServerConfig.SOUL_CAMPFIRE_RAIN_PARTICLE_FACTOR.get();
            } else {
                particleFactor = ServerConfig.CAMPFIRE_RAIN_PARTICLE_FACTOR.get();
            }
        }
        for (int i = 0; i < particleFactor; i++) {
            this.animateTick(stateIn, worldIn, pos, rand);
        }
    }

}
