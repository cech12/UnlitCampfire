package de.cech12.unlitcampfire.mixin;

import de.cech12.unlitcampfire.CommonLoader;
import de.cech12.unlitcampfire.mixinaccess.ICampfireBlockEntityMixin;
import de.cech12.unlitcampfire.mixinaccess.ICampfireBlockMixin;
import de.cech12.unlitcampfire.platform.Services;
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
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(CampfireBlockEntity.class)
public abstract class CampfireBlockEntityMixin extends BlockEntity implements ICampfireBlockEntityMixin {

    @Shadow protected abstract void markUpdated();

    @Shadow public abstract void dowse();

    @Unique
    private Boolean unlitCampfire$isSoulCampfire;

    @Unique
    private int unlitCampfire$litTime = 0;
    @Unique
    private int unlitCampfire$rainTime = 0;

    public CampfireBlockEntityMixin(BlockPos pos, BlockState state) {
        super(BlockEntityType.CAMPFIRE, pos, state);
        CommonLoader.addCampfire(this);
    }

    @Override
    public boolean unlitCampfire$isSoulCampfire() {
        if (unlitCampfire$isSoulCampfire == null) {
            if (this.level != null) {
                unlitCampfire$isSoulCampfire = this.level.getBlockState(this.worldPosition).getBlock() == Blocks.SOUL_CAMPFIRE;
                return unlitCampfire$isSoulCampfire;
            }
            return false;
        }
        return unlitCampfire$isSoulCampfire;
    }

    @Unique
    private int unlitCampfire$getMaxLitTime() {
        return ((ICampfireBlockMixin) this.getBlockState().getBlock()).unlitCampfire$getMaxLitTime(this.getBlockState());
    }

    @Unique
    private boolean unlitCampfire$burnsInfinite() {
        return ((ICampfireBlockMixin) this.getBlockState().getBlock()).unlitCampfire$burnsInfinite(this.getBlockState());
    }

    @Unique
    private void unlitCampfire$playUnlitSound() {
        if (this.level != null && !this.level.isClientSide()) {
            this.level.levelEvent(null, 1009, this.getBlockPos(), 0);
        }
    }

    @Unique
    private void unlitCampfire$dropAllContainingItems() {
        if (this.level != null) {
            CampfireBlock.dowse(null, this.level, this.getBlockPos(), this.getBlockState());
        }
    }

    @Unique
    private void unlitCampfire$destroyCampfire() {
        if (this.level != null) {
            this.unlitCampfire$playUnlitSound();
            this.unlitCampfire$dropAllContainingItems();
            this.level.setBlockAndUpdate(this.getBlockPos(), Blocks.AIR.defaultBlockState());
        }
    }

    @Unique
    private void unlitCampfire$unlitCampfire() {
        if (this.level != null) {
            this.unlitCampfire$playUnlitSound();
            if (Services.CONFIG.isDroppingItemsWhenUnlitByTimeOrRain(unlitCampfire$isSoulCampfire())) {
                this.unlitCampfire$dropAllContainingItems();
            } else {
                this.dowse();
            }
            this.level.setBlockAndUpdate(this.getBlockPos(), this.getBlockState().setValue(CampfireBlock.LIT, false));
        }
    }

    @Override
    public int unlitCampfire$getLitTime() {
        return this.unlitCampfire$litTime;
    }

    @Override
    public boolean unlitCampfire$addLitTime(int litTimeToAdd) {
        if (this.unlitCampfire$burnsInfinite()) {
            return false;
        }
        if (litTimeToAdd < 0) {
            return unlitCampfire$removeLitTime(-litTimeToAdd);
        }
        if (this.unlitCampfire$litTime <= 0 || !this.getBlockState().getValue(CampfireBlock.LIT)) {
            return false;
        }
        this.unlitCampfire$litTime = this.unlitCampfire$litTime - litTimeToAdd;
        this.markUpdated();
        return true;
    }

    @Override
    public boolean unlitCampfire$removeLitTime(int litTimeToRemove) {
        if (this.unlitCampfire$burnsInfinite()) {
            return false;
        }
        if (litTimeToRemove < 0) {
            return unlitCampfire$addLitTime(-litTimeToRemove);
        }
        if (this.unlitCampfire$litTime >= this.unlitCampfire$getMaxLitTime() || !this.getBlockState().getValue(CampfireBlock.LIT)) {
            return false;
        }
        this.unlitCampfire$litTime = this.unlitCampfire$litTime + litTimeToRemove;
        this.markUpdated();
        return true;
    }

