package xyz.snaker.tq.level.block.entity.shader;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

import xyz.snaker.tq.level.block.entity.ShaderBlockEntity;
import xyz.snaker.tq.rego.BlockEntities;

/**
 * Created by SnakerBone on 14/08/2023
 **/
public class MulticolourBlockEntity extends ShaderBlockEntity<MulticolourBlockEntity>
{
    public MulticolourBlockEntity(BlockPos pos, BlockState state)
    {
        super(BlockEntities.MULTICOLOUR.get(), pos, state);
    }
}
