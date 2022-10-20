package cech12.unlitcampfire.mixin;

import cech12.unlitcampfire.ModTags;
import cech12.unlitcampfire.config.ServerConfig;
import cech12.unlitcampfire.mixinaccess.ICampfireBlockEntityMixin;
import cech12.unlitcampfire.mixinaccess.ICampfireBlockMixin;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CampfireBlock;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.common.ForgeHooks;
import org.spongepowered.asm.mixin.Intrinsic;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import javax.annotation.Nonnull;
import java.util.Random;

@Mixin(CampfireBlock.class)
public abstract class CampfireBlockMixin extends BaseEntityBlock implements ICampfireBlockMixin {

    protected CampfireBlockMixin(Properties builder) {
        super(builder);
    }

    @Override
    public int getMaxLitTime(BlockState state) {
        return state.getBlock() == Blocks.SOUL_CAMPFIRE ? ServerConfig.SOUL_CAMPFIRE_LIT_TIME.get() : ServerConfig.CAMPFIRE_LIT_TIME.get();
    }

    @Override
    public boolean burnsInfinite(BlockState state) {
        return state.getValue(ICampfireBlockMixin.INFINITE) || getMaxLitTime(state) < 1;
    }

    private boolean canAddBurnables(BlockState state) {
        return !state.getValue(ICampfireBlockMixin.INFINITE) &&
                (state.getBlock() == Blocks.SOUL_CAMPFIRE ? ServerConfig.SOUL_CAMPFIRE_ADDING_BURNABLES.get() : ServerConfig.CAMPFIRE_ADDING_BURNABLES.get());
    }

    @Inject(at = @At("RETURN"), method = "<init>*")
    protected void initProxy(boolean spawnParticles, int fireDamage, BlockBehaviour.Properties properties, CallbackInfo info) {
        this.registerDefaultState(this.defaultBlockState()
                .setValue(CampfireBlock.LIT, false)
                .setValue(ICampfireBlockMixin.INFINITE, false)
        );
    }

    @Inject(at = @At("RETURN"), method = "getStateForPlacement", cancellable = true)
    protected void getStateForPlacementProxy(BlockPlaceContext context, CallbackInfoReturnable<BlockState> cir) {
        if (cir.getReturnValue() != null) {
            cir.setReturnValue(cir.getReturnValue().setValue(CampfireBlock.LIT, false));
            cir.setReturnValue(cir.getReturnValue().setValue(ICampfireBlockMixin.INFINITE, false));
            cir.cancel();
        }
    }

    @Inject(at = @At("RETURN"), method = "use", cancellable = true)
    protected void useProxy(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult, CallbackInfoReturnable<InteractionResult> cir) {
        //when previous interaction was successful, do nothing
        if (cir.getReturnValue().consumesAction()) {
            return;
        }
        //when campfire is not lit, or it burns infinite, do nothing
        if (!state.getValue(CampfireBlock.LIT) || this.burnsInfinite(state)) {
            return;
        }
        //when infinity item is used - set campfire to infinite
        ItemStack stack = player.getItemInHand(hand);
        if (stack.is(ModTags.Items.MAKES_CAMPFIRE_INFINITE)) {
            level.setBlock(pos, state.setValue(ICampfireBlockMixin.INFINITE, true), 3);
            cir.setReturnValue(InteractionResult.sidedSuccess(!level.isClientSide));
            cir.cancel();
        } else {
            //when configuration forbids to extend the burn time
            if (!this.canAddBurnables(state)) {
                return;
            }
            //when interaction item has no burn time, do nothing
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
        }
        //consume interaction item
        if (!player.getAbilities().instabuild && cir.getReturnValue().consumesAction()) {
            ItemStack remainingStack = stack.getContainerItem();
            if (remainingStack != ItemStack.EMPTY) {
                player.setItemInHand(hand, remainingStack);
            } else {
                stack.shrink(1);
            }
        }
    }

    @Inject(at = @At("RETURN"), method = "createBlockStateDefinition")
    protected void createBlockStateDefinitionProxy(StateDefinition.Builder<Block, BlockState> stateBuilder, CallbackInfo info) {
        stateBuilder.add(INFINITE);
    }

    //overrides animateTick method and has access to the original method
    @Intrinsic(displace = true)
    public void id$animateTick(@Nonnull BlockState stateIn, Level worldIn, BlockPos pos, @Nonnull Random rand) {
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
