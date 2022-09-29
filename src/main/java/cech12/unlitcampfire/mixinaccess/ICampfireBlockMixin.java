package cech12.unlitcampfire.mixinaccess;

import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BooleanProperty;

public interface ICampfireBlockMixin {

    BooleanProperty INFINITE = BooleanProperty.create("infinite");

    int getMaxLitTime(BlockState state);

    boolean burnsInfinite(BlockState state);

}
