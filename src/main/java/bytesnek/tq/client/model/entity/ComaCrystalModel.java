package bytesnek.tq.client.model.entity;

import xyz.snaker.snakerlib.resources.ResourceReference;

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import org.jetbrains.annotations.NotNull;

import bytesnek.tq.level.entity.ComaCrystal;

/**
 * Created by SnakerBone on 14/11/2023
 **/
public class ComaCrystalModel extends EntityModel<ComaCrystal>
{
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceReference("monolith"), "main");

    public final ModelPart cube;
    public final ModelPart glass;
    public final ModelPart base;

    public ComaCrystalModel(ModelPart root)
    {
        this.cube = root.getChild("cube");
        this.glass = root.getChild("glass");
        this.base = root.getChild("base");
    }

    public static LayerDefinition createBodyLayer()
    {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition root = mesh.getRoot();

        root.addOrReplaceChild("glass", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -4.0F, -4.0F, 8.0F, 8.0F, 8.0F), PartPose.ZERO);
        root.addOrReplaceChild("cube", CubeListBuilder.create().texOffs(32, 0).addBox(-4.0F, -4.0F, -4.0F, 8.0F, 8.0F, 8.0F), PartPose.ZERO);
        root.addOrReplaceChild("base", CubeListBuilder.create().texOffs(0, 16).addBox(-6.0F, 0.0F, -6.0F, 12.0F, 4.0F, 12.0F), PartPose.ZERO);

        return LayerDefinition.create(mesh, 64, 32);
    }

    @Override
    public void setupAnim(@NotNull ComaCrystal monolith, float pLimbSwing, float pLimbSwingAmount, float pAgeInTicks, float pNetHeadYaw, float pHeadPitch)
    {

    }

    @Override
    public void renderToBuffer(@NotNull PoseStack stack, @NotNull VertexConsumer consumer, int packedLight, int packedOverlay, float pRed, float pGreen, float pBlue, float pAlpha)
    {
        cube.render(stack, consumer, packedLight, packedOverlay);
        glass.render(stack, consumer, packedLight, packedOverlay);
        base.render(stack, consumer, packedLight, packedOverlay);
    }
}
