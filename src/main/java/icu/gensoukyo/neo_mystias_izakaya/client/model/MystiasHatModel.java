package icu.gensoukyo.neo_mystias_izakaya.client.model;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;

public class MystiasHatModel extends HumanoidModel<HumanoidRenderState> {
    public MystiasHatModel(ModelPart root) {
        super(root);
    }

    public static MeshDefinition setup(CubeDeformation innerArmorDeformation) {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition root = mesh.getRoot();

        // 帽子主体 + 前檐
        PartDefinition head = root.addOrReplaceChild("head",
                CubeListBuilder.create()
                        .texOffs(0, 0)
                        .addBox(-4.5F, -8.0F, -4.5F, 9.0F, 5.0F, 9.0F, innerArmorDeformation)
                        .texOffs(28, 5)
                        .addBox(-4.5F, -4.0F, 3.5F, 9.0F, 4.0F, 0.0F, innerArmorDeformation),
                PartPose.ZERO);

        // 帽子外层 + 底部环边
        head.addOrReplaceChild("hat",
                CubeListBuilder.create()
                        .texOffs(0, 14)
                        .addBox(-4.7F, -8.2F, -4.7F, 9.4F, 5.4F, 9.4F, innerArmorDeformation.extend(0.2F))
                        .texOffs(28, 13)
                        .addBox(-5.1F, -3.2F, -5.1F, 10.2F, 2.2F, 10.2F, innerArmorDeformation.extend(0.1F)),
                PartPose.ZERO);

        return mesh;
    }
}
