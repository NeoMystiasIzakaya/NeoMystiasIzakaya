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
    @Override
    public void render(@NonNull GuiGraphicsExtractor guiGraphics, @NonNull DeltaTracker deltaTracker) {
        LocalPlayer player = Minecraft.getInstance().player;
        if (player == null) {
            return;
        }

        ItemStack itemBySlot = player.getItemBySlot(EquipmentSlot.HEAD);

        // 高亮厨房用具
        List<BlockPos> kitchenware = itemBySlot.get(NMIDataComponentTypes.BOUND_KITCHENWARE);
        if (kitchenware != null) {
            kitchenware.forEach(pos -> {

            });
        }

        // 高亮餐桌
        List<BlockPos> diningTables = itemBySlot.get(NMIDataComponentTypes.BOUND_DINING_TABLES);
        if (diningTables != null) {
            diningTables.forEach(pos -> {

            });
        }
    }
}
