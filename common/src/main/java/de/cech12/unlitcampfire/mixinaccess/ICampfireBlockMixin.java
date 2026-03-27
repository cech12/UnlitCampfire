package de.cech12.unlitcampfire.mixinaccess;

import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BooleanProperty;

public interface ICampfireBlockMixin {

    BooleanProperty INFINITE = BooleanProperty.create("infinite");
    BooleanProperty RUNS_OUT = BooleanProperty.create("runs_out");

    long unlitCampfire$getMaxLitTime(BlockState state);

    long unlitCampfire$getMaxLitTimeExtension(BlockState state);

    long unlitCampfire$getRunsOutIndicatorTime(BlockState state);

    boolean unlitCampfire$burnsInfinite(BlockState state);

}
