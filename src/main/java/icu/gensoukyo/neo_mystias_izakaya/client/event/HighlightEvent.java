/*
 * Copyright 2026 NeoMystiasIzakaya Team
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package icu.gensoukyo.neo_mystias_izakaya.client.event;

import icu.gensoukyo.neo_mystias_izakaya.registry.NMIDataComponentTypes;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.gizmos.GizmoStyle;
import net.minecraft.gizmos.Gizmos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ExtractLevelRenderStateEvent;

import java.util.List;

@EventBusSubscriber(value = Dist.CLIENT)
public class HighlightEvent {

    private static final int COLOR_CONTROLLER = 0xFFFFFFFF;   // 白色 - 控制器
    private static final int COLOR_KITCHENWARE = 0xFFFF8C00;  // 橙色 - 厨房用具
    private static final int COLOR_DINING_TABLE = 0xFF00BFFF;  // 蓝色 - 餐桌
    private static final int COLOR_CORNER_A = 0xFF00FF00;      // 绿色 - 角点A
    private static final int COLOR_CORNER_B = 0xFFFFFF00;      // 黄色 - 角点B
    private static final int COLOR_REGION_EDGE = 0x80FFFF00;   // 半透明黄 - 区域边框
    private static final int COLOR_REGION_FILL = 0x30FFFFFF;   // 极淡白 - 区域填充

    @SubscribeEvent
    public static void highlightBlock(ExtractLevelRenderStateEvent event) {
        Minecraft mc = Minecraft.getInstance();
        if (!(mc.player instanceof LocalPlayer localPlayer)) return;

        ItemStack mainHandItem = localPlayer.getMainHandItem();
        BlockPos controllerPos = mainHandItem.get(NMIDataComponentTypes.BOUND_CONTROLLER);

        // 高亮角点 A（绿色）
        BlockPos cornerA = mainHandItem.get(NMIDataComponentTypes.SCAN_CORNER_A);
        if (cornerA != null) {
            Gizmos.cuboid(cornerA, GizmoStyle.stroke(COLOR_CORNER_A, 6));
        }

        // 高亮角点 B（黄色）
        BlockPos cornerB = mainHandItem.get(NMIDataComponentTypes.SCAN_CORNER_B);
        if (cornerB != null) {
            Gizmos.cuboid(cornerB, GizmoStyle.stroke(COLOR_CORNER_B, 6));
        }

        // 两角点均存在 → 绘制包围盒线框 + 半透明填充
        if (cornerA != null && cornerB != null) {
            drawBoundingBox(cornerA, cornerB);
        }

        if (controllerPos == null) return;

        // 高亮控制器
        Gizmos.cuboid(controllerPos, GizmoStyle.stroke(COLOR_CONTROLLER, 8));

        // 高亮厨房用具
        List<BlockPos> kitchenware = mainHandItem.get(NMIDataComponentTypes.BOUND_KITCHENWARE);
        if (kitchenware != null) {
            kitchenware.forEach(pos -> Gizmos.cuboid(pos, GizmoStyle.stroke(COLOR_KITCHENWARE, 4)));
        }

        // 高亮餐桌
        List<BlockPos> diningTables = mainHandItem.get(NMIDataComponentTypes.BOUND_DINING_TABLES);
        if (diningTables != null) {
            diningTables.forEach(pos -> Gizmos.cuboid(pos, GizmoStyle.stroke(COLOR_DINING_TABLE, 4)));
        }
    }

    /**
     * 绘制两角点之间的包围盒（12 条棱边 + 半透明面）
     */
    private static void drawBoundingBox(BlockPos a, BlockPos b) {
        int minX = Math.min(a.getX(), b.getX());
        int minY = Math.min(a.getY(), b.getY());
        int minZ = Math.min(a.getZ(), b.getZ());
        int maxX = Math.max(a.getX(), b.getX()) + 1;
        int maxY = Math.max(a.getY(), b.getY()) + 1;
        int maxZ = Math.max(a.getZ(), b.getZ()) + 1;

        // 8 个顶点
        Vec3 v000 = new Vec3(minX, minY, minZ);
        Vec3 v100 = new Vec3(maxX, minY, minZ);
        Vec3 v010 = new Vec3(minX, maxY, minZ);
        Vec3 v110 = new Vec3(maxX, maxY, minZ);
        Vec3 v001 = new Vec3(minX, minY, maxZ);
        Vec3 v101 = new Vec3(maxX, minY, maxZ);
        Vec3 v011 = new Vec3(minX, maxY, maxZ);
        Vec3 v111 = new Vec3(maxX, maxY, maxZ);

        // 底面 4 条边 (y = minY)
        Gizmos.line(v000, v100, COLOR_REGION_EDGE);
        Gizmos.line(v100, v101, COLOR_REGION_EDGE);
        Gizmos.line(v101, v001, COLOR_REGION_EDGE);
        Gizmos.line(v001, v000, COLOR_REGION_EDGE);

        // 顶面 4 条边 (y = maxY)
        Gizmos.line(v010, v110, COLOR_REGION_EDGE);
        Gizmos.line(v110, v111, COLOR_REGION_EDGE);
        Gizmos.line(v111, v011, COLOR_REGION_EDGE);
        Gizmos.line(v011, v010, COLOR_REGION_EDGE);

        // 4 条竖直棱边
        Gizmos.line(v000, v010, COLOR_REGION_EDGE);
        Gizmos.line(v100, v110, COLOR_REGION_EDGE);
        Gizmos.line(v101, v111, COLOR_REGION_EDGE);
        Gizmos.line(v001, v011, COLOR_REGION_EDGE);

        // 半透明填充面（6 个面，正反双向）
        GizmoStyle faceStyle = GizmoStyle.fill(COLOR_REGION_FILL);
        // 底面
        Gizmos.rect(v000, v100, v101, v001, faceStyle);
        Gizmos.rect(v001, v101, v100, v000, faceStyle);
        // 顶面
        Gizmos.rect(v010, v110, v111, v011, faceStyle);
        Gizmos.rect(v011, v111, v110, v010, faceStyle);
        // 前面 (z = minZ)
        Gizmos.rect(v000, v100, v110, v010, faceStyle);
        Gizmos.rect(v010, v110, v100, v000, faceStyle);
        // 后面 (z = maxZ)
        Gizmos.rect(v001, v101, v111, v011, faceStyle);
        Gizmos.rect(v011, v111, v101, v001, faceStyle);
        // 左面 (x = minX)
        Gizmos.rect(v000, v001, v011, v010, faceStyle);
        Gizmos.rect(v010, v011, v001, v000, faceStyle);
        // 右面 (x = maxX)
        Gizmos.rect(v100, v101, v111, v110, faceStyle);
        Gizmos.rect(v110, v111, v101, v100, faceStyle);
    }
}
