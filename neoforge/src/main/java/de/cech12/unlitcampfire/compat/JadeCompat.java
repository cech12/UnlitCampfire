package de.cech12.unlitcampfire.compat;

import de.cech12.unlitcampfire.Constants;
import de.cech12.unlitcampfire.mixinaccess.ICampfireBlockEntityMixin;
import de.cech12.unlitcampfire.mixinaccess.ICampfireBlockMixin;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.block.entity.CampfireBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import snownee.jade.api.BlockAccessor;
import snownee.jade.api.IServerDataProvider;
import snownee.jade.api.IWailaCommonRegistration;
import snownee.jade.api.IWailaPlugin;
import snownee.jade.api.WailaPlugin;

@SuppressWarnings("unused")
@WailaPlugin()
public class JadeCompat implements IWailaPlugin, IServerDataProvider<BlockAccessor> {

    @Override
    public Identifier getUid() {
        return Constants.id("campfireinfo");
    }

    @Override
    public void register(IWailaCommonRegistration registration) {
        registration.registerBlockDataProvider(this, CampfireBlockEntity.class);
    }

    @Override
    public void appendServerData(CompoundTag data, BlockAccessor blockAccessor) {
        BlockState blockState = blockAccessor.getBlockState();

        if (blockAccessor.getBlockEntity() instanceof ICampfireBlockEntityMixin campfireBlockEntity && blockState.getBlock() instanceof ICampfireBlockMixin campfireBlock) {
            data.putBoolean("BurnsInfinite", campfireBlock.unlitCampfire$burnsInfinite(blockState));
            data.putLong("MaxLitTime", campfireBlock.unlitCampfire$getMaxLitTime(blockState));

            if (!campfireBlock.unlitCampfire$burnsInfinite(blockState)) {
                data.putLong("LitTime", campfireBlockEntity.unlitCampfire$getLitTime());
            }
        }
    }

}
