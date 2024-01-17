package de.cech12.unlitcampfire.mixin;

import de.cech12.unlitcampfire.ModTags;
import de.cech12.unlitcampfire.mixinaccess.ICampfireBlockEntityMixin;
import de.cech12.unlitcampfire.mixinaccess.ICampfireBlockMixin;
import de.cech12.unlitcampfire.platform.Services;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShovelItem;
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
import org.spongepowered.asm.mixin.Intrinsic;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import javax.annotation.Nonnull;

@Mixin(CampfireBlock.class)
public abstract class CampfireBlockMixin extends BaseEntityBlock implements ICampfireBlockMixin {

    protected CampfireBlockMixin(Properties builder) {
        super(builder);
    }

    @Override
    public int unlitCampfire$getMaxLitTime(BlockState state) {
        return Services.CONFIG.getLitTime(state.getBlock() == Blocks.SOUL_CAMPFIRE);
    }

    @Override
    public boolean unlitCampfire$burnsInfinite(BlockState state) {
        return state.getValue(ICampfireBlockMixin.INFINITE) || unlitCampfire$getMaxLitTime(state) < 1;
    }

    @Unique
    private boolean unlitCampfire$canAddBurnables(BlockState state) {
        return !state.getValue(ICampfireBlockMixin.INFINITE) && Services.CONFIG.canAddBurnables(state.getBlock() == Blocks.SOUL_CAMPFIRE);
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
        if (!state.getValue(CampfireBlock.LIT) || this.unlitCampfire$burnsInfinite(state)) {
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
            if (!this.unlitCampfire$canAddBurnables(state)) {
                return;
            }
            //when shovel item is used, do nothing, to avoid using (wooden) shovel as burning material (issue #27)
            if (stack.getItem() instanceof ShovelItem) {
                return;
            }
            //when interaction item has no burn time, do nothing
            int burnTime = Services.PLATFORM.getBurnTimeOf(stack);
            if (burnTime < 1) {
                return;
            }
            //subtract items burn time from campfires lit time to let it burn longer
            BlockEntity blockentity = level.getBlockEntity(pos);
            if (blockentity instanceof ICampfireBlockEntityMixin campfireBlockEntityMixin
                    && campfireBlockEntityMixin.unlitCampfire$addLitTime(burnTime)) {
                cir.setReturnValue(InteractionResult.sidedSuccess(!level.isClientSide));
                cir.cancel();
            }
        }
        //consume interaction item
        if (!player.getAbilities().instabuild && cir.getReturnValue().consumesAction()) {
            ItemStack remainingStack = Services.PLATFORM.getRemainingStackAfterUsage(stack);
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
    public void id$animateTick(@Nonnull BlockState stateIn, Level worldIn, BlockPos pos, @Nonnull RandomSource rand) {
        int particleFactor = 1;
        if (worldIn.isRainingAt(pos.above())) {
            particleFactor = Services.CONFIG.getRainParticleFactor(worldIn.getBlockState(pos).getBlock() == Blocks.SOUL_CAMPFIRE);
        }
        for (int i = 0; i < particleFactor; i++) {
            this.animateTick(stateIn, worldIn, pos, rand);
        }
    }

}
