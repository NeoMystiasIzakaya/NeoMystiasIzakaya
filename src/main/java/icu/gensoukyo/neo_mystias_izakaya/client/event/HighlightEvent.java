package icu.gensoukyo.neo_mystias_izakaya.client.event;

import icu.gensoukyo.neo_mystias_izakaya.registry.NMIDataComponentTypes;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.gizmos.GizmoStyle;
import net.minecraft.gizmos.Gizmos;
import net.minecraft.world.item.ItemStack;
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

    @SubscribeEvent
    public static void highlightBlock(ExtractLevelRenderStateEvent event) {
        Minecraft mc = Minecraft.getInstance();
        if (!(mc.player instanceof LocalPlayer localPlayer)) return;

        ItemStack mainHandItem = localPlayer.getMainHandItem();
        BlockPos controllerPos = mainHandItem.get(NMIDataComponentTypes.BOUND_CONTROLLER);
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
}
