package de.cech12.unlitcampfire.client;

import de.cech12.unlitcampfire.Constants;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.CampfireBlock;
import snownee.jade.api.BlockAccessor;
import snownee.jade.api.IBlockComponentProvider;
import snownee.jade.api.ITooltip;
import snownee.jade.api.IWailaClientRegistration;
import snownee.jade.api.IWailaPlugin;
import snownee.jade.api.WailaPlugin;
import snownee.jade.api.config.IPluginConfig;

@WailaPlugin()
public class JadeClientCompat implements IWailaPlugin, IBlockComponentProvider  {

    @Override
    public ResourceLocation getUid() {
        return Constants.id("campfireinfo");
    }

    @Override
    public void registerClient(IWailaClientRegistration registration) {
        registration.registerBlockComponent(this, CampfireBlock.class);
    }

    @Override
    public void appendTooltip(ITooltip tooltip, BlockAccessor accessor, IPluginConfig pluginConfig) {
        CompoundTag serverData = accessor.getServerData();

        if (serverData.getBooleanOr("BurnsInfinite", false)) {
            tooltip.add(Component.translatable("hud.unlitcampfire.infinite"));
        } else if (serverData.contains("LitTime")) {
            tooltip.add(Component.translatable("hud.unlitcampfire.n_seconds", getSecondsLeft(serverData)));
        }
    }

    private int getSecondsLeft(CompoundTag serverData) {
        return (serverData.getIntOr("MaxLitTime", 0) - serverData.getIntOr("LitTime", 0)) / 20;
    }

}
