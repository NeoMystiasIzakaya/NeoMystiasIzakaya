package icu.gensoukyo.neo_mystias_izakaya.client.gui;

import icu.gensoukyo.neo_mystias_izakaya.client.util.ClientUtil;
import icu.gensoukyo.neo_mystias_izakaya.common.blockentity.AbstractKitchenwareBE;
import icu.gensoukyo.neo_mystias_izakaya.registry.NMIMenus;
import icu.gensoukyo.neo_mystias_izakaya.registry.NMIVanillaTags;
import lombok.Getter;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.tags.TagKey;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.fml.loading.FMLEnvironment;

@Getter
public class KitchenwareMenu extends AbstractContainerMenu {
    private final ContainerLevelAccess access = ContainerLevelAccess.NULL;
    private AbstractKitchenwareBE kitchenwareBE;

    protected final int INV_START = 6;
    protected static final int INV_SIZE = 36;

    public KitchenwareMenu(int containerId, Inventory inventory, FriendlyByteBuf buf) {
        this(containerId, inventory, buf.readBlockPos());
    }

    public KitchenwareMenu(int containerId, Inventory inventory, BlockPos blockPos) {
        super(NMIMenus.KITCHENWARE_MENU.get(), containerId);
        BlockEntity blockEntity = inventory.player.level().getBlockEntity(blockPos);
        if (blockEntity instanceof AbstractKitchenwareBE kitchenware) {
            this.kitchenwareBE = kitchenware;
            addItems(kitchenwareBE, this.kitchenwareBE);
            addPlayerInventory(inventory);
        }
    }

    protected void addItems(Container items, AbstractKitchenwareBE cookerTE) {
        for (int i = 0; i < 5; ++i) {
            addSlot(new Slot(items, i, 17 + i * 25, 110) {
                @Override
                public int getMaxStackSize(ItemStack pStack) {
                    return 1;
                }

                @Override
                public boolean mayPlace(ItemStack pStack) {
                    return pStack.tags().anyMatch(itemTagKey -> itemTagKey.equals(NMIVanillaTags.INGREDIENT));
                }

                @Override
                public void setChanged() {
                    super.setChanged();
                    if (FMLEnvironment.getDist().isClient()) {
                        ClientUtil.updateKitchenwareScreen();
                    }
                }
            });
        }
    }

    protected void addPlayerInventory(Inventory inv) {
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 9; j++)
                addSlot(new Slot(inv, j + i * 9 + 9, 36 + j * 18, 137 + i * 18));
        for (int i = 0; i < 9; i++)
            addSlot(new Slot(inv, i, 36 + i * 18, 195));
    }

    @Override
    public ItemStack quickMoveStack(Player playerIn, int index) {
        ItemStack itemStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);

        if (slot.hasItem()) {
            ItemStack slotStack = slot.getItem();
            itemStack = slotStack.copy();
            if (index < INV_START) {
                if (!this.moveItemStackTo(slotStack, INV_START, INV_SIZE + INV_START, true)) {
                    return ItemStack.EMPTY;
                }
                slot.onQuickCraft(slotStack, itemStack);
            } else {
                if (index < INV_SIZE + INV_START && !this.moveItemStackTo(slotStack, 0, INV_START, false))
                    return ItemStack.EMPTY;
            }
            if (slotStack.isEmpty()) {
                slot.set(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }
            if (slotStack.getCount() == itemStack.getCount()) {
                return ItemStack.EMPTY;
            }
            slot.onTake(playerIn, slotStack);
        }
        return itemStack;
    }

    @Override
    public boolean stillValid(Player player) {
        return access.evaluate((level, pos) -> player.isWithinBlockInteractionRange(pos, 4.0F), true);
    }

    public enum KitchenwareType {
        BOILING_POT(NMIVanillaTags.BOILING_POT),
        CUTTING_BOARD(NMIVanillaTags.CUTTING_BOARD),
        FRYING_PAN(NMIVanillaTags.FRYING_PAN),
        GRILL(NMIVanillaTags.GRILL),
        STEAMER(NMIVanillaTags.STEAMER);

        public final TagKey<Block> KITCHENWARE_TYPE;

        KitchenwareType(TagKey<Block> tag) {
            this.KITCHENWARE_TYPE = tag;
        }
    }
}
