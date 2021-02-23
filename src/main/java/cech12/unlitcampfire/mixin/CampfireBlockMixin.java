package cech12.unlitcampfire.mixin;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.CampfireBlock;
import net.minecraft.item.ItemPlacementContext;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

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
}
