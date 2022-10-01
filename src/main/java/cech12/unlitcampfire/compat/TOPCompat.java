package cech12.unlitcampfire.compat;

import cech12.unlitcampfire.UnlitCampfireMod;
import cech12.unlitcampfire.mixinaccess.ICampfireBlockEntityMixin;
import cech12.unlitcampfire.mixinaccess.ICampfireBlockMixin;
import mcjty.theoneprobe.api.CompoundText;
import mcjty.theoneprobe.api.IIconStyle;
import mcjty.theoneprobe.api.IProbeHitData;
import mcjty.theoneprobe.api.IProbeInfo;
import mcjty.theoneprobe.api.IProbeInfoProvider;
import mcjty.theoneprobe.api.ITheOneProbe;
import mcjty.theoneprobe.api.ProbeMode;
import mcjty.theoneprobe.api.TextStyleClass;
import mcjty.theoneprobe.apiimpl.styles.IconStyle;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.CampfireBlock;
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
                private static final IIconStyle FIRE_STYLE =  new IconStyle().bounds(8, 8).textureBounds(8, 64);
                private static final ResourceLocation FIRE_ICON = new ResourceLocation("minecraft:textures/block/campfire_fire.png");

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
                        iProbeInfo.horizontal().text(CompoundText.create().label("top.unlitcampfire.infinite"));
                    } else if (blockState.getValue(CampfireBlock.LIT)) {
                        final int litTime = campfireBlockEntity.getLitTime();
                        iProbeInfo.horizontal()
                                .icon(FIRE_ICON, 0, (int) (level.getGameTime() % 8 * 16), FIRE_STYLE.getWidth(), FIRE_STYLE.getHeight(), FIRE_STYLE)
                                .text(CompoundText.create()
                                        .label("top.unlitcampfire.lit")
                                        .text(": ")
                                        .style(TextStyleClass.INFO)
                                        .text(Component.translatable("top.unlitcampfire.n_ticks", campfireBlock.getMaxLitTime(blockState) - litTime))
                                );
                    }
                }
            });
            return null;
        }
    }
}
