package cech12.unlitcampfire.mixin;

import cech12.unlitcampfire.config.ServerConfig;
import cech12.unlitcampfire.mixinaccess.ICampfireBlockEntityMixin;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CampfireBlock;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.common.ForgeHooks;
import org.spongepowered.asm.mixin.Intrinsic;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import javax.annotation.Nonnull;

@Mixin(CampfireBlock.class)
public abstract class CampfireBlockMixin extends BaseEntityBlock implements ICampfireBlockEntityMixin {

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

    @Inject(at = @At("RETURN"), method = "use", cancellable = true)
    protected void useProxy(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult, CallbackInfoReturnable<InteractionResult> cir) {
        //when previous interaction was successful, do nothing
        if (cir.getReturnValue().consumesAction()) {
            return;
        }
        //when campfire is not lit, do nothing
        if (!state.getValue(CampfireBlock.LIT)) {
            return;
        }
        //when configuration forbids to extend the burn time
        int maxLitTime = (state.getBlock() == Blocks.SOUL_CAMPFIRE) ? ServerConfig.SOUL_CAMPFIRE_LIT_TIME.get() : ServerConfig.CAMPFIRE_LIT_TIME.get();
        boolean canAddBurnables = (state.getBlock() == Blocks.SOUL_CAMPFIRE) ? ServerConfig.SOUL_CAMPFIRE_ADDING_BURNABLES.get() : ServerConfig.CAMPFIRE_ADDING_BURNABLES.get();
        if (maxLitTime < 1 || !canAddBurnables) {
            return;
        }
        //when interaction item has no burn time, do nothing
        ItemStack stack = player.getItemInHand(hand);
        int burnTime = ForgeHooks.getBurnTime(stack, null);
        if (burnTime < 1) {
            return;
        }
        //subtract items burn time from campfires lit time to let it burn longer
        BlockEntity blockentity = level.getBlockEntity(pos);
        if (blockentity instanceof ICampfireBlockEntityMixin campfireBlockEntityMixin 
                && campfireBlockEntityMixin.addLitTime(burnTime)) {
            cir.setReturnValue(InteractionResult.sidedSuccess(!level.isClientSide));
            cir.cancel();
        }
        //consume interaction item
        if (!player.getAbilities().instabuild && cir.getReturnValue().consumesAction()) {
            ItemStack remainingStack = stack.getCraftingRemainingItem();
            if (remainingStack != ItemStack.EMPTY) {
                player.setItemInHand(hand, remainingStack);
            } else {
                stack.shrink(1);
            }
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
