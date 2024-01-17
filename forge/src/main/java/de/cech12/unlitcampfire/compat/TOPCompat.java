package de.cech12.unlitcampfire.compat;
/*
import de.cech12.unlitcampfire.UnlitCampfireMod;
import de.cech12.unlitcampfire.mixinaccess.ICampfireBlockEntityMixin;
import de.cech12.unlitcampfire.mixinaccess.ICampfireBlockMixin;
import mcjty.theoneprobe.api.CompoundText;
import mcjty.theoneprobe.api.IProbeHitData;
import mcjty.theoneprobe.api.IProbeInfo;
import mcjty.theoneprobe.api.IProbeInfoProvider;
import mcjty.theoneprobe.api.ITheOneProbe;
import mcjty.theoneprobe.api.ProbeMode;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.fml.InterModComms;

import java.util.function.Function;

public class TOPCompat {

    public static void register() {
        InterModComms.sendTo("theoneprobe", "getTheOneProbe", PluginTOPRegistry::new);
    }

    public static class PluginTOPRegistry implements Function<ITheOneProbe, Void> {

        @Override
        public Void apply(ITheOneProbe probe) {
            probe.registerProvider(new IProbeInfoProvider() {

                @Override
                public ResourceLocation getID() {
                    return new ResourceLocation(UnlitCampfireMod.MOD_ID, "campfireinfo");
                }

                @Override
                public void addProbeInfo(ProbeMode probeMode, IProbeInfo iProbeInfo, Player player, Level level, BlockState blockState, IProbeHitData iProbeHitData) {
                    BlockEntity blockEntity = level.getBlockEntity(iProbeHitData.getPos());
                    if (!(blockEntity instanceof ICampfireBlockEntityMixin campfireBlockEntity) || !(blockState.getBlock() instanceof ICampfireBlockMixin campfireBlock)) {
                        return;
                    }
                    if (campfireBlock.burnsInfinite(blockState)) {
                        iProbeInfo.horizontal().text(CompoundText.create().label("hud.unlitcampfire.infinite"));
                    } else {
                        iProbeInfo.horizontal().text(CompoundText.create().label(Component.translatable(
                                "hud.unlitcampfire.n_seconds",
                                (campfireBlock.getMaxLitTime(blockState) - campfireBlockEntity.getLitTime()) / 20
                        )));
                    }
                }
            });
            return null;
        }
    }
}
 */
