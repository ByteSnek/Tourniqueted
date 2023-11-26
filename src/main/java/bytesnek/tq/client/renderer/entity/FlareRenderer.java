package bytesnek.tq.client.renderer.entity;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

import org.jetbrains.annotations.NotNull;

import bytesnek.snakerlib.resources.ResourceReference;
import bytesnek.tq.client.layer.FlareLayer;
import bytesnek.tq.client.model.entity.FlareModel;
import bytesnek.tq.level.entity.mob.Flare;

/**
 * Created by SnakerBone on 26/05/2023
 **/
public class FlareRenderer extends MobRenderer<Flare, FlareModel>
{
    public FlareRenderer(EntityRendererProvider.Context context)
    {
        super(context, new FlareModel(context.bakeLayer(FlareModel.LAYER_LOCATION)), 0.5F);
        addLayer(new FlareLayer(this));
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull Flare flare)
    {
        return new ResourceReference("textures/solid.png");
    }
}