package cech12.unlitcampfire.compat;
/*
import cech12.unlitcampfire.UnlitCampfireMod;
import cech12.unlitcampfire.mixinaccess.ICampfireBlockEntityMixin;
import cech12.unlitcampfire.mixinaccess.ICampfireBlockMixin;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.CampfireBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.CampfireBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import snownee.jade.api.BlockAccessor;
import snownee.jade.api.IBlockComponentProvider;
import snownee.jade.api.IServerDataProvider;
import snownee.jade.api.ITooltip;
import snownee.jade.api.IWailaClientRegistration;
import snownee.jade.api.IWailaCommonRegistration;
import snownee.jade.api.IWailaPlugin;
import snownee.jade.api.WailaPlugin;
import snownee.jade.api.config.IPluginConfig;

@WailaPlugin()
public class JadeCompat implements IWailaPlugin, IBlockComponentProvider, IServerDataProvider<BlockEntity> {
    @Override
    public void register(IWailaCommonRegistration registration) {
        registration.registerBlockDataProvider(this, CampfireBlockEntity.class);
    }

    @Override
    public void registerClient(IWailaClientRegistration registration) {
        registration.registerBlockComponent(this, CampfireBlock.class);
    }

    @Override
    public void appendTooltip(ITooltip tooltip, BlockAccessor accessor, IPluginConfig pluginConfig) {
        CompoundTag serverData = accessor.getServerData();

        if (serverData.getBoolean("BurnsInfinite")) {
            tooltip.add(Component.translatable("hud.unlitcampfire.infinite"));
        } else if (serverData.contains("LitTime")) {
            tooltip.add(Component.translatable("hud.unlitcampfire.n_seconds", getSecondsLeft(serverData)));
        }
    }

    private int getSecondsLeft(CompoundTag serverData) {
        return (serverData.getInt("MaxLitTime") - serverData.getInt("LitTime")) / 20;
    }

    @Override
    public void appendServerData(CompoundTag data, ServerPlayer player, Level world, BlockEntity blockEntity, boolean showDetails) {
        BlockState blockState = blockEntity.getBlockState();

        if (blockEntity instanceof ICampfireBlockEntityMixin campfireBlockEntity && blockState.getBlock() instanceof ICampfireBlockMixin campfireBlock) {
            data.putBoolean("BurnsInfinite", campfireBlock.burnsInfinite(blockState));
            data.putInt("MaxLitTime", campfireBlock.getMaxLitTime(blockState));

            if (!campfireBlock.burnsInfinite(blockState)) {
                data.putInt("LitTime", campfireBlockEntity.getLitTime());
            }
        }
    }

    @Override
    public ResourceLocation getUid() {
        return new ResourceLocation(UnlitCampfireMod.MOD_ID, "campfireinfo");
    }
}
 */
