package icu.gensoukyo.neo_mystias_izakaya.client.gui.menu;

import icu.gensoukyo.neo_mystias_izakaya.client.gui.widget.BeverageSlot;
import icu.gensoukyo.neo_mystias_izakaya.client.gui.widget.CuisineSlot;
import icu.gensoukyo.neo_mystias_izakaya.common.blockentity.DiningTableBlockEntity;
import icu.gensoukyo.neo_mystias_izakaya.registry.NMIMenus;
import lombok.Getter;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

import java.util.List;

@Getter
public class DishServingMenu extends AbstractNMIMenu {
    // === 网格布局常量 ===
    /** 每个餐桌单元格宽度 */
    public static final int CELL_WIDTH = 115;
    /** 同行两个单元格之间的间距 */
    public static final int CELL_SPACING = 14;
    /** 单元格内菜品槽与饮品槽的间距 */
    public static final int SLOT_SPACING = 20;
    /** 槽位在单元格内的 X 偏移 */
    public static final int SLOT_X_OFFSET = 4;
    /** 第一行单元格的起始 Y */
    public static final int START_Y = 18;
    /** 行间距 */
    public static final int ROW_HEIGHT = 28;
    /** GUI 图像宽度 */
    public static final int IMAGE_WIDTH = 256;

    /** 两个单元格的总宽度 */
    public static final int TWO_CELLS_WIDTH = CELL_WIDTH * 2 + CELL_SPACING;
    /** 正常双列布局的起始 X（左单元格） */
    public static final int START_X = (IMAGE_WIDTH - TWO_CELLS_WIDTH) / 2;
    /** 奇数最后一行居中时的单元格 X */
    public static final int CENTERED_X = (IMAGE_WIDTH - CELL_WIDTH) / 2;

    private final int tableCount;
    private final boolean lastRowSingle;

    public DishServingMenu(int containerId, Inventory inventory, FriendlyByteBuf buf) {
        this(containerId, inventory, buf.readList(BlockPos.STREAM_CODEC));
    }

    public DishServingMenu(int containerId, Inventory inventory, List<BlockPos> diningTables) {
        super(NMIMenus.DISH_SERVING_MENU.get(), containerId);
        Level level = inventory.player.level();
        this.tableCount = diningTables.size();
        this.lastRowSingle = tableCount % 2 == 1;

        for (int i = 0; i < tableCount; i++) {
            int row = i / 2;
            int col = i % 2;

            // 计算单元格左上角 X
            int cellX;
            if (lastRowSingle && i == tableCount - 1) {
                cellX = CENTERED_X;
            } else {
                cellX = START_X + col * (CELL_WIDTH + CELL_SPACING);
            }
            int cellY = START_Y + row * ROW_HEIGHT;

            BlockPos blockPos = diningTables.get(i);
            if (level.isLoaded(blockPos)) {
                if (level.getBlockEntity(blockPos) instanceof DiningTableBlockEntity tableBlockEntity) {
                    // 槽位在单元格内的位置：垂直居中（单元格高 24，槽位 18x18，上下各留 3）
                    int slotY = cellY + 3;
                    addSlot(new CuisineSlot(tableBlockEntity, 0, cellX + SLOT_X_OFFSET, slotY));
                    addSlot(new BeverageSlot(tableBlockEntity, 1, cellX + SLOT_X_OFFSET + SLOT_SPACING, slotY));
                    invStart += 2;
                }
            }
        }
        addPlayerInventory(inventory, 49);
    }

    /**
     * 获取指定餐桌索引的单元格左上角 X 坐标（相对 GUI 坐标）
     */
    public static int getCellX(int tableIndex, boolean lastRowSingle, int tableCount) {
        if (lastRowSingle && tableIndex == tableCount - 1) {
            return CENTERED_X;
        }
        int col = tableIndex % 2;
        return START_X + col * (CELL_WIDTH + CELL_SPACING);
    }

    /**
     * 获取指定餐桌索引的单元格左上角 Y 坐标（相对 GUI 坐标）
     */
    public static int getCellY(int tableIndex) {
        int row = tableIndex / 2;
        return START_Y + row * ROW_HEIGHT;
    }

    @Override
    public boolean stillValid(Player player) {
        return true;
    }
}
