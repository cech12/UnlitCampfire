package cech12.unlitcampfire.mixinaccess;

import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BooleanProperty;

public interface ICampfireBlockMixin {

    BooleanProperty INFINITE = BooleanProperty.create("infinite");
    BooleanProperty RUNS_OUT = BooleanProperty.create("runs_out");

    int getMaxLitTime(BlockState state);

    int unlitCampfire$getMaxLitTimeExtension(BlockState state);

    int unlitCampfire$getRunsOutIndicatorTime(BlockState state);

    boolean burnsInfinite(BlockState state);

}
