package cech12.unlitcampfire.mixin;

import cech12.unlitcampfire.config.ServerConfig;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.CampfireBlock;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.CampfireTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(CampfireTileEntity.class)
public abstract class CampfireTileEntityMixin extends TileEntity {

    private int litTime = 0;

    public CampfireTileEntityMixin() {
        super(TileEntityType.CAMPFIRE);
    }

    @Inject(at = @At("RETURN"), method = "tick")
    protected void tickProxy(CallbackInfo info) {
        World world = this.getWorld();
        if (world != null) {
            int maxLitTime = ServerConfig.CAMPFIRE_LIT_TIME.get();
            if (maxLitTime > 0 && this.getBlockState().get(CampfireBlock.LIT)) {
                litTime++;
                if (litTime >= maxLitTime) {
                    //unlit sound
                    if (!world.isRemote()) {
                        world.playEvent(null, 1009, this.getPos(), 0);
                    }
                    if (ServerConfig.CAMPFIRE_BREAKS_AFTER_LIT_TIME.get()) {
                        //drop all items
                        CampfireBlock.func_235475_c_(world, this.getPos(), this.getBlockState());
                        //destroy campfire
                        world.setBlockState(this.getPos(), Blocks.AIR.getDefaultState());
                    } else {
                        if (ServerConfig.CAMPFIRE_DROPS_ITEMS_AFTER_LIT_TIME.get()) {
                            //drop all items
                            CampfireBlock.func_235475_c_(world, this.getPos(), this.getBlockState());
                        }
                        //unlit campfire
                        world.setBlockState(this.getPos(), this.getBlockState().with(CampfireBlock.LIT, false));
                    }
                }
            } else {
                litTime = 0;
            }
        }
    }


    @Inject(at = @At("RETURN"), method = "read")
    protected void readProxy(BlockState stateIn, CompoundNBT nbtIn, CallbackInfo info) {
        if (nbtIn.contains("CampfireLitTime")) {
            this.litTime = nbtIn.getInt("CampfireLitTime");
        }
    }

    @Inject(at = @At("RETURN"), method = "write", cancellable = true)
    protected void writeProxy(CompoundNBT compound, CallbackInfoReturnable<CompoundNBT> cir) {
        CompoundNBT nbt = cir.getReturnValue();
        if (nbt != null) {
            nbt.putInt("CampfireLitTime", this.litTime);
            cir.setReturnValue(nbt);
            cir.cancel();
        }
    }

}
