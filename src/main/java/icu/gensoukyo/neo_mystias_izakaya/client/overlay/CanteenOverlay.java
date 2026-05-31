package icu.gensoukyo.neo_mystias_izakaya.client.overlay;

import icu.gensoukyo.neo_mystias_izakaya.registry.NMIDataComponentTypes;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.client.gui.GuiLayer;
import org.jspecify.annotations.NonNull;

import java.util.List;

public class CanteenOverlay implements GuiLayer {

    /** 每个物品矩形宽度 */
    private static final int ITEM_RECT_WIDTH = 60;
    /** 每个物品矩形高度 */
    private static final int ITEM_RECT_HEIGHT = 24;
    /** 物品矩形之间的间距 */
    private static final int ITEM_RECT_SPACING = 4;
    /** 矩形填充颜色 (半透明灰) */
    private static final int FILL_COLOR = 0x40FFFFFF;
    /** 矩形边框颜色 (白色) */
    private static final int BORDER_COLOR = 0xFFFFFFFF;

    @Override
    public void render(@NonNull GuiGraphicsExtractor guiGraphics, @NonNull DeltaTracker deltaTracker) {
        LocalPlayer player = Minecraft.getInstance().player;
        if (player == null) {
            return;
        }

        ItemStack itemBySlot = player.getItemBySlot(EquipmentSlot.HEAD);

        // 获取屏幕尺寸
        var window = Minecraft.getInstance().getWindow();
        int screenWidth = window.getGuiScaledWidth();
        int screenHeight = window.getGuiScaledHeight();
        int centerY = screenHeight / 2;

        // 厨房用具列表
        List<BlockPos> kitchenware = itemBySlot.get(NMIDataComponentTypes.BOUND_KITCHENWARE);
        // 餐桌列表
        List<BlockPos> diningTables = itemBySlot.get(NMIDataComponentTypes.BOUND_DINING_TABLES);

        // --- 左侧：厨房用具矩形组 ---
        if (kitchenware != null && !kitchenware.isEmpty()) {
            drawCenteredRectGroup(guiGraphics, 0, ITEM_RECT_WIDTH, centerY, kitchenware.size());
        }

        // --- 右侧：餐桌矩形组 ---
        if (diningTables != null && !diningTables.isEmpty()) {
            int rightX0 = screenWidth - ITEM_RECT_WIDTH;
            drawCenteredRectGroup(guiGraphics, rightX0, screenWidth, centerY, diningTables.size());
        }
    }

    /**
     * 以垂直中线为对称轴，绘制 N 个上下对称排列的矩形
     *
     * @param guiGraphics 图形上下文
     * @param x0          矩形左边界 X
     * @param x1          矩形右边界 X
     * @param centerY     屏幕垂直中心
     * @param count       矩形数量
     */
    private void drawCenteredRectGroup(GuiGraphicsExtractor guiGraphics, int x0, int x1, int centerY, int count) {
        // 计算整组的总高度
        int totalHeight = count * ITEM_RECT_HEIGHT + (count - 1) * ITEM_RECT_SPACING;
        // 起始 Y：使整组以 centerY 为中心
        int startY = centerY - totalHeight / 2;

        for (int i = 0; i < count; i++) {
            int y0 = startY + i * (ITEM_RECT_HEIGHT + ITEM_RECT_SPACING);
            int y1 = y0 + ITEM_RECT_HEIGHT;
            drawItemRect(guiGraphics, x0, y0, x1, y1);
        }
    }

    /**
     * 绘制单个物品矩形（填充 + 边框）
     */
    private void drawItemRect(GuiGraphicsExtractor guiGraphics, int x0, int y0, int x1, int y1) {
        // 半透明填充
        guiGraphics.fill(x0 + 1, y0 + 1, x1 - 1, y1 - 1, FILL_COLOR);
        // 上边框
        guiGraphics.fill(x0, y0, x1, y0 + 1, BORDER_COLOR);
        // 下边框
        guiGraphics.fill(x0, y1 - 1, x1, y1, BORDER_COLOR);
        // 左边框
        guiGraphics.fill(x0, y0, x0 + 1, y1, BORDER_COLOR);
        // 右边框
        guiGraphics.fill(x1 - 1, y0, x1, y1, BORDER_COLOR);
    }
}
