package cech12.unlitcampfire.mixin;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.CampfireBlock;
import net.minecraft.item.ItemPlacementContext;

import cech12.unlitcampfire.UnlitCampfireMod;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Intrinsic;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Random;

@Mixin(CampfireBlock.class)
public abstract class CampfireBlockMixin extends BlockWithEntity {

    protected CampfireBlockMixin(AbstractBlock.Settings builder) {
        super(builder);
    }

    @Inject(at = @At("RETURN"), method = "<init>*")
    protected void initProxy(CallbackInfo info) {
        this.setDefaultState(this.getDefaultState().with(CampfireBlock.LIT, false));
    }

    @Inject(at = @At("RETURN"), method = "getPlacementState", cancellable = true)
    protected void getPlacementStateProxy(ItemPlacementContext context, CallbackInfoReturnable<BlockState> cir) {
        if (cir.getReturnValue() != null) {
            cir.setReturnValue(cir.getReturnValue().with(CampfireBlock.LIT, false));
            cir.cancel();
        }
    }

    //overrides randomDisplayTick method and has access to the original method
    @Intrinsic(displace = true)
    public void id$randomDisplayTick(BlockState stateIn, World world, BlockPos pos, Random rand) {
        int particleFactor = 1;
        if (world.hasRain(pos.up())) {
            if (world.getBlockState(pos).getBlock() == Blocks.SOUL_CAMPFIRE) {
                particleFactor = UnlitCampfireMod.CONFIG.SOUL_CAMPFIRE.SOUL_CAMPFIRE_RAIN_PARTICLE_FACTOR;
            } else {
                particleFactor = UnlitCampfireMod.CONFIG.CAMPFIRE.CAMPFIRE_RAIN_PARTICLE_FACTOR;
            }
        }
        for (int i = 0; i < particleFactor; i++) {
            this.randomDisplayTick(stateIn, world, pos, rand);
        }
    }

}
