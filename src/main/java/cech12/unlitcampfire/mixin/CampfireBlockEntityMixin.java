package cech12.unlitcampfire.mixin;

import cech12.unlitcampfire.UnlitCampfireMod;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.CampfireBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.CampfireBlockEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(CampfireBlockEntity.class)
public abstract class CampfireBlockEntityMixin extends BlockEntity {

    private Boolean isSoulCampfire;

    private int litTime = 0;
    private int rainTime = 0;

    public CampfireBlockEntityMixin() {
        super(BlockEntityType.CAMPFIRE);
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
            return UnlitCampfireMod.CONFIG.SOUL_CAMPFIRE.SOUL_CAMPFIRE_LIT_TIME;
        }
        return UnlitCampfireMod.CONFIG.CAMPFIRE.CAMPFIRE_LIT_TIME;
    }

    private boolean dropsItemsWhenUnlitByTimeOrRain() {
        if (isSoulCampfire()) {
            return UnlitCampfireMod.CONFIG.SOUL_CAMPFIRE.SOUL_CAMPFIRE_DROPS_ITEMS_WHEN_UNLIT_BY_TIME_OR_RAIN;
        }
        return UnlitCampfireMod.CONFIG.CAMPFIRE.CAMPFIRE_DROPS_ITEMS_WHEN_UNLIT_BY_TIME_OR_RAIN;
    }

    private boolean breaksWhenUnlitByTime() {
        if (isSoulCampfire()) {
            return UnlitCampfireMod.CONFIG.SOUL_CAMPFIRE.SOUL_CAMPFIRE_BREAKS_WHEN_UNLIT_BY_TIME;
        }
        return UnlitCampfireMod.CONFIG.CAMPFIRE.CAMPFIRE_BREAKS_WHEN_UNLIT_BY_TIME;
    }

    private int getRainUnlitTime() {
        if (isSoulCampfire()) {
            return UnlitCampfireMod.CONFIG.SOUL_CAMPFIRE.SOUL_CAMPFIRE_RAIN_UNLIT_TIME;
        }
        return UnlitCampfireMod.CONFIG.CAMPFIRE.CAMPFIRE_RAIN_UNLIT_TIME;
    }

    private int getParticleFactorDuringRain() {
        if (isSoulCampfire()) {
            return UnlitCampfireMod.CONFIG.SOUL_CAMPFIRE.SOUL_CAMPFIRE_RAIN_PARTICLE_FACTOR;
        }
        return UnlitCampfireMod.CONFIG.CAMPFIRE.CAMPFIRE_RAIN_PARTICLE_FACTOR;
    }

    private void playUnlitSound() {
        if (this.world != null && !this.world.isClient()) {
            this.world.playSound(null, this.getPos(), SoundEvents.BLOCK_FIRE_EXTINGUISH, SoundCategory.BLOCKS, 1.0F, 1.0F);
        }
    }

    private void dropAllContainingItems() {
        if (this.world != null) {
            CampfireBlock.extinguish(this.world, this.getPos(), this.getCachedState());
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
            this.world.setBlockState(this.getPos(), this.getCachedState().with(CampfireBlock.LIT, false));
        }
    }

    @Inject(at = @At("RETURN"), method = "tick")
    protected void tickProxy(CallbackInfo info) {
        World world = this.getWorld();
        if (world != null) {
            if (this.getCachedState().get(CampfireBlock.LIT)) {
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
                if (world.hasRain(this.getPos().up())) {
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
                    if (this.world != null && this.world.isClient && particleFactor > 1) {
                        BlockEntity blockEntity = this.world.getBlockEntity(this.pos);
                        if (blockEntity instanceof CampfireBlockEntity) {
                            CampfireBlockEntity campfireTileEntity = (CampfireBlockEntity)blockEntity;
                            for (int i = 0; i < particleFactor - 1; i++) {
                                campfireTileEntity.spawnSmokeParticles();
                            }
                        }
                    }
                }
            } else {
                litTime = 0;
            }
        }
    }


    @Inject(at = @At("RETURN"), method = "fromTag")
    protected void fromTagProxy(BlockState stateIn, CompoundTag nbtIn, CallbackInfo info) {
        if (nbtIn.contains("CampfireLitTime")) {
            this.litTime = nbtIn.getInt("CampfireLitTime");
        }
    }

    @Inject(at = @At("RETURN"), method = "toTag", cancellable = true)
    protected void toTagProxy(CompoundTag compound, CallbackInfoReturnable<CompoundTag> cir) {
        CompoundTag nbt = cir.getReturnValue();
        if (nbt != null) {
            nbt.putInt("CampfireLitTime", this.litTime);
            cir.setReturnValue(nbt);
            cir.cancel();
        }
    }

}