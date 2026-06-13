package icu.gensoukyo.neo_mystias_izakaya.client.model;

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import org.joml.Vector3f;
import org.jspecify.annotations.NonNull;

public class MystiasHatModel extends EntityModel<LivingEntityRenderState> {
    private final ModelPart head;

    public MystiasHatModel(ModelPart root) {
        super(root);
        this.head = root.getChild("head");
    }

    public static MeshDefinition setup(CubeDeformation innerArmorDeformation) {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition root = mesh.getRoot();

        PartDefinition Root = root.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-4.5F, -10.0F, -4.5F, 9.0F, 5.0F, 9.0F, new CubeDeformation(0.0F))
                .texOffs(40, 28).addBox(-5.0F, -14.5F, -0.5F, 10.0F, 5.0F, 1.0F, new CubeDeformation(-0.5F))
                .texOffs(0, 14).addBox(-4.5F, -10.0F, -4.5F, 9.0F, 5.0F, 9.0F, new CubeDeformation(0.2F))
                .texOffs(0, 28).addBox(-5.0F, -7.0F, -5.0F, 10.0F, 2.0F, 10.0F, new CubeDeformation(0.1F)), PartPose.ZERO);
        return mesh;
    }

    @Override
    public void setupAnim(@NonNull LivingEntityRenderState state) {
        super.setupAnim(state);
        this.head.xRot = state.xRot * ((float)Math.PI / 180F);
        this.head.yRot = state.yRot * ((float)Math.PI / 180F);
    }
}
