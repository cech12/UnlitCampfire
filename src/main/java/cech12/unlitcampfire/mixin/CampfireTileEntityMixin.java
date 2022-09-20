package cech12.unlitcampfire.mixin;

import cech12.unlitcampfire.config.ServerConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CampfireBlock;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.CampfireBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(CampfireBlockEntity.class)
public abstract class CampfireTileEntityMixin extends BlockEntity {

    private Boolean isSoulCampfire;

    private int litTime = 0;
    private int rainTime = 0;

    public CampfireTileEntityMixin(BlockPos pos, BlockState state) {
        super(BlockEntityType.CAMPFIRE, pos, state);
    }

    private boolean isSoulCampfire() {
        if (isSoulCampfire == null) {
            if (this.level != null) {
                isSoulCampfire = this.level.getBlockState(this.worldPosition).getBlock() == Blocks.SOUL_CAMPFIRE;
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
        if (this.level != null && !this.level.isClientSide()) {
            this.level.levelEvent(null, 1009, this.getBlockPos(), 0);
        }
    }

    private void dropAllContainingItems() {
        if (this.level != null) {
            CampfireBlock.dowse(null, this.level, this.getBlockPos(), this.getBlockState());
        }
    }

    private void destroyCampfire() {
        if (this.level != null) {
            this.playUnlitSound();
            this.dropAllContainingItems();
            this.level.setBlockAndUpdate(this.getBlockPos(), Blocks.AIR.defaultBlockState());
        }
    }

    private void unlitCampfire() {
        if (this.level != null) {
            this.playUnlitSound();
            if (this.dropsItemsWhenUnlitByTimeOrRain()) {
                this.dropAllContainingItems();
            }
            this.level.setBlockAndUpdate(this.getBlockPos(), this.getBlockState().setValue(CampfireBlock.LIT, false));
            this.litTime = 0;
        }
    }

    @Inject(at = @At("RETURN"), method = "cookTick")
    private static void cookTickProxy(Level level, BlockPos pos, BlockState state, CampfireBlockEntity blockEntity, CallbackInfo info) {
        CampfireTileEntityMixin mixinEntity = (CampfireTileEntityMixin) (BlockEntity) blockEntity;
        if (level != null) {
            if (state.getValue(CampfireBlock.LIT)) {
                //if lit time is active
                int maxLitTime = mixinEntity.getMaxLitTime();
                if (maxLitTime > 0) {
                    mixinEntity.litTime++;
                    if (mixinEntity.litTime >= maxLitTime) {
                        if (mixinEntity.breaksWhenUnlitByTime()) {
                            mixinEntity.destroyCampfire();
                        } else {
                            mixinEntity.unlitCampfire();
                        }
                        return; //fixes destroying while raining
                    }
                }
                if (level.isRainingAt(pos.above())) {
                    //if rain should unlit a campfire and it is raining there
                    int rainUnlitTime = mixinEntity.getRainUnlitTime();
                    if (rainUnlitTime >= 0) {
                        mixinEntity.rainTime++;
                        if (mixinEntity.rainTime >= rainUnlitTime) {
                            mixinEntity.rainTime = 0;
                            mixinEntity.unlitCampfire();
                        }
                    } else {
                        mixinEntity.rainTime = 0;
                    }
                }
            } else {
                mixinEntity.litTime = 0;
            }
        }
    }

    @Inject(at = @At("RETURN"), method = "particleTick")
    private static void particleTickProxy(Level level, BlockPos pos, BlockState state, CampfireBlockEntity blockEntity, CallbackInfo info) {
        CampfireTileEntityMixin mixinEntity = (CampfireTileEntityMixin) (BlockEntity) blockEntity;
        //during rain the campfire has more particles (if activated)
        int particleFactor = mixinEntity.getParticleFactorDuringRain();
        if (level != null && level.isClientSide && particleFactor > 1 && level.isRainingAt(pos.above())) {
            for (int i = 0; i < particleFactor - 1; i++) {
                CampfireBlock.makeParticles(level, pos, state.getValue(CampfireBlock.SIGNAL_FIRE), false);
            }
        }
    }


    @Inject(at = @At("RETURN"), method = "load")
    protected void loadProxy(CompoundTag nbtIn, CallbackInfo info) {
        if (nbtIn.contains("CampfireLitTime")) {
            this.litTime = nbtIn.getInt("CampfireLitTime");
        }
    }

    @Inject(at = @At("RETURN"), method = "saveAdditional")
    protected void saveAdditionalProxy(CompoundTag compound, CallbackInfo info) {
        if (compound != null) {
            compound.putInt("CampfireLitTime", this.litTime);
        }
    }

    @Inject(at = @At("RETURN"), method = "getUpdateTag")
    protected void getUpdateTagProxy(CallbackInfoReturnable<CompoundTag> info) {
        CompoundTag compound = info.getReturnValue();
        if (compound != null) {
            compound.putInt("CampfireLitTime", this.litTime);
        }
    }

}
