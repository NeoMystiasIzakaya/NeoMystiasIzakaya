/*
 * Copyright 2026 NeoMystiasIzakaya Team
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package icu.gensoukyo.neo_mystias_izakaya.common.menu;

import icu.gensoukyo.neo_mystias_izakaya.client.util.NMIClientUtil;
import icu.gensoukyo.neo_mystias_izakaya.common.blockentity.AbstractKitchenwareBE;
import icu.gensoukyo.neo_mystias_izakaya.content.tag.consts.NMICuisinesTags;
import icu.gensoukyo.neo_mystias_izakaya.registry.NMIMenus;
import icu.gensoukyo.neo_mystias_izakaya.registry.NMIVanillaTags;
import icu.gensoukyo.neo_mystias_izakaya.registry.item.NMIMainItems;
import lombok.Getter;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.TagKey;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.neoforged.fml.loading.FMLEnvironment;

@Getter
public class KitchenwareMenu extends AbstractNMIMenu {
    private final ContainerLevelAccess access = ContainerLevelAccess.NULL;
    private final ContainerData data;
    private AbstractKitchenwareBE kitchenwareBE;

    public KitchenwareMenu(int containerId, Inventory inventory, FriendlyByteBuf buf) {
        this(containerId, inventory, buf.readBlockPos(), new SimpleContainerData(2));
    }

    public KitchenwareMenu(int containerId, Inventory inventory, BlockPos blockPos, ContainerData data) {
        super(NMIMenus.KITCHENWARE_MENU.get(), containerId);
        this.invStart = 6;
        this.data = data;
        this.addDataSlots(data);
        BlockEntity blockEntity = inventory.player.level().getBlockEntity(blockPos);
        if (blockEntity instanceof AbstractKitchenwareBE kitchenware) {
            this.kitchenwareBE = kitchenware;
            addItems(kitchenwareBE);
            addPlayerInventory(inventory, 36);
        }
    }

    protected void addItems(Container items) {
        for (int i = 0; i < 5; ++i) {
            addSlot(new Slot(items, i, 17 + i * 25, 110) {
                @Override
                public int getMaxStackSize(ItemStack pStack) {
                    return 1;
                }

                @Override
                public boolean mayPlace(ItemStack pStack) {
                    return pStack.tags().anyMatch(itemTagKey -> itemTagKey.equals(NMIVanillaTags.INGREDIENT))
                            && !kitchenwareBE.getBlockState().getValue(BlockStateProperties.LIT);
                }

                @Override
                public void setChanged() {
                    super.setChanged();
                    if (FMLEnvironment.getDist().isClient()) {
                        NMIClientUtil.updateKitchenwareScreen();
                    }
                }
            });
        }

        addSlot(new Slot(items, 5, 180, 110) {
            @Override
            public boolean mayPlace(ItemStack pStack) {
                return false;
            }

            @Override
            public void setChanged() {
                this.container.setChanged();
            }
        });
    }

    @Override
    public boolean stillValid(Player player) {
        return access.evaluate((level, pos) -> player.isWithinBlockInteractionRange(pos, 4.0F), true);
    }

}
