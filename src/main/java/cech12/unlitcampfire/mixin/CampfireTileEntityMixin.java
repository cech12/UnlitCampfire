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

    private Boolean isSoulCampfire;

    private int litTime = 0;
    private int rainTime = 0;

    public CampfireTileEntityMixin() {
        super(TileEntityType.CAMPFIRE);
    }

    private boolean isSoulCampfire() {
        if (isSoulCampfire == null) {
            if (this.world != null) {
                isSoulCampfire = this.world.getBlockState(this.pos).getBlock() == Blocks.SOUL_CAMPFIRE;
                return isSoulCampfire;
            }
            return false;
        }
        return isSoulCampfire;
    }

    private int getMaxLitTime() {
        if (isSoulCampfire()) {
            return ServerConfig.SOUL_CAMPFIRE_LIT_TIME.get();
        }
        return ServerConfig.CAMPFIRE_LIT_TIME.get();
    }

    private boolean dropsItemsWhenUnlitByTimeOrRain() {
        if (isSoulCampfire()) {
            return ServerConfig.SOUL_CAMPFIRE_DROPS_ITEMS_WHEN_UNLIT_BY_TIME_OR_RAIN.get();
        }
        return ServerConfig.CAMPFIRE_DROPS_ITEMS_WHEN_UNLIT_BY_TIME_OR_RAIN.get();
    }

    private boolean breaksWhenUnlitByTime() {
        if (isSoulCampfire()) {
            return ServerConfig.SOUL_CAMPFIRE_BREAKS_WHEN_UNLIT_BY_TIME.get();
        }
        return ServerConfig.CAMPFIRE_BREAKS_WHEN_UNLIT_BY_TIME.get();
    }

    private int getRainUnlitTime() {
        if (isSoulCampfire()) {
            return ServerConfig.SOUL_CAMPFIRE_RAIN_UNLIT_TIME.get();
        }
        return ServerConfig.CAMPFIRE_RAIN_UNLIT_TIME.get();
    }

    private int getParticleFactorDuringRain() {
        if (isSoulCampfire()) {
            return ServerConfig.SOUL_CAMPFIRE_RAIN_PARTICLE_FACTOR.get();
        }
        return ServerConfig.CAMPFIRE_RAIN_PARTICLE_FACTOR.get();
    }

    private void playUnlitSound() {
        if (this.world != null && !this.world.isRemote()) {
            this.world.playEvent(null, 1009, this.getPos(), 0);
        }
    }

    private void dropAllContainingItems() {
        if (this.world != null) {
            CampfireBlock.func_235475_c_(this.world, this.getPos(), this.getBlockState());
        }
    }

    private void destroyCampfire() {
        if (this.world != null) {
            this.playUnlitSound();
            this.dropAllContainingItems();
            this.world.setBlockState(this.getPos(), Blocks.AIR.getDefaultState());
        }
    }

    private void unlitCampfire() {
        if (this.world != null) {
            this.playUnlitSound();
            if (this.dropsItemsWhenUnlitByTimeOrRain()) {
                this.dropAllContainingItems();
            }
            this.world.setBlockState(this.getPos(), this.getBlockState().with(CampfireBlock.LIT, false));
        }
    }

    @Inject(at = @At("RETURN"), method = "tick")
    protected void tickProxy(CallbackInfo info) {
        World world = this.getWorld();
        if (world != null) {
            if (this.getBlockState().get(CampfireBlock.LIT)) {
                //if lit time is active
                int maxLitTime = this.getMaxLitTime();
                if (maxLitTime > 0) {
                    litTime++;
                    if (litTime >= maxLitTime) {
                        if (this.breaksWhenUnlitByTime()) {
                            this.destroyCampfire();
                        } else {
                            this.unlitCampfire();
                        }
                        return; //fixes destroying while raining
                    }
                }
                if (world.isRainingAt(this.getPos().up())) {
                    //if rain should unlit a campfire and it is raining there
                    int rainUnlitTime = this.getRainUnlitTime();
                    if (rainUnlitTime >= 0) {
                        rainTime++;
                        if (rainTime >= rainUnlitTime) {
                            rainTime = 0;
                            this.unlitCampfire();
                            return; //no particles needed
                        }
                    } else {
                        rainTime = 0;
                    }
                    //during rain the campfire has more particles (if activated)
                    int particleFactor = this.getParticleFactorDuringRain();
                    if (this.world != null && this.world.isRemote && particleFactor > 1) {
                        TileEntity tileEntity = this.world.getTileEntity(this.pos);
                        if (tileEntity instanceof CampfireTileEntity) {
                            CampfireTileEntity campfireTileEntity = (CampfireTileEntity)tileEntity;
                            for (int i = 0; i < particleFactor - 1; i++) {
                                campfireTileEntity.addParticles();
                            }
                        }
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
