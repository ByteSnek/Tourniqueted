package xyz.snaker.tq.level.item.icon;

import java.util.function.Consumer;

import xyz.snaker.snakerlib.data.DefaultItemProperties;
import xyz.snaker.snakerlib.level.Icon;
import xyz.snaker.tq.client.render.icon.BlockTabIconRenderer;

import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;

import org.jetbrains.annotations.NotNull;

/**
 * Created by SnakerBone on 12/06/2023
 **/
public class BlockTabIcon extends Item implements Icon
{
    public BlockTabIcon(Properties properties)
    {
        super(properties);
    }

    public BlockTabIcon()
    {
        super(DefaultItemProperties.EMPTY);
    }

    @Override
    public void initializeClient(@NotNull Consumer<IClientItemExtensions> consumer)
    {
        consumer.accept(new IClientItemExtensions()
        {
            public BlockTabIconRenderer getRenderer()
            {
                return new BlockTabIconRenderer();
            }

            @Override
            public BlockEntityWithoutLevelRenderer getCustomRenderer()
            {
                return getRenderer();
            }
        });
    }
}
