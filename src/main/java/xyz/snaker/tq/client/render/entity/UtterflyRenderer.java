package xyz.snaker.tq.client.render.entity;

import java.awt.*;

import xyz.snaker.snakerlib.math.PoseStackBuilder;
import xyz.snaker.snakerlib.utility.ResourcePath;
import xyz.snaker.tq.client.fx.RayFX;
import xyz.snaker.tq.client.layer.UtterflyLayer;
import xyz.snaker.tq.client.model.entity.UtterflyModel;
import xyz.snaker.tq.level.entity.boss.Utterfly;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

import com.mojang.blaze3d.vertex.PoseStack;

import org.jetbrains.annotations.NotNull;

/**
 * Created by SnakerBone on 4/01/2023
 **/
public class UtterflyRenderer extends MobRenderer<Utterfly, UtterflyModel>
{
    public UtterflyRenderer(EntityRendererProvider.Context context)
    {
        super(context, new UtterflyModel(context.bakeLayer(UtterflyModel.LAYER_LOCATION)), 0.5F);
        addLayer(new UtterflyLayer(this));
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull Utterfly utterfly)
    {
        switch (utterfly.getPhase()) {
            case 1 -> {
                return new ResourcePath("textures/entity/boss/utterfly0.png");
            }
            case 2 -> {
                return new ResourcePath("textures/entity/boss/utterfly1.png");
            }
            case 3, 4 -> {
                return new ResourcePath("textures/entity/boss/utterfly2.png");
            }
            default -> {
                return new ResourcePath("textures/entity/creature/flutterfly.png");
            }
        }
    }

    @Override
    public void render(@NotNull Utterfly utterfly, float entityYaw, float partialTicks, @NotNull PoseStack stack, @NotNull MultiBufferSource source, int packedLight)
    {
        PoseStackBuilder builder = new PoseStackBuilder(stack);

        Color colour;
        double scale;

        int phase = utterfly.getPhase();

        switch (phase) {
            case 1 -> {
                scale = 4;
                colour = Color.decode("#FFE800");
            }
            case 2 -> {
                scale = 6;
                colour = Color.decode("#FF8300");
            }
            case 3 -> {
                scale = 8;
                colour = Color.decode("#FF0000");
            }
            default -> {
                scale = 1;
                colour = Color.decode("000000");
            }
        }

        builder.scale(scale);

        if (utterfly.getCharging()) {
            RayFX.create(stack, source, colour, 16, 64, 16, 0, 0, 0);
        }

        super.render(utterfly, entityYaw, partialTicks, stack, source, packedLight);
    }
}
