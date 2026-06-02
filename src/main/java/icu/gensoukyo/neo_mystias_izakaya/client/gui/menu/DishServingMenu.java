package icu.gensoukyo.neo_mystias_izakaya.client.gui.menu;

import icu.gensoukyo.neo_mystias_izakaya.client.gui.widget.BeverageSlot;
import icu.gensoukyo.neo_mystias_izakaya.client.gui.widget.CuisineSlot;
import icu.gensoukyo.neo_mystias_izakaya.common.blockentity.DiningTableBlockEntity;
import icu.gensoukyo.neo_mystias_izakaya.registry.NMIMenus;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.jspecify.annotations.NonNull;

import java.util.List;

public class DishServingMenu extends AbstractNMIMenu {
    public DishServingMenu(int containerId, Inventory inventory, FriendlyByteBuf buf) {
        this(containerId, inventory, buf.readList(BlockPos.STREAM_CODEC));
    }

    public DishServingMenu(int containerId, Inventory inventory, List<BlockPos> diningTables) {
        super(NMIMenus.DISH_SERVING_MENU.get(), containerId);
        Level level = inventory.player.level();
        for (int i = 0; i < diningTables.size(); i++) {
            BlockPos blockPos = diningTables.get(i);
            if (level.isLoaded(blockPos)) {
                if (level.getBlockEntity(blockPos) instanceof DiningTableBlockEntity tableBlockEntity) {
                    addSlot(new CuisineSlot(tableBlockEntity, 0, 0, i * 18));
                    addSlot(new BeverageSlot(tableBlockEntity, 1, 18, i * 18));
                    invStart += 2;
                }
            }
        }
        addPlayerInventory(inventory);
    }

    @Override
    public boolean stillValid(@NonNull Player player) {
        return true;
    }
}
