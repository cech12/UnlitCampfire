package de.cech12.unlitcampfire.mixin;

import de.cech12.unlitcampfire.ModTags;
import de.cech12.unlitcampfire.mixinaccess.ICampfireBlockEntityMixin;
import de.cech12.unlitcampfire.mixinaccess.ICampfireBlockMixin;
import de.cech12.unlitcampfire.platform.Services;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.stats.Stats;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.component.CookingFuel;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CampfireBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.providers.number.ints.ResolvableInt;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Intrinsic;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;

@Mixin(CampfireBlock.class)
public abstract class CampfireBlockMixin extends BaseEntityBlock implements ICampfireBlockMixin {

    protected CampfireBlockMixin(Properties builder) {
        super(builder);
    }

    @Override
    public long unlitCampfire$getMaxLitTimeExtension(BlockState state) {
        return Services.CONFIG.getMaxLitTimeExtension(state.getBlock() == Blocks.SOUL_CAMPFIRE);
    }

    @Override
    public long unlitCampfire$getMaxLitTime(BlockState state) {
        return Services.CONFIG.getLitTime(state.getBlock() == Blocks.SOUL_CAMPFIRE);
    }

    @Override
    public long unlitCampfire$getRunsOutIndicatorTime(BlockState state) {
        return Services.CONFIG.getRunOutIndicatorTime(state.getBlock() == Blocks.SOUL_CAMPFIRE);
    }

    @Override
    public boolean unlitCampfire$burnsInfinite(BlockState state) {
        return !state.hasProperty(ICampfireBlockMixin.INFINITE) || state.getValue(ICampfireBlockMixin.INFINITE) || unlitCampfire$getMaxLitTime(state) < 1;
    }

    @Unique
    private boolean unlitCampfire$canAddBurnables(BlockState state) {
        return (state.hasProperty(ICampfireBlockMixin.INFINITE) && !state.getValue(ICampfireBlockMixin.INFINITE)) && Services.CONFIG.canAddBurnables(state.getBlock() == Blocks.SOUL_CAMPFIRE);
    }

    @Unique
    private BlockState unlitCampfire$createDefaultState(BlockState state) {
        state = state.setValue(CampfireBlock.LIT, false);
        if (state.hasProperty(ICampfireBlockMixin.INFINITE)) {
            state = state.setValue(ICampfireBlockMixin.INFINITE, false);
        }
        if (state.hasProperty(ICampfireBlockMixin.RUNS_OUT)) {
            state = state.setValue(ICampfireBlockMixin.RUNS_OUT, false);
        }
        return state;
    }

    @Inject(at = @At("RETURN"), method = "<init>*")
    protected void initProxy(boolean spawnParticles, int fireDamage, BlockBehaviour.Properties properties, CallbackInfo info) {
        this.registerDefaultState(this.unlitCampfire$createDefaultState(this.defaultBlockState()));
    }

    @Inject(at = @At("RETURN"), method = "getStateForPlacement", cancellable = true)
    protected void getStateForPlacementProxy(BlockPlaceContext context, CallbackInfoReturnable<BlockState> cir) {
        if (cir.getReturnValue() != null) {
            cir.setReturnValue(this.unlitCampfire$createDefaultState(cir.getReturnValue()));
        }
    }

    @Inject(at = @At("RETURN"), method = "useItemOn", cancellable = true)
    protected void useProxy(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult, CallbackInfoReturnable<InteractionResult> cir) {
        //when previous interaction was successful, do nothing
        if (cir.getReturnValue().consumesAction()) {
            return;
        }
        //when campfire is not lit, or it burns infinite, do nothing
        if (!state.getValue(CampfireBlock.LIT) || this.unlitCampfire$burnsInfinite(state)) {
            return;
        }
        //when infinity item is used - set campfire to infinite
        if (stack.is(ModTags.Items.MAKES_CAMPFIRE_INFINITE)) {
            if (level instanceof ServerLevel) {
                level.setBlock(pos, state.setValue(ICampfireBlockMixin.INFINITE, true), 3);
                player.awardStat(Stats.INTERACT_WITH_CAMPFIRE);
                cir.setReturnValue(InteractionResult.SUCCESS_SERVER);
            } else {
                cir.setReturnValue(InteractionResult.CONSUME);
            }
            cir.cancel();
        } else {
            //when configuration forbids to extend the burn time
            if (!this.unlitCampfire$canAddBurnables(state)) {
                return;
            }
            //when shovel item is used, do nothing, to avoid using (wooden) shovel as burning material (issue #27)
            if (stack.is(ItemTags.DOUSES_CAMPFIRES)) {
                return;
            }
            if (!stack.has(DataComponents.COOKING_FUEL)) {
                return;
            }
            int burnTime = 0;
            BlockEntity blockentity = level.getBlockEntity(pos);
            if (level instanceof ServerLevel serverLevel) {
                //when interaction item has no burn time, do nothing
                burnTime = ResolvableInt.getFromItem(stack, DataComponents.COOKING_FUEL, CookingFuel::burnTime, this.unlitCampfire$getLootContext(serverLevel, state, pos, blockentity), 0);
                if (burnTime < 1) {
                    return;
                }
            }
            //subtract items burn time from campfires lit time to let it burn longer
            if (blockentity instanceof ICampfireBlockEntityMixin campfireBlockEntityMixin
                    && campfireBlockEntityMixin.unlitCampfire$addLitTime(burnTime)) {
                if (level instanceof ServerLevel) {
                    player.awardStat(Stats.INTERACT_WITH_CAMPFIRE);
                    cir.setReturnValue(InteractionResult.SUCCESS_SERVER);
                } else {
                    cir.setReturnValue(InteractionResult.CONSUME);
                }
                cir.cancel();
            }
        }
        //consume interaction item
        if (!player.getAbilities().instabuild && cir.getReturnValue().consumesAction()) {
            ItemStackTemplate remainingStack = Services.PLATFORM.getRemainingStackAfterUsage(stack);
            if (remainingStack != null && remainingStack.count() > 0) {
                player.setItemInHand(hand, remainingStack.create());
            } else {
                stack.shrink(1);
            }
        }
    }

    @Inject(at = @At("RETURN"), method = "createBlockStateDefinition")
    protected void createBlockStateDefinitionProxy(StateDefinition.Builder<Block, BlockState> stateBuilder, CallbackInfo info) {
        stateBuilder.add(INFINITE, RUNS_OUT);
    }

    //overrides animateTick method and has access to the original method
    @Intrinsic(displace = true)
    public void id$animateTick(@NotNull BlockState stateIn, Level worldIn, BlockPos pos, @NotNull RandomSource rand) {
        int particleFactor = 1;
        if (worldIn.isRainingAt(pos.above())) {
            particleFactor = Services.CONFIG.getRainParticleFactor(worldIn.getBlockState(pos).getBlock() == Blocks.SOUL_CAMPFIRE);
        }
        for (int i = 0; i < particleFactor; i++) {
            this.animateTick(stateIn, worldIn, pos, rand);
        }
    }

    @Unique
    protected LootContext unlitCampfire$getLootContext(final ServerLevel level, final BlockState state, final BlockPos pos, final BlockEntity blockentity) {
        return new LootContext.Builder(
                new LootParams.Builder(level)
                        .withParameter(LootContextParams.BLOCK_STATE, state)
                        .withParameter(LootContextParams.BLOCK_ENTITY, blockentity)
                        .withParameter(LootContextParams.ORIGIN, Vec3.atCenterOf(pos))
                        .create(LootContextParamSets.BLOCK_INTERACT)
        ).create(Optional.empty());
    }

}