    @Inject(at = @At("RETURN"), method = "cookTick")
    private static void cookTickProxy(Level level, BlockPos pos, BlockState state, CampfireBlockEntity blockEntity, CallbackInfo info) {
        CampfireBlockEntityMixin mixinEntity = (CampfireBlockEntityMixin) (BlockEntity) blockEntity;
        if (level != null) {
            if (state.getValue(CampfireBlock.LIT) && !mixinEntity.unlitCampfire$burnsInfinite()) {
                mixinEntity.unlitCampfire$litTime++;
                if (mixinEntity.unlitCampfire$litTime >= mixinEntity.unlitCampfire$getMaxLitTime()) {
                    if (Services.CONFIG.isBreakingWhenUnlitByTime(mixinEntity.unlitCampfire$isSoulCampfire())) {
                        mixinEntity.unlitCampfire$destroyCampfire();
                    } else {
                        mixinEntity.unlitCampfire$unlitCampfire();
                    }
                    return; //fixes destroying while raining
                }
                //refresh client side once per second if burnables can be added to campfire
                if (mixinEntity.unlitCampfire$litTime % 20 == 1 && Services.CONFIG.canAddBurnables(mixinEntity.unlitCampfire$isSoulCampfire())) {
                    mixinEntity.markUpdated();
                }
                //if rain should unlit a campfire, and it is raining there
                int rainUnlitTime = Services.CONFIG.getRainUnlitTime(mixinEntity.unlitCampfire$isSoulCampfire());
                if (rainUnlitTime >= 0 && level.isRainingAt(pos.above())) {
                    mixinEntity.unlitCampfire$rainTime++;
                    if (mixinEntity.unlitCampfire$rainTime >= rainUnlitTime) {
                        mixinEntity.unlitCampfire$unlitCampfire();
                    }
                } else {
                    mixinEntity.unlitCampfire$rainTime = 0;
                }
            } else {
                mixinEntity.unlitCampfire$litTime = 0;
            }
        }
    }

    @Inject(at = @At("RETURN"), method = "particleTick")
    private static void particleTickProxy(Level level, BlockPos pos, BlockState state, CampfireBlockEntity blockEntity, CallbackInfo info) {
        CampfireBlockEntityMixin mixinEntity = (CampfireBlockEntityMixin) (BlockEntity) blockEntity;
        //during rain the campfire has more particles (if activated)
        int particleFactor = Services.CONFIG.getRainParticleFactor(mixinEntity.unlitCampfire$isSoulCampfire());
        if (level != null && level.isClientSide && particleFactor > 1 && level.isRainingAt(pos.above())) {
            for (int i = 0; i < particleFactor - 1; i++) {
                CampfireBlock.makeParticles(level, pos, state.getValue(CampfireBlock.SIGNAL_FIRE), false);
            }
        }
    }


    @Inject(at = @At("RETURN"), method = "load")
    protected void loadProxy(CompoundTag nbtIn, CallbackInfo info) {
        if (nbtIn.contains("CampfireLitTime")) {
            this.unlitCampfire$litTime = nbtIn.getInt("CampfireLitTime");
        }
    }

    @Inject(at = @At("RETURN"), method = "saveAdditional")
    protected void saveAdditionalProxy(CompoundTag compound, CallbackInfo info) {
        if (compound != null) {
            compound.putInt("CampfireLitTime", this.unlitCampfire$litTime);
        }
        CommonLoader.addCampfire(this); //remember server side block entities
    }

    @Inject(at = @At("RETURN"), method = "getUpdateTag")
    protected void getUpdateTagProxy(CallbackInfoReturnable<CompoundTag> info) {
        CompoundTag compound = info.getReturnValue();
        if (compound != null) {
            compound.putInt("CampfireLitTime", this.unlitCampfire$litTime);
        }
    }

    @Inject(at = @At("RETURN"), method = "dowse")
    protected void dowseProxy(CallbackInfo info) {
        //is called by multiple sources such as shovel, water potion, water bucket extinguishing
        this.unlitCampfire$litTime = 0;
        this.unlitCampfire$rainTime = 0;
        this.markUpdated();
    }

}
