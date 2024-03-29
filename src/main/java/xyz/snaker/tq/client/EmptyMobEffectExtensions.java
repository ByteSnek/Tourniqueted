package xyz.snaker.tq.client;

import net.minecraft.world.effect.MobEffectInstance;
import net.neoforged.neoforge.client.extensions.common.IClientMobEffectExtensions;

/**
 * Created by SnakerBone on 17/09/2023
 **/
public interface EmptyMobEffectExtensions extends IClientMobEffectExtensions
{
    EmptyMobEffectExtensions EMPTY = new EmptyMobEffectExtensions() {};

    @Override
    default boolean isVisibleInGui(MobEffectInstance instance)
    {
        return false;
    }

    @Override
    default boolean isVisibleInInventory(MobEffectInstance instance)
    {
        return false;
    }
}
