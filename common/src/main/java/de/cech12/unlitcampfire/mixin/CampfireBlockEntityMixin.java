package de.cech12.unlitcampfire.mixin;

import de.cech12.unlitcampfire.CommonLoader;
import de.cech12.unlitcampfire.mixinaccess.ICampfireBlockEntityMixin;
import de.cech12.unlitcampfire.mixinaccess.ICampfireBlockMixin;
import de.cech12.unlitcampfire.platform.Services;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.crafting.CampfireCookingRecipe;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.SingleRecipeInput;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CampfireBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.CampfireBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
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

    @Unique
    private Boolean unlitCampfire$isSoulCampfire;

    @Unique
    private long unlitCampfire$litTime = 0L;
    @Unique
    private long unlitCampfire$rainTime = 0L;

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
    private long unlitCampfire$getMaxLitTime() {
        return ((ICampfireBlockMixin) this.getBlockState().getBlock()).unlitCampfire$getMaxLitTime(this.getBlockState());
    }

    @Unique
    private long unlitCampfire$getMaxLitTimeExtension() {
        return ((ICampfireBlockMixin) this.getBlockState().getBlock()).unlitCampfire$getMaxLitTimeExtension(this.getBlockState());
    }

    @Unique
    private long unlitCampfire$getRunsOutIndicator() {
        return ((ICampfireBlockMixin) this.getBlockState().getBlock()).unlitCampfire$getRunsOutIndicatorTime(this.getBlockState());
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
    private void unlitCampfire$destroyCampfire() {
        if (this.level != null) {
            this.unlitCampfire$playUnlitSound();
            this.level.setBlockAndUpdate(this.getBlockPos(), Blocks.AIR.defaultBlockState());
        }
    }

    @Unique
    private void unlitCampfire$unlitCampfire() {
        if (this.level != null) {
            this.unlitCampfire$playUnlitSound();
            this.level.setBlockAndUpdate(this.getBlockPos(), this.getBlockState().setValue(CampfireBlock.LIT, false));
            this.level.setBlockAndUpdate(this.getBlockPos(), this.getBlockState().setValue(ICampfireBlockMixin.RUNS_OUT, false));
        }
    }

    @Override
    public long unlitCampfire$getLitTime() {
        return this.unlitCampfire$litTime;
    }

    @Override
    public boolean unlitCampfire$addLitTime(long litTimeToAdd) {
        if (this.unlitCampfire$burnsInfinite()) {
            return false;
        }
        if (litTimeToAdd < 0L) {
            return unlitCampfire$removeLitTime(-litTimeToAdd);
        }
        if (this.unlitCampfire$litTime <= -this.unlitCampfire$getMaxLitTimeExtension() || !this.getBlockState().getValue(CampfireBlock.LIT)) {
            return false;
        }
        this.unlitCampfire$litTime = this.unlitCampfire$litTime - litTimeToAdd;
        this.markUpdated();
        return true;
    }

    @Override
    public boolean unlitCampfire$removeLitTime(long litTimeToRemove) {
        if (this.unlitCampfire$burnsInfinite()) {
            return false;
        }
        if (litTimeToRemove < 0L) {
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
    private static void cookTickProxy(ServerLevel level, BlockPos pos, BlockState state, CampfireBlockEntity blockEntity, RecipeManager.CachedCheck<SingleRecipeInput, CampfireCookingRecipe> cachedRecipe, CallbackInfo info) {
        CampfireBlockEntityMixin mixinEntity = (CampfireBlockEntityMixin) (BlockEntity) blockEntity;
        if (level != null && mixinEntity != null && state.getValue(CampfireBlock.LIT)) {
            if (!mixinEntity.unlitCampfire$burnsInfinite()) {
                mixinEntity.unlitCampfire$litTime++;
                if (mixinEntity.unlitCampfire$litTime >= mixinEntity.unlitCampfire$getMaxLitTime()) {
                    if (Services.CONFIG.isBreakingWhenUnlitByTime(mixinEntity.unlitCampfire$isSoulCampfire())) {
                        mixinEntity.unlitCampfire$destroyCampfire();
                    } else {
                        mixinEntity.unlitCampfire$unlitCampfire();
                    }
                    return; //fixes destroying while raining
                }
                //update "runs out" flag
                if (state.hasProperty(ICampfireBlockMixin.RUNS_OUT)) {
                    boolean runOutIndicatorReached = mixinEntity.unlitCampfire$litTime >= (mixinEntity.unlitCampfire$getMaxLitTime() - mixinEntity.unlitCampfire$getRunsOutIndicator());
                    boolean isRunOutActive = state.getValue(ICampfireBlockMixin.RUNS_OUT);
                    if ((runOutIndicatorReached && !isRunOutActive) || (!runOutIndicatorReached && isRunOutActive)) {
                        level.setBlockAndUpdate(pos, state.setValue(ICampfireBlockMixin.RUNS_OUT, !isRunOutActive));
                    }
                }
                //refresh client side once per second if burnables can be added to campfire
                if (mixinEntity.unlitCampfire$litTime % 20L == 1L && Services.CONFIG.canAddBurnables(mixinEntity.unlitCampfire$isSoulCampfire())) {
                    mixinEntity.markUpdated();
                }
            } else {
                mixinEntity.unlitCampfire$litTime = 0L;
            }
            //if rain should unlit a campfire, and it is raining there
            long rainUnlitTime = Services.CONFIG.getRainUnlitTime(mixinEntity.unlitCampfire$isSoulCampfire());
            boolean shouldUnlitByRain = rainUnlitTime >= 0L && (!mixinEntity.unlitCampfire$burnsInfinite() || !Services.CONFIG.isInfiniteCampfireIgnoringRain(mixinEntity.unlitCampfire$isSoulCampfire()));
            if (shouldUnlitByRain && level.isRainingAt(pos.above())) {
                mixinEntity.unlitCampfire$rainTime++;
                if (mixinEntity.unlitCampfire$rainTime >= rainUnlitTime) {
                    mixinEntity.unlitCampfire$unlitCampfire();
                }
            } else {
                mixinEntity.unlitCampfire$rainTime = 0L;
            }
        }
    }

    @Inject(at = @At("RETURN"), method = "cooldownTick")
    private static void cooldownTickProxy(Level level, BlockPos pos, BlockState state, CampfireBlockEntity blockEntity, CallbackInfo info) {
        CampfireBlockEntityMixin mixinEntity = (CampfireBlockEntityMixin) (BlockEntity) blockEntity;
        if (level != null && mixinEntity != null && !state.getValue(CampfireBlock.LIT)) {
            mixinEntity.unlitCampfire$litTime = 0L;
            mixinEntity.unlitCampfire$rainTime = 0L;
        }
    }

    @Inject(at = @At("RETURN"), method = "particleTick")
    private static void particleTickProxy(Level level, BlockPos pos, BlockState state, CampfireBlockEntity blockEntity, CallbackInfo info) {
        CampfireBlockEntityMixin mixinEntity = (CampfireBlockEntityMixin) (BlockEntity) blockEntity;
        //during rain the campfire has more particles (if activated)
        int particleFactor = Services.CONFIG.getRainParticleFactor(mixinEntity.unlitCampfire$isSoulCampfire());
        if (level != null && level.isClientSide() && particleFactor > 1 && level.isRainingAt(pos.above())) {
            for (int i = 0; i < particleFactor - 1; i++) {
                CampfireBlock.makeParticles(level, pos, state.getValue(CampfireBlock.SIGNAL_FIRE), false);
            }
        }
    }

    @Inject(at = @At("RETURN"), method = "loadAdditional")
    protected void loadAdditionalProxy(ValueInput valueInput, CallbackInfo info) {
        valueInput.getLong("CampfireLitTime").ifPresent(value -> this.unlitCampfire$litTime = value);
    }

    @Inject(at = @At("RETURN"), method = "saveAdditional")
    protected void saveAdditionalProxy(ValueOutput valueOutput, CallbackInfo info) {
        if (valueOutput != null) {
            valueOutput.putLong("CampfireLitTime", this.unlitCampfire$litTime);
        }
        CommonLoader.addCampfire(this); //remember server side block entities
    }

    @Inject(at = @At("RETURN"), method = "getUpdateTag")
    protected void getUpdateTagProxy(CallbackInfoReturnable<CompoundTag> info) {
        CompoundTag compound = info.getReturnValue();
        if (compound != null) {
            compound.putLong("CampfireLitTime", this.unlitCampfire$litTime);
        }
    }

}
